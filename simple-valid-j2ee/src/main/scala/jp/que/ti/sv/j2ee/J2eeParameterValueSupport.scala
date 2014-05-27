package jp.que.ti.sv.j2ee

import javax.servlet.http.HttpServletRequest
import jp.que.ti.sv.ParameterInfo
import jp.que.ti.sv.MessageBox

trait J2eeParameterValueSupport {
  def request: HttpServletRequest

  protected def paramValue(prmInfo: ParameterInfo): Option[String] =
    Option(request.getParameter(prmInfo.parameter))

}