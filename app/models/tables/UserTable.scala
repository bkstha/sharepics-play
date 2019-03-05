package models.tables

import java.sql.Timestamp

import constants.UserType
import constants.UserType.UserType
import models.domain.User
import slick.jdbc.PostgresProfile.api._

private[models] trait UserTable {

  protected val driver: slick.jdbc.JdbcProfile

  protected val users = TableQuery[Users]

  implicit val userTypeMapper = MappedColumnType.base[UserType, String](
    e => e.toString,
    s => UserType.withName(s)
  )

  /**
    * `User` table mapping
    */
  private[models] class Users(tag: Tag) extends Table[User](tag, "USERS") {

    def id: Rep[String] = column[String]("id", O.PrimaryKey, O.Length(36))

    def email: Rep[String] = column[String]("email", O.Length(100))

    def name: Rep[String] = column[String]("name", O.Length(50))

    def userProfileUrl: Rep[Option[String]] = column[Option[String]]("user_profile_url", O.Length(100))

    def userType: Rep[UserType] = column[UserType]("user_type", O.Default(UserType.user), O.Length(2))

    def lastLogin: Rep[Option[Timestamp]] = column[Option[Timestamp]]("last_login")

    def created: Rep[Timestamp] = column[Timestamp]("created")

    def modified: Rep[Option[Timestamp]] = column[Option[Timestamp]]("modified")

    def active: Rep[Boolean] = column[Boolean]("active", O.Default(false))

    def * = (id.?, email, name, userType, created, modified, lastLogin, active.?, userProfileUrl) <>
      ((User.apply _).tupled, User.unapply)

  }

}

