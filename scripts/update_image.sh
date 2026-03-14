#!/bin/bash

VERSION=$1

sed -i "s|image:.*|image: dockerhub-user/java-demo:$VERSION|g" deployment.yaml

git config user.email "ci-bot@company.com"
git config user.name "ci-bot"

git add deployment.yaml

git commit -m "update image version $VERSION [skip ci]"

git push
