Feature: All the process from adding drones to the warehouse to scheduling an order and sending the order

  Background:
    Given a warehouse client
    And an office client
    And the warehouse that received new parcels from carriers that indicated in an API what parcels have been delivered
    And deliveries
    And a drone with ID 001 that flew 19 hours

  Scenario: Adding parcels, adding drones, scheduling deliveries, sending deliveries, charging and reviewing the drone,viewing statistics and viewing an invoice

    When Marcel tells the system to fetch the newly arrived parcels from the carrier API to add them to the database
    Then the system possesses new deliveries for each parcel received
    And a new invoice is created with the delivery and the carrier information

    When a customer calls Clissandre to plan a delivery at 14pm
    Then a new time slot is created in the planning at 14pm with the drone 001

    When Clissandre fills the drone 001 schedule with deliveries and she tries to add a new delivery to the schedule
    Then the system indicates that there is no more time slot available for every time slot

    When Marcel adds a new drone 002 in the system
    Then a new drone with the ID 002 is now available in the warehouse
    When Clissandre plans a delivery at 10:00am
    Then a new delivery is added at 10am with the drone 002

    When at 7:50 Marcel looks at his screen to have the references of the next parcel to load which is the delivery D1 that must leave at 8:00 with the drone 001
    Then the screen displays the parcel the delivery D1, the hour 8:00 and the drone 001
    When Marcel loads the drone with the corresponding parcel and presses the button that initiates the delivery process
    Then the drone receives the launch signal with the corresponding parcel
    And the address to head to
    And the hour at which he must leave
    And the state of the drone changes to ON_DELIVERY
    And the state of the delivery changes to ONGOING

    When the drone 001 is back
    Then the status of the delivery is not ONGOING anymore
    And the drone status is AVAILABLE
    And the flight time of the drone is updated to 19 hours and 15 minutes

    When Marcel launches 2 more deliveries
    Then the flight time of the drone is now 19 hours and 45 minutes

    When Charlene retrieves the drone 001 and puts it in charge for 3 hours as it flew 45 minutes today
    Then the drone status is now ON_CHARGE

    When at 11h45 Marcel initiates a new delivery with the drone 001 that flew 19 hours and 45 minutes and the drone comes back
    Then the drone has now 20 hours of flight
    When Charlene retrieves it and brings it to Garfield for review
    Then the drone is now ON_REVIEW
    And its flight time is now reinitialized to 0
    When Garfield finished revising the drone and brings it to Marcel
    Then the state of the drone is now AVAILABLE

    When Bob retrieves the occupation rate of the drone 001
    Then he obtains an occupation rate of XX% as the drone flew 4 times, was 3 hours n review and was put one time in charge

    When Bob retrieves the occupation rate of the drone 002
    Then he obtains an occupation rate of 0% as the drone never flew

    When Gisele asks the system to see the invoices at the end of the day
    Then she obtains the invoice containing the information of the carrier who delivered parcel in the morning
    And every deliveries received in the morning with this carrier
    And a price of of 90 (30 + 10 * 6)