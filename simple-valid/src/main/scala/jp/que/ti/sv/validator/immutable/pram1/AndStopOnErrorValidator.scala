package jp.que.ti.sv.validator.immutable.pram1

import jp.que.ti.sv.Validator1ParamIF
import jp.que.ti.sv.MessageBox
import jp.que.ti.sv.ParameterInfo

class AndStopOnErrorValidator protected (val validators: Traversable[Validator1ParamIF])
  extends Validator1ParamIF {

  def valid(paramValue: Option[String], parameterInfo: ParameterInfo, messageBox: MessageBox): MessageBox = {
    var exsistError = false
    val it = validators.toList.iterator

    var rtn = MessageBox()
    while (exsistError == false && it.hasNext) {
      val validator = it.next
      val mb = validator.valid(paramValue, parameterInfo, MessageBox())
      if (mb.isEmpty) {
        //エラーなし
      } else {
        rtn = mb
        exsistError = true
      }
    }
    rtn
  }
}

object AndStopOnErrorValidator {
  def apply(validators: Traversable[Validator1ParamIF]) = new AndStopOnErrorValidator(validators)
}