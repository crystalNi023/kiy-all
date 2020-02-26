#!/bin/sh

# chkconfig: - 60 50
# description: KIY Cloud
# processname: kiy-cloud

USER=root
JAVA_DIR=$JAVA_HOME
WORK_DIR=/home/kiy-cloud
FILE_LOG=$WORK_DIR/console.log

COMMAND="nohup $JAVA_DIR/bin/java -server -Duser.dir=$WORK_DIR -cp $WORK_DIR/kiy-cloud.jar com.kiy.cloud.Service >> $FILE_LOG 2>&1 &"
# echo $COMMAND

PID=`$JAVA_DIR/bin/jps -l | grep com.kiy.cloud.Service`
PID=${PID%% *}

if [ -n "$1" ]; then

	# START
	if [ start = $1 ]; then
	
		if [ -n "$PID" ]; then
			echo SERVER ALREADY RUNNING
			exit $?
		fi
	
		echo START KIY CLOUD SERVER
		su - $USER -c "$COMMAND"
	
	fi
	
	# STOP
	if [ stop = $1 ]; then
	
		echo STOP KIY CLOUD SERVER
		su - $USER -c "kill $PID"
	
	fi

fi

PID=`$JAVA_DIR/bin/jps -l | grep com.kiy.cloud.Service`
echo PID:$PID
exit $?

# /etc/init.d/
# chkconfig --add kiy-cloud
# chkconfig --del kiy-cloud