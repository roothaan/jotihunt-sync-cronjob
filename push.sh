#!/usr/bin/env bash

docker build . --tag jasperroel/jotihunt-sync-cronjob:0.0.1
docker push jasperroel/jotihunt-sync-cronjob:0.0.1
