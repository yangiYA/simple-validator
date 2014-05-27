package jp.que.ti.sv.validator.immutable.pram1

import jp.que.ti.sv.NotRequiredBase
import jp.que.ti.sv.ParameterInfo

/**
 * パラメータの文字列長が、指定の長さであることをチェックする Validator
 */
class Length protected (val length: Int) extends NotRequiredBase("length") {

  def isValidInputed(paramValue: String): Boolean = { length == trim(paramValue).length() }

  override def messageArgs(parameterInfo: ParameterInfo): Seq[String] = parameterInfo.nam4Msg :: length + "" :: Nil

}

object Length {
  def apply(length: Int) = if (length <= 24 && length >= 1) {
    valids(length)
  } else {
    new Length(length)
  }

  private val valids = new Array[Length](25)
  (1 to 24).foreach { idx =>
    valids(idx) = new Length(idx)
  }

}