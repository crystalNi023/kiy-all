#!/bin/sh  
  
# chkconfig: - 60 50
# description: KIY Servo Service
# processname: kiy-servo

# Source function library.
. /etc/rc.d/init.d/functions  
  
RETVAL=0
prog="kiy-servo"
JAVA_HOME=/usr/java/jdk1.8.0_112
WORK_HOME=/data/kiy-service

# jsvc path
DAEMON_HOME=/data/kiy-service  
  
USER=root
PID=$WORK_HOME/jsvc_stat.pid
CLASSPATH=$CLASSPATH:$WORK_HOME/commons-daemon-1.0.15.jar:$WORK_HOME/kiy-servo.jar
  
  
case "$1" in 
start)  

# Start Serivce  
  
$DAEMON_HOME/jsvc -user $USER -home $JAVA_HOME -Duser.dir=$WORK_HOME -wait 5000 -pidfile $PID -outfile $WORK_HOME/log/jsvc.out -errfile '&1' -cp $CLASSPATH com.kiy.servo.Service
  
exit $?
;;  

stop)  
  
# Stop Serivce
  
$DAEMON_HOME/jsvc -stop -pidfile $PID com.kiy.servo.Service  
  
exit $?
;;  

*)  

echo "Usage myjsvc start/stop"  
exit 1;;
esac