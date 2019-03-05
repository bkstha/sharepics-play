package models.domain

import play.api.libs.json.Json


case class ImageTag(
                     imageId: String,
                     tagId: Int
                   )

object ImageTag {
  implicit val imageTagsWrites = Json.writes[ImageTag]
}


