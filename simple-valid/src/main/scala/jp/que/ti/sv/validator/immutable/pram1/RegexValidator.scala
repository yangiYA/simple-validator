package jp.que.ti.sv.validator.immutable.pram1

import jp.que.ti.sv.NotRequiredBase
import scala.util.matching.Regex

class RegexValidator(val regex: Regex) extends NotRequiredBase("invalid") {

  def isValidInputed(paramValue: String): Boolean = if (regex.findFirstIn(paramValue).isEmpty) {
    false
  } else {
    true
  }
}

object RegexValidator {
  def apply(regex: Regex) = new RegexValidator(regex)
}