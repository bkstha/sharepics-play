package controllers.requests

import play.api.libs.json.Json


case class UserRequest(email: String, name: String, pictureUrl: Option[String])

object UserRequest {
  implicit val userRequest = Json.format[UserRequest]
}