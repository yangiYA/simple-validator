package jp.que.ti.sv.j2ee

import jp.que.ti.sv.MessageBox
import javax.servlet.http.HttpServletRequest

class AndStopOnErrorValidatorDecoratorJ2ee protected (
  val request: HttpServletRequest //
  , val validatorDecoratorJ2eeList: List[ValidatorDecoratorJ2eeBase]) extends ValidatorDecoratorJ2eeBase {

  /**
   * J2eeをサポートする ValidatorDecorator で定義すべきメソッド。サブクラスで実装する
   */
  def valid(messageBox: MessageBox): MessageBox = {
    var exsistError = false
    val it = validatorDecoratorJ2eeList.toList.iterator

    var rtn = MessageBox()
    while (exsistError == false && it.hasNext) {
      val validator = it.next
      val mb = validator.valid(MessageBox())
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

object AndStopOnErrorValidatorDecoratorJ2ee {
  def apply(request: HttpServletRequest, validatorDecoratorJ2eeList: List[ValidatorDecoratorJ2eeBase]) =
    new AndStopOnErrorValidatorDecoratorJ2ee(request, validatorDecoratorJ2eeList)

  def apply(request: HttpServletRequest, validatorDecoratorJ2eeList: ValidatorDecoratorJ2eeBase*) =
    new AndStopOnErrorValidatorDecoratorJ2ee(request, validatorDecoratorJ2eeList.toList)
}

