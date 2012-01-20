#!/bin/bash

echo $@

for i in $@
do
	touch $i.txt
done
