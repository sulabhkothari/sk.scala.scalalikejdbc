//import scalikejdbc.{ConnectionPool, ConnectionPoolSettings, DB}
import scalikejdbc._
import java.sql.Connection
import scalikejdbc.config._

object ConnectionPoolWrapper {
  def orm = {
    val empNamed = NamedDB("key") readOnly { implicit session =>
      val empp = sql"select id, tstamp from goose_db_version where id = ${id}"
        .map(rs => Emp(rs.int("id"), rs.string("tstamp"))).single.apply()
      println("______________________"+empp)
      val e = Prod.syntax("e")
      withSQL {
        select.from(Prod as e).where.eq(e.num, id)
      }.map(Prod(e.resultName)).single().apply()
    }
    println(empNamed)
  }

  def main(args: Array[String]): Unit = {
    classOf[org.postgresql.Driver]
    //    DBs.setupAll()
    //    val settings = ConnectionPoolSettings(
    //      initialSize = 5,
    //      maxSize = 20,
    //      connectionTimeoutMillis = 3000L,
    //      validationQuery = "select 1 from dual")

    ConnectionPool.add("key",
      "jdbc:postgresql://localhost:5432/mydb",
      "usr", "pwd",
      ConnectionPoolSettings())

    ConnectionPool.add("key",
      "jdbc:postgresql://localhost:5432/mydb",
      "usr", "pwd",
      ConnectionPoolSettings())

    ConnectionPool.add('key,
      "jdbc:postgresql://localhost:5432/mydb",
      "usr", "pwd",
      ConnectionPoolSettings())

    //    ConnectionPool.singleton(
    //      "jdbc:postgresql://localhost:5432/postgres",
    //      "price_user", "C0mplexPwd",
    //      ConnectionPoolSettings())

    //    ConnectionPool.singleton(
    //      "jdbc:postgresql://localhost:5432/ds_price",
    //      "price_user", "C0mplexPwd",
    //      ConnectionPoolSettings())

    val emp: Option[Emp] = using(DB(ConnectionPool.borrow("key"))) { db =>
      db.readOnly { implicit session =>
        sql"select id, tstamp from goose_db_version where id = ${id}"
          .map(rs => Emp(rs.int("id"), rs.string("tstamp"))).single.apply()
      }
    }

    println(emp)
    //DBs.closeAll()

    val emp2: Option[Emp] = using(DB(ConnectionPool.borrow("key"))) { db =>
      db.readOnly { implicit session =>
        sql"select id, tstamp from goose_db_version where id = ${id}"
          .map(rs => Emp(rs.int("id"), rs.string("tstamp"))).single.apply()
      }
    }

    println("----------------------------")

    orm
  }

  val id = 2

  // define a class to map the result
  case class Emp(num: Int, name: String)

  //QueryDSL
  object Prod extends SQLSyntaxSupport[Emp] {
    override val connectionPoolName = "key"
    override val schemaName = Some("sch")
    override val tableName = "goose_db_version"
    override val nameConverters = Map("name" -> "tstamp", "num" -> "id")

    def apply(e: ResultName[Emp])(rs: WrappedResultSet): Emp = new Emp(num = rs.get(e.num), name = rs.get(e.name))
  }

}




