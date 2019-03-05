package controllers

import java.io.File
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

import constants.ImageDisplayType
import controllers.requests.ImageRequest
import models.domain.{Image, ImageTag, Tag}


object Utility {
  val TEMP_FOLDER: String = "tmp/pictures"
  val FILE_STORAGE_FOLDER: String = "public/pictures"
  val FULL: String = "full"
  val LOW: String = "low"
  val dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")

  val azureStorageConnectionString = "DefaultEndpointsProtocol=https;AccountName=sharepics;AccountKey=a0acaIhtW5azpO0e2QbHASg78talmeKpydJwhvd61dMRv2Qk3X0Jtt8MLV7y6i3WKnXr4j8u+KhXLaf6SMhPow==;EndpointSuffix=core.windows.net"

  def createTempFolder(): Unit = {
    val folder = new File(TEMP_FOLDER)
    if (!folder.exists) folder.mkdir
  }

  def createUserFolder(userId: String): Unit = {
    val folder = new File(FILE_STORAGE_FOLDER + "/" + userId)
    val fullFolder = new File(FILE_STORAGE_FOLDER + "/" + userId + "/" + FULL)
    val lowFolder = new File(FILE_STORAGE_FOLDER + "/" + userId + "/" + LOW)
    if (!folder.exists) {
      folder.mkdir
      fullFolder.mkdir()
      lowFolder.mkdir()
    }

  }

  def getImageObject(imageRequest: ImageRequest): Image = {
    Image(imageRequest.id, imageRequest.title, ImageDisplayType.`public`, new Timestamp(new Date().getTime), imageRequest.userId, Option(new Timestamp(new Date().getTime)))
  }

  def getTagObject(tag: String): Tag = {
    Tag(tag)
  }

  def getImageTagObject(imageId: String, tagId: Int): ImageTag = {
    ImageTag(imageId, tagId)
  }

}