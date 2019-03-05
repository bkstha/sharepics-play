package models.repositories

import javax.inject.{Inject, Singleton}
import models.tables.TagTable
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  *
  */

@Singleton
class TagRepository @Inject()(val dbConfigProvider: DatabaseConfigProvider)
                             (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] with TagTable {

  import profile.api._

  def createSchema: Future[Unit] = db.run(tags.schema.create)

  /**
    * Return the requested tag
    *
    * @param id identifier
    */
  def get(id: Int): Future[Option[models.domain.Tag]] = {
    db.run(tags.filter(_.id === id).result.headOption)
  }

  /**
    * Return the requested tag
    *
    * @param title identifier
    */
  def get(title: String): Future[Option[models.domain.Tag]] = {
    db.run(tags.filter(_.title === title).result.headOption)
  }

  /**
    * Returns list of the requested tag
    *
    * @param ids Ids of the image
    */
  def find(ids: List[Int]): Future[List[models.domain.Tag]] = if (ids.isEmpty)
    Future.successful(List()) // returning empty list
  else
    db.run(tags.filter(_.id inSet ids).result).map(_.toList)

  def list(): Future[Seq[models.domain.Tag]] = db.run(tags.result)

  /** Insert a new tag */
  def insert(tag: models.domain.Tag): Future[models.domain.Tag] = {
    db.run(tags returning tags.map(obj => obj) += tag)
  }

  def count: Future[Int] = {
    db.run(tags.length.result)
  }
}
