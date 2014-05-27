package jp.que.ti.sv

import org.slf4j.LoggerFactory
import jp.que.ti.sv.util.MessageResource
import jp.que.ti.sv.util.StringUtil
import jp.que.ti.sv.util.TrimIsEmptySupport

/**
 * 単項目チェック用の validator の基本インタフェース。
 * 1つの値を受け取ってチェックするインタフェース
 */
trait Validator1ParamIF extends Validator[Option[String]]

/**
 * 単項目チェック用の validator の基本抽象クラス。
 * 1つの値を受け取ってチェックする。
 */
abstract class Validator1Param(val messageKey: String)
  extends Validator1ParamIF
  with ValidatorImpl[Option[String]]
  with MessageResourceSupport
  with TrimIsEmptySupport {
}

abstract class NotRequiredBase(messageKey: String)
  extends Validator1Param(messageKey) {

  /**
   *  チェック内容を定義する。
   *  @return true の場合、チェックOK。 false の場合、チェックNG
   */
  def isValid(paramValue: Option[String]): Boolean = paramValue.map(v => trim(v)) match {
    case None => true // 空の場合チェックしない
    case Some(value) => if (value.isEmpty()) { true /* 空の場合チェックしない*/ } else {
      isValidInputed(value) // 空でない場合チェックする
    }
  }

  def isValidInputed(paramValue: String): Boolean

}

abstract class Validator1ParamDecorator(validator: Validator1ParamIF)
  extends ValidatorDecorator[Option[String], Validator1ParamIF](validator)
