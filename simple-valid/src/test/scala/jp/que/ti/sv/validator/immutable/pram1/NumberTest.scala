package jp.que.ti.sv.validator.immutable.pram1

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import jp.que.ti.sv.MessageBox
import jp.que.ti.sv.ParameterInfo
import jp.que.ti.sv.util.MessageResource
import jp.que.ti.sv.util.MessageResource.message
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class NumberTest extends FunSuite {

  private def validNumber(paramVal: Option[String]): MessageBox =
    Number().valid(paramVal, ParameterInfo("param1", "p4Msg"), MessageBox())

  /** エラーメッセージを作成します。引数 length は、validatorに指定する長さ   */
  private def errorMessageBox(itemName: String = "p4Msg"): MessageBox =
    MessageBox("param1", message(Number().messageKey, itemName))

  //***************************************

  test("error massage.") {
    var mb = validNumber(Option("aaa"))
    val msg = MessageResource.message("number", "p4Msg")
    assert { (mb == MessageBox("param1", msg)) }
  }

  test("message key.") {
    assert { Number().messageKey == "number" }

  }

  test("""when the parameter is number .""") {
    var mb = validNumber(Option("0"))
    assert { (mb == MessageBox()) }

    mb = validNumber(Option("12345678901234567890"))
    assert { (mb == MessageBox()) }

    mb = validNumber(Option("1234567890１２３４５６７８９０"))
    assert { (mb == MessageBox()) }

    mb = validNumber(Option("  \r\n　 \r\n　 1２  \r\n　 \r\n　 "))
    assert { (mb == MessageBox()) }

  }

  test("""when the parameter is not number .""") {
    var mb = validNumber(Option(" あ  "))
    assert { (mb == errorMessageBox()) }

    mb = validNumber(Option("1234567890a1234567890"))
    assert { (mb == errorMessageBox()) }

    mb = validNumber(Option("1234567890*１２３４５６７８９０"))
    assert { (mb == errorMessageBox()) }
  }

  test("""when the parameter is empty .""") {
    var mb = validNumber(Option(""))
    assert { (mb == MessageBox()) }

    mb = validNumber(Option("  "))
    assert { (mb == MessageBox()) }

    mb = validNumber(Option("  \r\n　 \r\n　 "))
    assert { (mb == MessageBox()) }
  }
}