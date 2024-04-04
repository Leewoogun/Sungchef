FROM ubuntu:20.04

# install essential libraries
RUN apt-get -y update
RUN apt-get -y upgrade
RUN apt-get -y dist-upgrade

RUN apt-get install -y wget unzip ssh openssh-* net-tools

# install java 1.8 for Hadoop
RUN apt-get install -y openjdk-8-jdk
RUN find / -name java-8-openjdk-amd64 2>/dev/null

# set environment variable
ENV PATH="${PATH}:/usr/lib/jvm/java-8-openjdk-amd64/bin"
ENV JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64"

SHELL ["/bin/bash", "-c"]
RUN echo 'export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64' >> ~/.bashrc
RUN source ~/.bashrc

# install python 3.8
RUN apt-get install -y python3-pip

# ssh setup
RUN ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
RUN cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
RUN chmod 0600 ~/.ssh/authorized_keys

# install hadoop
RUN wget https://dlcdn.apache.org/hadoop/common/hadoop-3.2.4/hadoop-3.2.4.tar.gz
RUN tar -zxvf hadoop-3.2.4.tar.gz
RUN mkdir ~/cluster/hadoop
RUN mv hadoop-3.2.4 ~/cluster/hadoop

# set environment variable
ENV PATH="${PATH}:/root/cluster/hadoop/bin:/root/cluster/hadoop/sbin"
ENV HADOOP_HOME="/root/cluster/hadoop"

RUN echo 'export HADOOP_HOME=/root/cluster/hadoop' >> ~/.bashrc;
RUN echo 'export HADOOP_COMMON_HOME=$HADOOP_HOME' >> ~/.bashrc;
RUN echo 'export HADOOP_HDFS_HOME=$HADOOP_HOME' >> ~/.bashrc;
RUN echo 'export HADOOP_CONF_DIR=$HADOOP_HOME/etc/hadoop' >> ~/.bashrc;
RUN echo 'export YARN_CONF_DIR=$HADOOP_HOME/etc/hadoop' >> ~/.bashrc;
RUN echo 'export HADOOP_YARN_HOME=$HADOOP_HOME' >> ~/.bashrc;
RUN echo 'export HADOOP_MAPRED_HOME=$HADOOP_HOME' >> ~/.bashrc;

RUN source ~/.bashrc