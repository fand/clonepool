import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Clonepool",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      "org.backuity" %% "ansi-interpolator" % "1.1.0"
    )
  )

publishTo := Some(Resolver.file("clonepool",  new File( "./docs/clonepool" )) )
