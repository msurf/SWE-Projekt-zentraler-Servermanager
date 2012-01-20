#!/bin/bash

#this script creates a new textdocument for each argument given at its call
# created by pt

echo $@

for i in $@
do
	touch $i.txt
done
