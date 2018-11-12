swagger-01
==========

[![Build Status](https://travis-ci.org/wiiitek/swagger-01.svg?branch=master)](https://travis-ci.org/wiiitek/swagger-01)

Build
-----

      gradlew build --warning-mode all

Description
-----------

This is a project to check Swagger for:

* Differences between Swagger 2.x and Open Api 3.x
* API described in multiple files
* Support for REST returning files
* Various ways for generating documentation
* Code and mocks generation

### Persistence

By default this project use H2 database in `~/swagger-01/h2.db` file.

On Windows this will be in `C:\Users\<user>\swagger-01` folder.
