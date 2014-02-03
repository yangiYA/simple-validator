package jp.que.ti.sv.validator

import jp.que.ti.sv.ValidOneParam
import jp.que.ti.sv.ValidatorBaseWithMessageProp
import javax.servlet.http.HttpServletRequest
import jp.que.ti.sv.J2eeApiSupport

abstract class Maxlength(
  paramName: String //
  , val max: Int //
  , paramNameForMessage: Option[String] = None //
  ) extends ValidOnInputedValueBase(

  paramName //
  , "maxlength" //
  , paramNameForMessage //
  ) with ValidOneParam {

  /**
   * メッセージは、引数2つを使って表示する
   */
  override def errorMessage = message(messageKey, paramName4Message, max.toString)

  /**
   * Function for validation
   */
  override def isValidOnInputed(param: String) = {
    param.trim().length() <= max
  }

}

class Maxlength4J2ee(
  paramName: String //
  , max: Int //
  , val request: HttpServletRequest //
  , paramNameForMessage: Option[String] = None //
  ) extends Maxlength(
  paramName, max //
  , paramNameForMessage //
  ) with J2eeApiSupport
