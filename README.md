<a href="https://github.com/justAnArthur/ticket_ordering_system"><img src=".github/banner.svg" alt="Airline ticket ordering: Spring Boot web app that finds direct and one-stop flights, seats a whole group and emails every passenger." width="100%"></a>

# Airline ticket ordering

A Spring Boot web app for searching flights, booking seats for a group of passengers and mailing each of them a confirmation, with an admin side for airports, airlines and schedules. Built for Object-Oriented Programming (OOP) at FIIT STU in spring 2023.

> Finished and archived. The assigned topic was path planning; the report narrows it to airline ticket ordering. Payment is a stub: `PaymentReservationProcess` passes straight through and is not part of the booking chain.

![Search page: date and airports on the left, scheduled flights on the right](src/main/resources/static/templates.mainPage.png)

## What it does

- Search by date and two airports: lists direct flights and one-stop connections whose second leg leaves within 12 hours of the first landing
- Book a route: a chain of responsibility creates one reservation per leg, gives every passenger a free seat from the aircraft's seat list (or fails if there are too few) and saves the itinerary
- Notify: `NotificationServiceImpl` passes each passenger to its registered listeners, and `EmailNotificationListener` sends the confirmation through Spring Mail
- Admin at `/admin`: add, edit and delete airports; add and edit airlines with their aircraft, flights and dated flight instances; browse itineraries
- 17 JPA entities on HyperSQL, including a `Person` hierarchy (passenger, pilot, crew, admin) mapped with joined-table inheritance

## How it works

```mermaid
flowchart TD
  A[Search form: date, from, to] --> B[findRoutes: direct flights, or two legs where the second leaves within 12 h of landing]
  B --> C[Pick a route and enter passengers for each leg]
  C --> D[CreateReservationProcess: one reservation per leg, a free seat for every passenger, itinerary saved]
  D -->|not enough seats| X[Unable to create reservation]
  D --> E[NotificationReservationProcess]
  E --> F[NotificationServiceImpl hands each passenger to its listeners]
  F --> G[EmailNotificationListener sends the confirmation mail]
```

The main entities:

```mermaid
classDiagram
  class Person {
    <<abstract>>
    email
    name
    phone
  }
  Person <|-- Passenger
  Person <|-- Pilot
  Person <|-- Crew
  class Flight {
    flightNumber
    schedule
  }
  class FlightInstance {
    date
    gate
    status
  }
  class FlightReservation {
    seatMap
    status
  }
  class Itinerary {
    creationDate
  }
  Flight --> Airport : departure, arrival
  Flight "1" --> "*" FlightInstance : instances
  FlightInstance --> Aircraft
  FlightInstance --> "*" Pilot : pilots
  FlightInstance --> "*" Crew : crew
  FlightReservation --> FlightInstance : flight
  FlightReservation --> "*" Passenger : seat map
  Itinerary "1" --> "*" FlightReservation : reservations
  Itinerary --> Airport : start, final
```

## Run

The app connects to an HSQLDB server at `jdbc:hsqldb:hsql://localhost/test`; start one first (command adapted from `index.md`):

```bash
java -cp hsqldb-2.7.1.jar org.hsqldb.server.Server --database.0 file.test --dbname.0 test
```

Then build and start the app on http://localhost:8080 (admin at `/admin`):

```bash
./mvnw package -DskipTests
java -jar target/oop_ticket_ordering_system-0.0.1-SNAPSHOT.jar
```

It targets Java 19. On JDK 21, build with `-Dlombok.version=1.18.34`: the Lombok that Spring Boot 3.0.4 pins does not compile there. Confirmation mails go out through Gmail SMTP (`spring.mail.*` in `application.properties`); set the password in `SPRING_MAIL_PASSWORD`.

## Stack

Java 19, Spring Boot 3.0.4 (Web, Data JPA, Mail, Mustache), HyperSQL 2.7.1, Lombok, Tailwind CSS from its CDN, Maven wrapper.

## Documentation

- [src/main/resources/index.pdf](src/main/resources/index.pdf): the project report, also kept in full below
- [src/main/resources/static/diagram.jpg](src/main/resources/static/diagram.jpg): the full UML class diagram ([draw.io source](src/main/resources/static/diagram.drawio))
- [src/main/resources/javadoc](src/main/resources/javadoc): generated JavaDoc
- [hsqldb.service](hsqldb.service), [oop_ticket_ordering_system.service](oop_ticket_ordering_system.service): the systemd units it ran under on a server, with the jar from [artifact](artifact)

## License

[CC BY-NC-ND 4.0](LICENSE): share it with credit, but no changes and no commercial use. Don't hand it in as your own coursework.

---

## Report

# Airline management system

![templates.mainPage.png](src/main/resources/static/templates.mainPage.png)

As part of the course, we needed to implement a software solution for:

> Path planning
>
> Journeys can be very complex events that can really benefit from software support. They often involve detours in order
> to visit places of interest close to the main route. Various services and events may be offered at these locations.
> These places can be recommended based on the needs and boundaries of the traveler. The original itinerary may change
> during the trip. Traveling in a group may involve synchronizing detours. Whether the travel plan is created by a
> professional or by the travelers themselves, it may be worthy of sharing with others.

My decision was made - to implement a system for ordering tickets on a train - a plane. So, trains and airplanes are the
most common form of long-distance transportation.
---

### Selected technologies

In my project - I decided to use the following techniques:

<div class="grid-2">

- **Java 19** is one of the latest versions of Java and has many new features and improvements. Using the new Java
  features
  can simplify development and make code more readable.

- **Spring Boot** is a Java web application development framework that simplifies and speeds up the process of creating
  applications. It provides a number of handy tools and features such as automatic application configuration and
  assembly, integration with databases, and the ability to easily create REST APIs.

- **Spring Web** is a module of the Spring framework that provides functionality for building web applications. It makes
  it
  easy to create REST APIs and handle HTTP requests and responses.
    - **Mustache template manager** - a template engine that allows you to create HTML pages using templates. It is very
      simple and easy to use.
    - **Tailwind CSS** - a CSS framework that makes it easy to create responsive web pages. It provides a number of
      pre-defined styles and components that can be used to create a web page.

- **HyperSQL** is an open relational database in Java. It can be used to create local or remote databases, and has many
  features such as SQL support, indexing, transactions, etc.

- And of course a **Maven** build tool that makes it easy to manage dependencies and build the project:

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-mustache</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-mail</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.hsqldb</groupId>
        <artifactId>hsqldb</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

</div>

---

### 🐛 Versions

Unfortunately due to a hashing failure on the local and remote server - I've lost my commit history. Everything should
have looked like this:

```mermaid
%%{init: { 'logLevel': 'debug', 'theme': 'base' } }%%
gitGraph
    commit id: "Project Objective" tag: "5 points"
    commit
    commit id: "Working program version" tag: "Working program version"
    commit
    commit
    commit id: "Better UI"
    commit
    commit
    commit id: "Final version of program" tag: "Program"
    commit
    commit id: "Documentation" tag: "Documentation"
```

#### Project Objective

- index.md - file with project description and next wanted features to implement
- pom.xml - file with project dependencies I'll use

#### Working program version

- dao/model/ - all required classes with relations that you can see in the UML diagram.
- dao/repository/ - all required interfaces that I'll use to work with database.
- api/controllers/ - rest api controllers to manage all requests and return a correct mustache template with provided
  required data.
- services/ - all complex logic.
- resources/templates/ - all static files that I'll use in the project.

#### Better UI

- resources/templates/ - changed all mustache with Tailwind CSS templates to make them more user-friendly.

#### Working version

- services/ - added more complex logic to handle email sending for example.

#### Documentation

- resources/index.md - documentation file with all required information about the project.

### 🫠 My thinking about

In my opinion, my project should be given a maximum score, or one that comes close to it. Why? Because of:

- I have implemented all the criteria.
- I've used half the technology
- And also put some soul into this project.

---

## Problem definition

An airline management system is a piece of software that is used to efficiently handle all aspects of the airline
system. Every airline now has a management system in place to digitize the process of scheduling flights, managing
workers, making ticket bookings, and executing other airline management operations. Furthermore, the system maintains
track of the quantity of aircraft, pilots, and airport availability. As a result, the airline management system assists
consumers as well as administrators and
regulates all airline operations.

## Design approach

### UML class diagram

As the number of classes as well as the number of all attributes and methods is large, the UML-Diagram itself is huge,
so here is part of it:

![templates.diagram.png](src/main/resources/static/templates.diagram.png)

> You can see the full version in the attached file.

### Classes definitions

All definitions for classes you can find in the comments in the code. But basically all classes are divided into 3
packages:

- dao/model/ - all required classes with relations.
- services/ - all complex logic that have a main chain of handling a reservation for example.
- api/controllers/ and etc. - for manage gui and rest api requests.

---

## OOP criteria and principles

> The main criteria in project assessment are the extent to which the assignment is fulfilled, application of
> object-oriented mechanisms in the program (an appropriate use of inheritance, encapsulation, and aggregation), code
> organization, and documentation quality.

### Basic principles

Basic principles of OOP. In my project I've used:

### Inheritance

For example in my project I've used inheritance in the following class:

![templates.inheritance.diagram.png](src/main/resources/static/templates.inheritance.diagram.png)

### Encapsulation

I am using encapsulation in every class. To not make my code as a scrapyard I've used a lombok annotations. For
example:

```java

@Data // ! this annotation generate all getters and setters for all fields
@Entity
@Table(name = "itinerary")
public final class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Airport startingAirport;
    @ManyToOne
    private Airport finalAirport;
    private LocalDateTime creationDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlightReservation> reservations = new LinkedList<>();

    /**
     * @return all unique passengers in a reservation
     */
    @JsonProperty(value = "passengers", required = true)
    public Collection<Passenger> getAllPassengers() {
        return this.reservations.stream()
                .map(FlightReservation::getPassengers)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }
}
```

### Polymorphism

I am using a polymorphism for example in Observers:

```java
public interface NotificationListener {
    void handleSend(Person person, NotificationType type);
}

// ...

@Component
public class EmailNotificationListener implements NotificationListener {

    private final JavaMailSender emailSender;

    @Autowired
    public EmailNotificationListener(
            JavaMailSender emailSender
    ) {
        this.emailSender = emailSender;
    }

    @Override
    public void handleSend(Person person, NotificationType type) {
        // ...
    }
}
```

### Abstraction

For example an abstract chain class:

```java
/**
 * Abstract class for process element.
 *
 * @param <R> handler request
 * @param <T> object for changing
 */
@Setter
public abstract class AbstractProcessElement<R, T> implements ProcessElement<R, T> {

    private BiFunction<R, T, T> next;

    protected T callNext(R request, T object) {
        if (next != null) {
            return next.apply(request, object);
        }
        return object;
    }
}
```

---

### Design patterns

I've used the following design patterns:

### Chain of responsibility

To handle creation reservation process I've used a chain of responsibility pattern:

![templates.designPatterns.diagram.png](src/main/resources/static/templates.designPatterns.diagram.png)

### Observers

I am using to handle notification for a users.

![templates.designPatterns.diagram2.png](src/main/resources/static/templates.designPatterns.diagram2.png)

---

### Custom extensions

I've used the following custom extensions:

<div class="grid-2">

```java

@EqualsAndHashCode(callSuper = true)
@Data
public class TOSException extends RuntimeException {
    private Object[] args;
    private ExceptionCode code;

    public TOSException(ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public TOSException(String message, ExceptionCode code) {
        super(message);
        this.code = code;
    }

    public TOSException(Object[] args, ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
```

![templates.exception.diagram.png](src/main/resources/static/templates.exception.diagram.png)

</div>

---

### Front-end solution

<div class="grid-3">

<div style="border: 1px solid #ccc; border-radius: 10px">

![gui.folderStructures.png](src/main/resources/static/gui.folderStructures.png)

</div>

<div style="grid-column: span 2 / span 2;">

![templates.adminPage.png](src/main/resources/static/templates.adminPage.png)

> All client and admin requests are handled by the same controllers, but the admin has more options.

</div>
</div>

---

### Multithreading

I've used multithreading for sending emails to users for example. And I am using a new thread like this:

```java
public class FindItineraryResponse {

    public FindItineraryResponse(List<List<FlightInstance>> routes) {
        this.routes = routes.stream()
                .parallel() // that is actually multithreading
                .map(route ->
                        new FindItineraryWithLink(
                                route,
                                route.stream()
                                        .map(FlightInstance::getId)
                                        .map(String::valueOf)
                                        .collect(Collectors.joining(","))
                        )
                )
                .toList();
    }
}
```

---

### Generic types

In my project I've used generic types for example in the following class:

```java
public interface ProcessElement<R, T> {
    T handler(R request, T object);
}

//...

public interface ProcessChainService<R, T> {
    T evalute(R request, T object);
}

//..

public class CreateReservationProcess extends AbstractProcessElement<ReserveItineraryRequest, Itinerary> {
}
```

---

### Lambda expressions

I am using stream api, so every function in stream is a lambda expression. For example:

```java
public class FindItineraryResponse {
    List<FindItineraryWithLink> routes;

    public FindItineraryResponse(List<List<FlightInstance>> routes) {
        this.routes = routes.stream()
                .map(route -> // ! here you go. That is a lambda expression
                        new FindItineraryWithLink(
                                route,
                                route.stream()
                                        .map(FlightInstance::getId)
                                        .map(String::valueOf)
                                        .collect(Collectors.joining(","))
                        )
                )
                .toList();
    }
}
```

Or storing a function in a variable:

```java
public abstract class AbstractProcessElement<R, T> implements ProcessElement<R, T> {

    private BiFunction<R, T, T> next; // here you go. That is a lambda expression

    protected T callNext(R request, T object) {
        if (next != null) {
            return next.apply(request, object);
        }
        return object;
    }
}

```

---

### AspectJ

I am using lombok annotation. For example:

```java

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "crew")
@DiscriminatorValue("crew")
public class Crew extends Person {
}
```

---

### Serialization

Here is an example of serialization:

```java

@Data
@Entity
@Table(name = "notification")
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private NotificationType type;

    @CreatedDate
    private Date createdDate;

    @ManyToOne
    private Person person;
}
```

And all writing to file is handled by spring boot and hyper-sql.
