package models.repositories

import javax.inject.{Inject, Singleton}
import models.domain.{Image, ImageTag}
import models.tables.{ImageTable, ImageTagTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  *
  */

@Singleton
class ImageTagRepository @Inject()(val dbConfigProvider: DatabaseConfigProvider)
                                  (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] with ImageTagTable {

  import profile.api._

  def createSchema = db.run(imageTags.schema.create)

  /**
    * Return the requested image
    *
    * @param imageId identifier
    * @param tagId   identifier
    */
  def get(imageId: String, tagId: Int): Future[Option[ImageTag]] = db.run(imageTags.filter(_.imageId === imageId).filter(_.tagId === tagId).result.headOption)


  def list(): Future[Seq[ImageTag]] = db.run(imageTags.result)

  /** Insert a new ImageTag */
  def insert(image: ImageTag): Future[Option[ImageTag]] = {
    println("inserting image tag")
    db.run(imageTags += image).map(_ => ())
    get(image.imageId, image.tagId)
  }

  /** Insert a new ImageTag */
  def insertOnly(image: ImageTag): Future[Unit] = {
    db.run(imageTags += image).map(_ => ())
  }

  def insert(imageTag: Seq[ImageTag]): Future[Unit] =
    db.run(imageTags ++= imageTag).map(_ => ())

  def count: Future[Int] = {
    db.run(imageTags.length.result)
  }
}
