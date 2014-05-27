package jp.que.ti.sv.validator.immutable.pram1

import jp.que.ti.sv.NotRequiredBase
import jp.que.ti.sv.util.StringUtil._
import jp.que.ti.sv.ParameterInfo

/**
 * パラメータの文字列長が、指定の長さ以上であることをチェックする Validator
 */
class Minlength protected (val min: Int) extends NotRequiredBase("minlength") {

  def isValidInputed(paramValue: String): Boolean = { min <= trim(paramValue).length() }

  override def messageArgs(parameterInfo: ParameterInfo): Seq[String] = parameterInfo.nam4Msg :: min + "" :: Nil

}

object Minlength {
  def apply(min: Int) = if (min <= 24 && min >= 1) {
    valids(min)
  } else {
    new Minlength(min)
  }

  private val valids = new Array[Minlength](25)
  (1 to 24).foreach { idx =>
    valids(idx) = new Minlength(idx)
  }

}