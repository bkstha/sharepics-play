package models.database.repositories

import javax.inject.{Inject, Singleton}
import models.domain.User
import models.tables.UserTable
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  *
  */

@Singleton
class UserRepository @Inject()(val dbConfigProvider: DatabaseConfigProvider)
                              (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] with UserTable {

  import profile.api._

  def createSchema = db.run(users.schema.create)

  /**
    * Return the requested user
    *
    * @param id Address identifier
    */
  def get(id: String): Future[User] = db.run(users.filter(_.id === id).result).map(_.head)

  def getByEmail(email: String): Future[Option[User]] = {
    println("email")
    println(email)
    db.run(users.filter(_.email === email).result.headOption)
  }

  /**
    * Returns list of the requested user
    *
    * @param ids Ids of the user
    */
  def find(ids: List[String]): Future[List[User]] = if (ids.isEmpty)
    Future.successful(List()) // empty list
  else
    db.run(users.filter(_.id inSet ids).result).map(_.toList)

  def list(): Future[Seq[User]] = db.run(users.result)

  /** Insert a new user */
  def insert(user: User): Future[User] =
    db.run(users returning users.map(_.id) into ((user,id) => user.copy(id=Some(id))) += user)

  /** Insert new sequence of users */
  def insert(users: Seq[User]): Future[Unit] =
    db.run(this.users ++= users).map(_ => ())

  def update(user: User): Future[Int] =
    db.run(users.filter(_.id === user.id).update(user))

  /** Update a user. */
  def update(id: String, user: User): Future[Unit] = {
    val userToUpdate: User = user.copy(Some(id))
    db.run(users.filter(_.id === id).update(userToUpdate)).map(_ => ())
  }

  def count: Future[Int] = {
    db.run(users.length.result)
  }
}
