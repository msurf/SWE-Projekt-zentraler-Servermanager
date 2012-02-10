#!/bin/bash

## Usage: createuser USERNAME PASSWORD

## The User who runs this script needs adminrights
## so add the following two lines to visudo
## %USER ALL=NOPASSWD: way-to-createuser.sh
## %USER ALL=NOPASSWD: /usr/sbin/useradd

## if you can't run visudo please check if sudo is installed, else get it

## by Philipp Tendyra


grep $1 /etc/passwd
if [ $? = 1 ]
then
	passwort=$2
	salt=$(($RANDOM % 100))
	crypt=$(perl -e 'print crypt("'$passwort'", "'$salt'")')
	sudo useradd -m -s /bin/bash -p $crypt $1
	if [ $? = 0 ]
	then
		grep $1 /etc/passwd
		if [ $? = 0 ]
		then
			echo The User $1 was created successfully
		else
			echo a problem accrued. please check.
		fi
	else
		echo User could not be added
	fi
else
	echo The User already  exists
fi
