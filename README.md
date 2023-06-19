**This example provides an availability search by implementing 2 different microservices to achieve
a CQRS concern separation.** Both projects use Springboot 3.0.6 and Java 17.

**The project solution architecture proposed is the following:**

![](.\docs\example_arq_diagram.png)

**Note**: In this case PostgreSQL due to the requirement of querying `similar` matches).

- **hotels-search-command-svc**: Exposes a `/hotels-availability/search` rest operation (8080), that handles the command operations over hotel search requests.
Sends an event to the topic `hotel_availability_searches` which will be consumed
on the same application to be afterwards saved in the database. 


  - **Hexagonal architecture**: Has been chosen for this microservice due
  to the quantity of external technologies to take account in the future
  (spring web, kafka, postgres).

- **hotels-search-query-svc**: Exposes a `/hotels-availability/count` rest operation (8081) to perform
querying to the database with the results of the previous microservice, retrieving a count of similar 
searches to the one provided.
  - **3 Layered architecture**: Has been chosen for this microservice because
  an increased complexity is not needed in this scenario.


The swagger definition for the rest endpoints is contained in the following
[swagger yml](.\docs\swagger_definition.yaml)

**Locally running and considerations:**

- Although there are a lots of Tests and Integration Tests (Using test containers to avoid using external dockerized instances)
I provide a [`docker-compose.yml`](docker-compose.yml) file in the root of the project, to be able to run the project infrastructure locally,
you just have to make a `docker-compose up -d` inside this folder before running the projects under the `local` springboot profile.
They are exposed on ports (8080, 8081) by default.

- The PostgreSQL instance will use the [generation script](sql/schema-generation.sql) on the docker compose file
to generate the schema locally (on the ITS the default generation is used), this file also include some indexes
for the "Recommended" search query.

**Tradeoffs:**

- Due to lack of time and in favor of simplifying the example, I avoided using another layer of mapping for the events, hence of this, I decided to directly
  pass the Domain to the producer/consumer. This should be avoided generally, creating another layer and mapping
  the domain to a proper Event.

**Thanks for taking the time to look into this, it was very fun example to do :) - [dev](http://www.github.com/droar)**
-