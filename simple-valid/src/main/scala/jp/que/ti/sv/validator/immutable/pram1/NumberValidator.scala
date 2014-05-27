package jp.que.ti.sv.validator.immutable.pram1

import jp.que.ti.sv.NotRequiredBase
import jp.que.ti.sv.util.StringUtil

/**
 * パラメータの文字が、数字であることをチェックする Validator
 */
class NumberValidator protected () extends NotRequiredBase("number") {

  def isValidInputed(paramValue: String): Boolean =
    StringUtil.isNumberOnly(
      StringUtil.toHalfSizeNumberCharacter(paramValue))

}

object NumberValidator {
  private val numberObject = new NumberValidator()

  def apply() = numberObject
}