package models.tables

import java.sql.Timestamp

import constants.ImageDisplayType.ImageDisplayType
import constants.ImageDisplayType
import models.domain.Image
import slick.jdbc.PostgresProfile.api._


private[models] trait ImageTable {

  protected val driver: slick.jdbc.JdbcProfile

  protected val images = TableQuery[Images]

  implicit val imageDisplayTypeMapper = MappedColumnType.base[ImageDisplayType, String](
    e => e.toString,
    s => ImageDisplayType.withName(s)
  )

  /**
    * `Image` table mapping
    */
  private[models] class Images(tag: Tag) extends Table[Image](tag, "IMAGES") {

    def id: Rep[String] = column[String]("id", O.PrimaryKey, O.Length(36))

    def title: Rep[String] = column[String]("title", O.Length(50))

    def imageDisplayType: Rep[ImageDisplayType] = column[ImageDisplayType]("image_display_type", O.Default(ImageDisplayType.`public`), O.Length(2))

    def createdAt: Rep[Timestamp] = column[Timestamp]("created_at")

    def createdBy: Rep[String] = column[String]("created_by", O.Length(36))

    def modifiedAt: Rep[Option[Timestamp]] = column[Option[Timestamp]]("modified_at")

    def * = (id, title, imageDisplayType, createdAt, createdBy, modifiedAt) <>
      ((Image.apply _).tupled, Image.unapply)

  }

}

