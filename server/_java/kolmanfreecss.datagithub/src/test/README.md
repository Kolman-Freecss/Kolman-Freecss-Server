## Test Information

- Tests are automatically run by `mvn clean test` command.
  - mvn clean test automatically will search for tests in the `src/test` folder with suffix `Test` (between other suffixes). 
- JaCoCo reports are generated in the `target` folder.
  - Custom reports are generated in the `target/custom-jacoco-report` folder.
  - The custom report is generated by the `mvn clean test` command.
  - There you will find the `index.html` file to see the report.
    - With all missing lines of code, coverage, and more. 
- Some tests are implemented with Groovy just for fun. :)
- There are next tests:
  - Unit tests
  - Integration tests
  - End-to-end tests