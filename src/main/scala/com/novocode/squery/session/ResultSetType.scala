package com.novocode.squery.session

import java.sql.ResultSet

sealed abstract case class ResultSetType(intValue: Int) { self =>
  def apply[T](base: Session)(f: Session => T): T = f(base.forParameters(rsType = self))
  def apply[T](f: => T)(implicit base: Session): T = apply(base)(Database.dyn.withValue(_)(f))
}

object ResultSetType {
  case object ForwardOnly       extends ResultSetType(ResultSet.TYPE_FORWARD_ONLY)
  case object ScrollInsensitive extends ResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE)
  case object ScrollSensitive   extends ResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE)
}