package jp.que.ti.sv.validator

import jp.que.ti.sv.ValidOneParam
import jp.que.ti.sv.ValidatorBaseWithMessageProp
import javax.servlet.http.HttpServletRequest
import jp.que.ti.sv.J2eeApiSupport

/**
 * 入力があった場合チェックするValidator の基礎クラス。
 * 入力がなかった場合は、チェックしない。(入力ない場合は、 isValid の戻り値はtrueにする)
 * サブクラスは、isValidOnInputed を実装すること。
 */
abstract class ValidOnInputedValueBase(
  paramName: String //
  , messageKey: String //
  , paramNameForMessage: Option[String] = None //
  ) extends ValidatorBaseWithMessageProp(
  paramName //
  , messageKey //
  , paramNameForMessage //
  ) with ValidOneParam {

  /**
   * Function for validation
   */
  override def isValid(param: Option[String]) = {
    param match {
      case None => true
      case Some(value) => {
        isValidOnInputed(value)
      }
    }
  }

  /**
   * Function for validation
   */
  def isValidOnInputed(param: String): Boolean

}
