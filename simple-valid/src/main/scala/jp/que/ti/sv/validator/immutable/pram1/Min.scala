package jp.que.ti.sv.validator.immutable.pram1

import jp.que.ti.sv.Validator1ParamIF

class Min protected (validators: Traversable[Validator1ParamIF]) extends AndValidator(validators)

object Min {
  def apply(min: Int) = new Min(Number() :: MinValue(min) :: Nil)
  def apply(min: Long) = new Min(Number() :: MinValue(min) :: Nil)
}