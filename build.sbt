// https://typelevel.org/sbt-typelevel/faq.html#what-is-a-base-version-anyway
ThisBuild / tlBaseVersion := "0.2" // your current series x.y

ThisBuild / organization     := "me.wojnowski"
ThisBuild / organizationName := "Jakub Wojnowski"
ThisBuild / startYear        := Some(2023)
ThisBuild / licenses         := Seq(License.MIT)
ThisBuild / developers       := List(
  // your GitHub handle and name
  tlGitHubDev("jwojnowski", "Jakub Wojnowski")
)

ThisBuild / tlCiReleaseBranches        := Seq()
ThisBuild / githubWorkflowJavaVersions := Seq(JavaSpec.temurin("21"))

val Scala213 = "2.13.16"
ThisBuild / crossScalaVersions := Seq(Scala213, "3.7.1")
ThisBuild / scalaVersion       := Scala213 // the default Scala

lazy val root = tlCrossRootProject.aggregate(core, circe, tapir)

lazy val core =
  project
    .in(file("core"))
    .settings(
      name := "scuid",
      libraryDependencies ++= Seq(
        "org.typelevel" %% "cats-core"               % "2.13.0",
        "org.typelevel" %% "cats-effect"             % "3.6.3",
        "org.scalameta" %% "munit"                   % "1.1.1"    % Test,
        "org.scalameta" %% "munit-scalacheck"        % "1.1.0"    % Test,
        "org.typelevel" %% "munit-cats-effect"       % "2.1.0"    % Test,
        "org.typelevel" %% "scalacheck-effect-munit" % "2.0.0-M2" % Test,
        "co.fs2"        %% "fs2-core"                % "3.12.0"   % Test
      )
    )

lazy val circe =
  project
    .in(file("circe"))
    .dependsOn(core % "compile->compile;test->test")
    .settings(
      name := "scuid-circe",
      libraryDependencies ++= Seq(
        "io.circe"      %% "circe-core"       % "0.14.14",
        "io.circe"      %% "circe-literal"    % "0.14.14" % Test,
        "org.scalameta" %% "munit"            % "1.1.1"   % Test,
        "org.scalameta" %% "munit-scalacheck" % "1.1.0"   % Test
      )
    )

lazy val tapir =
  project
    .in(file("tapir"))
    .dependsOn(core % "compile->compile;test->test")
    .settings(
      name := "scuid-tapir",
      libraryDependencies ++= Seq(
        "com.softwaremill.sttp.tapir" %% "tapir-core" % "1.11.40"
      )
    )
