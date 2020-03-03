#URL Shortener #

### Prerequisites
* Postgres running locally
  * host: localhost
  * port: 5432
##### To run

````
docker-compose up -d
````

### Execution

```
java -jar url-shortner.jar
```

### API Details
#### Create Short URL:
`http://localhost:8080/rest/url`

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

#### Get Original URL:
`http://localhost:8080/rest/url/{shortUrl}`

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
