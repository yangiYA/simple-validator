package jp.que.ti.sv.validator.immutable.pram1

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import jp.que.ti.sv.MessageBox
import jp.que.ti.sv.ParameterInfo
import jp.que.ti.sv.util.MessageResource._
import jp.que.ti.sv.util.MessageResource

@RunWith(classOf[JUnitRunner])
class MinValueTest extends FunSuite {

  private def validMinValue(minValue: Int, paramVal: Option[String]): MessageBox =
    MinValue(minValue).valid(paramVal, ParameterInfo("param1", "p4Msg"), MessageBox())

  private def validMinValue(minValue: Long, paramVal: Option[String]): MessageBox =
    MinValue(minValue).valid(paramVal, ParameterInfo("param1", "p4Msg"), MessageBox())

  //  private def errorMessageBox(minValue: Int, itemName: String = "p4Msg"): MessageBox =
  //    MessageBox("param1", message(MinValue(1).messageKey, itemName, minValue + ""))

  private def errorMessageBox(minValue: Long, itemName: String = "p4Msg"): MessageBox =
    MessageBox("param1", message(MinValue(1L).messageKey, itemName, minValue + ""))

  //***************************************

  test("error massage.") {
    val value = 10
    var mb = validMinValue(value, Option("2"))
    val msg = MessageResource.message("minvalue", "p4Msg", value + "")
    assert { (mb == MessageBox("param1", msg)) }
  }

  test("message key.") {
    assert { MinValue(1).messageKey == "minvalue" }
  }

  test("""when the parameter  is greater or "==" .""") {
    var mb = validMinValue(1, Some("1"))
    assert { (mb == MessageBox()) }

    mb = validMinValue(1, Option("2"))
    assert { (mb == MessageBox()) }

    mb = validMinValue(Int.MaxValue, Option((Int.MaxValue + 100L) + ""))
    assert { (mb == MessageBox()) }

    mb = validMinValue(Int.MaxValue + 1L, Option((Int.MaxValue + 100L) + ""))
    assert { (mb == MessageBox()) }

  }

  test("""when the parameter is less. .""") {
    var mb = validMinValue(2, Some("1"))
    assert { (mb == errorMessageBox(2)) }

    mb = validMinValue(5, Option("4"))
    assert { (mb == errorMessageBox(5)) }

    mb = validMinValue(Long.MaxValue, Option((Int.MaxValue + 1) + ""))
    assert { (mb == errorMessageBox(Long.MaxValue)) }

    mb = validMinValue(Long.MaxValue, Option((Int.MaxValue) + ""))
    assert { (mb == errorMessageBox(Long.MaxValue)) }

  }

  test("""when the parameter is not Number .""") {
    var mb = validMinValue(1, Some("hoge"))
    assert { (mb == MessageBox()) }
  }

}