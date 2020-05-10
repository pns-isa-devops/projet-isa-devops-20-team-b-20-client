Feature: All the process from adding drones to the warehouse to scheduling an order and sending the order

  Background:
    Given a warehouse client
    And an office client
    And a drone with ID "001" that flew 19 hours

  Scenario: Adding parcels, adding drones, scheduling deliveries, sending deliveries, charging and reviewing the drone,viewing statistics and viewing an invoice

    When Marcel tells the system to fetch the newly arrived parcels from the carrier API to add them to the database
    Then the system possesses new deliveries for each parcel received :
      | 123456789A |
      | 123456789B |
      | 123456789C |
      | 123456789D |
      | 123456789E |
      | 123456789F |
      | 123456789G |
      | 123456789H |
      | 123456789I |
      | 123456789J |
      | 123456789K |
      | 123456789L |
      | 123456789M |
      | 123456789N |
      | 123456789O |
      | 123456789P |
      | 123456789Q |
      | 123456789R |
      | 123456789S |
      | 123456789T |
      | 123456789U |

    When Clissandre schedules "123456789B" a at "8:00"
    Then the client returns "Delivery scheduled!"
    When Clissandre views the planning
    Then a new time slot has been created in the planning at 8:00 with the drone 001 "[8:0] DELIVERY"

    When Clissandre fills the drone 001 schedule with deliveries:
      | 123456789A |  8:15 |
      | 123456789F |  8:30 |
      | 123456789D |  9:45 |
      | 123456789E |  10:0 |
      | 123456789C |  10:15 |
      | 123456789G |  11:30 |
      | 123456789H |  11:45 |
      | 123456789I |  12:0 |
      | 123456789L |  13:15 |
      | 123456789M |  13:30 |
      | 123456789N |  13:45 |
      | 123456789O |  15:0 |
      | 123456789P |  15:15 |
      | 123456789Q |  15:30 |
      | 123456789R |  16:45 |
      | 123456789S |  17:0 |
      | 123456789T |  17:15 |
    Then the planning does not contain "AVAILABLE" time slots

    When Clissandre tries to schedule a delivery "123456789U" at "12:0"
    Then the system returns "There is no free drone for the Timeslot : 12:0"

    When Marcel adds a new drone "002" in the system
    Then the system returns "Drone added to warehouse."

    When Clissandre tries to schedule a delivery "123456789U" at "12:0"
    Then a new delivery is added for drone "002" at "[12:0] DELIVERY"

    #Utiliser 5 rue vert ici
    When at "7:50" Marcel looks at his screen to have the references of the next parcel to load which is the delivery "123456789B" with the drone "001"
    Then the screen displays "Number: 123456789B" "Drone: 001" "Parcel number: 123456789B"
    When Marcel loads the drone with the corresponding parcel and presses the button that initiates the delivery process "123456789B"
    Then the cli displays "Starting delivery : 123456789B" "Drone 001 launched!"

    When at 7:51 Marcel launches a new delivery "123456789B"
    Then the system displays "Drone 001 is not available"
#    Then the drone is back so Marcel put the drone "001" as available

    # lancer avec instant travel
    When Marcel launches 2 more deliveries "123456789A" at "8:05" and "123456789F" at "8:25"
#    Then the drone is back so Marcel put the drone "001" as available
    Then the flight time of the drone is now 19 hours and 45 minutes
    When Charlene retrieves the drone "001" and puts it in charge for 1 hours as it flew 45 minutes today
    Then the client displays "Drone 001 is in CHARGE mode"

    When Charlene tries to put the drone "001" in charge or in review while it is already on charge
    Then the client displays "The drone 001 is not available. It is currently on charge."

    When at 9h40 Marcel initiates a new delivery with the drone "001" that flew 19 hours and 45 minutes and the drone comes back
    Then the drone has now 20 hours of flight
    When Charlene retrieves it and brings it to Garfield for review
    Then the client displays "Drone 001 is in REVIEW mode"

    When Charlene tries to put the drone "001" in charge or in review while it is on review
    Then the client displays "The drone 001 is not available. It is currently on review."

    When Garfield finished revising the drone "001" and brings it to Marcel and Marcel puts it available
    Then the client displays "Drone 000 is AVAILABLE"

    When Bob retrieves the occupation rate of the drone "001"
    # occupancy rate
    Then he obtains an occupation rate of "0.5" % as the drone flew 4 times, was 3 hours in review and was put one time in charge

    When Bob retrieves the occupation rate of the drone "002"
    Then he obtains an occupation rate of 0% as the drone never flew

    When Gisele asks the system to see the invoices at the end of the day
    Then she obtains the invoice containing the information of the carrier "Colissimo"
    And every deliveries received in the morning with this carrier "Colissimo"
    # (30 + 10 * 6)
    And a price of 90.0 HT
    And a price of 108.0 TTC

    When Gisele marks the invoice as paid
    Then the invoice is marked as paid