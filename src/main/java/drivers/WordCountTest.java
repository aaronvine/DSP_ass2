package drivers;

import mappers.WordCountMapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import reducers.WordCountReducer;
import writable.WordsInDecadeWritable;

public class WordCountTest {

    public static void main(String[] args) throws Exception {

//        Configuration conf = new Configuration();
//
//        Job job = new Job(conf, "Word Count");
//
//        job.setOutputKeyClass(Text.class);
//        job.setOutputValueClass(MapWritable.class);
//
//        job.setMapperClass(WordCountMapper.class);
//        job.setReducerClass(WordCountReducer.class);
//
//        job.setInputFormatClass(SequenceFileInputFormat.class);
//        job.setOutputFormatClass(TextOutputFormat.class);
//
//        FileInputFormat.addInputPath(job, new Path(args[0]));
//        FileOutputFormat.setOutputPath(job, new Path(args[1]));
//
//        job.waitForCompletion(true);

        System.out.println("Running Word Count Test!");
        Configuration conf = new Configuration();
//        conf.set("mapred.map.tasks","10");
//        conf.set("mapred.reduce.tasks","2");
        Job job = new Job(conf, "Word Count");
        job.setJarByClass(WordCount.class);
        job.setMapperClass(WordCountMapper.class);
//        job.setPartitionerClass(PartitionerClass.class);
//        job.setCombinerClass(ReduceClass.class);
        job.setReducerClass(WordCountReducer.class);

        job.setOutputKeyClass(WordsInDecadeWritable.class);
        job.setOutputValueClass(LongWritable.class);

        job.setInputFormatClass(SequenceFileInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}