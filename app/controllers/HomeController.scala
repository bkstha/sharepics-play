package controllers

import javax.inject._
import models.database.repositories.UserRepository
import play.api.db.slick.DatabaseConfigProvider
import play.api.i18n.I18nSupport
import play.api.libs.json.Json.toJson
import play.api.mvc._

import scala.concurrent.ExecutionContext

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(
                                dbConfigProvider: DatabaseConfigProvider,
                                cc: ControllerComponents,
                                userRepo: UserRepository
                              )(implicit executionContext: ExecutionContext) extends AbstractController(cc) with I18nSupport {


  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok("Welcome to sharepics")
  }

  def getUserList = Action.async {
    userRepo.list().map(records => Ok(toJson(records)).as("text/json"))
  }
}
