name := """sharepics-play"""
organization := "sharepics"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"




libraryDependencies ++= Seq(
  guice,
  //  filters,
  "com.typesafe.play" %% "play-slick" % "4.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.0",
  //  "com.typesafe.play" %% "play-slick" % "3.0.3",
  //  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.3",
  //  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
  "com.microsoft.azure" % "azure-storage-blob" % "10.5.0", //microsoft azure storage
  "org.postgresql" % "postgresql" % "42.2.5",
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1" % Test,
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "sharepics.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "sharepics.binders._"
