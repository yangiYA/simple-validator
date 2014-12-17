package jp.que.ti.sv.validator.immutable.pram1

import jp.que.ti.sv.NotRequiredBase
import jp.que.ti.sv.ParameterInfo

/**
 * パラメータの数値が、指定の数値以上であることをチェックする Validator。
 * 数字でないパラメータが指定された場合は、エラーにしません。
 * エラーにする場合は、[[jp.que.ti.sv.validator.immutable.pram1.NumberValidator]] と組み合わせて使用してください。
 */
abstract sealed class MinValue[A <: AnyVal] protected (val min: A) extends NotRequiredBase("minvalue") {
  override def messageArgs(parameterInfo: ParameterInfo): Seq[String] = parameterInfo.nam4Msg :: min + "" :: Nil
}

class MinValueLong(min: Long) extends MinValue[Long](min) {
  def isValidInputed(paramValue: String): Boolean = try {
    min <= trim(paramValue).toLong
  } catch {
    case e: Exception => true // 数字でない場合はエラーにしない
  }
}

class MinValueInt(min: Int) extends MinValue[Int](min) {
  def isValidInputed(paramValue: String): Boolean = try {
    min <= trim(paramValue).toLong
  } catch {
    case e: Exception => true // 数字でない場合はエラーにしない
  }
}

object MinValue {
  def apply(min: Int): MinValue[Int] = new MinValueInt(min)
  def apply(min: Long): MinValue[Long] = new MinValueLong(min)

}

