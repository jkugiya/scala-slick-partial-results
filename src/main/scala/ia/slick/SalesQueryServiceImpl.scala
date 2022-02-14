package ia.slick

import ia.slick.Profile.api._
import models.{Sale, SalesQueryService}

import java.time.LocalDateTime
import scala.concurrent.Future
import Tables._

class SalesQueryServiceImpl(db: Database) extends SalesQueryService {

  override def find(from: Option[LocalDateTime], pageSize: Int, desc: Boolean): Future[Seq[Sale]] = db.run {
    val selectSales = from.map(filterSales(_, desc)).getOrElse(sales)
    selectSales.sortBy(sortOrder(_, desc)).take(pageSize).result
  }

  private def sortOrder(sales: Sales, desc: Boolean) = {
    if (desc) (sales.date.desc, sales.id.desc) else (sales.date.asc, sales.id.asc)
  }

  private def filterSales(from: LocalDateTime, desc: Boolean) = {
    sales.filter(s => if (desc) s.date < from else s.date > from)
  }


}
