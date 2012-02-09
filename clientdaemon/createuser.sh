#!/bin/bash

## Usage: createusr USERNAME PASSWORD
## by Philipp Tendyra


grep $1 /etc/passwd
if [ $? = 1 ]
then
	passwort=$2
	salt=$(($RANDOM % 100))
	crypt=$(perl -e 'print crypt("'$passwort'", "'$salt'")')
	useradd -m -s /bin/bash -p $crypt $1
	if [ $? = 0 ]
	then
		echo The User $1 was created successfully
	else
		echo User could not be added
	fi
else
	echo The User already  exists
fi
