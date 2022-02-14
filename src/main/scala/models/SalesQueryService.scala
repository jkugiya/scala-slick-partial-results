package models

import java.time.LocalDateTime
import scala.concurrent.Future

trait SalesQueryService {

  def find(from: Option[LocalDateTime], pageSize: Int, desc: Boolean): Future[Seq[Sale]]

}
