# movie-service

*) Create a Spring Boot application that provides the following REST endpoints:-
==============================================================================
1) /movies - returns all movies. Implement caching when returning results.
The following optional parameters can be passed to narrow down the results.
'startDate': '<yyyyMMdd_format>' // starting date, i.e. if the date is today, the search should list movies from today onwards
'screenType': '<string>' // Standard or IMAX
2) /movies/{id} - get movie information. Implement caching when returning result.
3) /movies/{id} - update movie information.

Refer to the file movies.json for the source of data. Load this file to an in memory database using H2: H2 Database Engine
DOs:
1. Use best practices in your implementation -- use your industry experience using Java, Spring Boot and RDS to finish this assignment.
2. Use JDK 17 + Kotlin recent version
3. Use Spring Boot 3.x.x and a recent version of H2

