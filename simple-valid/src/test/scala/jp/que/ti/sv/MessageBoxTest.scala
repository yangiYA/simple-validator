package jp.que.ti.sv

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MessageBoxTest extends FunSuite {

  test("Empty MessageBox equals test") {
    assert((MessageBox() == MessageBox()))
    assert(MessageBox() === MessageBox())

  }

  test("equals test -- normal single Item") {
    var m1 = MessageBox("k1", "val1")
    var m2 = MessageBox("k1", "val1")

    assert((m1 == m2))
    assert((m2 == m1))

    //key null と 空文字は同じとして扱う
    m1 = MessageBox("", "val1")
    m2 = MessageBox(null, "val1")

    assert((m1 == m2))
    assert((m2 == m1))
  }

  test("equals test -- normal multiple Items") {
    var m1 = MessageBox("k1", "val1").add("k2", "val2-1").add("k2", "val2-2")
    var m2 = MessageBox("k1", "val1") ++ MessageBox("k2", "val2-1" :: "val2-2" :: Nil)

    assert((m1 == m2))
    assert((m2 == m1))

    m1 = m1 add ("k3", "val3-1") add ("k4", "val4-1") add ("k3", "val3-2") add ("k5", "val5-1") add ("k4", "val4-2")
    m2 = m2 ++
      MessageBox("k3", List("val3-1", "val3-2")) ++
      MessageBox("k4", List("val4-1", "val4-2")) ++
      MessageBox("k5", List("val5-1"))

    assert((m1 == m2))
    assert((m2 == m1))

    m1 = m1 add ("k6", None)
    m2 = m2 ++ None

    assert((m1 == m2))
    assert((m2 == m1))

    m1 = m1 add ("k6", None)
    m2 = m2 ++ None

    assert((m1 == m2))
    assert((m2 == m1))

  }

  test("""Not equals test. "value" isn't same.""") {
    var m1 = MessageBox("k1", "val1")
    var m2 = MessageBox("k1", "val1-1")

    assert((m1 != m2))
    assert((m2 != m1))

    m2 = MessageBox("k1", " val1 ") // value is added spaces
    assert((m1 != m2))
    assert((m2 != m1))
  }

  test("""Not equals test. "key" isn't same.""") {
    var m1 = MessageBox("k1", "val1")
    var m2 = MessageBox(" k1 ", "val1") // key is added spaces

    assert((m1 != m2))
    assert((m2 != m1))

    m2 = MessageBox(" k1 ", "val1")

    assert((m1 != m2))
    assert((m2 != m1))
  }

  test("""Not equals test. "key's order " isn't same.""") {
    var m1 = MessageBox("k1", List("val1-1")) add ("k2", "val2-1") add ("k3", "val3-1") add (
      "k2", "val2-2") add ("k1", "val1-2")
    var m2 = MessageBox("k1", List("val1-1")) add ("k3", "val3-1") add ("k2", "val2-1") add (
      "k2", "val2-2") add ("k1", "val1-2")

    assert((m1 != m2))
    assert((m2 != m1))
  }

  test("""The order of the messagelist.""") {
    var m1 = MessageBox("k1", List("v1-1", "v1-2")) ++ MessageBox("k2", List("v2-1", "v2-2")) ++ MessageBox("k3", List("v3-1", "v3-2"))
    assert((m1.messages).toString == """List(v1-1, v1-2, v2-1, v2-2, v3-1, v3-2)""")

    m1 = m1 add ("k1", "v1-3") add ("k2", "v2-3") add ("k3", "v3-3")
    assert((m1.messages).toString == """List(v1-1, v1-2, v1-3, v2-1, v2-2, v2-3, v3-1, v3-2, v3-3)""")

  }

  test("""specified key's messages""") {
    var m1 = MessageBox("k1", List("v1-1", "v1-2")) ++ MessageBox("k2", List("v2-1", "v2-2")) ++ MessageBox("k3", List("v3-1", "v3-2"))
    assert((m1.messages("k2")).toString == """List(v2-1, v2-2)""")

    m1 = m1 add ("k1", "v1-3") add ("k2", "v2-3") add ("k3", "v3-3")
    assert((m1.messages("k2")).toString == """List(v2-1, v2-2, v2-3)""")

  }

  test("""htmlString string""") {
    var m1 = MessageBox("k1", List("v1-1", "v1-2")) ++ MessageBox("k2", List("v2-1", "v2-2")) ++ MessageBox("k3", List("v3-1", "v3-2"))
    assert((m1.htmlString) == """
<ol>
  <li>v1-1</li>
  <li>v1-2</li>
  <li>v2-1</li>
  <li>v2-2</li>
  <li>v3-1</li>
  <li>v3-2</li>
</ol>
""")

  }

}