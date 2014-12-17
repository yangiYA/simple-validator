package jp.que.ti.sv.validator.immutable.pram1

import jp.que.ti.sv.NotRequiredBase
import jp.que.ti.sv.ParameterInfo

/**
 * パラメータの数値が、指定の数値以下であることをチェックする Validator。
 * 数字でないパラメータが指定された場合は、エラーにしません。
 * エラーにする場合は、[[jp.que.ti.sv.validator.immutable.pram1.NumberValidator]] と組み合わせて使用してください。
 */
abstract sealed class MaxValue[A <: AnyVal] protected (val max: A) extends NotRequiredBase("maxvalue") {
  override def messageArgs(parameterInfo: ParameterInfo): Seq[String] = parameterInfo.nam4Msg :: max + "" :: Nil
}

class MaxValueLong(max: Long) extends MaxValue[Long](max) {
  def isValidInputed(paramValue: String): Boolean = try {
    max >= trim(paramValue).toLong
  } catch {
    case e: Exception => true // 数字でない場合はエラーににしない
  }
}

class MaxValueInt(max: Int) extends MaxValue[Int](max) {
  def isValidInputed(paramValue: String): Boolean = try {
    max >= trim(paramValue).toLong
  } catch {
    case e: Exception => true // 数字でない場合はエラーににしない
  }
}

object MaxValue {
  def apply(max: Int): MaxValue[Int] = new MaxValueInt(max)
  def apply(max: Long): MaxValue[Long] = new MaxValueLong(max)

}

