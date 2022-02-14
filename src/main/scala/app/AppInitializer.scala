package app

import app.usecases.PagingSalesList
import ia.slick.{Profile, SalesQueryServiceImpl}
import models.{Sale, SalesQueryService}
import slick.basic.DatabaseConfig

import java.time.LocalDateTime
import ia.slick.Profile.api._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object AppInitializer {

  lazy val db: Database = DatabaseConfig.forConfig[Profile]("slick").db
  def initDb(): Unit = {
    import ia.slick.Tables._
    val future = db.run(DBIO.seq(
      sales.schema.dropIfExists,
      sales.schema.createIfNotExists,
      sales ++= Seq(
        Sale(1, LocalDateTime.parse("2022-02-01T00:00:00")),
        Sale(2, LocalDateTime.parse("2022-02-02T00:00:00")),
        Sale(3, LocalDateTime.parse("2022-02-03T00:00:00")),
        Sale(4, LocalDateTime.parse("2022-02-04T00:00:00")),
        Sale(5, LocalDateTime.parse("2022-02-05T00:00:00")),
        Sale(6, LocalDateTime.parse("2022-02-06T00:00:00")),
        Sale(7, LocalDateTime.parse("2022-02-07T00:00:00")),
        Sale(8, LocalDateTime.parse("2022-02-08T00:00:00")),
        Sale(9, LocalDateTime.parse("2022-02-09T00:00:00")),
        Sale(10, LocalDateTime.parse("2022-02-10T00:00:00")),
        Sale(11, LocalDateTime.parse("2022-02-11T00:00:00")),
        Sale(12, LocalDateTime.parse("2022-02-12T00:00:00")),
        Sale(13, LocalDateTime.parse("2022-02-13T00:00:00")),
        Sale(14, LocalDateTime.parse("2022-02-14T00:00:00")),
        Sale(15, LocalDateTime.parse("2022-02-15T00:00:00")),
        Sale(16, LocalDateTime.parse("2022-02-16T00:00:00")),
        Sale(17, LocalDateTime.parse("2022-02-17T00:00:00")),
        Sale(18, LocalDateTime.parse("2022-02-18T00:00:00")),
        Sale(19, LocalDateTime.parse("2022-02-19T00:00:00")),
        Sale(20, LocalDateTime.parse("2022-02-20T00:00:00")),
        Sale(21, LocalDateTime.parse("2022-02-21T00:00:00")),
        Sale(22, LocalDateTime.parse("2022-02-22T00:00:00")),
        Sale(23, LocalDateTime.parse("2022-02-23T00:00:00")),
        Sale(24, LocalDateTime.parse("2022-02-24T00:00:00")),
        Sale(25, LocalDateTime.parse("2022-02-25T00:00:00")),
        Sale(26, LocalDateTime.parse("2022-02-26T00:00:00")),
        Sale(27, LocalDateTime.parse("2022-02-27T00:00:00")),
        Sale(28, LocalDateTime.parse("2022-02-28T00:00:00")),
        Sale(29, LocalDateTime.parse("2022-03-01T00:00:00")),
        Sale(30, LocalDateTime.parse("2022-03-02T00:00:00")),
        Sale(31, LocalDateTime.parse("2022-03-03T00:00:00")),
        Sale(32, LocalDateTime.parse("2022-03-04T00:00:00")),
        Sale(33, LocalDateTime.parse("2022-03-05T00:00:00")),
        Sale(34, LocalDateTime.parse("2022-03-06T00:00:00")),
        Sale(35, LocalDateTime.parse("2022-03-07T00:00:00")),
        Sale(36, LocalDateTime.parse("2022-03-08T00:00:00")),
        Sale(37, LocalDateTime.parse("2022-03-09T00:00:00")),
        Sale(38, LocalDateTime.parse("2022-03-10T00:00:00")),
        Sale(39, LocalDateTime.parse("2022-03-11T00:00:00")),
        Sale(40, LocalDateTime.parse("2022-03-12T00:00:00")),
        Sale(41, LocalDateTime.parse("2022-03-13T00:00:00")),
        Sale(42, LocalDateTime.parse("2022-03-14T00:00:00")),
        Sale(43, LocalDateTime.parse("2022-03-15T00:00:00")),
        Sale(44, LocalDateTime.parse("2022-03-16T00:00:00")),
        Sale(45, LocalDateTime.parse("2022-03-17T00:00:00")),
        Sale(46, LocalDateTime.parse("2022-03-18T00:00:00")),
        Sale(47, LocalDateTime.parse("2022-03-19T00:00:00")),
        Sale(48, LocalDateTime.parse("2022-03-20T00:00:00")),
        Sale(49, LocalDateTime.parse("2022-03-21T00:00:00")),
        Sale(50, LocalDateTime.parse("2022-03-22T00:00:00")),
        Sale(51, LocalDateTime.parse("2022-03-23T00:00:00")),
        Sale(52, LocalDateTime.parse("2022-03-24T00:00:00")),
        Sale(53, LocalDateTime.parse("2022-03-25T00:00:00")),
        Sale(54, LocalDateTime.parse("2022-03-26T00:00:00")),
        Sale(55, LocalDateTime.parse("2022-03-27T00:00:00")),
        Sale(56, LocalDateTime.parse("2022-03-28T00:00:00")),
        Sale(57, LocalDateTime.parse("2022-03-29T00:00:00")),
        Sale(58, LocalDateTime.parse("2022-03-30T00:00:00")),
        Sale(59, LocalDateTime.parse("2022-03-31T00:00:00"))
      )
    ))
    Await.result(future, Duration.Inf)
  }

  lazy val salesQueryService: SalesQueryService = new SalesQueryServiceImpl(
    db
  )

  lazy val pagingSalesList: PagingSalesList = new PagingSalesList(salesQueryService)

}
