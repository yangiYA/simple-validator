package jp.que.ti.sv

/**
 *  パラメータのKEYと、パラメータをエラーメッセージでどのように表示するかを保持したクラス
 *  <ol>
 *  <li>[[jp.que.ti.sv.ParameterInfo#parameter]] ... パラメータのKEY</li>
 *  <li>[[jp.que.ti.sv.ParameterInfo#paramName4Message]] ... エラーメッセージで表示するパラメータ名称</li>
 *  </ol>
 *  @param parameter パラメータのKEY
 */
abstract class ParameterInfo(

  /** パラメータのKEY */
  val parameter: String) {

  /**
   * エラーメッセージで表示するパラメータ名称をパラメータのkeyとは別に指定したい場合にはこの値を設定する。
   * 設定しない場合は、None を返却する。
   */
  def paramName4Message: Option[String]

  /**
   * エラーメッセージで表示するパラメータ名称を取得します。
   *  paramName4Message が設定されて
   *  いればその値。なければ、parameter の値が返却されます
   */
  def parameterNameForMessage: String

  /** parameterNameForMessage の別名 */
  def nam4Msg = parameterNameForMessage

}

object ParameterInfo {
  def apply(parameter: String) = new ParameterInfoWithoutParamName4Message(parameter)

  def apply(parameter: String //
  , paramName4Message: String //
  ) = if (parameter == paramName4Message) {
    new ParameterInfoWithoutParamName4Message(parameter)
  } else {
    new ParameterInfoWithParamName4Message(parameter, paramName4Message)
  }

  def unapply(p: ParameterInfo) = (p.parameter, p.paramName4Message)
}

/**
 *  ParameterInfo の実装クラス
 *  このクラスは、paramName4Message の情報を持つクラスです。
 *  @param parameter パラメータのKEY
 *  @param paramName4Message エラーメッセージで表示するパラメータ名称
 */
protected class ParameterInfoWithParamName4Message(
  parameter: String //
  , protected val paramName4MessageString: String //
  ) extends ParameterInfo(parameter) {

  /** エラーメッセージで表示するパラメータ名称を Option 型で返却します。 */
  val paramName4Message: Option[String] = Option(paramName4MessageString)

  /** エラーメッセージで表示するパラメータ名称を取得します。*/
  def parameterNameForMessage: String = paramName4MessageString

}

/**
 *  ParameterInfo の実装クラス
 *  このクラスは、paramName4Message の情報を持たないクラスです。
 *  @param parameter パラメータのKEY
 */
protected class ParameterInfoWithoutParamName4Message(
  parameter: String)
  extends ParameterInfo(parameter) {

  /**
   * エラーメッセージで表示するパラメータ名称を Option 型で返却します。
   * このメソッドは必ず None を返却します。
   */
  def paramName4Message: Option[String] = None

  /**
   * エラーメッセージで表示するパラメータ名称として、パラメータのKEYを返却します。
   * このメソッドの返却値は、parameter と同じです
   */
  def parameterNameForMessage: String = parameter
}
