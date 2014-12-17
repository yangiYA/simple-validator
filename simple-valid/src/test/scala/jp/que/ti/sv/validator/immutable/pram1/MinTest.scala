package jp.que.ti.sv.validator.immutable.pram1

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import jp.que.ti.sv.MessageBox
import jp.que.ti.sv.ParameterInfo
import jp.que.ti.sv.util.MessageResource.message
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MinTest extends FunSuite {

  private def validMin(minValue: Int, paramVal: Option[String]): MessageBox =
    Min(minValue).valid(paramVal, ParameterInfo("param1", "p4Msg"), MessageBox())

  private def validMin(minValue: Long, paramVal: Option[String]): MessageBox =
    Min(minValue).valid(paramVal, ParameterInfo("param1", "p4Msg"), MessageBox())

  private def errorMessageBoxMin(minValue: Long, itemName: String = "p4Msg"): MessageBox =
    MessageBox("param1", message(MinValue(1).messageKey, itemName, minValue + ""))

  private def errorMessageBoxNumber(itemName: String = "p4Msg"): MessageBox =
    MessageBox("param1", message(Number().messageKey, itemName))

  test("""when the parameter is greater or "==" .""") {
    var mb = validMin(1, Some("1"))
    assert { (mb == MessageBox()) }

    mb = validMin(4, Option("5"))
    assert { (mb == MessageBox()) }

    mb = validMin(Long.MinValue, Option("987454321098765432"))
    assert { (mb == MessageBox()) }

    mb = validMin(Int.MaxValue, Option((Int.MaxValue + 1L) + ""))
    assert { (mb == MessageBox()) }

  }

  test("""when the parameter is less .""") {
    var mb = validMin(2, Some("0"))
    assert { (mb == errorMessageBoxMin(2)) }

    mb = validMin(5, Option("4"))
    assert { (mb == errorMessageBoxMin(5)) }

    mb = validMin(Int.MaxValue + 1L, Option(Int.MaxValue + ""))
    assert { (mb == errorMessageBoxMin(Int.MaxValue + 1L)) }

    mb = validMin(Long.MaxValue, Option((Long.MaxValue - 1) + ""))
    assert { (mb == errorMessageBoxMin(Long.MaxValue)) }

  }

  test("""when the parameter is not Number .""") {
    var mb = validMin(1, Some("hoge"))
    assert { (mb == errorMessageBoxNumber()) }
  }

}