package jp.que.ti.sv.j2ee

import javax.servlet.http.HttpServletRequest
import jp.que.ti.sv.MessageBox
import jp.que.ti.sv.ParameterInfo
import jp.que.ti.sv.Validator1Param
import jp.que.ti.sv.Validator1ParamDecorator
import jp.que.ti.sv.Validator1ParamIF
import jp.que.ti.sv.validator.immutable.pram1.AndValidator
import jp.que.ti.sv.validator.immutable.pram1.AndStopOnErrorValidator

class Validator1ParamDecoratorJ2ee protected (
  val request: HttpServletRequest //
  , val parameterInfo: ParameterInfo //
  , validator1Param: Validator1ParamIF //
  ) extends Validator1ParamDecorator(validator1Param)
  with ValidatorDecoratorJ2eeBase {

  def valid(messageBox: MessageBox): MessageBox = valid(paramValue(parameterInfo), parameterInfo, messageBox)
}

object Validator1ParamDecoratorJ2ee {

  def apply(parameterInfo: ParameterInfo, validator1Param: Validator1ParamIF) //
  (implicit request: HttpServletRequest) =
    new Validator1ParamDecoratorJ2ee(request, parameterInfo, validator1Param)

  def and(parameterInfo: ParameterInfo, validators: Traversable[Validator1ParamIF]) //
  (implicit request: HttpServletRequest) =
    apply(parameterInfo, AndValidator(validators))

  def andStopOnError(parameterInfo: ParameterInfo, validators: Traversable[Validator1ParamIF]) //
  (implicit request: HttpServletRequest) =
    apply(parameterInfo, AndStopOnErrorValidator(validators))

}