name := "sk.scala.algorithms"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc" % "3.3.2",
  "org.scalikejdbc" %% "scalikejdbc-test" % "3.3.2" % "test",
  "org.scalikejdbc" %% "scalikejdbc-config" % "3.3.2",
  "com.h2database" % "h2" % "1.4.197",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.postgresql" % "postgresql" % "9.4.1207.jre7"
)