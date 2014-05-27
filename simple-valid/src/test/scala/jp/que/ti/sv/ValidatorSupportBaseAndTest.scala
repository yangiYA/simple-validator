package jp.que.ti.sv

import org.junit.runner.RunWith
import org.mockito.Mockito
import org.scalatest._
import org.scalatest.FunSuite
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.slf4j.LoggerFactory

import javax.servlet.http.HttpServletRequest

//@RunWith(classOf[JUnitRunner])
class ValidatorSupportBaseAndTest extends FunSuite
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
//  test("simple and test") {
//
//    var validator = and("item1", Option("ParamName4Message"), maxlength(8), minlength(5)) //item1 = "item-1-1"
//    var result: MessageBox = validator.valid()
//
//    assert(result.isEmpty === true, "fail? result=" + result)
//    assert(result === MessageBox())
//
//    //
//    validator = and("item1", Option("ParamName4Message"), maxlength(7), minlength(9))
//    result = validator.valid()
//    assert(result.keys == List("item1"))
//
//    assert(result.keys == List("item1"))
//    var msgs = result.messages("item1")
//    assert(msgs.length == 2)
//    var msg1 = msgs.head
//    var msg2 = (msgs.tail).head
//
//
//    log.debug(""" msg1={} """, msg1)
//    log.debug(""" msg2={} """, msg2)
//
//    assert(msg1.contains("ParamName4Message"), "メッセージに、パラメータで指定した項目名が含まれているか確認 : msg=" + msg1)
//    assert(msg2.contains("ParamName4Message"), "メッセージに、パラメータで指定した項目名が含まれているか確認 : msg=" + msg2)
//
//    //
//    validator = and("noItem-name", Option("ParamName4Message"), maxlength(7), minlength(9))
//    result = validator.valid()
//
//    assert(result.isEmpty === true, "fail? result=" + result)
//    assert(result === MessageBox())
//
//
//  }
}