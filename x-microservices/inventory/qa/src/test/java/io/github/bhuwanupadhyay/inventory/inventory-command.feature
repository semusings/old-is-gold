Feature: payment service

  Background:

  Scenario: create product

    * url 'http://localhost:9000/inventory/products'

    Given request { name: 'Product Name' }
    When method post
    Then status 200
    And match response == { id: '#notnull', name: 'Product Name' }
    And def id = response.id

    * url 'http://localhost:9001/inventory/products'
    Given path id
    When method get
    Then status 200

