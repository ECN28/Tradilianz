# Tradilianz

Tradilianz mobile shop system with rewards --> detailed information will added soon

### Prerequisites

To run this application successfully, you need to install following tools on your system:

- PostgreSQL version 12+
- Java Development Kit (JDK). I recommend latest version
- Apache Maven 3.6.3
- Spring Tool Suite (Optional)

### Installing

First of all, lets take care about setting up our Database with PostgreSQL. You can follow my Instructions step by step:

1. Download PostgreSQL version 12 --> https://www.enterprisedb.com/downloads/postgres-postgresql-downloads
2. While Installation you set a password which is important to connect to PostgreSQL server! Pls set your own password and modify the application.properties file in the Spring Boot application. 
    ```
    Tradilianz/src/main/resources/application.properties:
    line7: spring.datasource.password=Ercoo309 to spring.datasource.password=YourPasswordHere
    ```
3. Start pgAdmin and connect to PostgreSQL12 server by entering your password. Now you are going to create the database. For this right-click on Databases and select create -> Database... After that you type in a name for your database and save it. Update your application.properties file like this:

    ```
    line5: spring.datasource.url=jdbc:postgresql://localhost:5432/YourDatabaseNameHere
    ```
You are done with setting up your database! Next we have to Install JDK and Maven to run our application. Lets go Ahead:
1. Downloand Maven: http://maven.apache.org/download.cgi
2. And follow this tutorial: https://mkyong.com/maven/how-to-install-maven-in-windows/
3. Download Java: https://www.oracle.com/de/java/technologies/javase-downloads.html
4. And follow the instructions on the same website.

There is nothing else needed to run now our Spring Boot application. Based on your IDE you can start now your application, and it should run successfully. Before I recommend to update Maven project by Maven -> update project, so that all required dependencies and resources are downloaded. 

## Test api with Postman

Download Postman: https://www.postman.com/downloads/

Use my postman collection for testing the applicaton. Open Postman, click on import and add this link: https://www.getpostman.com/collections/b36e14d94b1ad6792256 . Now you can test the api.

## Deployment

To deploy this application on a server there are many provider, who could handle this for you. For Example you can do this by using [Heroku](https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku).

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management


## Versioning

I use Git and the GUI TortoiseGit for versioning. For the versions available, see the [tags on this repository](https://github.com/ECN28/Tradilianz/tags). 

## Authors

* **Ercan Yildiz** 

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - Free Software, Hell Yeah!

## Todos
- Writing Tests
- Add shopping cart, retailer, products and many other stuff..
