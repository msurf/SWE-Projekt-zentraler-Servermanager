#!/bin/bash

## start/stop-script
## by Philipp Tendyra and Samuel Schueppen
# /etc/init.d/installhelper

##DAEMON: User-Name for screen
##Params: start-parameters

DAEMON="swe"
CMD="/home/$DAEMON/startServer $DAEMON"

start() {

if [ `whoami` != "root" ]; then
	echo "Wrong User : "'whoami'
	exit 1
fi

screen -AmdS $DAEMON $CMD
}

stop() {


if [ `whoami` != "root" ]; then
	echo "Wrong User : "'whoami'
	exit 1
fi

screen -r $DAEMON

}

restart() {
	stop
	start

}

case "$1" in
start)
	start
	;;
stop)
	stop
	;;
restart)
	restart
	;;
*)
	echo "Usage: $0 {start|stop|restart}"
	exit 1
esac
