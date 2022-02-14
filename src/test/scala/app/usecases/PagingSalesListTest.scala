package app.usecases

import app.AppInitializer
import org.scalatest.concurrent.ScalaFutures.convertScalaFuture
import org.scalatest.matchers.should.Matchers._

class PagingSalesListTest extends BaseTest {
  var pagingSalesList: PagingSalesList = _

  override def beforeAll(): Unit = {
    AppInitializer.initDb()
    pagingSalesList = AppInitializer.pagingSalesList
  }

  "run" should {
    AppInitializer.initDb()
    // See https://use-the-index-luke.com/sql/partial-results/fetch-next-page
    "Provides API for seek method(desc)" in {
      val pageSize = 10
      val firstPages = pagingSalesList.run(pageSize, desc = true).futureValue
      firstPages.sales.size shouldBe pageSize
       firstPages.sales.head.id shouldBe 59
      firstPages.sales.last.id shouldBe 50
      firstPages.last shouldBe firstPages.sales.last.date
      val secondPages = pagingSalesList.run(firstPages.last, pageSize, desc = true).futureValue
      secondPages.sales.size shouldBe pageSize
      secondPages.sales.head.id shouldBe 49
      secondPages.sales.last.id shouldBe 40
      secondPages.last shouldBe secondPages.sales.last.date
    }
  }
  "Provides API for seek method(asc)" in {
    val pageSize = 10
    val firstPages = pagingSalesList.run(pageSize, desc = false).futureValue
    firstPages.sales.size shouldBe pageSize
    firstPages.sales.head.id shouldBe 1
    firstPages.sales.last.id shouldBe 10
    firstPages.last shouldBe firstPages.sales.last.date
    val secondPages = pagingSalesList.run(firstPages.last, pageSize, desc = false).futureValue
    secondPages.sales.size shouldBe pageSize
    secondPages.sales.head.id shouldBe 11
    secondPages.sales.last.id shouldBe 20
    secondPages.last shouldBe secondPages.sales.last.date
  }
}
