

# JAVA EE
# 12 hour home exam
* Student: Stella Jovanovic
* Subject: PG5100-1 17V Enterpriseprogrammering 1

This repository contains exam delivery of javaEE application (enterprise system)

https://github.com/stellaselena/myitems.git

#### Points covered:
* 2 entities and 2 EJBs
* 7 jsf pages
* Additional selenium tests
* Additional arquillian tests
* 93% JaCoCo code coverage

#### Project structure
Exam module contains 3 modules: backend, frontend, report  

#### Tests
* User backend tests located in UserEJBTest
* Item backend tests located in ItemEJBTests
* User frontend tests located in UserCreateAndLoginIT
* Item frontend tests located in MyItemsIT
 
## Extra functionality
* Custom Validation Constraint
* User:  
`non registered user:`  
can: login, register, see item overview and item statistics  
cannot: create items and check items as used   
`registered user:`  
can: login, logout, see his/her profile information, create new items,  filter them to only show items created by the user, they can also see in user details how many items they have posted 
* All users can see how many users are using the item, only registered users can check items that they are using
* Item statistics shows how many items there are in each available type that is chosen during item creation
* Users can filter maps by all, indoor or outdoor categories

## How to run application  
1. Clone repo  
2. Run from application root folder  
`mvn install -DskipTests` - will install app without tests  
or (OPTIONAL)  
`mvn install` -  will install and run all Arquillian tests  
or (OPTIONAL)  
`mvn install -P selenium` - will install and run Arquillian & Selenium tests  
!NB for Selenium tests needs [Chrome driver](https://sites.google.com/a/chromium.org/chromedriver/) in user root folder  
3. go to `~/frontend` module and run there  
`mvn wildfly:run`  
4. Open in browser `localhost:8080/my_items`  
