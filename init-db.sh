#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE user_service;
    CREATE DATABASE job_service;
    CREATE DATABASE bidding_service;
    CREATE DATABASE payment_service;
    CREATE DATABASE notification_service;
    CREATE DATABASE rating_service;
    CREATE DATABASE skill_service;
EOSQL
