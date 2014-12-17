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

  private def validMaxValue(maxValue: Long, paramVal: Option[String]): MessageBox =
    MaxValue(maxValue).valid(paramVal, ParameterInfo("param1", "p4Msg"), MessageBox())

  private def errorMessageBox(maxValue: Long, itemName: String = "p4Msg"): MessageBox =
    MessageBox("param1", message(MaxValue(1L).messageKey, itemName, maxValue + ""))

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

  test("""when the parameter is less or "==" .""") {
    var mb = validMaxValue(1, Some("1"))
    assert { (mb == MessageBox()) }

    mb = validMaxValue(2, Option("1"))
    assert { (mb == MessageBox()) }

    mb = validMaxValue(9999999999L, Option("8888888888"))
    assert { (mb == MessageBox()) }

    mb = validMaxValue(Int.MaxValue, Option((Int.MaxValue - 1) + ""))
    assert { (mb == MessageBox()) }

  }

  test("""when the parameter is greater .""") {
    var mb = validMaxValue(1, Some("2"))
    assert { (mb == errorMessageBox(1)) }

    mb = validMaxValue(2, Option("3"))
    assert { (mb == errorMessageBox(2)) }

    mb = validMaxValue(8888888888L, Option("9999999999"))
    assert { (mb == errorMessageBox(8888888888L)) }

    mb = validMaxValue(Int.MaxValue, Option((Int.MaxValue + 100L) + ""))
    assert { (mb == errorMessageBox(Int.MaxValue)) }

  }

  test("""when the parameter is not Number .""") {
    var mb = validMaxValue(1, Some("hoge"))
    assert { (mb == MessageBox()) }
  }

}