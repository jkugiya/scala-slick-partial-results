package app.usecases

import models.{Sale, SalesQueryService}

import java.time.LocalDateTime
import scala.concurrent.{ExecutionContext, Future}

class PagingSalesListLikeFacebook(salesQueryService: SalesQueryService)(implicit
    ec: ExecutionContext = ExecutionContext.Implicits.global
) {
  import PagingSalesListLikeFacebook._

  def run(query: Query): Future[Result] = {
    for {
      (hasBefore, sales, hasAfter) <- query match {
        case FirstPage(limit) => handleFirstQuery(limit)
        case After(limit, date) => handleAfterQuery(limit, date)
        case Before(limit, date) => handleBeforeQuery(limit, date)
      }
    } yield if (sales.isEmpty) NotFound
    else {
      val first = sales.head
      val last = sales.last
      FetchResult(
        sales = sales,
        cursors = Cursors(
          before = if (hasBefore) Option(first.date) else None,
          after = if (hasAfter) Option(last.date) else None
        ),
        previous = if (hasBefore) Option(Before(limit = query.limit, date = first.date)) else None,
        next = if(hasAfter) Option(After(limit = query.limit, date = last.date)) else None
      )
    }
  }

  private def handleBeforeQuery(limit: Int, date: LocalDateTime) = {
    for {
      sales <- salesQueryService
        .find(Option(date), limit + 1, desc = false)
      tryAfter <- salesQueryService.find(Option(date), 1, desc = true).map(_.nonEmpty)
    } yield {
      val tryBefore = sales.drop(limit).nonEmpty
      (
        tryBefore,
        if (tryBefore) sales.reverse.tail else sales.reverse,
        tryAfter
      )
    }
  }

  private def handleAfterQuery(limit: Int, date: LocalDateTime) = {
    for {
      sales <- salesQueryService.find(Option(date), limit + 1, desc = true)
      tryBefore <- salesQueryService.find(None, 1, desc = false).map(_.nonEmpty)
    } yield {
      val tryAfter = sales.drop(limit).nonEmpty
      (
        tryBefore,
        if (tryAfter) sales.dropRight(1) else sales,
        tryAfter
      )
    }
  }

  private def handleFirstQuery(limit: Int) = {
    for {
      sales <- salesQueryService.find(None, limit + 1, desc = true)
    } yield {
      val tryAfter = sales.drop(limit).nonEmpty
      (
        false,
        if (tryAfter) sales.dropRight(1) else sales,
        tryAfter
      )
    }
  }
}

object PagingSalesListLikeFacebook {
 sealed trait Result
  case object NotFound extends Result
  /** @see [[https://developers.facebook.com/docs/graph-api/results]]
    */
  case class FetchResult(
      sales: Seq[Sale],
      cursors: Cursors,
      previous: Option[Before],
      next: Option[After]
  ) extends Result

  case class Cursors(
      before: Option[LocalDateTime],
      after: Option[LocalDateTime]
  )

  sealed trait Query {
    def limit: Int
  }
  case class FirstPage(limit: Int) extends Query
  case class After(limit: Int, date: LocalDateTime) extends Query
  case class Before(limit: Int, date: LocalDateTime) extends Query
}
