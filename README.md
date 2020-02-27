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
./gradlew :v2:java-spring:project:bootRun
```

and then check Swagger at [localhost:8084/swagger-ui.html](http://localhost:8084/swagger-ui.html).


Description
-----------

This repository goal is to check Swagger for:

* API described in multiple files
* Code generation

### v2:java-spring

This submodule will use `org.hidetake.swagger.generator` and `io.springfox`.

### Persistence

H2 console will be available at [localhost:8084/h2](http://localhost:8084/h2).

By default this project saves database in `~/test` file. On Windows this will be in `C:\Users\<user>` folder.

