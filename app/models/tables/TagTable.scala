package models.tables

import slick.jdbc.PostgresProfile.api._

private[models] trait TagTable {

  protected val driver: slick.jdbc.JdbcProfile

  protected val tags = TableQuery[TagsTable]

  /**
    * `Tag` table mapping
    */
  private[models] class TagsTable(tag: Tag) extends Table[models.domain.Tag](tag, "TAGS") {

    def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def title: Rep[String] = column[String]("title", O.Unique, O.Length(25))

    def * = (title, id) <> ((models.domain.Tag.apply _).tupled, models.domain.Tag.unapply)

    //    def autoInc = (title, id).mapTo[models.domain.Tag] returning id
    //    def * = (title, id).mapTo[models.domain.Tag]

  }

}

