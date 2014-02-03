package jp.que.ti.sv.validator

import javax.servlet.http.HttpServletRequest
import jp.que.ti.sv._
import jp.que.ti.sv.ValidOneParam
import jp.que.ti.sv.ValidatorBaseWithMessageProp
import jp.que.ti.sv.J2eeApiSupport
import org.slf4j.LoggerFactory

abstract class Required(
  paramName: String, paramNameForMessage: Option[String] = None) extends ValidatorBaseWithMessageProp(
  paramName //
  , "required" //
  , paramNameForMessage //
  ) with ValidOneParam {

  /**
   * Function for validation
   */
  override def isValid(param: Option[String]) = {
    param match {
      case None        => false // 空の場合エラー
      case Some(value) => !(value.trim().isEmpty()) // 空の場合エラー
    }
  }
}

class Required4J2ee(
  paramName: String //
  , val request: HttpServletRequest //
  , paramNameForMessage: Option[String] = None //
  ) extends Required(paramName, paramNameForMessage) with J2eeApiSupport

  //TODO 不要？
//object Required4J2ee {
//  def required(request: HttpServletRequest): (String => Required) = {
//    val rtn = (param: String) => new Required4J2ee(param, request)
//    rtn
//  }
//}