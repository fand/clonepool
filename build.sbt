import Dependencies._

lazy val root = (project in file(".")).settings(
  inThisBuild(List(
    organization := "com.example",
    scalaVersion := "2.11.8",
    version      := "0.0.0"
  )),
  name := "Clonepool",
  libraryDependencies ++= Seq(
    scalaTest % Test,
    "org.backuity" %% "ansi-interpolator" % "1.1"
  )
)

lazy val docs = project.settings(
  name := "ClonepoolWeb",
  scalaJSUseMainModuleInitializer := true,
  libraryDependencies ++= Seq(
    "com.github.japgolly.scalacss" %% "core" % "0.5.1",
    "org.scala-js" %%% "scalajs-dom" % "0.9.1"
  )
)
.enablePlugins(ScalaJSPlugin)

publishTo := Some(Resolver.file("clonepool",  new File( "./docs/clonepool")))
