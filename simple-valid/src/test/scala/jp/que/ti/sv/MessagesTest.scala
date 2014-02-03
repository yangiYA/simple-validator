package jp.que.ti.sv
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MessagesTest extends FunSuite {

  test("Empty Messages equals test") {
    assert((Messages("a", Nil) == Messages("a", Nil)))
    assert(Messages("a", Nil) === Messages("a", Nil))

  }

  test("equals test (normal)") {
    var m1 = Messages("k1", "hoge1" :: "hoge2" :: Nil)
    var m2 = Messages("k1", "hoge1" :: "hoge2" :: Nil)

    assert((m1 == m2))
    assert((m2 == m1))
  }

  test("equals test, variation") {
    var m1 = Messages("k1", "hoge1") add "hoge2" add "hoge3" add "hoge4"
    var m2 = Messages("k1", "hoge1" :: "hoge2" :: Nil) add "hoge3" :: "hoge4" :: Nil

    assert((m1 == m2))
    assert((m2 == m1))

  }

  test("*** Exceptional CASE A's key not equals B's key.") {
    var m1 = Messages("k1", "hoge1") add "hoge2" add "hoge3" add "hoge4"
    var m2 = Messages("l1", "hoge1" :: "hoge2" :: Nil) add "hoge3" :: "hoge4" :: Nil

    assert((m1 != m2))
    assert((m2 != m1))

  }

  test("*** Exceptional CASE A's value not equals B's value.") {
    var m1 = Messages("k1", "hoge1") add "hoge2" add "hoge3" add "hoge4"
    var m2 = Messages("k1", "hoge1" :: "hoge20000" :: Nil) add "hoge3" :: "hoge4" :: Nil

    assert((m1 != m2))
    assert((m2 != m1))

  }

  test("*** Exceptional CASE sorted A's order of rows not equals B's order of rows") {
    var m1 = Messages("k1", "hoge1") add "hoge2" add "hoge3" add "hoge4"
    var m2 = Messages("k1", "hoge2" :: "hoge1" :: Nil) add "hoge4" :: "hoge3" :: Nil

    assert((m1 != m2))
    assert((m2 != m1))

  }

}