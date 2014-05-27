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
class ValidatorSupportBaseRegexTest extends FunSuite
//with ValidatorSupportBase
{
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
//  test("regex test") {
//
//    var validator = regexValid("item1", "length" /*仮のメッセージID*/ , """item.*""".r)
//    var result: MessageBox = validator.valid()
//
//    assert(result.isEmpty === true, "fail? result=" + result)
//    assert(result === MessageBox())
//
//    validator = regexValid("item1", "length" /*仮のメッセージID*/ , """HOGE.*""".r)
//    result = validator.valid()
//    assert(result.keys == List("item1"))
//
//    //*** paramName4Message test ***
//    validator = regexValid("item1", "length" /*仮のメッセージID*/ , """HOGE.*""".r, "ParamName4Message")
//    result = validator.valid()
//
//    assert(result.keys == List("item1"))
//    var msgs = result.messages("item1")
//    assert(msgs.length == 1)
//    var msg = msgs.head
//    assert(msg.contains("ParamName4Message"), "メッセージに、パラメータで指定した項目名が含まれているか確認 : msg=" + msg)
//
//  }
}