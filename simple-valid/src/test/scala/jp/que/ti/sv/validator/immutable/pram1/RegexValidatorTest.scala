package jp.que.ti.sv.validator.immutable.pram1

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import jp.que.ti.sv.MessageBox
import jp.que.ti.sv.ParameterInfo
import jp.que.ti.sv.util.MessageResource
import jp.que.ti.sv.util.MessageResource.message
import org.scalatest.junit.JUnitRunner
import scala.util.matching.Regex

@RunWith(classOf[JUnitRunner])
class RegexValidatorTest extends FunSuite {

  private def validRegex(regex: Regex, paramVal: Option[String]): MessageBox =
    RegexValidator(regex).valid(paramVal, ParameterInfo("param1", "p4Msg"), MessageBox())

  private def errorMessageBox(regex: Regex, itemName: String = "p4Msg"): MessageBox =
    MessageBox("param1", message(RegexValidator(regex).messageKey, itemName))

  //***************************************

  test("message key.") {
    assert { RegexValidator("""a""".r).messageKey == "invalid" }
  }

  test("error massage.") {
    var mb = validRegex("""not match""".r, Option("foo"))
    val msg = MessageResource.message("invalid", "p4Msg")
    assert { (mb == MessageBox("param1", msg)) }
  }

  test("""match case test. It must be OK.""") {
    var mb = validRegex("""^aBcD$""".r, Option("aBcD"))
    assert { (mb == MessageBox()) }

    mb = validRegex("""^aBcD$""".r, Option(" aBcD "))
    assert { (mb == MessageBox()) }

    mb = validRegex("""^aBcD$""".r, Option(" \r\n　aBcD \r\n　"))
    assert { (mb == MessageBox()) }
  }

  test("""partial match case test. It must be OK.""") {
    var mb = validRegex(""".*[aA][B].*""".r, Option("e57897592875aB128329492"))
    assert({ mb == MessageBox() }, "fail!! " + """.*[aA][B].*""")

    mb = validRegex("""AA.*ZZ""".r, Option(" \r\n　mmmmmAA652387658275ZZtitititi \r\n　"))
    assert({ mb == MessageBox() }, "fail!! " + """AA.*ZZ""")
  }

  test("""not match case test. It must be Error.""") {
    var mb = validRegex("""^aBcD$""".r, Option("AbCd"))
    assert { (mb == errorMessageBox("""^aBcD$""".r)) }

  }

}