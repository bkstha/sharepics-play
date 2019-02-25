package bootstrap

import java.sql.Timestamp
import java.util

import constants.UserType
import javax.inject.Inject
import models.database.repositories.UserRepository
import models.domain.User

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
import scala.util.Try

private[bootstrap] class InitialData @Inject()(
                                                userRepo: UserRepository
                                              )(implicit executionContext: ExecutionContext) {

  def insert(): Unit = {
    val insertInitialDataFuture = for {
      count <- userRepo.count if count == 0
      _ <- userRepo.insert(InitialData.users)
    } yield ()

    Try(Await.result(insertInitialDataFuture, Duration.Inf))
  }

  insert()
}

private[bootstrap] object InitialData {

  def users = Seq(
    User(Option("bikash"), "email@shresthab.com.np", "Bikash Shrestha", UserType.admin, new Timestamp(new util.Date().getTime), Option(new Timestamp(new util.Date().getTime)), Option(null), Option(true), Option(null)),
    User(Option("nikesh"), "nikeshmaharjan@gmail.com", "Nikesh Maharjan", UserType.admin, new Timestamp(new util.Date().getTime), Option(new Timestamp(new util.Date().getTime)), Option(null), Option(true), Option(null)),
  )
}
