package models.tables

import models.domain.ImageTag
import slick.jdbc.PostgresProfile.api._

private[models] trait ImageTagTable extends ImageTable with TagTable {

  protected val driver: slick.jdbc.JdbcProfile


  protected val imageTags = TableQuery[ImageTags]

  /**
    * `ImageTag` table mapping.
    */
  private[models] class ImageTags(tag: Tag) extends Table[ImageTag](tag, "IMAGES_TAGS") {

    def imageId: Rep[String] = column[String]("image_id")

    def tagId: Rep[Int] = column[Int]("tag_id")

    def pk = primaryKey("pk_images_tags", (imageId, tagId))

    def imageFk = foreignKey("fk_images_tags_image", imageId, images)(image => image.id, onDelete = ForeignKeyAction.Cascade)

    def tagFk = foreignKey("fk_images_tags_tag", tagId, tags)(tag => tag.id, onDelete = ForeignKeyAction.Cascade)

    def * = (imageId, tagId) <> ((ImageTag.apply _).tupled, ImageTag.unapply)

  }

}
