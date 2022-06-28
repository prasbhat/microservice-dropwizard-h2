# microservice-dropwizard-h2

We will build a **Rest CRUD API using Dropwizard Framework** for a **Todo Task Application** in that:

- Each **Todo Task** has *id, title, description, creation date, due date, status and comments*.
- APIs help to *Create, Read, Update, Delete* **Todo Tasks**.

Below-mentioned are the ***REST APIs*** for CRUD Operations using **Spring Boot**.

|          Description          | CRUD Operation | HTTP Method | REST API Endpoint |
|:-----------------------------:|:--------------:|:-----------:|:-----------------:|
|     Create New Todo Task      |     CREATE     |    POST     |     `/tasks`      |
|     Fetch All Todo Tasks      |      READ      |     GET     |     `/tasks`      |
|      Fetch One Todo Task      |      READ      |     GET     |   `/tasks/{id}`   |
| Update One Specific Todo Task |     UPDATE     |     PUT     |     `/tasks`      |
| Delete One Specific Todo Task |     DELETE     |   DELETE    |   `/tasks/{id}`   |

**Dropwizard Framework** will serve as back-end server, and I will be using ***Relational Database*** known as 
**H2 Database**, *it is also known as an Embedded Java Database*, for persisting(storing) the data.

## Requirements

- [JDK 1.8+](https://www.oracle.com/java/technologies/javase-downloads.html) - Java™ Platform, Standard Edition
  Development Kit
- [Dropwizard](https://www.dropwizard.io/en/latest/)- is an open source Java Framework for the rapid development of REST APIs.
- [Maven](https://maven.apache.org/) - Dependency Management (**Dropwizard Framework** officially supports **Maven**)
- [Jetty](https://www.eclipse.org/jetty/) is embedded in the **Dropwizard Framework** and can be used as Web application
for running this project.
- [Intellij IDEA IDE](https://www.jetbrains.com/idea/download/#section=windows) - An IDE for developing the code. You
  can use any IDE, I have used Intellij IDEA IDE.

## Running the application locally

There are several ways to run a Dropwizard application on your local machine.

### Clone the repository to your local drive

```shell
git clone https://github.com/prasbhat/microservice-dropwizard-h2.git
```
Import the project as "Maven Project" into your favourite IDE

### Build Application Uber Fat Jar File

Go into your project directory and run this:
```shell
mvn clean package (or run the `clean package` goal from your IDE)
```

### Start Application Jetty server
In your project directory, run this:
```shell
java -jar .\target\microservice-dropwizard-h2-1.0-SNAPSHOT.jar server configuration.yml
```

More detailed documentation regarding this project can be found [here](https://myzonesoft.com/post/microservice-dropwizard-h2/).