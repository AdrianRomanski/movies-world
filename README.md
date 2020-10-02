# <div align="center"> Movies World </div>
# <div align="center">[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/AdrianRomanski/movies-world.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/AdrianRomanski/movies-world/context:java)  [![codecov](https://codecov.io/gh/AdrianRomanski/movies-world/branch/master/graph/badge.svg)](https://codecov.io/gh/AdrianRomanski/movies-world) ![Total alerts](https://img.shields.io/lgtm/alerts/g/AdrianRomanski/movies-world.svg?logo=lgtm&logoWidth=18) [![CircleCI](https://circleci.com/gh/AdrianRomanski/movies-world.svg?style=shield)](https://circleci.com/gh/AdrianRomanski/movies-world) [![Generic badge](https://img.shields.io/badge/Status-Progress-<COLOR>.svg)](https://shields.io/) [![GitHub license](https://img.shields.io/github/license/Naereen/StrapDown.js.svg)](https://github.com/Naereen/StrapDown.js/blob/master/LICENSE) </div>

## <div align="left"> Development Heroku Server </div>

#### User
- https://movies-world-romanski.herokuapp.com

#### Admin 
- https://movies-world-romanski.herokuapp.com/admin
- https://movies-world-romanski.herokuapp.com/actuator/info

## <div align="left"> Running movies-world locally </div>

    git clone git@github.com:AdrianRomanski/movies-world.git
    cd movies-world
    mvn package
    java -jar target/movies-world-0.0.1-SNAPSHOT.jar

## <div align="left"> Working with movies-world in your IDE
    
### Prerequisites

The following items should be installed in your system:

- Java 11 or newer.
- git command line tool (https://help.github.com/articles/set-up-git)
- Your preferred IDE
    - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
    - [Eclipse](https://www.eclipse.org/)
    - [Spring Tools Suite](https://spring.io/tools)
    - [VS Code](https://code.visualstudio.com/)

### Steps:

## 1) On the command line
git clone git@github.com:AdrianRomanski/movies-world.git

## 2) IDE
### IntelliJ IDEA
- In the main menu, choose File -> Open and select the pom.xml inside rest-school directory.
- Click on the Open as Project button.
- Run -> Run 'RestSchoolApplication'

### Eclipse or STS
- File -> Import -> Maven -> Existing Maven project
- Run the application main method by right clicking on it and choosing Run As -> Java Application.

## 3) Endpoints
- User Panel - http://localhost:8080/
- Admin Panel - http://localhost:8080/admin

- Visit http://localhost:8080/h2 in your browser to inspect the content of the database
    - JDBC URL: jdbc:h2:mem:school
    - User Name: stephen
    - Password: king
- Visit http://localhost:8080/actuator/info in your browser for basic informations about application
- Visit http://localhost:8080/actuator/health in your browser to inspect status of application
- Visit http://localhost:8080/actuator/ in your browser for list of all actuator endpoints
