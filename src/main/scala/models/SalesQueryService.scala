package models

import java.time.LocalDateTime
import scala.concurrent.Future

trait SalesQueryService {

  // Note: if determinacy of results is required, `id` is also needed.
  def find(from: Option[LocalDateTime], pageSize: Int, desc: Boolean): Future[Seq[Sale]]

}
