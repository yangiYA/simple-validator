package jp.que.ti.sv.util

trait TrimIsEmptySupport {

  /**
   * 全角スペースもトリムします。
   * @param value トリム対象の文字列
   * @return トリム後の文字列
   */
  def trim(str: String): String = StringUtil.trim(str)

  /**
   * 空か否かを判定する。
   * トリムした後、空か否かを判定して、空の場合、trueを返却します
   * @return 空の場合、true を返却します
   */
  def isEmpty(value: Option[String]) =
    value.map(trim(_)).map(_.isEmpty()) match { /* trim して、空か否かを返却 */
      case None          => true
      case Some(isEmpty) => isEmpty
    }

}