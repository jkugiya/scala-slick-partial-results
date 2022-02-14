package app.usecases

import models.{Sale, SalesQueryService}

import java.time.LocalDateTime
import scala.concurrent.{ExecutionContext, Future}

class PagingSalesList(salesQueryService: SalesQueryService)(implicit ec: ExecutionContext = ExecutionContext.Implicits.global) {

  private def run(date: Option[LocalDateTime], pageSize: Int, desc: Boolean): Future[PagingSalesList.Result] = {
    for {
      sales <- salesQueryService.find(date, pageSize, desc)
    } yield PagingSalesList.Result(
      sales = sales,
      last = sales.last.date
    )
  }

  def run(pageSize: Int, desc: Boolean): Future[PagingSalesList.Result] =
    run(None, pageSize, desc)

  def run(date: LocalDateTime, pageSize: Int, desc: Boolean): Future[PagingSalesList.Result] =
    run(Option(date), pageSize, desc)


}

object PagingSalesList {
  case class Result(
      sales: Seq[Sale],
      last: LocalDateTime
  )
}
