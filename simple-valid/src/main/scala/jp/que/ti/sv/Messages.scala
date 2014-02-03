package jp.que.ti.sv

import scala.collection.immutable.Nil

/**
 * A Key and Messages class. This is immutable.
 * When call method "add", the item are added to the top of the list.
 */
class Messages(val key: String, val messageList: List[String] = Nil) {
  private def this(key: String, message: String) =
    this(key, message :: Nil)

  def add(message: String): Messages = new Messages(key, message :: messageList)

  def add(messages: List[String]): Messages = new Messages(key, messages.foldLeft(messageList)((acc, msg) => msg :: acc))

  override def equals(obj: Any) = obj match {
    case null => false
    case mes: Messages =>
      if (eq(mes)) {
        true
      } else if (hashCodeCache != mes.hashCodeCache) {
        false

      } else {
        mes match {
          case Messages(ky, ml) => {
            key == ky && messageList == ml
          }
        }
      }
    case otherType => false
  }

  /**
   * hashCode メソッドの計算結果をキャッシュするprivate フィールド。
   *  当インスタンスはイミュータブルなのでキャッシュすることが可能
   */
  private var hashCodeCache: Int = Int.MinValue
  override def hashCode() = {
    //Int.MinValue の場合はキャッシュされていないとみなす。
    //たまたま計算結果がInt.MinValueになる可能性は極端に低いのでその場合若干計算が無駄になってもあきらめる
    if (hashCodeCache == Int.MinValue) {
      hashCodeCache = key.hashCode() + messageList.hashCode * 7;
    }
    hashCodeCache
  }

  override def toString() = "Messages([" + key + "]," + messageList + ")"
}

object Messages {

  def apply(key: String, messageList: List[String]) = new Messages(key, messageList.reverse)

  def apply(key: String, message: String) = new Messages(key, message :: Nil)

  /**
   * @param srcMessages 元とする、[[jp.que.ti.scalatla.valid.Messages]] 。Option[Message] 型
   * @param key このオブジェクトのkey。通常は項目名を指定する。srcMessages の key が異なる場合
   *        このパラメータが有効
   * @param message メッセージ。srcMessages に追加したいエラーメッセージ
   */
  def apply(srcMessages: Option[Messages], key: String, message: String) = srcMessages match {
    case None       => new Messages(key, message)
    case Some(msgs) => new Messages(key, message :: msgs.messageList)
  }

  /**
   * @param srcMessages 元とする、[[jp.que.ti.scalatla.valid.Messages]] 。Option[Message] 型
   * @param key このオブジェクトのkey。通常は項目名を指定する。srcMessages の key が異なる場合
   *        このパラメータが有効
   * @param messages メッセージの一覧。srcMessages に追加したいエラーメッセージ
   */
  def apply(srcMessages: Option[Messages], key: String, messages: List[String]) = srcMessages match {
    case None       => new Messages(key, messages)
    case Some(msgs) => new Messages(key, messages ++ msgs.messageList)
  }

  def unapply(m: Messages) = Some(m.key, m messageList)

}