# URL Shortener

## 1. Prerequisites

* Mongo running locally
  * host: localhost
  * port: 27017
#### To run

````
docker-compose up
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
    "url": "http://localhost:8080/rest/url/cac87a2c"
}
```
Response codes:

| HTTP Status | Description           |
|-------------|-----------------------|
| 200         | successful operation  |
| 400         | bad request           |
| 500         | internal server error |

#### Retrive Original URL
URL:

`http://localhost:8080/rest/url/{shortUrl}`

Method:
````
GET
````

Response body:
```
```
Response codes:

| HTTP Status | Description           |
|-------------|-----------------------|
| 200         | successful operation  |
| 404         | not found             |
| 500         | internal server error |
