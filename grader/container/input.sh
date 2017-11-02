#!/bin/bash

LANGUAGE=$1
SPEC=$2
SOURCE=$3
RESULTS_FILE=$(echo "require 'uuidtools'; puts UUIDTools::UUID.random_create" | ruby).xml

echo $SOURCE | base64 --decode > file.rb
echo $SPEC | base64 --decode > file.spec.rb

rspec file.spec.rb -f RspecJunitFormatter -o $RESULTS_FILE > /dev/null

cat $RESULTS_FILE