package jp.que.ti.sv.validator.immutable.pram1

import jp.que.ti.sv.Validator1ParamIF
import jp.que.ti.sv.MessageBox
import jp.que.ti.sv.ParameterInfo

class AndValidator protected (val validators: Traversable[Validator1ParamIF])
  extends Validator1ParamIF {

  def valid(paramValue: Option[String], parameterInfo: ParameterInfo, messageBox: MessageBox): MessageBox = {
    validators.foldLeft(messageBox) { (acc: MessageBox, validator: Validator1ParamIF) =>
      validator.valid(paramValue, parameterInfo, acc)
    }
  }
}

object AndValidator {
  def apply(validators: Traversable[Validator1ParamIF]) = new AndValidator(validators)
}
