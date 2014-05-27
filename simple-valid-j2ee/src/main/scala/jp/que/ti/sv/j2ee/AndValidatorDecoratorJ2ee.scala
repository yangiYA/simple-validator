package jp.que.ti.sv.j2ee

import jp.que.ti.sv.MessageBox
import javax.servlet.http.HttpServletRequest

class AndValidatorDecoratorJ2ee protected (
  val request: HttpServletRequest //
  , val validatorDecoratorJ2eeList: List[ValidatorDecoratorJ2eeBase]) extends ValidatorDecoratorJ2eeBase {

  /**
   * J2eeをサポートする ValidatorDecorator で定義すべきメソッド。サブクラスで実装する
   */
  def valid(messageBox: MessageBox): MessageBox = validatorDecoratorJ2eeList.foldLeft(messageBox) {
    (acc: MessageBox, validatorDecoratorJ2ee: ValidatorDecoratorJ2eeBase) => validatorDecoratorJ2ee.valid(messageBox)
  }

}

object AndValidatorDecoratorJ2ee {
  def apply(request: HttpServletRequest, validatorDecoratorJ2eeList: List[ValidatorDecoratorJ2eeBase]) =
    new AndValidatorDecoratorJ2ee(request, validatorDecoratorJ2eeList)

  def apply(request: HttpServletRequest, validatorDecoratorJ2eeList: ValidatorDecoratorJ2eeBase*) =
    new AndValidatorDecoratorJ2ee(request, validatorDecoratorJ2eeList.toList)
}

