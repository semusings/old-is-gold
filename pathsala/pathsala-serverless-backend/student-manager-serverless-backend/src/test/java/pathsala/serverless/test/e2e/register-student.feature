Feature: Register a student
  Only accept valid student parameters

  Background:
    * def Utils = Java.type('pathsala.serverless.utils.Utils')
    * url Utils.endpointUri()
    * header Accept = 'application/json'

  Scenario: Register a student when there is no violations
    Given path 'student/register'
    * json payload = read('classpath:StudentParams.json')
    * set payload.studentId = Utils.id()
    And request payload
    When method post
    Then print karate.prettyPrint(response)
    And status 200
    And match response = {studentId: payload.studentId}
