# appointment-management-system
## Spring Boot, postgresql, JPA , lombok ,springdoc , Docker ,Maven

Medical clinic with one doctor needs an appointment management system, where the clinic admin can review appointments by date or patient.
Also, admin can check patient history on demand, add new appointment, cancel appointment with cancellation reason (No show, based on patient request, Physician apology) 


## Requirements

1. Java - 17
2. Maven 
3. postgresql 
4. Spring Data
5. Spring Boot  
6. lombok
7. springdoc
8. Docker 

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/mohamedragab33/appointment-management-system.git
```

**2. Run spring-boot with postgresql with docker compose with this commands on terminal at project path **

```bash
cd src/main/docker
docker-compose down
docker rmi docker-spring-boot-postgres:latest
docker-compose up
```

The app will start running at <http://localhost:8080>.

## Explore Rest APIs By import  

```bash
you can use : 
springdocs-openapi to show 
JSON : http://localhost:8080/v3/api-docs 
 or 
 UI Documentation : http://localhost:8080/swagger-ui/index.html for all APIS.
```

## All api covered with unit testing 
## you can import me.collection to post man to test allEnpoints  

## By: Mohamed Ragab
