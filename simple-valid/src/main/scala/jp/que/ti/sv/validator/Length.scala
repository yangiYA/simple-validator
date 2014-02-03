package jp.que.ti.sv.validator

import jp.que.ti.sv.ValidOneParam
import jp.que.ti.sv.ValidatorBaseWithMessageProp
import javax.servlet.http.HttpServletRequest
import jp.que.ti.sv.J2eeApiSupport

abstract class Length(
  paramName: String //
  , val length: Int //
  , paramNameForMessage: Option[String] = None //
  ) extends ValidOnInputedValueBase(

  paramName //
  , "length" //
  , paramNameForMessage //
  ) with ValidOneParam {

  /**
   * メッセージは、引数2つを使って表示する
   */
  override def errorMessage = message(messageKey, paramName4Message, length.toString)

  /**
   * Function for validation
   */
  override def isValidOnInputed(param: String) =
    length == param.trim().length()
}

class Length4J2ee(
  paramName: String, min: Int //
  , val request: HttpServletRequest //
  , paramNameForMessage: Option[String] = None //
  ) extends Length(
  paramName //
  , min //
  , paramNameForMessage //
  ) with J2eeApiSupport
