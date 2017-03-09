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
      "org.eclipse.jgit" % "org.eclipse.jgit" % "4.6.1.201703071140-r",
      "org.slf4j" % "slf4j-nop" % "1.7.24"
    )
  )

publishTo := Some(Resolver.file("clonepool",  new File( "./docs/clonepool" )) )
