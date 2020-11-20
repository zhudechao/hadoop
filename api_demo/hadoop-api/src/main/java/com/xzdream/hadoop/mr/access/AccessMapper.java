package com.xzdream.hadoop.mr.access;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 自定义map处理类
 */
public class AccessMapper extends Mapper<LongWritable, Text,Text,Access> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] lines = value.toString().split("\t");

        String phone = lines[0];
        long up = Long.parseLong(lines[1]);
        long down = Long.parseLong(lines[2]);

        context.write(new Text(phone),new Access(phone,up,down));
    }
}
