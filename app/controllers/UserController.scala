package controllers

import java.nio.file.Paths
import java.util.UUID

import controllers.requests.{ImageRequest, UserRequest}
import controllers.responses.ImageUploadResponse
import javax.inject._
import models.services.{ImageService, UserService}
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
                                imageService: ImageService,
                              )(implicit executionContext: ExecutionContext) extends AbstractController(controllerComponents) {
  private val logger = Logger(getClass)

  val userForm = Form(
    mapping(
      "email" -> email,
      "name" -> text,
      "pictureUrl" -> optional(text)
    )(UserRequest.apply)(UserRequest.unapply)
  )

  val imageForm = Form(
    mapping(
      "id" -> text,
      "userId" -> text,
      "title" -> text,
      "tags" -> seq(text)
    )(ImageRequest.apply)(ImageRequest.unapply)
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

  def upload = Action(parse.multipartFormData) { request =>
    request.body.file("image").map { picture =>
      Utility.createTempFolder()
      val imageId = UUID.randomUUID().toString
      val rootPath = play.Environment.simple().rootPath()
      val tempFolder = Utility.TEMP_FOLDER
      picture.ref.copyTo(Paths.get(s"$rootPath/$tempFolder/$imageId.jpg"), replace = true)

      Ok(Json.toJson(new ImageUploadResponse(imageId, picture.fileSize)))
    }.getOrElse {
      BadRequest("error")
    }
  }

  def saveImage = Action.async(parse.json) { implicit request =>
    logger.trace("saving image: ")
    imageForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(BadRequest("Error saving Image"))
      },
      imageRequest => {
        imageService.saveImage(imageRequest).map {
          response => Ok(Json.toJson(response))
        }
      }
    )
  }


}