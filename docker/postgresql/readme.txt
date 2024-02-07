
docker-compose -f docker-compose-pgadmin.yml -p pgadmin up -d
http://localhost:5050/browser/

Postgresql without compose file
docker run --name kuzds-postgres -p 5432:5432 -e POSTGRES_USER=test -e POSTGRES_PASSWORD=test -e POSTGRES_DB=test_db -d postgres
docker run --name kuzds-postgres -p 5432:5432 -e POSTGRES_USER=test -e POSTGRES_PASSWORD=test -e POSTGRES_DB=test_db -d postgres:14-alpine