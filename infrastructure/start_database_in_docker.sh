#!/usr/bin/env bash

SCRIPT_DIR="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

#echo $SCRIPT_DIR

DOCKER_RUN_ARGS=()
DOCKER_RUN_ARGS+=(--env MARIADB_ROOT_PASSWORD=secret)
DOCKER_RUN_ARGS+=(--env MARIADB_PASSWORD=secret)
DOCKER_RUN_ARGS+=(--env MARIADB_USER=employee)
DOCKER_RUN_ARGS+=(--env MARIADB_DATABASE=employee)
DOCKER_RUN_ARGS+=(--name employee-db)
DOCKER_RUN_ARGS+=(--publish 3306:3306)
DOCKER_RUN_ARGS+=(--rm)
DOCKER_RUN_ARGS+=(--volume $SCRIPT_DIR/sql/employee.sql:/docker-entrypoint-initdb.d/employee.sql:ro)

docker run "${DOCKER_RUN_ARGS[@]}" mariadb:10.6
