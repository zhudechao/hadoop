```
Hadoop12345678
```
```
创建hadoop用户
[root@hadoop001 ~]# useradd hadoop
[root@hadoop002 ~]# useradd hadoop
[root@hadoop003 ~]# useradd hadoop
```
```
切换到hadoop用户
[root@hadoop001 ~]# su hadoop
[root@hadoop002 ~]# su hadoop
[root@hadoop003 ~]# su hadoop
```
```
配置多用户ssh互相信任关系
[hadoop@hadoop001 ~]$ ssh-keygen
[hadoop@hadoop002 ~]$ ssh-keygen
[hadoop@hadoop003 ~]$ ssh-keygen

选择一台机器生成公钥文件
[hadoop@hadoop001 ~]$ cd .ssh/
[hadoop@hadoop001 .ssh]$ cat id_rsa.pub >> authorized_keys

将02,03机器的公钥传给01机器
[hadoop@hadoop002 .ssh]$ scp id_rsa.pub root@172.18.251.89:/home/hadoop/.ssh/id_rsa02
[hadoop@hadoop003 .ssh]$ scp id_rsa.pub root@172.18.251.89:/home/hadoop/.ssh/id_rsa03

将02,03机器公钥追加到authorized_keys文件
[hadoop@hadoop001 .ssh]$ cat id_rsa02 >> authorized_keys 
[hadoop@hadoop001 .ssh]$ cat id_rsa03 >> authorized_keys 

```
```
hadoop001机器配置hosts
[root@hadoop001 ~]# cat /etc/hosts
172.18.121.230 hadoop001 hadoop001
172.18.121.229 hadoop002 hadoop002
172.18.121.228 hadoop003 hadoop003

[root@hadoop002 ~]# cat /etc/hosts
172.18.121.230 hadoop001 hadoop001
172.18.121.229 hadoop002 hadoop002
172.18.121.228 hadoop003 hadoop003

[root@hadoop003 ~]# cat /etc/hosts
172.18.121.230 hadoop001 hadoop001
172.18.121.229 hadoop002 hadoop002
172.18.121.228 hadoop003 hadoop003

[hadoop@hadoop001 .ssh]$ scp authorized_keys root@hadoop002:/home/hadoop/.ssh/
[hadoop@hadoop001 .ssh]$ scp authorized_keys root@hadoop003:/home/hadoop/.ssh/

[hadoop@hadoop001 .ssh]$ chmod 600 authorized_keys
[hadoop@hadoop002 .ssh]$ chmod 600 authorized_keys
[hadoop@hadoop003 .ssh]$ chmod 600 authorized_keys

```
```
三台机器分别部署JDK
[root@hadoop001 software]# tar -zxvf jdk-8u212-linux-x64.tar.gz
[root@hadoop002 software]# tar -zxvf jdk-8u212-linux-x64.tar.gz
[root@hadoop003 software]# tar -zxvf jdk-8u212-linux-x64.tar.gz

[root@hadoop001 software]# mv jdk1.8.0_212/ /usr/java/
[root@hadoop002 software]# mv jdk1.8.0_212/ /usr/java/
[root@hadoop003 software]# mv jdk1.8.0_212/ /usr/java/

配置权限
[root@hadoop001 java]# chown -R root:root jdk1.8.0_212/
[root@hadoop001 java]# chown -R root:root jdk1.8.0_212/*
[root@hadoop002 java]# chown -R root:root jdk1.8.0_212/
[root@hadoop002 java]# chown -R root:root jdk1.8.0_212/*
[root@hadoop003 java]# chown -R root:root jdk1.8.0_212/
[root@hadoop003 java]# chown -R root:root jdk1.8.0_212/*

配置环境变量/etc/profile
JAVA_HOME=/usr/java/jdk1.8.0_212
PATH=$JAVA_HOME/bin:$PATH
export PATH
```

```
配置zookeeper
[hadoop@hadoop001 software]$ wget http://archive.apache.org/dist/zookeeper/zookeeper-3.4.9/zookeeper-3.4.9.tar.gz
[hadoop@hadoop002 software]$ wget http://archive.apache.org/dist/zookeeper/zookeeper-3.4.9/zookeeper-3.4.9.tar.gz
[hadoop@hadoop003 software]$ wget http://archive.apache.org/dist/zookeeper/zookeeper-3.4.9/zookeeper-3.4.9.tar.gz

解压并移动到app目录
[hadoop@hadoop001 software]$ mv zookeeper-3.4.9 ../app/

建立软链接
[hadoop@hadoop001 app]$ ln -s /home/hadoop/app/zookeeper-3.4.9/ /home/hadoop/app/zookeeper
[hadoop@hadoop002 app]$ ln -s /home/hadoop/app/zookeeper-3.4.9/ /home/hadoop/app/zookeeper
[hadoop@hadoop003 app]$ ln -s /home/hadoop/app/zookeeper-3.4.9/ /home/hadoop/app/zookeeper

配置01机器zookeeper配置文件
[hadoop@hadoop001 conf]$ cp zoo_sample.cfg zoo.cfg

创建myid文件
[hadoop@hadoop001 zookeeper]$ mkdir data
[hadoop@hadoop001 zookeeper]$ touch data/myid
[hadoop@hadoop001 zookeeper]$ echo 1 > data/myid

将zoo.cfg配置文件分别scp到02,03机器
[hadoop@hadoop001 conf]$ scp zoo.cfg hadoop002:/home/hadoop/app/zookeeper/conf/
[hadoop@hadoop001 conf]$ scp zoo.cfg hadoop003:/home/hadoop/app/zookeeper/conf/

[hadoop@hadoop002 zookeeper]$ mkdir data
[hadoop@hadoop002 zookeeper]$ touch data/myid
[hadoop@hadoop002 zookeeper]$ echo 2 > data/myid

[hadoop@hadoop003 zookeeper]$ mkdir data
[hadoop@hadoop003 zookeeper]$ touch data/myid
[hadoop@hadoop003 zookeeper]$ echo 3 > data/myid 
```
```
配置完之后启动zookeeper
[hadoop@hadoop001 bin]$ ./zkServer.sh start
[hadoop@hadoop002 bin]$ ./zkServer.sh start
[hadoop@hadoop003 bin]$ ./zkServer.sh start

查看zookeeper启动状态(一个leader,两个follower)
[hadoop@hadoop001 bin]$ ./zkServer.sh status
[hadoop@hadoop002 bin]$ ./zkServer.sh status
[hadoop@hadoop003 bin]$ ./zkServer.sh status
```

```
安装Hadoop
[hadoop@hadoop001 software]$ mv hadoop-2.6.0-cdh5.7.0 ../app/
[hadoop@hadoop002 software]$ mv hadoop-2.6.0-cdh5.7.0 ../app/
[hadoop@hadoop003 software]$ mv hadoop-2.6.0-cdh5.7.0 ../app/

[hadoop@hadoop001 app]$ ln -s /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/ /home/hadoop/app/hadoop
[hadoop@hadoop002 app]$ ln -s /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/ /home/hadoop/app/hadoop
[hadoop@hadoop003 app]$ ln -s /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/ /home/hadoop/app/hadoop

创建hdfs配置的目录
[hadoop@hadoop001 hadoop]$ mkdir -p /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/data/dfs/name
[hadoop@hadoop002 hadoop]$ mkdir -p /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/data/dfs/name
[hadoop@hadoop003 hadoop]$ mkdir -p /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/data/dfs/name

[hadoop@hadoop001 hadoop]$ mkdir -p /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/data/dfs/data
[hadoop@hadoop002 hadoop]$ mkdir -p /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/data/dfs/data
[hadoop@hadoop003 hadoop]$ mkdir -p /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/data/dfs/data

[hadoop@hadoop001 app]$ mkdir -p /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/data/dfs/jn
[hadoop@hadoop002 app]$ mkdir -p /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/data/dfs/jn
[hadoop@hadoop003 app]$ mkdir -p /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/data/dfs/jn
```
```
覆盖配置文件
core-site.xml
hdfs-site.xml
mapred-site.xml
yarn-site.xml
slaves

/home/hadoop/app/hadoop/etc/hadoop
三台机器都要覆盖
```
```
启动journalnode
[hadoop@hadoop001 sbin]$ ./hadoop-daemon.sh start journalnode
[hadoop@hadoop002 sbin]$ ./hadoop-daemon.sh start journalnode
[hadoop@hadoop003 sbin]$ ./hadoop-daemon.sh start journalnode

[hadoop@hadoop001 bin]$ jps
20309 JournalNode
19349 QuorumPeerMain
20425 Jps

```
```
格式化hadoop文件系统
[hadoop@hadoop001 bin]$ ./hadoop namenode -format
```
```
[hadoop@hadoop001 hadoop]$ scp -r data/ hadoop002:/home/hadoop/app/hadoop
[hadoop@hadoop001 hadoop]$ scp -r data/ hadoop003:/home/hadoop/app/hadoop
[hadoop@hadoop001 hadoop]$ hdfs zkfc -formatZK
20/11/17 22:00:38 INFO ha.ActiveStandbyElector: Successfully created /hadoop-ha/xzdream in ZK.
```
```
启动dfs
[hadoop@hadoop001 sbin]$ ./start-dfs.sh 
20/11/17 22:31:09 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
Starting namenodes on [hadoop001 hadoop002]
hadoop001: starting namenode, logging to /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/logs/hadoop-hadoop-namenode-hadoop001.out
hadoop002: starting namenode, logging to /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/logs/hadoop-hadoop-namenode-hadoop002.out
hadoop002: starting datanode, logging to /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/logs/hadoop-hadoop-datanode-hadoop002.out
hadoop001: starting datanode, logging to /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/logs/hadoop-hadoop-datanode-hadoop001.out
hadoop003: starting datanode, logging to /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/logs/hadoop-hadoop-datanode-hadoop003.out
Starting journal nodes [hadoop001 hadoop002 hadoop003]
hadoop001: journalnode running as process 20309. Stop it first.
hadoop002: journalnode running as process 19783. Stop it first.
hadoop003: journalnode running as process 19827. Stop it first.
20/11/17 22:31:19 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
Starting ZK Failover Controllers on NN hosts [hadoop001 hadoop002]
hadoop001: starting zkfc, logging to /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/logs/hadoop-hadoop-zkfc-hadoop001.out
hadoop002: starting zkfc, logging to /home/hadoop/app/hadoop-2.6.0-cdh5.7.0/logs/hadoop-hadoop-zkfc-hadoop002.out
[hadoop@hadoop001 sbin]$ 
[hadoop@hadoop001 sbin]$ 
[hadoop@hadoop001 sbin]$ jps
20644 NameNode
21076 DFSZKFailoverController
20309 JournalNode
19349 QuorumPeerMain
20774 DataNode
21148 Jps

```

```
启动yarn
[hadoop@hadoop001 sbin]$ ./start-yarn.sh
[hadoop@hadoop002 sbin]$ ./yarn-daemon.sh start resourcemanager
```

```
上传一个文件到hdfs
[hadoop@hadoop001 hadoop]$ hdfs dfs -put README.txt hdfs://xzdream
[hadoop@hadoop001 hadoop]$ hdfs dfs -ls /
```






















