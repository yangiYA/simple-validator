package jp.que.ti.sv

import scala.util.matching.Regex

class MessageBox private (val messagesMap: Map[String, Messages], val keys: List[String]) {

  private def this(messagesMap: Map[String, Messages]) = { this(messagesMap, messagesMap.keys.toList) }

  /** 唯一のサブクラスへの公開コンストラクタ。引数なしで空のMessageBoxを作成する */
  protected def this() = this(Map[String, Messages]())

  override def toString() = format4toString("""%s,""", """MessageBox(%s)""")

  private def format4toString(lineFormat: String, summaryFormat: String) = {
    val lineStringFunction = { (accumulator: String, messageMapKey: String) =>
      accumulator + lineFormat.format(messagesMap.getOrElse(messageMapKey, "").toString())
    }
    val messagesListString = (keys.reverse).foldLeft("")(lineStringFunction)
    summaryFormat.format(messagesListString)
  }

  def toStringFormatted(lineFormat: String, summaryFormat: String, messagesKey: Option[String] = None) = {
    val lineStringFunction = { (accumulator: String, message: String) =>
      accumulator + lineFormat.format(message)
    }
    val messagesListString = messages(messagesKey).foldLeft("")(lineStringFunction)
    summaryFormat.format(messagesListString)
  }

  def htmlString() = toStringFormatted(
    """  <li>%s</li>""" + "\n" //
    ,
    """
<ol>
%s</ol>
""")

  override def equals(obj: Any) = obj match {
    case null => false
    case mes: MessageBox =>
      if (eq(mes)) {
        true
      } else if (hashCodeCache != mes.hashCodeCache) {
        false

      } else {
        mes match {
          case MessageBox(mMap, keys2) => {
            (keys == keys2) && {
              val msgKeys = messagesMap.keys
              msgKeys.forall { k => messagesMap.get(k) == mMap.get(k) }
            }
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
      hashCodeCache = messagesMap.hashCode() + keys.hashCode * 7;
    }
    hashCodeCache
  }

  def add(key: String, message: String): MessageBox = {
    if (isEmpty) {
      if (message == null || message.isEmpty()) {
        MessageBox.EmptyMessageBox
      } else {
        val ky = MessageBox.null2Empty(key)
        val mss = Messages(ky, message)
        new MessageBox(Map(ky -> mss), ky :: Nil)
      }
    } else {
      val msgsOrNone: Option[Messages] = messagesMap.get(key)
      val newMessageBox = msgsOrNone match {
        case None => {
          new MessageBox(
            messagesMap + (key -> Messages(key, message)),
            key :: keys)
        }
        case Some(ms) => {
          new MessageBox(
            messagesMap + (key -> ms.add(message)),
            keys)
        }
      }
      newMessageBox
    }
  }

  def add(messages: Option[Messages]): MessageBox = messages match {
    case None       => this
    case Some(msgs) => add(msgs)
  }

  def add(key: String, messageList: List[String]): MessageBox = messageList match {
    case Nil   => this
    case mList => addSimply(key, messageList)
  }
  private def addSimply(key: String, messageList: List[String]): MessageBox =
    (messageList.reverse).foldLeft(this)((acc, message) => acc.add(key, message))

  def add(messages: Messages): MessageBox = add(messages.key, messages.messageList)

  def add(key: String, message: Option[String]): MessageBox = message match {
    case None      => this
    case Some(msg) => add(key, msg)
  }

  def addMessageBox(messageBox: MessageBox): MessageBox = if (messageBox.isEmpty) { this } else {
    val keyList = messageBox.keys
    keyList.foldLeft(this) { (acc, ky) => acc.add(messageBox.messagesMap(ky)) }
  }

  def addMessageBox(messageBox: Option[MessageBox]): MessageBox = messageBox match {
    case None       => this
    case Some(mbox) => addMessageBox(mbox)
  }

  def ++(messageBox: MessageBox): MessageBox = addMessageBox(messageBox)
  def ++(messageBox: Option[MessageBox]): MessageBox = addMessageBox(messageBox)

  def ++:(messageBox: MessageBox): MessageBox = messageBox.addMessageBox(this)
  def ++:(messageBox: Option[MessageBox]): MessageBox = {
    messageBox match {
      case None       => this
      case Some(mbox) => mbox.addMessageBox(this)
    }
  }

  def isEmpty: Boolean = if (this == MessageBox.EmptyMessageBox) { true } else {
    keys.isEmpty
  }

  def messages(key: String): List[String] = messagesMap.get(key) match {
    case None           => Nil
    case Some(messages) => messages.messageList.reverse
  }

  def messages(key: Option[String]): List[String] = key match {
    case None     => messages
    case Some(ky) => messages(ky)
  }

  def messages: List[String] = (keys.reverse).foldLeft(Nil: List[String])(
    (acc, key) => acc ++ messages(key))

}

/** Companion Object of [jp.que.ti.scalatla.valid.MessageBox] */
object MessageBox {
  val EmptyMessageBox = new MessageBox()

  def apply() = EmptyMessageBox

  def apply(key: String, message: String) = EmptyMessageBox add (null2Empty(key), message)

  def apply(key: String, messages: List[String]) = {
    val ky = null2Empty(key)
    messages match {
      case Nil   => EmptyMessageBox
      case other => messages.foldLeft(new MessageBox) { (acc, message) => acc add (ky, message) }
    }
  }

  def unapply(m: MessageBox) = Option(m.messagesMap, m.keys)

  private def null2Empty(key: String) = if (key == null) { "" } else { key }
}
