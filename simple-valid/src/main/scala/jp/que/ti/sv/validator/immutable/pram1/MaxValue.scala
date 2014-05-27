package jp.que.ti.sv.validator.immutable.pram1

import jp.que.ti.sv.NotRequiredBase
import jp.que.ti.sv.ParameterInfo

/**
 * パラメータの文字列長が、指定の長さ以下であることをチェックする Validator
 */
class MaxValue protected (max: Int) extends MaxValueBase[Int](max) {
  def isValidInputed(paramValue: String): Boolean = { max >= trim(paramValue).toLong }
}

class MaxValueLong protected (max: Long) extends MaxValueBase[Long](max) {
  def isValidInputed(paramValue: String): Boolean = { max >= trim(paramValue).toLong }
}

protected class MaxValueBase[A <: { def toLong: Long; def toInt: Int; }] protected (val max: A)
  extends NotRequiredBase("maxvalue") {

  //  def isValidInputed(paramValue: String): Boolean = { max.toInt >= trim(paramValue).toLong }

  override def messageArgs(parameterInfo: ParameterInfo): Seq[String] = parameterInfo.nam4Msg :: max + "" :: Nil

}

object MaxValue {
  def apply(max: Int) = new MaxValue(max)
  def apply(max: Long) = new MaxValueLong(max)

}

