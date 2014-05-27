package jp.que.ti.sv.validator.immutable.pram1

import jp.que.ti.sv.NotRequiredBase
import jp.que.ti.sv.ParameterInfo

/**
 * パラメータの文字列長が、指定の長さ以下であることをチェックする Validator
 */
class Maxlength(val max: Int) extends NotRequiredBase("maxlength") {

  def isValidInputed(paramValue: String): Boolean = { max >= trim(paramValue).length() }

  override def messageArgs(parameterInfo: ParameterInfo): Seq[String] = parameterInfo.nam4Msg :: max + "" :: Nil

}

object Maxlength {
  def apply(max: Int) = if (max <= 24 && max >= 1) {
    valids(max)
  } else {
    new Maxlength(max)
  }

  private val valids = new Array[Maxlength](25)
  (1 to 24).foreach { idx =>
    valids(idx) = new Maxlength(idx)
  }
}

