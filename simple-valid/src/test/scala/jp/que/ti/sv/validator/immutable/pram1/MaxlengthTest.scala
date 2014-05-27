package jp.que.ti.sv.validator.immutable.pram1

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import jp.que.ti.sv.MessageBox
import jp.que.ti.sv.ParameterInfo
import jp.que.ti.sv.util.MessageResource._
import jp.que.ti.sv.util.MessageResource

@RunWith(classOf[JUnitRunner])
class MaxlengthTest extends FunSuite {

  private def validMaxlength(maxlength: Int, paramVal: Option[String]): MessageBox =
    Maxlength(maxlength).valid(paramVal, ParameterInfo("param1", "p4Msg"), MessageBox())

  private def errorMessageBox(maxlength: Int, itemName: String = "p4Msg"): MessageBox =
    MessageBox("param1", message(Maxlength(maxlength).messageKey, itemName, maxlength + ""))

  //***************************************

  test("error massage.") {
    var mb = validMaxlength(2, Option("123"))
    val msg = MessageResource.message("maxlength", "p4Msg", "2")
    assert { (mb == MessageBox("param1", msg)) }
  }

  test("message key.") {
    assert { Maxlength(1).messageKey == "maxlength" }
  }

  test("""when the parameter is "<=" or "==" .""") {
    var mb = validMaxlength(12, Option("a"))
    assert { (mb == MessageBox()) }

    mb = validMaxlength(12, Option("  \r\n　 \r\n　 \r\n　 \r\n　 \r\n　a \r\n　 "))
    assert { (mb == MessageBox()) }

    mb = validMaxlength(12, Option("123456789abc"))
    assert { (mb == MessageBox()) }

    mb = validMaxlength(12, Option(" 123456789abc "))
    assert { (mb == MessageBox()) }

    mb = validMaxlength(12, Option(" \r\n　123456789abc　\t "))
    assert { (mb == MessageBox()) }
  }

  test("""when the parameter is "<=" or "==" . (long data case) """) {
    var mb = validMaxlength(50, Option("12345678901234567890123456789012345678901234567890")) //50文字
    assert { (mb == MessageBox()) }

     mb = validMaxlength(50, Option("1234567890123456789012345678901234567890123456789")) //49文字
    assert { (mb == MessageBox()) }
  }

  test("""when the parameter is ">"  .""") {
    var mb = validMaxlength(3, Option("1234"))
    assert { (mb == errorMessageBox(3)) }

    mb = validMaxlength(3, Option(" 1234 "))
    assert { (mb == errorMessageBox(3)) }

    mb = validMaxlength(3, Option(" \r\n　1234　\t "))
    assert { (mb == errorMessageBox(3)) }
  }

  test("""when the parameter is ">"  . (long data case) """) {
    var mb = validMaxlength(50, Option("123456789012345678901234567890123456789012345678901")) //51文字
    assert { (mb == errorMessageBox(50)) }
  }

}