Feature: Order Management Service

  Background:
    * url Java.type('bhuwanupadhyay.TestUtils').endpointBaseUri()

  Scenario: receive an order and then get it by id

    * def order =
"""
{
  "customerId": "12345"
}
"""

    Given path 'orders'
    And request order
    When method post
    Then status 200
    * def id = response.body.orderId
    * print 'created id is: ' + id

    Given path 'orders', id
    When method get
    Then status 200
    And assert response.body.orderId == id
    And assert response.body.customerId == '12345'

    * def wait_for_10_secs = java.lang.Thread.sleep(10000)
    Given path 'orders', id
    When method get
    Then status 200
    And assert response.body.orderId == id
    And assert response.body.customerId == '12345'
    And assert response.body.orderStatus == 'SHIPPED'


