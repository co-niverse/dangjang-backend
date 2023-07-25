#!/bin/bash

cd ./src/main/resources/config
git commit -am "chore: $1"
git push

cd ../../../..
git submodule update --remote
git add ./src/main/resources/config
git commit -m "chore: update submodule"
