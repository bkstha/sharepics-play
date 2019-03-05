package controllers.requests

import play.api.libs.json.Json


case class ImageRequest(
                         id: String,
                         userId: String,
                         title: String,
                         tags: Seq[String]
                       )

object ImageRequest {
  implicit val imageRequest = Json.format[ImageRequest]
}