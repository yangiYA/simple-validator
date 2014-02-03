package jp.que.ti.sv.validator

import javax.servlet.http.HttpServletRequest
import jp.que.ti.sv._
import jp.que.ti.sv.ValidOneParam
import jp.que.ti.sv.ValidatorBaseWithMessageProp
import jp.que.ti.sv.J2eeApiSupport
import org.slf4j.LoggerFactory
import scala.util.matching.Regex

abstract class RegexValidator(
  paramName: String //
  , val regex: Regex //
  , messageKey: String //
  , paramNameForMessage: Option[String] = None //
  ) extends ValidOnInputedValueBase(

  paramName //
  , messageKey //
  , paramNameForMessage //
  ) with ValidOneParam {

  /**
   * メッセージは、引数2つを使って表示する
   */
  override def errorMessage = message(messageKey, paramName4Message)

  /**
   * Function for validation
   */
  override def isValidOnInputed(param: String) = param match {
    case regex() => true
    case _       => false
  }

}

class RegexValidator4J2ee(
  paramName: String //
  , regex: Regex //
  , messageKey: String //
  , val request: HttpServletRequest //
  , paramNameForMessage: Option[String] = None //
  ) extends RegexValidator(paramName, regex, messageKey, paramNameForMessage) with J2eeApiSupport

