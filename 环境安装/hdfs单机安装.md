```
配置hosts
vi /etc/hosts

127.0.0.1 hadoop001
```

```
安装jdk
software]# mkdir /usr/java
software]# tar -zxvf jdk-8u212-linux-x64.tar.gz -C /usr/java/
software]# cd /usr/java/

修改权限
java]# chown -R root:root jdk1.8.0_212/
java]# chown -R root:root jdk1.8.0_212/*

配置环境变量
java]# vi /etc/profile

JAVA_HOME=/usr/java/jdk1.8.0_212
export PATH=$JAVA_HOME/bin:$PATH

jdk1.8.0_212]# source /etc/profile

```

```
切换到hadoop用户
jdk1.8.0_212]# su hadoop
```

```
ssh无密码信任授权
[hadoop@hadoop001 ~]$ ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
 ~]$ cd .ssh/
[hadoop@hadoop001 .ssh]$ cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
[hadoop@hadoop001 .ssh]$ chmod 600 authorized_keys

验证是否成功
.ssh]$ ssh hadoop001 date
The authenticity of host 'hadoop001 (127.0.0.1)' can't be established.
ECDSA key fingerprint is c1:59:c3:ad:86:ae:8f:ef:1d:14:db:56:81:c4:01:3d.
Are you sure you want to continue connecting (yes/no)? yes
Warning: Permanently added 'hadoop001' (ECDSA) to the list of known hosts.
2020年 11月 13日 星期五 17:18:49 CST
[hadoop@hadoop001 .ssh]$ ssh hadoop001 date
2020年 11月 13日 星期五 17:18:52 CST
[hadoop@hadoop001 .ssh]$ 

```

```
安装hdfs
~]$ mkdir app software

~]$ cd software/
[hadoop@hadoop001 software]$ wget http://archive.cloudera.com/cdh5/cdh/5/hadoop-2.6.0-cdh5.7.0.tar.gz

解压到app目录
[hadoop@hadoop001 software]$ tar -zxvf hadoop-2.6.0-cdh5.7.0.tar.gz -C ../app/

修改配置文件
cd /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/etc/hadoop
core-site.xml
hadoop-env.sh
hdfs-site.xml
mapred-site.xml
slaves
yarn-site.xml
```
```
格式化
cd /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/bin
bin]$ ./hdfs namenode -format

```
```
启动
cd /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/sbin
sbin]$ ./start-dfs.sh
sbin]$ ./start-yarn.sh
```
```
启动成功
sbin]$ jps
19122 NameNode
19571 ResourceManager
19987 Jps
19668 NodeManager
19242 DataNode
19406 SecondaryNameNode

```








