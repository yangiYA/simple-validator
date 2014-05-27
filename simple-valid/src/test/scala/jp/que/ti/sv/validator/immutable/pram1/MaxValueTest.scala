package jp.que.ti.sv.validator.immutable.pram1

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import jp.que.ti.sv.MessageBox
import jp.que.ti.sv.ParameterInfo
import jp.que.ti.sv.util.MessageResource._
import jp.que.ti.sv.util.MessageResource

@RunWith(classOf[JUnitRunner])
class MaxValueTest extends FunSuite {

  private def validMaxValue(maxValue: Int, paramVal: Option[String]): MessageBox =
    MaxValue(maxValue).valid(paramVal, ParameterInfo("param1", "p4Msg"), MessageBox())

  private def errorMessageBox(minlength: Int, itemName: String = "p4Msg"): MessageBox =
    MessageBox("param1", message(Minlength(1).messageKey, itemName, minlength + ""))

  //***************************************

  test("error massage.") {
    val value = 10
    var mb = validMaxValue(value, Option("11"))
    val msg = MessageResource.message("maxvalue", "p4Msg", value + "")
    assert { (mb == MessageBox("param1", msg)) }
  }

  test("message key.") {
    assert { MaxValue(1).messageKey == "maxvalue" }
  }

  test("""when the parameter is "<" or "==" .""") {
    var mb = validMaxValue(1, Some("1"))
    assert { (mb == MessageBox()) }

    mb = validMaxValue(2, Option("1"))
    assert { (mb == MessageBox()) }

    mb = validMaxValue(9999999999L, Option("8888888888"))
    assert { (mb == MessageBox()) }

  }

  //  test("""when the parameter is ">=" or "==" .""") {
  //    var mb = validMinlength(1, None)
  //    assert { (mb == MessageBox()) }
  //
  //    mb = validMinlength(2, Option(" \r\n　"))
  //    assert { (mb == MessageBox()) }
  //
  //    mb = validMinlength(3, Option("123"))
  //    assert { (mb == MessageBox()) }
  //
  //    mb = validMinlength(4, Option("12345"))
  //    assert { (mb == MessageBox()) }
  //
  //    mb = validMinlength(5, Option(" 12345 "))
  //    assert { (mb == MessageBox()) }
  //
  //    mb = validMinlength(6, Option(" \r\n　123456　\t "))
  //    assert { (mb == MessageBox()) }
  //  }
  //
  //  test("""when the parameter is ">=" or "==" . (long data case) """) {
  //    var mb = validMinlength(50, Option("12345678901234567890123456789012345678901234567890")) //50文字
  //    assert { (mb == MessageBox()) }
  //
  //    mb = validMinlength(50, Option("123456789012345678901234567890123456789012345678901")) //51文字
  //    assert { (mb == MessageBox()) }
  //  }
  //
  //  test("""when the parameter is "<"  .""") {
  //    var mb = validMinlength(4, Option("123"))
  //    assert { (mb == errorMessageBox(4)) }
  //
  //    mb = validMinlength(5, Option(" 1234 "))
  //    assert { (mb == errorMessageBox(5)) }
  //
  //    mb = validMinlength(6, Option(" \r\n　12345　\t "))
  //    assert { (mb == errorMessageBox(6)) }
  //  }
  //
  //  test("""when the parameter is "<"  . (long data case) """) {
  //    var mb = validMinlength(50, Option("1234567890123456789012345678901234567890123456789")) //49文字
  //    assert { (mb == errorMessageBox(50)) }
  //  }

}