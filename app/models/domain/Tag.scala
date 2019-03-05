package models.domain

import play.api.libs.json.Json


final case class Tag(
                title: String,
                id: Int = 0,
              )

object Tag {
  implicit val tagWrites = Json.writes[Tag]
}

