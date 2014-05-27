package jp.que.ti.sv.validator.immutable.pram2

import jp.que.ti.sv.ParameterInfo
import jp.que.ti.sv.Validator2Param

class Requiredif protected (val parameterInfoSub1: ParameterInfo) extends Validator2Param("requiredif") {

  /**
   *  検証内容を定義するメソッドです。
   *  実装クラスで詳細を実装してください
   *
   *  @param param チェック対象項目のうち主となる項目
   *  @param paramSub1 チェック対象項目のうち従属的な項目
   *  @return true の場合、チェックOK。 false の場合、チェックNG
   */
  def isValid(param: Option[String], paramSub1: Option[String]): Boolean = if (isEmpty(paramSub1)) {
    true
  } else {
    if (isEmpty(param)) {
      false //paramSub1 が設定されている場合は、param は必須。つまりエラー
    } else {
      true
    }
  }

  override def messageArgs(parameterInfo: ParameterInfo): Seq[String] =
    parameterInfo.nam4Msg :: parameterInfoSub1.nam4Msg :: Nil

}

object Requiredif {
  def apply(parameterInfoSub1: ParameterInfo) = new Requiredif(parameterInfoSub1)
}