package jp.que.ti.sv.validator.immutable.pram1

import jp.que.ti.sv.Validator1Param

class Required protected ()
  extends Validator1Param("required") {

  /**
   *  チェック内容を定義する。
   *  @return true の場合、チェックOK。 false の場合、チェックNG
   */
  def isValid(paramValue: Option[String]): Boolean = {
    paramValue match {
      case None        => false // 空の場合エラー
      case Some(value) => !(trim(value).isEmpty()) // 空の場合エラー
    }
  }

}

object Required {
  private val instance = new Required

  def apply() = instance

}
