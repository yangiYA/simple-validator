package jp.que.ti.sv.util

import org.scalatest.enablers.Length
import jp.que.ti.sv.MessageBox

object StringUtil {
  /**
   * 全角スペースもトリムします。
   * @param value トリム対象の文字列
   * @return トリム後の文字列
   */
  def trim(value: String): String = {
    val valueLen = value.length()
    var len = valueLen

    if (value == null || valueLen == 0) {
      value
    } else {
      var st = 0

      val valChars: Array[Char] = value.toCharArray()

      while ((st < len) && ((valChars(st) <= ' ') || (valChars(st) == '　'))) {
        st = st + 1
      }

      while ((st < len) && ((valChars(len - 1) <= ' ') || (valChars(len - 1) == '　'))) {
        len = len - 1
      }

      if (st > 0 || len < valueLen) {
        value.substring(st, len)
      } else {
        value
      }
    }
  }

  /**
   * 全角数字を半角数字に変換します
   */
  def toHalfSizeNumberCharacter(numberChar: String): String = {
    if (numberChar == null) {
      return numberChar
    }

    var isChanged = false
    val tmpCharList: List[Char] = numberChar.toCharArray().toList.foldLeft(List[Char]()) { (acc, c) =>
      if (c >= '０' && c <= '９') {
        isChanged = true
        (c - ('０' - '0')).asInstanceOf[Char] :: acc // 半角数字に変換してからアキュムレータに追加
      } else {
        c :: acc
      }
    }
    if (isChanged) {
      new String(tmpCharList.reverse.toArray)
    } else {
      numberChar
    }
  }

  def isNumberOnly(str: String): Boolean = {
    if (str == null) {
      return false
    }
    val inTmpStr = trim(str)
    if (inTmpStr.length < 1) {
      return false
    }

    val it = inTmpStr.toCharArray().toList.iterator

    while (it.hasNext) {
      val ch = it.next
      if ('0' <= ch && ch <= '9') {
        //数字、次の文字をチェック
      } else {
        return false
      }
    }
    true //ここに来たら、全部数字だったということ
  }

}