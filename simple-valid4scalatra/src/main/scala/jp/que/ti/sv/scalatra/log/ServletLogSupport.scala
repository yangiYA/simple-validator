package jp.que.ti.sv.scalatra.log

import org.scalatra.ScalatraServlet
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import org.xml.sax.ErrorHandler
import org.slf4j.LoggerFactory

abstract class ServletLogSupport extends ScalatraServlet {

  private val log = LoggerFactory.getLogger(getClass());

  errorHandler = {
    case th: RuntimeException => {
      log.error(
        "システムエラー発生 (RuntimeException) Path=:" + Option(request).fold("")(_.getRequestURI) +
          "\n  ErrorMessage=" + th.getMessage, th)
      status = HttpServletResponse.SC_BAD_REQUEST
      "Exception occured!"
    }

    case th: Throwable => {
      log.error(
        "システムエラー発生 (RuntimeException以外) :\n  ErrorMessage=" + th.getMessage, th)
      throw th
    }
  }

  override def handle(req: HttpServletRequest, res: HttpServletResponse): Unit = {
    val path = req.getRequestURI
    val startMillis = System.currentTimeMillis()
    log.info("""[AccessLog] Path={}""", path)
    log.error("""[*** request ***]{}""", req.toMap)

    try {
      super.handle(req, res)
    } finally {
      val elapsedMillis = System.currentTimeMillis() - startMillis
      log.debug("""[EndLog]elapsedMillis={} ,Path={}""", elapsedMillis, path)
    }
  }
}