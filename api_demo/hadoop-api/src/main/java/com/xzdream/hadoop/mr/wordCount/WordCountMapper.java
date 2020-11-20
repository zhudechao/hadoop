package com.xzdream.hadoop.mr.wordCount;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * KEYIN: map任务读数据读key类型，offset,是每行数据起始位置读偏移量，Long
 * VALUEIN: map任务读数据的value类型，其实就是一行行的字符串，String
 *
 * KEYOUT: map方法自定义实现输出的key的类型,String
 * VALUEOUT:map方法自定义实现输出的value的类型，Integer
 */
public class WordCountMapper extends Mapper<LongWritable, Text,Text, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] words = value.toString().split("\t");

        for (String word:words){
            context.write(new Text(word),new IntWritable(1));
        }
    }
}
