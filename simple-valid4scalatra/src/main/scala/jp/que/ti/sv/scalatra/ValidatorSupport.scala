package jp.que.ti.sv.scalatra

import org.scalatra.ScalatraBase
import org.scalatra.ScalatraServlet

import jp.que.ti.sv.GenericValidatorMultiParamWithMessageProp
import jp.que.ti.sv.GenericValidatorMultiParamWithMessageProp4J2ee
import jp.que.ti.sv.GenericValidatorOneParamWithMessageProp
import jp.que.ti.sv.GenericValidatorOneParamWithMessageProp4J2ee
import jp.que.ti.sv.J2eeApiSupport
import jp.que.ti.sv.ValidatorBase
import jp.que.ti.sv.ValidatorSupportBase

trait ValidatorSupport extends ValidatorSupportBase with ScalatraBase

object ValidatorSupport extends ScalatraServlet with ValidatorSupport {

  def test = {
    genericValid("hoge", "foo", {
      (aaa: Option[String]) => true
    })

  }
}