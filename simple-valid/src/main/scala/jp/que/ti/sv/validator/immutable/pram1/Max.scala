package jp.que.ti.sv.validator.immutable.pram1

import jp.que.ti.sv.Validator1ParamIF

class Max protected (validators: Traversable[Validator1ParamIF]) extends AndValidator(validators)

object Max {
  def apply(max: Int) = new Max(Number() :: MaxValue(max) :: Nil)
  def apply(max: Long) = new Max(Number() :: MaxValue(max) :: Nil)
}