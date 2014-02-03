package jp.que.ti.sv.validator

import jp.que.ti.sv.MessageBox
import jp.que.ti.sv.ValidatorBase
import jp.que.ti.sv.ValidatorBase
import jp.que.ti.sv.ValidatorBaseWithMessageProp
import jp.que.ti.sv.ValidatorIF

private[validator] abstract class AndValidatorBase(val validators: List[ValidatorIF]) extends ValidatorBase("") {

  /**
   *  AndValidator ではでは、[valid(messageBox: jp.que.ti.sv.MessageBox): jp.que.ti.sv.MessageBox]メソッドを
   *  オーバーライドするので、このメソッドは使用しない。
   *  このため、このメソッドは常にfalseを返却する
   *  @return true の場合、チェックOK。 false の場合、チェックNG
   */
  protected def isValid: Boolean = false

  /** Hook method */
  protected def errorMessage: String = ""

  /** サブクラスでオーバーライドを強制する */
  override def valid(messageBox: MessageBox = MessageBox()): MessageBox

}

/**
 *
 */
class AndValidator(validators: List[ValidatorIF]) extends AndValidatorBase(validators) {

  override def valid(messageBox: MessageBox = MessageBox()): MessageBox = {
    validators.foldLeft(messageBox) { (acc, validator) =>
      {
        val mbox = validator.valid()
        if (mbox.isEmpty) { acc /* 追加するメッセージはなし */ } else { acc ++ mbox }
      }
    }
  }
}
object AndValidator {
  def apply(validators: List[ValidatorIF]) = new AndValidator(validators)
  def apply(validators: ValidatorIF*) = new AndValidator(validators.toList)
  def apply(parameter: String, validators: List[String => ValidatorBase]) =
    new AndValidator(validators.map(fValidator => fValidator(parameter)))
  def apply(parameter: String, paramName4Message: Option[String] //
  , validators: List[(String, Option[String]) => ValidatorBase]) =
    new AndValidator(validators.map(fValidator => fValidator(parameter, paramName4Message)))
}

/**
 *
 */
class AndStopOnErrorValidator(validators: List[ValidatorIF]) extends AndValidatorBase(validators) {

  override def valid(messageBox: MessageBox = MessageBox()): MessageBox = {
    var donotStop = true
    val returnMessageBox = validators.foldLeft(messageBox) { (acc, validator) =>
      {
        if (donotStop) {
          val mbox = validator.valid()
          if (mbox.isEmpty) {
            acc /*追加するメッセージはなし*/
          } else {
            donotStop = false // エラー発生なら、後続のチェックを実施しない
            acc ++ mbox
          }
        } else {
          acc //エラーが発生したらチェックをストップする場合の分岐
        }
      }
    }
    returnMessageBox
  }
}
object AndStopOnErrorValidator {
  def apply(validators: List[ValidatorIF]) = new AndStopOnErrorValidator(validators)
  def apply(validators: ValidatorIF*) = new AndStopOnErrorValidator(validators.toList)
  def apply(parameter: String, validators: List[String => ValidatorBase]) =
    new AndStopOnErrorValidator(validators.map(fValidator => fValidator(parameter)))
  def apply(parameter: String, paramName4Message: Option[String] //
  , validators: List[(String, Option[String]) => ValidatorBase]) =
    new AndStopOnErrorValidator(validators.map(fValidator => fValidator(parameter, paramName4Message)))
}
