swagger-01
==========

[![Build Status](https://travis-ci.org/wiiitek/swagger-01.svg?branch=master)](https://travis-ci.org/wiiitek/swagger-01)

Build
-----

```
./gradlew build --warning-mode all
```

Run
---

Run it with

```
./gradlew :java-spring:v2:project:bootRun
```

and then check Swagger at [localhost:8084/swagger-ui.html](http://localhost:8084/swagger-ui.html).


Description
-----------

This repository goal is to check Swagger for:

* Differences between Swagger 2.x and Open Api 3.x
* API described in multiple files
* Support for REST returning files
* Various ways for generating documentation
* Code and mocks generation

### java-spring:v2

This submodule will use `org.hidetake.swagger.generator` and `io.springfox`.

### Persistence

H2 console will be available at [localhost:8080/h2](http://localhost:8080/h2).

By default this project saves database in `~/test` file. On Windows this will be in `C:\Users\<user>` folder.

