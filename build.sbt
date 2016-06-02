name := "scamandrill"

organization := "io.github.scamandrill"

bintrayOrganization := Some("scamandrill")

bintrayRepository := "scamandrill"

bintrayReleaseOnPublish in ThisBuild := false

licenses += ("Apache-2.0", url("https://spdx.org/licenses/Apache-2.0"))

description := "Scala client for Mandrill api"

scalaVersion := "2.11.7"
val playVersion = "2.5.3"

crossScalaVersions := Seq("2.10.5", "2.11.7")

scalacOptions := Seq(
  "-feature", "-unchecked", "-deprecation", "-encoding", "utf8"
)

resolvers ++= Seq("spray repo" at "http://repo.spray.io/")

parallelExecution in Test := true

libraryDependencies ++= {
  Seq(
    "com.typesafe.play" %% "play-ws"          % playVersion,
    "com.typesafe"      % "config"            % "1.3.0",
    "org.slf4j"         % "slf4j-api"         % "1.7.21"
  ) ++ Seq(
    "org.scalatest"            %%  "scalatest"       % "2.2.6"     % "test->*",
    "com.typesafe.play"        %%  "play-test"       % playVersion % "test",
    "org.slf4j"                %   "slf4j-simple"    % "1.7.21"    % "test",
    "de.leanovate.play-mockws" %%  "play-mockws"     % "2.5.0"     % "test"
  )
}

publishArtifact in Test := false

publishMavenStyle := true

pomIncludeRepository := { _ => false }

pomExtra :=
  <url>http://github.com/scamandrill/scamandrill</url>
    <scm>
      <connection>scm:git:github.com/scamandrill/scamandrill.git</connection>
      <developerConnection>scm:git:git@github.com:scamandrill/scamandrill.git</developerConnection>
      <url>github.com/scamandrill/scamandrill</url>
    </scm>
    <developers>
      <developer>
        <id>dzsessona</id>
        <name>Diego Zambelli Sessona</name>
        <url>https://www.linkedin.com/in/diegozambellisessona</url>
      </developer>
      <developer>
        <id>graingert</id>
        <name>Thomas Grainger</name>
        <url>https://graingert.co.uk/</url>
      </developer>
    </developers>
