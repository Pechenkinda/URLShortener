# URL Shortener

## 1. Prerequisites

* Postgres running locally
  * host: localhost
  * port: 5432
#### To run

````
docker-compose up
````
#### Environment variables
````
POSTGRESQL_NODES - host and port for postgres (by default: localhost:5432) 
POSTGRESQL_DATABASE - database name (by default: postgres) 
POSTGRESQL_USER - username for postgres (by default: postgres)
POSTGRESQL_PASSWORD - password for postgres (by default: postgres)
````

## 2. Build app
#### Build jar
````
mvn clean install
````
#### Execution
```
java -jar url-shortner.jar
```

## 3. API Details
#### Create Short URL
URL:

`http://localhost:8080/rest/url`

Method:

````
POST
````
Request body:
```JSON
{
    "url": "https://www.google.com"
}
```
Response body:
```JSON
{
    "url": "cac87a2c"
}
```
Response codes:

| HTTP Status | Description           |
|-------------|-----------------------|
| 200         | successful operation  |
| 500         | internal server error |

#### Get Original URL
URL:

`http://localhost:8080/rest/url/{shortUrl}`

Method:
````
GET
````

Response body:
```JSON
{
    "url": "https://www.google.com/"
}
```
Response codes:

| HTTP Status | Description           |
|-------------|-----------------------|
| 200         | successful operation  |
| 404         | not found             |
| 500         | internal server error |
