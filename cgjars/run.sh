#!/bin/bash

nohup java -jar web-0.0.1-SNAPSHOT.jar &
nohup java -jar UserService-0.0.1-SNAPSHOT.jar &
nohup java -jar upload-0.0.1-SNAPSHOT.jar &
nohup java -jar SongService-0.0.1-SNAPSHOT.jar &
