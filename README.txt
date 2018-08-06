These are three versions of the same application, one in Java MapReduce, other in Spark and another using SparkSQL.
The application consists of listing every word from a given HDFS directory and printing them in reverse alphabetical order.

To execute the Java version:

hadoop jar {EXTRACTION_PATH}/target/WordCount-1.0-SNAPSHOT.jar  br.edu.positivo.wordInverseOrder.WordInverseOrder  /input /output