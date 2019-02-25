package models.domain

import java.sql.Timestamp

import constants.UserType.UserType
import play.api.libs.json.Json


case class User(
                 id: Option[String],
                 email: String,
                 name: String,
                 userType: UserType,
                 created: Timestamp,
                 modified: Option[Timestamp],
                 lastLogin: Option[Timestamp],
                 active: Option[Boolean],
                 userProfileUrl: Option[String]
               )

object User {
  implicit val userWrites = Json.writes[User]
}
