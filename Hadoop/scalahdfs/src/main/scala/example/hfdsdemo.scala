// hdfsdemo.scala
package example

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import java.io.PrintWriter;

object HdfsDemo {

  val path = "hdfs://sandbox-hdp.hortonworks.com:8020/user/maria_dev/"
  def main(args: Array[String]) {
    createFile()
    copyFromLocal()
  }

  def copyFromLocal(): Unit = {
    val src = "file:///home/maria_dev/files2.txt"
    val target = path + "files2.txt"
    println(s"Copying local file $src to $target ...")
    
    val conf = new Configuration()
    val fs = FileSystem.get(conf)

    val localpath = new Path(src)
    val hdfspath = new Path(target)
    
    fs.copyFromLocalFile(false, localpath, hdfspath)
    println(s"Done copying local file $src to $target ...")
  }

  def createFile(): Unit = {
    val filename = path + "sample.txt"
    println(s"Creating file $filename ...")
    
    val conf = new Configuration()
    val fs = FileSystem.get(conf)
    
    // Check if file exists. If yes, delete it.
    println("Checking if it already exists...")
    val filepath = new Path( filename)
    val isExisting = fs.exists(filepath)
    if(isExisting) {
      println("Yes it does exist. Deleting it...")
      fs.delete(filepath, false)
    }

    val output = fs.create(new Path(filename))
    
    val writer = new PrintWriter(output)
    writer.write("Hello World\n")
    writer.close()
    
    println(s"Done creating file $filename ...")
  }
}
