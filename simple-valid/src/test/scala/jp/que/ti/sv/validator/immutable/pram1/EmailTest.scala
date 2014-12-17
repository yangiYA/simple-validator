package jp.que.ti.sv.validator.immutable.pram1

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import jp.que.ti.sv.MessageBox
import jp.que.ti.sv.ParameterInfo
import jp.que.ti.sv.util.MessageResource._
import jp.que.ti.sv.util.MessageResource

@RunWith(classOf[JUnitRunner])
class EmailTest extends FunSuite {

  private def validEmail(paramVal: Option[String]): MessageBox =
    Email().valid(paramVal, ParameterInfo("param1", "p4Msg"), MessageBox())

  /** エラーメッセージを作成します。引数 length は、validatorに指定する長さ   */
  private def errorMessageBox(itemName: String = "p4Msg"): MessageBox =
    MessageBox("param1", message(Email().messageKey, itemName))

  //***************************************

  test("error massage.") {
    var mb = validEmail(Option("???"))
    val msg = MessageResource.message("email", "p4Msg")
    assert { (mb == MessageBox("param1", msg)) }
  }

  test("message key.") {
    assert { Email().messageKey == "email" }
  }

  test("""when the parameter is email format.""") {
    var mb = validEmail(Option("hoge@foo.com"))
    assert { (mb == MessageBox()) }

    mb = validEmail(Option("foo.foo-abc+001@hoge.hoo.boo.jp"))
    assert { (mb == MessageBox()) }
  }

  test("""when the parameter is invalid email format. There is no attmark."""") {
    var mb = validEmail(Option("noAttmark"))
    assert { (mb == errorMessageBox()) }

    mb = validEmail(Option("noAttmark.co.com.jp"))
    assert { (mb == errorMessageBox()) }
  }

  test("""when the parameter is invalid email format. Last charactor "." """) {
    var mb = validEmail(Option("abc@foo.com."))
    assert { (mb == errorMessageBox()) }
  }

  test("""when the parameter is invalid email format. domain is too long. """) {
    var mb = validEmail(Option("abc@foo.coooooooom"))
    assert { (mb == errorMessageBox()) }
  }

}