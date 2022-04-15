// Run on VM as: spark-submit ./scalahive_2.11-0.1.0-SNAPSHOT.jar  --class example.HiveDemo
package example

import java.io.IOException

import scala.util.Try

// Demo1.
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;

// // Demo2.
// import org.apache.hadoop.hive.cli.CliSessionState
// import org.apache.hadoop.hive.conf.HiveConf
// import org.apache.hadoop.hive.ql.Driver
// import org.apache.hadoop.hive.ql.session.SessionState
// import java.sql.Driver

object HiveDemo {
  def main(args: Array[String]) {
    println("Starting Hive Demo...")

    //demo1()
    demo2()

  }

  def demo1(): Unit = {

    //val hiveClient = new HiveClient
    //hiveClient.executeHQL("SELECT * FROM trucks")
    // hiveClient.executeHQL("DROP TABLE IF EXISTS DEMO")
    // hiveClient.executeHQL("CREATE TABLE IF NOT EXISTS DEMO(id int)")
    // hiveClient.executeHQL("INSERT INTO DEMO VALUES(1)")
    // hiveClient.executeHQL("SELECT * FROM DEMO")
    //hiveClient.executeHQL("SELECT COUNT(*) FROM DEMO")
  }

  def demo2(): Unit = {

    var con: java.sql.Connection = null;
    try {
      // For Hive2:
      var driverName = "org.apache.hive.jdbc.HiveDriver"
      val conStr = "jdbc:hive2://sandbox-hdp.hortonworks.com:10000/default";

      // For Hive1:
      //var driverName = "org.apache.hadoop.hive.jdbc.HiveDriver"
      //val conStr = "jdbc:hive://sandbox-hdp.hortonworks.com:10000/default";

      Class.forName(driverName);

      con = DriverManager.getConnection(conStr, "", "");
      val stmt = con.createStatement();
      stmt.executeQuery("Show databases");
      System.out.println("show database successfully");

      println("Executing SELECT * FROM trucks..")
      var res = stmt.executeQuery("SELECT * FROM trucks")
      while (res.next()) {
        println(s"${res.getString(1)}, ${res.getString(3)}, ${res.getString(4)}")
      }

      val tableName = "testHiveDriverTable";
      println(s"Dropping table $tableName..")
      stmt.execute("drop table IF EXISTS " + tableName);
      println(s"Creating table $tableName..")
      stmt.execute(
        "create table " + tableName + " (key int, value string) row format delimited  fields terminated by ','"
      );

      // show tables
      println(s"Show TABLES $tableName..")
      var sql = "show tables '" + tableName + "'";
      System.out.println("Running: " + sql);
      res = stmt.executeQuery(sql);
      if (res.next()) {
        System.out.println(res.getString(1));
      }

      // describe table
      println(s"Describing table $tableName..")
      sql = "describe " + tableName;
      System.out.println("Running: " + sql);
      res = stmt.executeQuery(sql);
      while (res.next()) {
        System.out.println(res.getString(1) + "\t" + res.getString(2));
      }

      // load data into table
      // NOTE: filepath has to be local to the hive server
      // NOTE: /tmp/a.txt is a comma separated file with two fields per line
      // For E.g.; 1,"One"
      val filepath = "/tmp/a.txt";
      sql = "load data local inpath '" + filepath + "' into table " + tableName;
      System.out.println("Running: " + sql);
      stmt.execute(sql);

      // select * query
      sql = "select * from " + tableName;
      System.out.println("Running: " + sql);
      res = stmt.executeQuery(sql);
      while (res.next()) {
        System.out.println(
          String.valueOf(res.getInt(1)) + "\t" + res.getString(2)
        );
      }

      // regular hive query
      sql = "select count(1) from " + tableName;
      System.out.println("Running: " + sql);
      res = stmt.executeQuery(sql);
      while (res.next()) {
        System.out.println(res.getString(1));
      }
    } catch {
      case ex => {
        ex.printStackTrace();
        throw new Exception(s"${ex.getMessage}")
      }
    } finally {
      try {
        if (con != null)
          con.close();
      } catch {
        case ex => {
          ex.printStackTrace();
          throw new Exception(s"${ex.getMessage}")
        }
      }
    }
  }
}

// // Hive meta API client.
// class HiveClient {

//   val hiveConf = new HiveConf(classOf[HiveClient])

//   // Get the hive ql driver to execute ddl or dml
//   private def getDriver: Driver = {
//     println(s"Getting Driver...")
//     val driver = new Driver(hiveConf)
//     SessionState.start(new CliSessionState(hiveConf))
//     driver
//   }

//   def executeHQL(hql: String): Int = {
//     println(s"Executing query $hql ...")
//     //val responseOpt = Try(getDriver.run(hql)).toEither
//     // val response = responseOpt match {
//     //   case Right(response) => response
//     //   case Left(exception) => throw new Exception(s"${ exception.getMessage }")
//     // }
//     try {
//       val responseOpt = getDriver.run(hql)
//       val response = responseOpt

//       val responseCode = response.getResponseCode
//       if (responseCode != 0) {
//         val err: String = response.getErrorMessage
//         throw new IOException(
//           "Failed to execute hql [" + hql + "], error message is: " + err
//         )
//       }
//       responseCode

//     } catch {
//       case ex => {
//         throw new Exception(s"${ex.getMessage}")
//       }
//     }
//   }

// }
