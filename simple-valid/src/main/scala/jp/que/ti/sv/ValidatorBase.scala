package jp.que.ti.sv

import org.slf4j.LoggerFactory
import jp.que.ti.sv.util.MessageResource
import jp.que.ti.sv.util.StringUtil

/**
 * validator の基本インタフェース。
 * エラーメッセージを取得する errorMessage メソッドが定義されています
 */
trait Validator[A] {

  def valid(paramValue: A, parameterInfo: ParameterInfo, messageBox: MessageBox): MessageBox

}

/** [jp.que.ti.sv.MessageResource]を使用して、エラーメッセージを生成する trait */
trait MessageResourceSupport {

  /** エラーメッセージリソースのKEY */
  def messageKey: String

  /**
   * エラーメッセージのパラメータ。
   *  パラメータを変更したい場合は、実装クラスでオーバーライドすることを想定しています。
   */
  def messageArgs(parameterInfo: ParameterInfo): Seq[String] = parameterInfo.nam4Msg :: Nil

  /** エラーメッセージ。チェックエラーの場合のメッセージ */
  def errorMessage(parameterInfo: ParameterInfo): String = MessageResource.message(messageKey, messageArgs(parameterInfo))
}

/**
 * ValidatorWithErrorMessageIF の
 *  実装クラス
 */
trait ValidatorImpl[A] extends Validator[A] {

  protected val log = LoggerFactory.getLogger(getClass())

  /**
   *  検証内容を定義するメソッドです。
   *  実装クラスで詳細を実装してください
   *  @return true の場合、チェックOK。 false の場合、チェックNG
   */
  def isValid(param: A): Boolean

  def valid(paramValue: A, parameterInfo: ParameterInfo, messageBox: MessageBox): MessageBox = {
    val validResult = isValid(paramValue)

    if (validResult) {
      messageBox
    } else {
      messageBox.add(parameterInfo.parameter, errorMessage(parameterInfo))
    }
  }

  /** エラーメッセージ。チェックエラーの場合のメッセージ */
  def errorMessage(parameterInfo: ParameterInfo): String

}

abstract class ValidatorDecorator[A, V <: Validator[A]](protected val validator: V) extends Validator[A] {

  def parameterInfo: ParameterInfo

  def valid(paramValue: A, parameterInfo: ParameterInfo, messageBox: MessageBox): MessageBox =
    validator.valid(paramValue, parameterInfo, messageBox)

}

