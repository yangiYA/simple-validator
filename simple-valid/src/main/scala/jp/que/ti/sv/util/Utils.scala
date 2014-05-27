package jp.que.ti.sv.util

import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.util.Properties

import scala.collection.JavaConverters._
import scala.util.control.Exception._

import org.slf4j.LoggerFactory

object Utils {
  private val log = LoggerFactory.getLogger(getClass());

  def inputStreamFromClasspath(propertiesName: String): Option[InputStream] = {
    var istr: Option[InputStream] = allCatchOption { getClass().getClassLoader().getResourceAsStream(propertiesName) }

    istr.orElse(allCatchOption {
      ClassLoader.getSystemClassLoader().getResourceAsStream(propertiesName)
    })
  }

  def readerFromClasspath(propertiesName: String): Option[Reader] =
    inputStreamFromClasspath(propertiesName) map
      (new InputStreamReader(_, "utf-8"))

  def propertiesMapFromClasspath(propertiesName: String): Map[String, String] = {
    val pr: Option[Properties] = readerFromClasspath(propertiesName) map { (rdr) =>
      val prp = new Properties()
      prp.load(rdr)
      prp
    }

    val propMap: Map[String, String] = pr match {
      case None    => Map()
      case Some(p) => p.asScala.toMap
    }

    log.debug("""|* properties File loaded. filePath={} : propMap={}""", propertiesName, propMap)
    propMap
  }

  /**
   * ブロックを抜けると自動的に、引数 resource をクローズする関数。
   * ローンパターン。
   *
   * <ol><li style="font-weight:700;">
   * 利用例<br/>
   * <pre>
   * using(Source.fromFile("foofoo.txt") ){ resource =>
   *   resource.getLines.mkString }
   * </pre>
   * </li></ol>
   *
   * @param resource 使い終わったらクローズしたいリソース
   * @param block 引数 resource を使用する処理ブロック
   */
  def using[A <: { def close() }, B](resource: A)(block: A => B): B = {
    try {
      block(resource) //処理ブロック
    } catch {
      case e: Throwable => {
        if (log.isDebugEnabled()) log.debug(
          e.getClass.getName() + " occured !! :: resource=" + resource, e);
        throw e
      }
    } finally {
      try {
        if (resource != null) resource.close()
      } catch {
        case e: Throwable => {
          if (log.isWarnEnabled()) log.warn(
            e.getClass.getName() + ".It cannot be closed!! :: resource=" + resource, e);
        }
      }
    }
  }

  /**
   * 例外発生時、None を返却するため、関数 func の実行結果を Option に包んで返却するメソッド
   * [scala.util.control.Exception.allCatch.opt]は、nullが返却された場合、
   * Some(null)が返却されて困るため、対策したメソッド
   */
  def allCatchOption[T](func: => T)(implicit onError: Throwable => Option[T] = { t: Throwable => None }): Option[T] = {
    try {
      Option(func)
    } catch {
      case c: Throwable => onError(c)
    }
  }


  //; hankaku

  //  import scala.collection.JavaConverters._
  //  def hoge(request: HttpServletRequest): String = {
  //    val paramMap = request.getParameterMap()
  //    val st = paramMap.entrySet()
  //    (st.asScala.seq).foldLeft(Nil: List[(String, List[String])]) { (acc, mEntry) =>
  //      (mEntry.getKey() -> mEntry.getValue().toList) :: acc
  //    }
  //
  //    ""
  //  }
}