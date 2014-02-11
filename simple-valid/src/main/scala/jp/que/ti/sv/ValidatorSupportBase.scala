package jp.que.ti.sv

import javax.servlet.http.HttpServletRequest
import jp.que.ti.sv.validator.AndValidator
import jp.que.ti.sv.validator.Required4J2ee
import jp.que.ti.sv.validator.Minlength4J2ee
import jp.que.ti.sv.validator.Maxlength4J2ee
import jp.que.ti.sv.validator.AndStopOnErrorValidator
import jp.que.ti.sv.validator.Length4J2ee
import jp.que.ti.sv.validator.AndStopOnErrorValidator
import jp.que.ti.sv.validator.Minlength4J2ee
import jp.que.ti.sv.validator.RegexValidator4J2ee
import jp.que.ti.sv.validator.RegexValidator4J2ee
import scala.util.matching.Regex
import scala.util.matching.Regex

trait ValidatorSupportBase extends RequestSupport //
  with And //
  with RegexValid //
  with Required //
  with RequiredAnd //
  with AndStopOnError //
  with Length //
  with MaxLength //
  with MinLength //
  with MinMaxLength //
  with GenericValid //
  {

  /**
   * パラメータが複数リクエストされた場合の汎用チェックValidator
   */
  def validatorMultiParam(paramName: String, messageKey: String //
  , checkLogic: Seq[String] => Boolean //
  ): GenericValidatorMultiParamWithMessageProp =
    new GenericValidatorMultiParamWithMessageProp4J2ee(
      paramName, messageKey, checkLogic, request)

}

protected trait RequestSupport {
  def request: HttpServletRequest
}

protected trait And extends RequestSupport {
  def and(validators: ValidatorIF*) = AndValidator(validators: _*)
  def and(validators: List[ValidatorIF]) = AndValidator(validators)
  //  def and(paramName: String //
  //  , validators: (String) => ValidatorBase*) =
  //    { AndValidator(paramName, validators.toList) }
  def and(paramName: String, paramName4Message: Option[String] //
  , validators: Function2[String, Option[String], ValidatorBase]*) = { AndValidator(paramName, paramName4Message, validators.toList) }
}

protected trait AndStopOnError extends RequestSupport {
  def andStopOnError(validators: ValidatorIF*) = AndStopOnErrorValidator(validators: _*)
  def andStopOnError(validators: List[ValidatorIF]) = AndStopOnErrorValidator(validators)
  //TODO この下不要にする？
  def andStopOnError(paramName: String //
  , validators: (String) => ValidatorBase*) = { AndStopOnErrorValidator(paramName, validators.toList) }
  def andStopOnError(paramName: String, paramName4Message: Option[String] //
  , validators: Function2[String, Option[String], ValidatorBase]*) = { AndStopOnErrorValidator(paramName, paramName4Message, validators.toList) }

}

protected trait GenericValid extends RequestSupport {
  //*********
  def genericValid(paramName: String, messageKey: String, checkLogic: Option[String] => Boolean) =
    new GenericValidatorOneParamWithMessageProp4J2ee(paramName, messageKey, checkLogic, request)
  def genericValid(paramName: String, messageKey: String, checkLogic: Option[String] => Boolean, paramName4Message: String) =
    new GenericValidatorOneParamWithMessageProp4J2ee(paramName, messageKey, checkLogic, request, Option(paramName4Message))
  //  def genericValid(messageKey: String, checkLogic: Option[String] => Boolean, paramName4Message: Option[String]) = {
  //    (paramName: String) =>
  //      new GenericValidatorOneParamWithMessageProp4J2ee(paramName, messageKey, checkLogic, request, paramName4Message)
  //  }
  def genericValid(messageKey: String, checkLogic: Option[String] => Boolean) = {
    (paramName: String, paramName4Message: Option[String]) =>
      new GenericValidatorOneParamWithMessageProp4J2ee(paramName, messageKey, checkLogic, request, paramName4Message)
  }

}

//genericValid
//  with MinMaxLength //

protected trait Length extends RequestSupport {
  def length(paramName: String, length: Int) = new Length4J2ee(paramName, length, request)
  def length(paramName: String, length: Int, paramName4Message: String) =
    new Length4J2ee(paramName, length, request, Option(paramName4Message))
  //  def length(length: Int, paramName4Message: Option[String]) = {
  //    (paramName: String) =>
  //      new Length4J2ee(paramName, length, request, paramName4Message)
  //  }
  def length(length: Int) = {
    (paramName: String, paramName4Message: Option[String]) =>
      new Length4J2ee(paramName, length, request, paramName4Message)
  }
}

protected trait MaxLength extends RequestSupport {
  def maxlength(paramName: String, max: Int) = new Maxlength4J2ee(paramName, max, request)
  def maxlength(paramName: String, max: Int, paramName4Message: String) =
    new Maxlength4J2ee(paramName, max, request, Option(paramName4Message))
  //  def maxlength(max: Int, paramName4Message: Option[String]) = {
  //    (paramName: String) =>
  //      new Maxlength4J2ee(paramName, max, request, paramName4Message)
  //  }
  def maxlength(max: Int) = {
    (paramName: String, paramName4Message: Option[String]) =>
      new Maxlength4J2ee(paramName, max, request, paramName4Message)
  }
}

protected trait MinLength extends RequestSupport {
  def minlength(paramName: String, min: Int) = new Minlength4J2ee(paramName, min, request)
  def minlength(paramName: String, min: Int, paramName4Message: String) =
    new Minlength4J2ee(paramName, min, request, Option(paramName4Message))
  //  def minlength(min: Int, paramName4Message: Option[String]) = {
  //    (paramName: String) =>
  //      new Minlength4J2ee(paramName, min, request, paramName4Message)
  //  }
  def minlength(min: Int) = {
    (paramName: String, paramName4Message: Option[String]) =>
      new Minlength4J2ee(paramName, min, request, paramName4Message)
  }
}

protected trait MinMaxLength extends RequestSupport {
  def minMaxLength(paramName: String, min: Int, max: Int): AndValidator =
    new MinMaxLength(paramName, min, max)
  def minMaxLength(paramName: String, min: Int, max: Int, paramName4Message: Option[String]): AndValidator =
    new MinMaxLength(paramName, min, max, paramName4Message)
  def minMaxLength(min: Int, max: Int): (String, Option[String]) => AndValidator =
    (paramName: String, paramName4Message: Option[String]) => new MinMaxLength(paramName, min, max, paramName4Message)

  private[sv] class MinMaxLength(
    paramName: String //
    , min: Int //
    , max: Int //
    , paramNameForMessage: Option[String] = None //
    ) extends AndValidator(
    List(new Minlength4J2ee(paramName, min, request, paramNameForMessage), new Maxlength4J2ee(paramName, max, request, paramNameForMessage) //
    ))

}

//  with Required //

protected trait RegexValid extends RequestSupport {
  def regexValid(paramName: String, messageKey: String, regex: Regex) =
    new RegexValidator4J2ee(
      paramName //
      , regex //
      , messageKey //
      , request //
      , None //
      )
  def regexValid(paramName: String, messageKey: String, regex: Regex, paramNameForMessage: String) =
    new RegexValidator4J2ee(
      paramName //
      , regex //
      , messageKey //
      , request //
      , Option(paramNameForMessage) //
      )
}

protected trait Required extends RequestSupport {
  def required(paramName: String) = new Required4J2ee(paramName, request)
  def required(paramName: String, paramName4Message: String) =
    new Required4J2ee(paramName, request, Option(paramName4Message))
  //  def required(paramName4Message: Option[String]) = {
  //    (paramName: String) =>
  //      new Required4J2ee(paramName, request, paramName4Message)
  //  }
  def required() = { (paramName: String, paramName4Message: Option[String]) =>
    new Required4J2ee(paramName, request, paramName4Message)
  }
}
protected trait RequiredAnd extends RequestSupport with AndStopOnError with Required with And {
  def requiredAnd(paramName: String, paramName4Message: Option[String], validators: List[ValidatorBase]) = {
    andStopOnError(
      List(paramName4Message match {
        case None            => required(paramName)
        case Some(pName4Msg) => required(paramName, pName4Msg)
      } //
      , and(validators) //
      ))
  }
  def requiredAnd(paramName: String, paramName4Message: Option[String] //
  , validators: Function2[String, Option[String], ValidatorBase]*) = {
    andStopOnError(
      List(paramName4Message match {
        case None            => required(paramName)
        case Some(pName4Msg) => required(paramName, pName4Msg)
      } //
      , and(paramName, paramName4Message, validators: _*) //
      ))
  }
}


