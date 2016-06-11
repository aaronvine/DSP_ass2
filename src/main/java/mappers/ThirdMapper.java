package mappers;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import writable.SecondReduceOutput;
import writable.SeconderySortWritable;
import writable.WordsInDecadeWritable;

import java.io.IOException;

public class ThirdMapper extends Mapper<LongWritable, Text, WordsInDecadeWritable, SecondReduceOutput> {

	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		// <key, value> format: < [word, decade], [word1, count, (word1, word2, count)] / count>
		System.out.println("Mapping 3nd time..");
		String[] primarySplit = value.toString().split("\t");
		WordsInDecadeWritable newKey;
		SecondReduceOutput newValue;
		if (primarySplit.length > 1) {
			String[] rawWordInDecade = primarySplit[0].split(" ");
			String[] rawSecondReduceOutput = primarySplit[1].split(" ");
			if (rawSecondReduceOutput.length > 1) {
				newKey = new WordsInDecadeWritable(rawWordInDecade[0], Integer.parseInt(rawWordInDecade[1]));
				newValue = new SecondReduceOutput(
						rawSecondReduceOutput[0].replaceAll("[^\\w\\s]", ""),
						Long.parseLong(rawSecondReduceOutput[1].replaceAll("[^\\w\\s]", "")),
						rawSecondReduceOutput[3].replaceAll("[^\\w\\s]", ""),
						Long.parseLong(rawSecondReduceOutput[4].replaceAll("[^\\w\\s]", ""))
						);
			} else {
				newKey = new WordsInDecadeWritable(rawWordInDecade[0], Integer.parseInt(rawWordInDecade[1]));
				newValue = new SecondReduceOutput(Long.parseLong(rawSecondReduceOutput[0]));
			}

		} else {
			return;
		}
		context.write(newKey, newValue);
	}

}