package jp.que.ti.sv

import jp.que.ti.sv.util.StringUtil
import jp.que.ti.sv.util.TrimIsEmptySupport

/**
 * 単項目チェック用の validator の基本インタフェース。
 * 1つの値を受け取ってチェックするインタフェース
 */
trait Validator2ParamIF extends Validator[Pair[Option[String], Option[String]]]

/**
 * 単項目チェック用の validator の基本抽象クラス。
 * 1つの値を受け取ってチェックする。
 */
abstract class Validator2Param(val messageKey: String)
  extends Validator2ParamIF
  with ValidatorImpl[Pair[Option[String], Option[String]]]
  with MessageResourceSupport
  with TrimIsEmptySupport {

  /**
   *  検証内容を定義するメソッドです。
   *  実装クラスで詳細を実装してください
   *
   *  @param param チェック対象項目のうち主となる項目
   *  @param paramSub1 チェック対象項目のうち従属的な項目
   *  @return true の場合、チェックOK。 false の場合、チェックNG
   */
  def isValid(param: Option[String], paramSub1: Option[String]): Boolean

  override def isValid(param: Pair[Option[String], Option[String]]): Boolean = isValid(param._1, param._2)
}

abstract class Validator2ParamDecorator(validator: Validator2ParamIF)
  extends ValidatorDecorator[Pair[Option[String], Option[String]], Validator2ParamIF](validator)
