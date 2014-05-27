package jp.que.ti.sv.validator.immutable.pram1

import jp.que.ti.sv.NotRequiredBase

/**
 * 汎用 Validator。入力値が存在する場合のみチェックします。
 * このライブラリが提供していないチェックを独自に作成するための汎用クラスです。
 * このクラスを使用せずに、単に、[jp.que.ti.sv.NotRequiredBase] をオーバーライドしても、同様のチェックが作成できます。
 * 用途に合わせて対応方法を選択してください。
 *
 * @param checkFunction 入力された String を判定して、Boolean を返却する関数を指定。チェックロジックを渡してください
 * @param messageKey エラーメッセージリソースのKEY
 */
class GenericValidator protected (val checkFunction: String => Boolean, messageKey: String) extends NotRequiredBase(messageKey) {
  def isValidInputed(paramValue: String): Boolean = checkFunction(paramValue)
}

object GenericValidator {
  def apply(checkFunction: String => Boolean, messageKey: String) =
    new GenericValidator(checkFunction, messageKey)
}