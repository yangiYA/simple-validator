package jp.que.ti.sv.validator.immutable.pram1

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import jp.que.ti.sv.MessageBox
import jp.que.ti.sv.ParameterInfo
import jp.que.ti.sv.util.MessageResource._
import jp.que.ti.sv.util.MessageResource

@RunWith(classOf[JUnitRunner])
class LengthTest extends FunSuite {

  private def validLength(maxlength: Int, paramVal: Option[String]): MessageBox =
    Length(maxlength).valid(paramVal, ParameterInfo("param1", "p4Msg"), MessageBox())

  /** エラーメッセージを作成します。引数 length は、validatorに指定する長さ   */
  private def errorMessageBox(length: Int, itemName: String = "p4Msg"): MessageBox =
    MessageBox("param1", message(Length(length).messageKey, itemName, length + ""))

  //***************************************

  test("error massage.") {
    var mb = validLength(2, Option("123"))
    val msg = MessageResource.message("length", "p4Msg", "2")
    assert { (mb == MessageBox("param1", msg)) }
  }

  test("message key.") {
    assert { Length(1).messageKey == "length" }
  }

  test("""when the parameter is "==" .""") {
    var mb = validLength(5, Option("12345"))
    assert { (mb == MessageBox()) }

    mb = validLength(6, Option("  \r\n　 \r\n　 \r\n　 \r\n　 \r\n　123456 \r\n　 "))
    assert { (mb == MessageBox()) }

    mb = validLength(50, Option("01234567890123456789012345678901234567890123456789"))
    assert { (mb == MessageBox()) }
  }

  test("""when the parameter is ">" or "<" .""") {
    var mb = validLength(3, Option("1234"))
    assert { (mb == errorMessageBox(3)) }

    mb = validLength(2, Option(" a "))
    assert { (mb == errorMessageBox(2)) }

    mb = validLength(1, Option(" \r\n　ab　\t "))
    assert { (mb == errorMessageBox(1)) }
  }

  test("""when the parameter is ">" or "<" . (long data case)""") {
    var mb = validLength(49, Option("01234567890123456789012345678901234567890123456789"))
    assert { (mb == errorMessageBox(49)) }

    mb = validLength(51, Option("01234567890123456789012345678901234567890123456789"))
    assert { (mb == errorMessageBox(51)) }

  }
}