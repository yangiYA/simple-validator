package jp.que.ti.sv.j2ee

import scala.util.matching.Regex
import jp.que.ti.sv.ParameterInfo
import jp.que.ti.sv.Validator1ParamIF
import jp.que.ti.sv.validator.immutable.pram1.GenericValidator
import jp.que.ti.sv.validator.immutable.pram1.Length
import jp.que.ti.sv.validator.immutable.pram1.Max
import jp.que.ti.sv.validator.immutable.pram1.Maxlength
import jp.que.ti.sv.validator.immutable.pram1.Min
import jp.que.ti.sv.validator.immutable.pram1.Minlength
import jp.que.ti.sv.validator.immutable.pram1.Number
import jp.que.ti.sv.validator.immutable.pram1.RangeLength
import jp.que.ti.sv.validator.immutable.pram1.RegexValidator
import jp.que.ti.sv.validator.immutable.pram1.Required
import jp.que.ti.sv.validator.immutable.pram2.GenericValidator2
import jp.que.ti.sv.validator.immutable.pram2.Requiredif
import jp.que.ti.sv.validator.immutable.pram1.Email
import javax.servlet.http.HttpServletRequest

trait ValidatorSupport4J2ee {

  self: { def request: HttpServletRequest } =>

  def validator(parameterInfo: ParameterInfo, v: Validator1ParamIF) =
    Validator1ParamDecoratorJ2ee(parameterInfo, v)(request)

  def and(parameterInfo: ParameterInfo, v: Validator1ParamIF*) =
    Validator1ParamDecoratorJ2ee.and(parameterInfo, v.toList)(request)

  def and(validatorDecoratorJ2ee: ValidatorDecoratorJ2eeBase*) =
    AndValidatorDecoratorJ2ee(request, validatorDecoratorJ2ee: _*)

  def andStopOnError(parameterInfo: ParameterInfo, v: Validator1ParamIF*) =
    Validator1ParamDecoratorJ2ee.andStopOnError(parameterInfo, v.toList)(request)

  def andStopOnErrorD(validatorDecoratorJ2ee: ValidatorDecoratorJ2eeBase*) =
    AndStopOnErrorValidatorDecoratorJ2ee(request, validatorDecoratorJ2ee: _*)

  def validator(checkFunction: String => Boolean, messageKey: String) = GenericValidator(checkFunction, messageKey)

  def email = Email()

  def length(length: Int) = Length(length)

  def maxlength(length: Int) = Maxlength(length)

  def max(value: Int) = Max(value)

  def max(value: Long) = Max(value)

  def minlength(length: Int) = Minlength(length)

  def min(value: Int) = Min(value)

  def number = Number()

  def rangelength(minLength: Int, maxLength: Int) = RangeLength(minLength, maxLength)

  def regexValid(regex: Regex) = RegexValidator(regex)

  def required = Required

  def requiredif(parameterInfoSub1: ParameterInfo) = Requiredif(parameterInfoSub1)

  def param(parameter: String, paramName4Message: String) = ParameterInfo(parameter, paramName4Message)

  def validator2(
    checkFunction: (Option[String], Option[String]) => Boolean //
    , parameterInfoSub1: ParameterInfo //
    , messageKey: String) =
    GenericValidator2(checkFunction, parameterInfoSub1, messageKey)

}