# FlightSearch API REST


> 
 I have implemented a REST API for the search of flights, the following parameters have to be sent:
 > 
-	departure: a departure airport IATA code
-	departureDateTime: a departure datetime in the departure airport timezone in ISO format
-	arrival: an arrival airport IATA code
-	arrivalDateTime: an arrival datetime in the arrival airport timezone in ISO format

> 
I have added an optional parameter to indicate the maximum number of stops:

> 
stops: number of stops, by default it will be a maximum of 1 stop, but with this field you can specify the maximum number of stops.
> 

> Sequence diagram of our API

![Sequence diagram](https://raw.githubusercontent.com/victoralvarez43/FlightSearch/master/diagrams/secuenceImg.png)


> Integrated git with travis CI: https://travis-ci.org/victoralvarez43/FlightSearch/builds
>

> The definition of the endpoints for the routes and months APIs is done in the restClient.properties properties:

```java
#Rest Route
rest.ryanair.endpoint.routes=https://api.ryanair.com/core/3/routes/
rest.ryanair.endpoint.month=https://api.ryanair.com/timetable/3/schedules/{airportFrom}/{airportTo}/years/{year}/months/{month}

#Rest Connect
rest.endpoint.timeout.connect=15000
rest.endpoint.timeout.read=15000
```

> To use the API you must make a get request like the following:

>
http://localhost:8080/flightSearch/interconnections?departure=DUB&arrival=WRO&departureDateTime=2018-06-01T07:00&arrivalDateTime=2018-06-01T21:50

>
This example will look for flights on the DUB-WRO route that are between the dates 2018-06-01T07: 00 and 2018-06-01T21: 500, and since the stop parameter has not been specified, you will have a maximum of 1 stop.

```json
[
    {
        "stops": 0,
        "legs": [
            {
                "departureDateTime": "2018-06-01T17:45",
                "arrivalDateTime": "2018-06-01T21:20",
                "departureAirport": "DUB",
                "arrivalAirport": "WRO"
            }
        ]
    },
    {
        "stops": 1,
        "legs": [
            {
                "departureDateTime": "2018-06-01T07:15",
                "arrivalDateTime": "2018-06-01T08:15",
                "departureAirport": "DUB",
                "arrivalAirport": "GLA"
            },
            {
                "departureDateTime": "2018-06-01T13:05",
                "arrivalDateTime": "2018-06-01T16:30",
                "departureAirport": "GLA",
                "arrivalAirport": "WRO"
            }
        ]
    },
    {
        "stops": 1,
        "legs": [
            {
                "departureDateTime": "2018-06-01T10:35",
                "arrivalDateTime": "2018-06-01T11:35",
                "departureAirport": "DUB",
                "arrivalAirport": "MAN"
            },
            {
                "departureDateTime": "2018-06-01T15:25",
                "arrivalDateTime": "2018-06-01T18:40",
                "departureAirport": "MAN",
                "arrivalAirport": "WRO"
            }
        ]
    }
]
```


>
For example if in the previous case we indicate the parameter stops=0:

>
http://localhost:8080/flightSearch/interconnections?departure=DUB&arrival=WRO&departureDateTime=2018-06-01T07:00&arrivalDateTime=2018-06-01T21:50&stops=0

>
Then only direct flights will be obtained:

>

```json
[
    {
        "stops": 0,
        "legs": [
            {
                "departureDateTime": "2018-06-01T17:45",
                "arrivalDateTime": "2018-06-01T21:20",
                "departureAirport": "DUB",
                "arrivalAirport": "WRO"
            }
        ]
    }
]
```

> Other example but this start in 2018 and end 2019

```json
[
  -----
  
    {
        "stops": 1,
        "legs": [
            {
                "departureDateTime": **"2018-12-31T21:30"**,
                "arrivalDateTime": **"2018-12-31T22:45"**,
                "departureAirport": "DUB",
                "arrivalAirport": "STN"
            },
            {
                "departureDateTime": **"2019-01-02T13:30"**,
                "arrivalDateTime": **"2019-01-02T16:55"**,
                "departureAirport": "STN",
                "arrivalAirport": "PMI"
            }
        ]
    },
    
  -----
]
```

>
To improve the performance i have configured 2 caches:

>
RouteCache: Cache the requests to the routes API, since there is only one API that obtains all the routes between airports, this cache only has 1 element.
monthCache: Cache the requests to the months API, as maximum 50 searches of months will be saved.

>
With these caches we improve the performance in flight searches, since the operation that is slower: searching all routes between airports, will be cached and will be immediate.

>
The data will be cacheed the first time the request is made, in the following requests they will be immediate since they will be searched.

>
I have also configured a retry logic for requests to the APIS of routes and months:

>
@Retryable (value = {RetryHTTPException.class}, maxAttempts = 3, backoff = @Backoff (delay = 5000))

>
With this annotation it is indicated that if the RetryHTTPException exception is carried out, the retry will be carried out with a maximum of 3 retries and, in addition, between retries, 5000 msec (5 seconds) will be expected.

>
To execute the application it can be done by means of jetty and maven executing the following command:

>
- mvn jetty: run

>
And a jetty server will be lifted at port 8080.

>
Another way is to create the war package and use a server like tomcat or another, to generate the war it must be executed:

>
- mvn clean package