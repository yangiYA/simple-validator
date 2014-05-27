package jp.que.ti.sv.validator.immutable.pram2

import jp.que.ti.sv.NotRequiredBase
import jp.que.ti.sv.Validator2Param
import jp.que.ti.sv.ParameterInfo

/**
 * 汎用 Validator。入力値が存在する場合のみチェックします。
 * このライブラリが提供していないチェックを独自に作成するための汎用クラスです。
 * このクラスを使用せずに、単に、[jp.que.ti.sv.NotRequiredBase] をオーバーライドしても、同様のチェックが作成できます。
 * 用途に合わせて対応方法を選択してください。
 *
 * @param checkFunction 入力された String を判定して、Boolean を返却する関数を指定。チェックロジックを渡してください
 * @param messageKey エラーメッセージリソースのKEY
 */
class GenericValidator2 protected (
  val checkFunction: (Option[String], Option[String]) => Boolean //
  , val parameterInfoSub1: ParameterInfo //
  , messageKey: String) extends Validator2Param(messageKey) {
  /**
   *  検証内容を定義するメソッドです。
   *  実装クラスで詳細を実装してください
   *
   *  @param param チェック対象項目のうち主となる項目
   *  @param paramSub1 チェック対象項目のうち従属的な項目
   *  @return true の場合、チェックOK。 false の場合、チェックNG
   */
  def isValid(param: Option[String], paramSub1: Option[String]): Boolean = checkFunction(param, paramSub1)

  override def messageArgs(parameterInfo: ParameterInfo): Seq[String] =
    parameterInfo.nam4Msg :: parameterInfoSub1.nam4Msg :: Nil

}

object GenericValidator2 {

  def apply(checkFunction: (Option[String], Option[String]) => Boolean //
  , parameterInfoSub1: ParameterInfo //
  , messageKey: String) =
    new GenericValidator2(checkFunction, parameterInfoSub1, messageKey)
}