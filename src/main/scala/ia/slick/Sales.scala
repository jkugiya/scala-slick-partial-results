package ia.slick

import Profile.api._
import models.Sale
import slick.lifted.ProvenShape

import java.time.LocalDateTime

class Sales(tag: Tag) extends Table[Sale](tag, "sales") {

  def id: Rep[Long] = column("id", O.PrimaryKey, O.AutoInc)
  def date: Rep[LocalDateTime] = column("date")

  override def * : ProvenShape[Sale] = (id, date) <> ((Sale.apply _).tupled, Sale.unapply)

}
