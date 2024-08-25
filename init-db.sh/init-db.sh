#!/bin/bash

DB_NAME="testTaskDB"
DB_USER="postgres"

# Создание базы данных
psql -U "$DB_USER" -c "CREATE DATABASE $DB_NAME;"
chmod +x init-db.sh
