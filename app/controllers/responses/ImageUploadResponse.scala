package controllers.responses

import play.api.libs.json.Json


case class ImageUploadResponse(id: String, size: Long)

object ImageUploadResponse {
  implicit val userUploadResponseWrites = Json.writes[ImageUploadResponse]
}



