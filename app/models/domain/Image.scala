package models.domain

import java.sql.Timestamp

import constants.ImageDisplayType.ImageDisplayType
import play.api.libs.json.Json


case class Image(
                  id: String,
                  title: String,
                  imageDisplayType: ImageDisplayType,
                  createdAt: Timestamp,
                  createdBy: String,
                  modifiedAt: Option[Timestamp]
                )
object Image {
  implicit val imageWrites = Json.writes[Image]
}

