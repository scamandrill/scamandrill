name := "scamandrill"

organization := "io.github.scamandrill"

bintrayOrganization := Some("scamandrill")

bintrayRepository := "scamandrill"

bintrayReleaseOnPublish in ThisBuild := false

licenses += ("Apache-2.0", url("https://spdx.org/licenses/Apache-2.0"))

description := "Scala client for Mandrill api"

scalaVersion := "2.12.2"

crossScalaVersions := Seq("2.11.11", "2.12.2")

scalacOptions := Seq(
  "-feature", "-unchecked", "-deprecation", "-encoding", "utf8"
)

parallelExecution in Test := true

libraryDependencies ++= {
  Seq(
    "com.typesafe.play" %% "play-ahc-ws-standalone"       % "1.0.1",
    "com.typesafe.play" %% "play-ws-standalone-json"      % "1.0.1",
    "org.slf4j"         % "slf4j-api"                     % "1.7.25"
  ) ++ Seq(
    "org.scalatest"            %%  "scalatest"       % "3.0.1"   % "test",
    "com.typesafe.play"        %%  "play-test"       % "2.6.1"   % "test",
    "org.slf4j"                %   "slf4j-simple"    % "1.7.21"  % "test"
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
