#!/bin/bash

# Pull new changes
git pull

# Prepare Jar
mvn clean
mvn package

#apply migrations
mvn liquibase:update

# Ensure, that docker-compose stopped
docker-compose stop

# Add environment variables
export DB_LOGIN=$1
export DB_PASSWORD=$2

# Start new deployment
docker-compose up --build -d