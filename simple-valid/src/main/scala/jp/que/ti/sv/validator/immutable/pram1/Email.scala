package jp.que.ti.sv.validator.immutable.pram1

/**
 * パラメータの文字列長が、指定の長さ以下であることをチェックする Validator
 */
class Email extends RegexValidator("""^[-+.\w]+@[-a-z0-9]+(\.[-a-z0-9]+)*\.[a-z]{2,6}$""".r, "email")

object Email {
  def apply() = new Email()
}
