package br.edu.positivo.wordInverseOrder;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * WordCount
 */
public class WordInverseOrder {
	
	public static String change(String aux)
	{
		String res="";
		
		for(int i=0; i<aux.length(); i++)
    	{
			res+=(char)('z'-aux.charAt(i)+'a')+"";				
    	}
		
		
		return res;
	}

    public static class CounterMapper extends Mapper<Object, Text, Text, Text>
    {

        private final static Text one = new Text();
        private Text word = new Text();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException 
        {
        	String aux;
        	
            StringTokenizer itr = new StringTokenizer(value.toString());
            while (itr.hasMoreTokens()) 
            {
            	aux = itr.nextToken().toLowerCase();
            	
            	aux=change(aux);
            	
                word.set(aux);    
                one.set("_");
                
                context.write(word, one);
            }
        }
    }

    public static class CounterReducer extends Reducer<Text,Text,Text,Text> 
    {
        private Text result = new Text();
        private Text word = new Text();

        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        	String aux = key.toString().toLowerCase();
        	        	
        	String ss=change(aux);  
        	
        	word.set(change(aux));
        	result.set("");
            context.write(word, result);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(WordInverseOrder.class);
        job.setMapperClass(CounterMapper.class);
        //job.setCombinerClass(CounterReducer.class);
        job.setReducerClass(CounterReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
