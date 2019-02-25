package controllers

import controllers.requests.UserRequest
import javax.inject._
import models.database.repositories.UserRepository
import models.services.UserService
import play.api.Logger
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(
                                controllerComponents: ControllerComponents,
                                userService: UserService,
                                userRepo: UserRepository
                              )(implicit executionContext: ExecutionContext) extends AbstractController(controllerComponents) {
  private val logger = Logger(getClass)

  val userForm = Form(
    mapping(
      "email" -> email,
      "name" -> text,
      "pictureUrl" -> optional(text)
    )(UserRequest.apply)(UserRequest.unapply)
  )

  def login = Action.async(parse.json) { implicit request =>
    logger.trace("process: ")
    userForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(BadRequest("Error"))
      },
      userRequest => {
        userService.addUser(userRequest).map {
          user => Ok(Json.toJson(user))
        }
      }
    )
  }


}