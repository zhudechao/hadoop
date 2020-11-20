package com.xzdream.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//java api 操作 HDFS 文件系统
public class HDFSApp {
    public static final String HDFS_PATH = "hdfs://47.106.12.155:8020";

    FileSystem fileSystem = null;
    Configuration configuration = null;

    @Before
    public void setUp() throws URISyntaxException, IOException, InterruptedException {
        configuration = new Configuration();
        fileSystem = FileSystem.get(new URI(HDFS_PATH),configuration,"hadoop");
    }

    @After
    public void tearDown(){
        configuration = null;
        fileSystem = null;
    }

    @Test
    public void mkdir() throws Exception {
        boolean result = fileSystem.mkdirs(new Path("/api/test2"));
        System.out.println(result);
    }

    @Test
    public void text() throws IOException {
        FSDataInputStream fins = fileSystem.open(new Path("/README.txt"));
        IOUtils.copyBytes(fins,System.out,1024);
    }

    //创建文件
    @Test
    public void create() throws IOException {
        FSDataOutputStream out = fileSystem.create(new Path("/yy1.txt"));
        out.writeUTF("ddccd");
        out.flush();
        out.close();
        System.out.println("成功");
    }

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
//        Configuration configuration = new Configuration();
//        FileSystem fileSystem = FileSystem.get(new URI("hdfs://47.106.12.155:8020"),configuration,"hadoop");
//
//        Path path = new Path("/api/test");
//        boolean result = fileSystem.mkdirs(path);
//        System.out.println(result);
    }
}
