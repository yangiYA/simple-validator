package jp.que.ti.sv.j2ee

import javax.servlet.http.HttpServletRequest
import jp.que.ti.sv.MessageBox
import jp.que.ti.sv.ParameterInfo
import jp.que.ti.sv.Validator1Param
import jp.que.ti.sv.Validator1ParamDecorator
import jp.que.ti.sv.Validator1ParamIF
import jp.que.ti.sv.validator.immutable.pram1.AndValidator
import jp.que.ti.sv.validator.immutable.pram1.AndStopOnErrorValidator
import jp.que.ti.sv.Validator2ParamIF
import jp.que.ti.sv.Validator2ParamDecorator

class Validator2ParamDecoratorJ2ee protected (
  val request: HttpServletRequest //
  , val parameterInfo: ParameterInfo //
  , val parameterInfoSub1: ParameterInfo //
  , validator2Param: Validator2ParamIF //
  ) extends Validator2ParamDecorator(validator2Param)
  with ValidatorDecoratorJ2eeBase {

  def valid(messageBox: MessageBox): MessageBox = valid((paramValue(parameterInfo), paramValue(parameterInfoSub1)), parameterInfo, messageBox)
}

object Validator2ParamDecoratorJ2ee {

  def apply(parameterInfo: ParameterInfo, parameterInfoSub1: ParameterInfo, validator2Param: Validator2ParamIF) //
  (implicit request: HttpServletRequest) =
    new Validator2ParamDecoratorJ2ee(request, parameterInfo, parameterInfoSub1, validator2Param)

}
