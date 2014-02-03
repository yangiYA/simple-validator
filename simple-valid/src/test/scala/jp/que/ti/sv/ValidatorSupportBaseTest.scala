package jp.que.ti.sv

import org.junit.runner.RunWith
import org.mockito.Mockito
import org.scalatest._
import org.scalatest.FunSuite
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.slf4j.LoggerFactory

import javax.servlet.http.HttpServletRequest

@RunWith(classOf[JUnitRunner])
class ValidatorSupportBaseTest extends FunSuite with ValidatorSupportBase {
  val log = LoggerFactory.getLogger(getClass())

  def request: HttpServletRequest = { //*** テストデータ用リクエストオブジェクト
    val rq: HttpServletRequest = Mockito.mock(classOf[HttpServletRequest]);

    setRequestParameter("item1", Array("item-1-1"), rq) //8文字
    setRequestParameter("item2withSpace", Array(" item2  "), rq) //5文字 +  3space
    setRequestParameter("item3FullWord", Array("Ｉｔｅｍ３フルワード"), rq) // 10文字
    setRequestParameter("itemNull", Array(), rq)
    setRequestParameter("itemEmpty", Array(""), rq)
    setRequestParameter("itemASpace", Array(" "), rq)
    setRequestParameter("itemASpace", Array("   "), rq)

    rq
  }

  private def setRequestParameter(key: String, params: Array[String], rq: HttpServletRequest) = {
    if (params.size == 0) {
      Mockito.when(rq.getParameter(key)).thenReturn(null)
    } else {
      Mockito.when(rq.getParameter(key)).thenReturn(params(0))
    }
    Mockito.when(rq.getParameterValues(key)).thenReturn(params)
  }

  //*************************************
  test("required test") {

    var validator = required("item1")
    var result: MessageBox = validator.valid()

    assert(result.isEmpty === true)
    assert(result === MessageBox())

    validator = required("item2withSpace")
    result = validator.valid()

    assert(result.isEmpty === true)
    assert(result === MessageBox())

    validator = required("itemNull")
    result = validator.valid()
    assert(result.keys == List("itemNull"))

    validator = required("itemEmpty")
    result = validator.valid()
    assert(result.keys == List("itemEmpty"))

    validator = required("itemASpace")
    result = validator.valid()
    assert(result.keys == List("itemASpace"))

    validator = required("itemASpace")
    result = validator.valid()
    assert(result.keys == List("itemASpace"))

    //*** paramName4Message test ***
    validator = required("itemEmpty", "ParamName4Message")
    result = validator.valid()

    assert(result.keys == List("itemEmpty"))
    var msgs = result.messages("itemEmpty")
    assert(msgs.length == 1)
    var msg = msgs.head
    assert(msg.contains("ParamName4Message"), "メッセージに、パラメータで指定した項目名が含まれているか確認 : msg=" + msg)

  }

  //*************************************
  test("maxlength test") {

    //*** item1 ***
    var validator = maxlength("item1", 8) // value=item-1-1
    var result: MessageBox = validator.valid()
    log.debug("result={}", result)

    assert(result.isEmpty === true) //エラーにならないはず
    assert(result === MessageBox()) //エラーにならないはず

    //***
    validator = maxlength("item1", 7)
    result = validator.valid()
    assert(result.keys == List("item1"), "Test Fail Info ■result=" + result + "■")
    assert(result.keys == List("item1"))

    //*** item2withSpace ***
    validator = maxlength("item2withSpace", 5)
    result = validator.valid()
    assert(result.isEmpty === true) //エラーにならないはず
    assert(result === MessageBox()) //エラーにならないはず

    //***
    validator = maxlength("item2withSpace", 4)
    result = validator.valid()
    assert(result.keys == List("item2withSpace"), "Test Fail Info ■result=" + result + "■")

    //*** item3FullWord ***
    validator = maxlength("item3FullWord", 10)
    result = validator.valid()
    assert(result.isEmpty === true) //エラーにならないはず
    assert(result === MessageBox()) //エラーにならないはず

    //***
    validator = maxlength("item3FullWord", 9)
    result = validator.valid()
    assert(result.keys == List("item3FullWord"), "Test Fail Info ■result=" + result + "■")

    //*** noItem ***
    validator = maxlength("noItem", 1)
    result = validator.valid()
    assert(result.isEmpty === true) //エラーにならないはず
    assert(result === MessageBox()) //エラーにならないはず

    //*** paramName4Message test ***
    validator = maxlength("item1", 7, "ParamName4Message")
    result = validator.valid()

    assert(result.keys == List("item1"))
    var msgs = result.messages("item1")
    assert(msgs.length == 1)
    var msg = msgs.head
    assert(msg.contains("ParamName4Message"), "メッセージに、パラメータで指定した項目名が含まれているか確認 : msg=" + msg)
  }

  //*************************************
  test("minlength test") {

    //*** item1 ***
    var validator = minlength("item1", 8) // value=item-1-1
    var result: MessageBox = validator.valid()
    log.debug("result={}", result)

    assert(result.isEmpty === true) //エラーにならないはず
    assert(result === MessageBox()) //エラーにならないはず

    //***
    validator = minlength("item1", 9)
    result = validator.valid()
    assert(result.keys == List("item1"), "Test Fail Info ■result=" + result + "■")
    assert(result.keys == List("item1"))

    //*** item2withSpace ***
    validator = minlength("item2withSpace", 5)
    result = validator.valid()
    assert(result.isEmpty === true) //エラーにならないはず
    assert(result === MessageBox()) //エラーにならないはず

    //***
    validator = minlength("item2withSpace", 6)
    result = validator.valid()
    assert(result.keys == List("item2withSpace"), "Test Fail Info ■result=" + result + "■")

    //*** item3FullWord ***
    validator = minlength("item3FullWord", 10)
    result = validator.valid()
    assert(result.isEmpty === true) //エラーにならないはず
    assert(result === MessageBox()) //エラーにならないはず

    //***
    validator = minlength("item3FullWord", 11)
    result = validator.valid()
    assert(result.keys == List("item3FullWord"), "Test Fail Info ■result=" + result + "■")

    //*** noItem ***
    validator = minlength("noItem", 1)
    result = validator.valid()
    assert(result.isEmpty === true) //エラーにならないはず
    assert(result === MessageBox()) //エラーにならないはず

    //*** paramName4Message test ***
    validator = minlength("item1", 9, "ParamName4Message")
    result = validator.valid()

    assert(result.keys == List("item1"))
    var msgs = result.messages("item1")
    assert(msgs.length == 1)
    var msg = msgs.head
    assert(msg.contains("ParamName4Message"), "メッセージに、パラメータで指定した項目名が含まれているか確認 : msg=" + msg)

  }

  //*************************************
  test("length test") {

    //*** item1 ***
    var validator = length("item1", 8) // value=item-1-1
    var result: MessageBox = validator.valid()
    log.debug("result={}", result)

    assert(result.isEmpty === true) //エラーにならないはず
    assert(result === MessageBox()) //エラーにならないはず

    //***
    validator = length("item1", 9)
    result = validator.valid()
    assert(result.keys == List("item1"), "Test Fail Info ■result=" + result + "■")
    assert(result.keys == List("item1"))

    //***
    validator = length("item1", 7)
    result = validator.valid()
    assert(result.keys == List("item1"), "Test Fail Info ■result=" + result + "■")
    assert(result.keys == List("item1"))

    //*** item2withSpace ***
    validator = length("item2withSpace", 5)
    result = validator.valid()
    assert(result.isEmpty === true) //エラーにならないはず
    assert(result === MessageBox()) //エラーにならないはず

    //***
    validator = length("item2withSpace", 6)
    result = validator.valid()
    assert(result.keys == List("item2withSpace"), "Test Fail Info ■result=" + result + "■")

    //***
    validator = length("item2withSpace", 4)
    result = validator.valid()
    assert(result.keys == List("item2withSpace"), "Test Fail Info ■result=" + result + "■")

    //*** item3FullWord ***
    validator = length("item3FullWord", 10)
    result = validator.valid()
    assert(result.isEmpty === true) //エラーにならないはず
    assert(result === MessageBox()) //エラーにならないはず

    //***
    validator = length("item3FullWord", 11)
    result = validator.valid()
    assert(result.keys == List("item3FullWord"), "Test Fail Info ■result=" + result + "■")

    //***
    validator = length("item3FullWord", 9)
    result = validator.valid()
    assert(result.keys == List("item3FullWord"), "Test Fail Info ■result=" + result + "■")

    //*** noItem ***
    validator = length("noItem", 1)
    result = validator.valid()
    assert(result.isEmpty === true) //エラーにならないはず
    assert(result === MessageBox()) //エラーにならないはず

    //*** paramName4Message test ***
    validator = length("item1", 9, "ParamName4Message")
    result = validator.valid()

    assert(result.keys == List("item1"))
    var msgs = result.messages("item1")
    assert(msgs.length == 1)
    var msg = msgs.head
    assert(msg.contains("ParamName4Message"), "メッセージに、パラメータで指定した項目名が含まれているか確認 : msg=" + msg)

  }

  //*************************************
  test("GenericValidator test") {

    //チェックロジック f ( 引数 testValue にパラメータが一致すればチェックOKと判定)
    val f = (param: Option[String], testValue: String) => {
      log.debug("param=" + param)

      param match {
        case None        => true
        case Some(value) => value == testValue
      }
    }

    //*** item1 ***
    var validator = genericValid(
      "item1" //
      , "required" //テスト用として、メッセージのキーを指定
      , x => f(x, "item-1-1") //
      )
    var result: MessageBox = validator.valid()
    assert(result.isEmpty === true, "Test Fail Info ■result=" + result + "■") //エラーにならないはず
    assert(result === MessageBox()) //エラーにならないはず

    //***
    validator = genericValid(
      "item1" //
      , "length" //テスト用として、メッセージのキーを指定
      , x => f(x, "item-1-100") //
      )
    result = validator.valid()
    assert(result.keys == List("item1"), "Test Fail Info ■result=" + result + "■")

    //*** paramName4Message test ***
    validator = genericValid(
      "item1" //
      , "length" //テスト用として、メッセージのキーを指定
      , x => f(x, "item-1-100") //
      , "ParamName4Message")
    result = validator.valid()

    assert(result.keys == List("item1"))
    var msgs = result.messages("item1")
    assert(msgs.length == 1)
    var msg = msgs.head
    assert(msg.contains("ParamName4Message"), "メッセージに、パラメータで指定した項目名が含まれているか確認 : msg=" + msg)
  }

  //*************************************
  test("between test") {

    //*** item1 ***
    var validator = minMaxLength("item1", 8, 8) // value=item-1-1
    var result: MessageBox = validator.valid()
    log.debug("result={}", result)

    assert(result.isEmpty === true) //エラーにならないはず
    assert(result === MessageBox()) //エラーにならないはず

    //***
    validator = minMaxLength("item1", 7, 8) // value=item-1-1
    result = validator.valid()

    assert(result.isEmpty === true) //エラーにならないはず
    assert(result === MessageBox()) //エラーにならないはず

    //***
    validator = minMaxLength("item1", 8, 9) // value=item-1-1
    result = validator.valid()

    assert(result.isEmpty === true) //エラーにならないはず
    assert(result === MessageBox()) //エラーにならないはず

    //***
    validator = minMaxLength("item1", 6, 7) // value=item-1-1
    result = validator.valid()
    assert(result.keys == List("item1"), "Test Fail Info ■result=" + result + "■")
    assert(result.keys == List("item1"))

    //***
    validator = minMaxLength("item1", 9, 10) // value=item-1-1
    result = validator.valid()
    assert(result.keys == List("item1"), "Test Fail Info ■result=" + result + "■")
    assert(result.keys == List("item1"))

    //***
    validator = minMaxLength("item2withSpace", 5, 5) // " item2  " , 5文字 +  3space
    result = validator.valid()
    assert(result.isEmpty === true) //エラーにならないはず
    assert(result === MessageBox()) //エラーにならないはず

    //***
    validator = minMaxLength("item2withSpace", 3, 4) // " item2  " , 5文字 +  3space
    result = validator.valid()
    assert(result.keys == List("item2withSpace"), "Test Fail Info ■result=" + result + "■")
    assert(result.keys == List("item2withSpace"))

    //***
    validator = minMaxLength("item2withSpace", 6, 7) // " item2  " , 5文字 +  3space
    result = validator.valid()
    assert(result.keys == List("item2withSpace"), "Test Fail Info ■result=" + result + "■")
    assert(result.keys == List("item2withSpace"))

    //***
    validator = minMaxLength("item3FullWord", 10, 10) // "Ｉｔｅｍ３フルワード" , 10文字
    result = validator.valid()
    assert(result.isEmpty === true) //エラーにならないはず
    assert(result === MessageBox()) //エラーにならないはず

    //***
    validator = minMaxLength("item3FullWord", 8, 9) // "Ｉｔｅｍ３フルワード" , 10文字
    result = validator.valid()
    assert(result.keys == List("item3FullWord"), "Test Fail Info ■result=" + result + "■")
    assert(result.keys == List("item3FullWord"))

    //***
    validator = minMaxLength("item3FullWord", 11, 12) // "Ｉｔｅｍ３フルワード" , 10文字
    result = validator.valid()
    assert(result.keys == List("item3FullWord"), "Test Fail Info ■result=" + result + "■")
    assert(result.keys == List("item3FullWord"))

    //*** noItem ***
    validator = minMaxLength("noItem", 100, 200)
    result = validator.valid()
    assert(result.isEmpty === true) //エラーにならないはず
    assert(result === MessageBox()) //エラーにならないはず

    //*** paramName4Message test (1) ***
    validator = minMaxLength("item1", 6, 7, Some("ParamName4Message")) // value=item-1-1
    result = validator.valid()
    assert(result.keys == List("item1"), "Test Fail Info ■result=" + result + "■")
    assert(result.keys == List("item1"))
    var msgs = result.messages("item1")
    assert(msgs.length == 1, "msgs=" + msgs)
    var msgHead = msgs.head
    assert(msgHead.contains("ParamName4Message"), "メッセージに、パラメータで指定した項目名が含まれているか確認 : msgHead=" + msgHead)

    //*** paramName4Message test (2) ***
    validator = minMaxLength("item1", 9, 10, Some("ParamName4Message")) // value=item-1-1
    result = validator.valid()
    assert(result.keys == List("item1"), "Test Fail Info ■result=" + result + "■")
    assert(result.keys == List("item1"))
    msgs = result.messages("item1")
    assert(msgs.length == 1, "msgs=" + msgs)
    msgHead = msgs.head
    assert(msgHead.contains("ParamName4Message"), "メッセージに、パラメータで指定した項目名が含まれているか確認 : msgHead=" + msgHead)

  }

}