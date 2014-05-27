package jp.que.ti.sv.j2ee

import jp.que.ti.sv.MessageBox

trait ValidatorDecoratorJ2eeBase extends J2eeParameterValueSupport {

  /**
   * J2eeをサポートする ValidatorDecorator で定義すべきメソッド。サブクラスで実装する
   */
  def valid(messageBox: MessageBox): MessageBox

}