package jp.que.ti.sv.validator.immutable.pram1

import jp.que.ti.sv.util.MessageResource._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import jp.que.ti.sv.util.MessageResource
import jp.que.ti.sv.MessageBox
import jp.que.ti.sv.ParameterInfo

@RunWith(classOf[JUnitRunner])
class MaxTest extends FunSuite {

  private def validMax(maxValue: Int, paramVal: Option[String]): MessageBox =
    Max(maxValue).valid(paramVal, ParameterInfo("param1", "p4Msg"), MessageBox())

  private def validMax(maxValue: Long, paramVal: Option[String]): MessageBox =
    Max(maxValue).valid(paramVal, ParameterInfo("param1", "p4Msg"), MessageBox())

  private def errorMessageBoxMax(maxValue: Long, itemName: String = "p4Msg"): MessageBox =
    MessageBox("param1", message(MaxValue(1).messageKey, itemName, maxValue + ""))

  private def errorMessageBoxNumber(itemName: String = "p4Msg"): MessageBox =
    MessageBox("param1", message(Number().messageKey, itemName))

  test("""when the parameter is "<" or "==" .""") {
    var mb = validMax(1, Some("1"))
    assert { (mb == MessageBox()) }

    mb = validMax(2, Option("1"))
    assert { (mb == MessageBox()) }

    mb = validMax(9999999999L, Option("8888888888"))
    assert { (mb == MessageBox()) }

  }

  test("""when the parameter is ">" .""") {
    var mb = validMax(1, Some("2"))
    assert { (mb == errorMessageBoxMax(1)) }

    mb = validMax(2, Option("3"))
    assert { (mb == errorMessageBoxMax(2)) }

    mb = validMax(8888888888L, Option("9999999999"))
    assert { (mb == errorMessageBoxMax(8888888888L)) }

  }

  test("""when the parameter is not Number .""") {
    var mb = validMax(1, Some("hoge"))
    assert { (mb == errorMessageBoxNumber()) }
  }

}