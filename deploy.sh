#!/bin/bash

git pull origin master
docker build . -t lukevers-design

docker ps | grep lukevers-design | awk '{print $1}' | xargs docker kill
docker run lukevers-design:latest -p 3030:3030 -d