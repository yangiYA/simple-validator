package jp.que.ti.sv.validator.immutable.pram1

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import jp.que.ti.sv.MessageBox
import jp.que.ti.sv.ParameterInfo
import jp.que.ti.sv.util.MessageResource._
import jp.que.ti.sv.util.MessageResource

@RunWith(classOf[JUnitRunner])
class RangeLengthTest extends FunSuite {

  private def validRangeLength(minlength: Int, maxlength: Int, paramVal: Option[String]): MessageBox =
    RangeLength(minlength, maxlength).valid(paramVal, ParameterInfo("param1", "p4Msg"), MessageBox())

  private def errorMessageBoxMin(minlength: Int, itemName: String = "p4Msg"): MessageBox =
    MessageBox("param1", message(Minlength(minlength).messageKey, itemName, minlength + ""))

  private def errorMessageBoxMax(maxlength: Int, itemName: String = "p4Msg"): MessageBox =
    MessageBox("param1", message(Maxlength(maxlength).messageKey, itemName, maxlength + ""))

  //***************************************

  test("error massage.") {
    var mb = validRangeLength(2, 3, Option("1"))
    var msg = MessageResource.message("minlength", "p4Msg", "2")
    assert { (mb == MessageBox("param1", msg)) }

    mb = validRangeLength(2, 3, Option("1234"))
    msg = MessageResource.message("maxlength", "p4Msg", "3")
    assert { (mb == MessageBox("param1", msg)) }
  }

  test("""when the parameter is "min <= parameter <= max" .""") {
    var mb = validRangeLength(2, 4, Option("ab"))
    assert { (mb == MessageBox()) }

    mb = validRangeLength(2, 4, Option("abcd"))
    assert { (mb == MessageBox()) }

    mb = validRangeLength(2, 4, Option("  \r\n　 \r\n　 \r\n　 \r\n　 \r\n　1234  \r\n　 \r\n　 \r\n　 \r\n　 \r\n　"))
    assert { (mb == MessageBox()) }
  }

  test("""when the parameter is " parameter < min " .""") {
    var mb = validRangeLength(2, 4, Option("a"))
    assert { (mb == errorMessageBoxMin(2)) }

    mb = validRangeLength(2, 4, Option("  \r\n　 \r\n　 \r\n　 \r\n　 \r\n　1  \r\n　 \r\n　 \r\n　 \r\n　 \r\n　"))
    assert { (mb == errorMessageBoxMin(2)) }
  }

  test("""when the parameter is " max < parameter " .""") {
    var mb = validRangeLength(2, 4, Option("abcde"))
    assert { (mb == errorMessageBoxMax(4)) }
  }

}