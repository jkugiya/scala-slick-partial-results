package app.usecases

import app.AppInitializer
import app.usecases.PagingSalesListLikeFacebook._
import org.scalatest.concurrent.ScalaFutures._
import org.scalatest.matchers.should.Matchers._

import scala.annotation.tailrec

class PagingSalesListLikeFacebookTest extends BaseTest {
  var pagingSalesListLikeFacebook: PagingSalesListLikeFacebook = _

  override def beforeAll(): Unit = {
    AppInitializer.initDb()
    pagingSalesListLikeFacebook = AppInitializer.pagingSalesListLikeFacebook
  }

  "run" should {
    "achieve pagination" in {
      val pageSize = 10
      @tailrec
      def testNext(prev: Result, after: After, pageNum: Int): Unit = {
        if (pageNum == 5) ()
        else {
          pagingSalesListLikeFacebook.run(after).futureValue match {
            case NotFound => fail(s"not found at ${pageNum}")
            case result @ FetchResult(sales, cursors, previous, next) =>
              sales.size shouldBe 10
              cursors.before shouldBe Option(sales.head.date)
              cursors.before shouldBe previous.map(_.date)
              pagingSalesListLikeFacebook.run(previous.get).futureValue shouldBe prev
              cursors.after shouldBe Option(sales.last.date)
              cursors.after shouldBe next.map(_.date)
              next.map(_.limit) shouldBe Option(pageSize)
              testNext(result, next.get, pageNum + 1)
          }
        }
      }
      val firstResult = pagingSalesListLikeFacebook.run(PagingSalesListLikeFacebook.FirstPage(pageSize)).futureValue
      firstResult match {
        case NotFound => fail()
        case FetchResult(sales, cursors, previous, next) =>
          sales.size shouldBe 10
          cursors.before.isEmpty shouldBe true
          previous.isEmpty shouldBe true
          cursors.after shouldBe Option(sales.last.date)
          cursors.after shouldBe next.map(_.date)
          next.map(_.limit) shouldBe Option(pageSize)
          testNext(firstResult, next.get, 1)
      }
    }
  }

}
