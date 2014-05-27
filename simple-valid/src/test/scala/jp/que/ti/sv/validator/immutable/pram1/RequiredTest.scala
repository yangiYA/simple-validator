package jp.que.ti.sv.validator.immutable.pram1

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import jp.que.ti.sv.MessageBox
import jp.que.ti.sv.ParameterInfo
import jp.que.ti.sv.util.MessageResource._
import jp.que.ti.sv.util.MessageResource

@RunWith(classOf[JUnitRunner])
class RequiredTest extends FunSuite {

  private def validRequired(paramVal: Option[String]): MessageBox =
    Required().valid(paramVal, ParameterInfo("param1", "p4Msg"), MessageBox())

  private def normalErrorMessageBox(itemName: String = "p4Msg"): MessageBox =
    MessageBox("param1", message(Required().messageKey, itemName))

  //***************************************

  test("error massage.") {
    var mb = validRequired(Option(""))
    val msg = MessageResource.message("required", "p4Msg")
    assert { (mb == MessageBox("param1", msg)) }
  }

  test("when the parameter exists.") {
    var mb = validRequired(Option("aaa"))
    assert { (mb == MessageBox()) }
  }

  test("when the parameter dosen't exist.") {
    var mb = validRequired(None)
    assert { mb == normalErrorMessageBox() }

  }

  test("when the parameter is empty string.") {
    var mb = validRequired(Option(""))
    assert { mb == normalErrorMessageBox() }

  }

  test("when the parameter is fullwidth spaces only.") {
    var mb = validRequired(Option("　　　"))
    assert { mb == normalErrorMessageBox() }

  }

}