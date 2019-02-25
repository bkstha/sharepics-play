package controllers.responses

import constants.UserType.UserType
import play.api.libs.json.Json


case class UserResponse(id: String, email: String, name: String, pictureUrl: Option[String], userType: UserType, active: Boolean)

object UserResponse {
  implicit val userResponseWrites = Json.writes[UserResponse]
}
