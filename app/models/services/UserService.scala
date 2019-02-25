package models.services

import java.sql.Timestamp
import java.util.{Date, UUID}

import constants.UserType
import controllers.requests.UserRequest
import controllers.responses.UserResponse
import javax.inject.{Inject, Singleton}
import models.database.repositories.UserRepository
import models.domain.User

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

/**
  *
  */

@Singleton
class UserService @Inject()(
                             userRepo: UserRepository
                           )(implicit executionContext: ExecutionContext) {

  /** Update a user. */
  def addUser(userRequest: UserRequest): Future[UserResponse] = {
    userRepo.getByEmail(userRequest.email) map {
      case Some(user) => mapUserToUserResponse(user)
      case None => addNewUser(userRequest)
    }
  }

  def createNewUserObject(userRequest: UserRequest): User = {
    new User(Option(UUID.randomUUID().toString), userRequest.email, userRequest.name, UserType.user,
      new Timestamp(new Date().getTime), Option(new Timestamp(new Date().getTime)),
      Option(new Timestamp(new Date().getTime)), Option(true), userRequest.pictureUrl)
  }

  def mapUserToUserResponse(user: User): UserResponse = {
    new UserResponse(user.id.get, user.email, user.name, user.userProfileUrl, user.userType, user.active.get)
  }


  def addNewUser(userRequest: UserRequest): UserResponse = {
    val newUser = userRepo.insert(createNewUserObject(userRequest))
    mapUserToUserResponse(Await.result(newUser, Duration.Inf))
  }




}
