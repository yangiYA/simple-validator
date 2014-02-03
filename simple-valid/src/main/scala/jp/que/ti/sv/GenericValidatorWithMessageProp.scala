package jp.que.ti.sv

import javax.servlet.http.HttpServletRequest

abstract class GenericValidatorWithMessageProp(
  paramName: String //
  , messageKey: String //
  , paramNameForMessage: Option[String] = None //
  ) extends ValidatorBaseWithMessageProp(paramName, messageKey, paramNameForMessage) {
}

abstract class GenericValidatorOneParamWithMessageProp(
  paramName: String //
  , messageKey: String //
  , checkLogic: Option[String] => Boolean //
  , paramNameForMessage: Option[String] = None //
  ) extends ValidatorBaseWithMessageProp(paramName, messageKey, paramNameForMessage) with ValidOneParam {

  override def isValid(param: Option[String]) = checkLogic(param)

}

abstract class GenericValidatorMultiParamWithMessageProp(
  paramName: String //
  , messageKey: String //
  , val checkLogic: Seq[String] => Boolean //
  , paramNameForMessage: Option[String] = None //
  ) extends ValidatorBaseWithMessageProp(paramName, messageKey, paramNameForMessage) with ValidMultiParams {

  override def isValid(params: Seq[String]) = checkLogic(params)
}

class GenericValidatorOneParamWithMessageProp4J2ee(
  paramName: String //
  , messageKey: String //
  , checkLogic: Option[String] => Boolean //
  , val request: HttpServletRequest //
  , paramNameForMessage: Option[String] = None //
  ) extends GenericValidatorOneParamWithMessageProp(paramName, messageKey, checkLogic, paramNameForMessage) with J2eeApiSupport {
}

class GenericValidatorMultiParamWithMessageProp4J2ee(
  paramName: String //
  , messageKey: String //
  , checkLogic: Seq[String] => Boolean //
  , val request: HttpServletRequest //
  , paramNameForMessage: Option[String] = None //
  ) extends GenericValidatorMultiParamWithMessageProp(paramName, messageKey, checkLogic, paramNameForMessage) with J2eeApiSupport {
}