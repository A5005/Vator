@echo off
title Server Console
java -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -Xmx1G -jar spigot-1.8.8-R0.1-SNAPSHOT-latest.jar
PAUSE