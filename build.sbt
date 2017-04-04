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
    "com.github.japgolly.scalacss" %% "core" % "0.5.3-RC1",
    "org.scala-js" %%% "scalajs-dom" % "0.9.1",
    "com.github.japgolly.scalacss" %%% "ext-react" % "0.5.3-RC1",
    "com.github.japgolly.scalajs-react" %%% "core" % "1.0.0-RC2"
  ),
  jsDependencies ++= Seq(
    "org.webjars.bower" % "react" % "15.4.2"
      /        "react-with-addons.js"
      minified "react-with-addons.min.js"
      commonJSName "React",
    "org.webjars.bower" % "react" % "15.4.2"
      /         "react-dom.js"
      minified  "react-dom.min.js"
      dependsOn "react-with-addons.js"
      commonJSName "ReactDOM",
    "org.webjars.bower" % "react" % "15.4.2"
      /         "react-dom-server.js"
      minified  "react-dom-server.min.js"
      dependsOn "react-dom.js"
      commonJSName "ReactDOMServer"
    )
)
.enablePlugins(ScalaJSPlugin)

publishTo := Some(Resolver.file("clonepool",  new File( "./docs/clonepool")))
