package jp.que.ti.sv.validator.immutable.pram1

import jp.que.ti.sv.NotRequiredBase
import jp.que.ti.sv.ParameterInfo

/**
 * パラメータの文字列長が、指定の長さであることをチェックする Validator
 */
class RangeLength protected (minLength: Int, maxLength: Int)
  extends AndStopOnErrorValidator(List(Minlength(minLength), Maxlength(maxLength)))

object RangeLength {
  def apply(minLength: Int, maxLength: Int) = new RangeLength(minLength, maxLength)
}