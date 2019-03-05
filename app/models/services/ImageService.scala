package models.services

import controllers.Utility
import controllers.requests.ImageRequest
import javax.inject.{Inject, Singleton}
import models.repositories.{ImageRepository, ImageTagRepository, TagRepository}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

/**
  *
  */

@Singleton
class ImageService @Inject()(
                              imageRepo: ImageRepository,
                              tagRepo: TagRepository,
                              imageTagRepo: ImageTagRepository
                            )(implicit executionContext: ExecutionContext) {


  def saveImage(imageRequest: ImageRequest): Future[ImageRequest] = {
    imageRepo.insert(Utility.getImageObject(imageRequest))
    imageRequest.tags.foreach(tag => {
      tagRepo.get(tag).map {
        case Some(obj) => {
          imageTagRepo.insert(Utility.getImageTagObject(imageRequest.id, obj.id))
        }
        case None => {
          try {
            val newTag = Await.result(tagRepo.insert(Utility.getTagObject(tag)), Duration.Inf)
            imageTagRepo.insert(Utility.getImageTagObject(imageRequest.id, newTag.id))
          } catch {
            case e: Exception => {
              e.printStackTrace()
              throw new Exception("Unable to insert image tags")
            }
          }
        }
      }
    })
    Future.successful(imageRequest)
  }

/*  def saveImageWithTags(imageRequest: ImageRequest): Future[ImageRequest] = {
    try {
      imageRepo.saveImageWithTags(imageRequest)
      Future.successful(imageRequest)
    }catch {
      case e: SQLException => {
        e.printStackTrace()
        throw new SQLException("Unable to insert image")
      }
    }
  }*/
}
