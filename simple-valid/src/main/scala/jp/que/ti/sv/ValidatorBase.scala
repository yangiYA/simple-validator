package jp.que.ti.sv

import org.slf4j.LoggerFactory

import javax.servlet.http.HttpServletRequest

trait ValidatorIF {
  /** abstract method */
  protected def errorMessage: String;
  def valid(messageBox: Option[MessageBox]): Option[MessageBox]
  def valid(messageBox: MessageBox = MessageBox()): MessageBox
}

abstract class ValidatorBase(

  val paramName: String) extends ValidatorIF {

  protected val log = LoggerFactory.getLogger(getClass())

  /**
   *  abstract method
   *  チェック内容を定義する。
   *  通常このメソッドは、trait [jp.que.ti.vs.ValidOneParam]、または、[jp.que.ti.vs.ValidMultiParams]
   *  with で実装することを想定
   *  @return true の場合、チェックOK。 false の場合、チェックNG
   */
  protected def isValid: Boolean

  /** abstract method */
  protected def errorMessage: String;

  def valid(messageBox: Option[MessageBox]): Option[MessageBox] = messageBox match {
    case None     => Option(valid())
    case Some(ms) => Option(valid(ms))
  }

  def valid(messageBox: MessageBox = MessageBox()): MessageBox = {
    val validResult = isValid
    log.debug("|* isValid={}", validResult)

    if (validResult) {
      messageBox
    } else {
      log.debug("|* messageBox.add(<{}>, <{}>)", paramName, errorMessage)
      messageBox.add(paramName, errorMessage)
    }
  }

  def message(key: => String): String =
    MessageResource.message(key)

  def message(key: => String, arg1: String): String =
    MessageResource.message(key, arg1)

  def message(key: String, arg1: => String, arg2: => String, args: String*): String =
    MessageResource.message(key, arg1, arg2, args: _*)

  def message(key: String, args: => List[String]): String =
    args match {
      case Nil                  => message(key)
      case arg1 :: Nil          => message(key, arg1)
      case arg1 :: arg2 :: Nil  => message(key, arg1, arg2)
      case arg1 :: arg2 :: tail => message(key, arg1, arg2, tail: _*)
    }

}

/**
 * @param servlet
 * @param paramName
 * @param messageKey
 */
abstract class ValidatorBaseWithMessageProp(

  paramName: String //
  , val messageKey: String //
  , protected val paramNameForMessage: Option[String] = None //
  ) extends ValidatorBase(paramName) {

  /** メッセージに表示する項目名 */
  protected def paramName4Message: String = paramNameForMessage match {
    case None        => paramName
    case Some(pName) => pName
  }

  override def errorMessage = {
    val msg = message(messageKey, paramName4Message)
    log.debug("""|* errorMessage : paramName={},messageKey={}, message=<{}>""", Array[Object](paramName, messageKey, msg))
    msg
  }

}

abstract class ValidatorBaseWithMessageProp4J2ee(
  paramName: String //
  , messageKey: String //
  , paramNameForMessage: Option[String] = None //
  ) extends ValidatorBaseWithMessageProp(paramName, messageKey, paramNameForMessage) with J2eeApiSupport

/**
 *  パラメータで指定されたパラメータ名で取得した先頭の値を検証する trait 。
 *  同名のパラメータは1つだけリクエストされる前提の場合に使用する。
 */
trait ValidOneParam { self: ValidatorBase =>

  /**
   *  abstract method
   *  引数 name に該当するパラメータを１つ取得する。
   *  @return true の場合、チェックOK。 false の場合、チェックNG
   */
  def param(name: String): Option[String]

  /**
   *  abstract method
   *  チェック内容を定義する。
   *  @return true の場合、チェックOK。 false の場合、チェックNG
   */
  protected def isValid(parames: Option[String]): Boolean

  /**
   *  チェック内容を定義する
   *  @return true の場合、チェックOK。 false の場合、チェックNG
   */
  protected def isValid: Boolean = {
    val prm = self.param(paramName)
    log.debug("self.param(paramName)={}", prm)
    isValid(prm)
  }

}

/**
 *  同名のパラメータ名が複数存在する場合、すべてのパラメータを検証できるようにするための trait
 */
trait ValidParamArray { self: ValidatorBase =>

  /**
   *  abstract method
   *  引数 name に該当するパラメータの一覧を取得する。
   *  @return true の場合、チェックOK。 false の場合、チェックNG
   */
  def multiParams(name: String): Seq[String]

  /**
   *  abstract method
   *  チェック内容を定義する
   *  @return true の場合、チェックOK。 false の場合、チェックNG
   */
  protected def isValid(params: Seq[String]): Boolean

  /**
   *  チェック内容を定義する
   *  @return true の場合、チェックOK。 false の場合、チェックNG
   */
  protected def isValid: Boolean = ValidParamArray.this.isValid(multiParams(paramName))

}

/**
 *  J2EE API を使ってパラメータを取得できるようにするための trait
 */
trait J2eeApiSupport { self: ValidatorBase =>

  def request: HttpServletRequest

  def param(name: String): Option[String] = {
    val rtnVal = Option(request.getParameter(name))
    log.debug("""|* Request param({})={} *|""", name, rtnVal)
    rtnVal
  }

  def multiParams(name: String): Seq[String] = {
    val params = request.getParameterMap().get(name)
    log.debug("""|* Request params({})=[{}] *|""", name, params)
    if (params == null) { Nil } else { params.toList }
  }

}