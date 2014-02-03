package jp.que.ti.sv

import java.util.Properties
import java.io.BufferedInputStream
import java.io.FileInputStream
import org.slf4j.helpers.MessageFormatter
import scala.collection.JavaConverters._
import org.slf4j.LoggerFactory
object MessageResource {
  private val log = LoggerFactory.getLogger(getClass())

  private val defaultMessagesPath = "messages_default.prop"
  private var messagesPath__ = "messages.prop"
  private var propMap_ : Map[String, String] = Map()

  {
    val (pMp, pt) = useNotEmptyProperties(messagesPath__, defaultMessagesPath)
    if (!pMp.isEmpty) {
      propMap_ = pMp
      messagesPath__ = pt
    }
    log.info("""|* "Loaded Properties File" : messagePath={} , propMap={}""", messagesPath__, propMap)
  }

  /**
   * プロパティーファイルのパスのための、setterメソッド
   */
  def messagesPath_=(propPath: String): Unit = { messagesPath__ = propPath }

  def propMap: Map[String, String] = propMap_

  /**
   * 引数 key に該当するメッセージを返却します
   * @param key メッセージを取得するためのキー
   * @return メッセージ文字列
   */
  def message(key: String): String = {
    val rtnMessage = propMap.getOrElse(key, " ")
    log.debug("""|* message key=<{}> , value=<{}>""", key, rtnMessage)
    rtnMessage
  }

  def message(key: String, arg1: => String): String =
    MessageFormatter.format(message(key), arg1).getMessage()

  def message(key: String, arg1: => String, arg2: => String, args: String*): String = {
    if (args.length == 0) {
      MessageFormatter.format(message(key), arg1, arg2).getMessage()
    } else {
      MessageFormatter.arrayFormat(message(key), (arg1 :: arg2 :: args.toList).toArray).getMessage()
    }
  }

  /**
   * 取得できた最初の path と、その path で取得できるプロパティーの Map[String, String]を返却します
   * @return (プロパティーの Map , プロパティーのパス)
   */
  private def useNotEmptyProperties(path: String*): (Map[String, String], String) = {
    var mp: Map[String, String] = Map()
    var pth = ""
    path.foreach {
      p =>
        if (mp.isEmpty) {
          pth = p
          mp = Utils.propertiesMapFromClasspath(pth)
        }
    }
    (mp, pth)
  }

}