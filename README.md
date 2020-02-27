# IssueTrackerStudy
Issue Tracker is study project for course TSS developed by Javalin

## Configuration your project

    configuration src/main/resources/database.properties

### Run database migrations:

    mvn db-migrator:create        - create database     
    mvn db-migrator:migrate       - create tables

### Build program:

    mvn clean install

### Instrumentation(IDE):

    mvn process-classes 
    (or) mvn activejdbc-instrumentation:instrument

## Contributing
### Consider naming conventions
#### Name of branches

    feature-name
    fix-name

#### Name of commits

    [FEATURE] name
    [FIX] name
