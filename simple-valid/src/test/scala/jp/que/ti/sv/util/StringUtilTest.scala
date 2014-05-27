package jp.que.ti.sv.util

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import StringUtil._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StringUtilTest extends FunSuite {

  test(""" Only "Full Width Characters" becomes to  empty string. """) {
    var testValue = "　"
    assert(trim(testValue) === "", "testValue is [%s]".format(testValue))

    testValue = "　　"
    assert(trim(testValue) === "", "testValue is [%s]".format(testValue))

    testValue = " 　 　 \n \r\n  　"
    assert(trim(testValue) === "", "testValue is [%s]".format(testValue))

  }

  test(""" "Full Width Characters" can be trimed. """) {
    var testValue = "　abc　"
    assert(trim(testValue) === "abc", "testValue is [%s]".format(testValue))

    testValue = " 　 \n \r\nあかさ　 \n \r\n"
    assert(trim(testValue) === "あかさ", "testValue is [%s]".format(testValue))

  }

  test(""" Strings without white space characters cannot be trimed. """) {
    var testValue = "abc"
    assert(trim(testValue) === "abc", "testValue is [%s]".format(testValue))

    testValue = "あかさ"
    assert(trim(testValue) === "あかさ", "testValue is [%s]".format(testValue))

  }

  test("""toHalfSizeNumberCharacter""") {
    var testValue = "1234567890"
    assert(toHalfSizeNumberCharacter(testValue) === "1234567890")

    testValue = "１２３４５６７８９０"
    assert(toHalfSizeNumberCharacter(testValue) === "1234567890")

    testValue = "０１２３４５６７８９０"
    assert(toHalfSizeNumberCharacter(testValue) === "01234567890")

    testValue = "０a１あ２ア３ｱ４亜５！６７８９０1234567890"
    assert(toHalfSizeNumberCharacter(testValue) === "0a1あ2ア3ｱ4亜5！678901234567890")

    testValue = ""
    assert(toHalfSizeNumberCharacter(testValue) === "")

    testValue = null
    assert(toHalfSizeNumberCharacter(testValue) === null)

  }

  test("""isNumber . number case""") {
    var testValue = "1234567890"
    assert(isNumberOnly(testValue), """testValue=""" + testValue)

    testValue = " 	　 1234567890 　 "
    assert(isNumberOnly(testValue), """testValue=""" + testValue)

  }

  test("""isNumber . not number case""") {
    var testValue = ""
    assert(isNumberOnly(testValue) == false, """testValue=""" + testValue)

    testValue = null
    assert(isNumberOnly(testValue) == false, """testValue=""" + testValue)

    testValue = " 	　 1234a567890 　 "
    assert(isNumberOnly(testValue) == false, """testValue=""" + testValue)

  }

}