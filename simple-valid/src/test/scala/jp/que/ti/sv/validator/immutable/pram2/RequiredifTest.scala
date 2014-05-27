package jp.que.ti.sv.validator.immutable.pram2

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import jp.que.ti.sv.MessageBox
import jp.que.ti.sv.ParameterInfo
import jp.que.ti.sv.util.MessageResource
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class RequiredifTest extends FunSuite {

  private def validRequiredif(paramVal: Option[String], paramValSub1: Option[String]): MessageBox =
    Requiredif(ParameterInfo("paramSub1", "p4MsgSub1")).valid((paramVal, paramValSub1), ParameterInfo("param1", "p4Msg"), MessageBox())

  /** エラーメッセージを作成します。引数 length は、validatorに指定する長さ   */
  private def errorMessageBox: MessageBox =
    MessageBox("param1", MessageResource.message("requiredif", "p4Msg", "p4MsgSub1"))

  //***************************************

  test("error massage.") {
    var mb = validRequiredif(None, Option("paramSub1"))
    val msg = MessageResource.message("requiredif", "p4Msg", "p4MsgSub1")
    assert { (mb == MessageBox("param1", msg)) }
  }

  test("message key.") {
    assert { Requiredif(ParameterInfo("paramSub1", "p4MsgSub1")).messageKey == "requiredif" }
  }

  test("""when the parameter exsists and the paramSub1 exsists .""") {
    var mb = validRequiredif(Some("hoge"), Some("paramSub1"))
    assert { (mb == MessageBox()) }
  }

  test("""when the paramSub1 don't exsist ,too .""") {
    var mb = validRequiredif(None, None)
    assert { (mb == MessageBox()) }

    mb = validRequiredif(Some("hoge"), None)
    assert { (mb == MessageBox()) }

    mb = validRequiredif(Some("  \r\n　 \r\n　 \r\n　 \r\n　 \r\n　   \r\n　 "), None)
    assert { (mb == MessageBox()) }
  }

  test("""when the parameter don't exsists and the paramSub1 exsists .""") {
    var mb = validRequiredif(None, Some("foo"))
    val errorMBox = errorMessageBox
    assert { (mb == errorMBox) }

    mb = validRequiredif(Some(""), Some("foo"))
    assert { (mb == errorMBox) }

    mb = validRequiredif(Some("  \r\n　 \r\n　 \r\n　 \r\n　 \r\n　   \r\n　 "), Some("foo"))
    assert { (mb == errorMBox) }

  }
}