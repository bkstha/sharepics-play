package models.repositories

import javax.inject.{Inject, Singleton}
import models.domain.Image
import models.tables.{ImageTable, ImageTagTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  *
  */

@Singleton
class ImageRepository @Inject()(
                                 val dbConfigProvider: DatabaseConfigProvider,
                                 val tagRepo: TagRepository,
                                 val imageTagRepo: ImageTagRepository
                               )
                               (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] with ImageTable with ImageTagTable {


  import profile.api._

  def createSchema = db.run(images.schema.create)

  /**
    * Return the requested image
    *
    * @param id identifier
    */
  def get(id: String): Future[Option[Image]] = db.run(images.filter(_.id === id).result.headOption)

  /**
    * Returns list of the requested image
    *
    * @param ids Ids of the image
    */
  def find(ids: List[String]): Future[List[Image]] = if (ids.isEmpty)
    Future.successful(List()) // returning empty list
  else
    db.run(images.filter(_.id inSet ids).result).map(_.toList)

  def list(): Future[Seq[Image]] = db.run(images.result)

  /** Insert a new Image */
  def insert(image: Image): Future[Image] =
    db.run(images returning images.map(obj => obj) += image)

  def count: Future[Int] = {
    db.run(images.length.result)
  }

  /*  def saveImageWithTags(imageRequest: ImageRequest): Future[Unit] = {
      print(Json.toJson(Utility.getImageObject(imageRequest)))
      val dbAction = (
        for {
          image <- (images += Utility.getImageObject(imageRequest)).map(_ => ())
  //        image <- (images returning images.map(_.id) into ((image, id) => image.copy(id = id)) += Utility.getImageObject(imageRequest)
          tags <- imageRequest.tags.foreach(tag => {
            tagRepo.get(tag).map {
  //            case Some(obj) => imageTagRepo.insert()
              case Some(obj) => (imageTags += Utility.getImageTagObject(imageRequest.id, obj.id.get)).map(_ => ())
              case None => {
  //              val insertTag = (tags returning tags.map(_.id) into ((tag, id) => tag.copy(id = Some(id))) += Utility.getTagObject(tag))
                tagRepo.insert(Utility.getTagObject(tag)).map {
                  tag => {
                    imageTagRepo.insert(getImageTagObject(imageRequest.id, tag.id.get))
                  }
                }
              }
            }
          })

        } yield ()
        ).transactionally
      db.run(dbAction)
    }*/

//  def saveImageWithTags(imageRequest: ImageRequest): Future[Unit] = (for {
//    _ <- insert(Utility.getImageObject(imageRequest))
//    _ <- imageRequest.tags.foreach(tag => {
//      println(tag)
//      tagRepo.get(tag).map {
//        case Some(obj) => imageTagRepo.insert(Utility.getImageTagObject(imageRequest.id, obj.id))
//        case None => {
//          //          try {
//          val newTag = Await.result(tagRepo.insert(Utility.getTagObject(tag)), Duration.Inf)
//          imageTagRepo.insert(Utility.getImageTagObject(imageRequest.id, newTag.id))
//          //          } catch {
//          //            case e: Exception => {
//          //              e.printStackTrace()
//          //            }
//          //          }
//        }
//      }
//    })
//  } yield ()).transactionally

  //  def saveImageWithTags(dbAction: Any) = ???
}
