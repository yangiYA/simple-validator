package jp.que.ti.sv.validator

import jp.que.ti.sv.ValidOneParam
import jp.que.ti.sv.ValidatorBaseWithMessageProp
import javax.servlet.http.HttpServletRequest
import jp.que.ti.sv.J2eeApiSupport

abstract class Minlength(
  paramName: String //
  , val min: Int //
  , paramNameForMessage: Option[String] = None //
  ) extends ValidOnInputedValueBase(
  paramName //
  , "minlength" //
  , paramNameForMessage //
  ) with ValidOneParam {

  /**
   * メッセージは、引数2つを使って表示する
   */
  override def errorMessage = message(messageKey, paramName4Message, min.toString)

  /**
   * Function for validation
   */
  override def isValidOnInputed(param: String) = min <= param.trim().length()
}

class Minlength4J2ee(
  paramName: String //
  , min: Int //
  , val request: HttpServletRequest //
  , paramNameForMessage: Option[String] = None //
  ) extends Minlength(
  paramName //
  , min //
  , paramNameForMessage //
  ) with J2eeApiSupport
