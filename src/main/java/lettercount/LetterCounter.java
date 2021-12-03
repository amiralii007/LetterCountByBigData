package lettercount;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

public class LetterCounter {
    String fileName;

    public LetterCounter(String fileName) {
        this.fileName = fileName;
    }

    public void letterCount(String outputDir) {
        System.out.println("Spark machine is being created");
        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("JD Word Counter");

        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        System.out.println("Spark machine reading the file ...");
        JavaRDD<String> inputFile = sparkContext.textFile(fileName);

        System.out.println("Spark machine parse all word in the file ...");
        JavaRDD<String> wordsFromFile = inputFile.flatMap(content -> Arrays.asList(content.split(" ")));

        System.out.println("Finding real words ...");
        JavaRDD<String> realWordsFromFile = wordsFromFile.filter(s -> !s.equals("") && !(s.charAt(0) < '\u0600') && !(s.charAt(0) > '\u06FF')); // character check

        System.out.println("PreParing teh output ...");
        JavaPairRDD countWords = realWordsFromFile.mapToPair(t -> new Tuple2(t.charAt(0), 1)).reduceByKey((x, y) -> (int) x + (int) y);

        System.out.println("save file in the given name directory");
        countWords.saveAsTextFile(outputDir);
    }
}
