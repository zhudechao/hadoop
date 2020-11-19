```
HDFS命令
```
```
列出文件或目录
[hadoop@hadoop001 app]$ hadoop fs -ls /

将本地文件上传到hdfs根目录
[hadoop@hadoop001 hadoop]$ hadoop fs -put NOTICE.txt /

查看hdfs文件内容
[hadoop@hadoop001 hadoop]$ hadoop fs -cat /README.txt

拷贝本地文件到hdfs
[hadoop@hadoop001 hadoop]$ hadoop fs -copyFromLocal README.txt /

移动本地文件到hdfs
[hadoop@hadoop001 hadoop]$ hadoop fs -moveFromLocal README.txt /

下载hdfs文件到本地
[hadoop@hadoop001 app]$ hadoop fs -get /README.txt ./

在hdfs中创建一个目录
[hadoop@hadoop001 app]$ hadoop fs -mkdir /uui
```
