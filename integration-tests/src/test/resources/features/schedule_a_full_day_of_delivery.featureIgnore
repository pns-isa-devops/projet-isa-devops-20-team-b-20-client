Feature: Schedule a full day of delivery

    Schedule the most deliveries we can. It includes charging and reviewing the drone

    Scenario: As a clean database, I want to schedule all the parcel I have for the current day
        Given the following drones :
            | "000" |
            | "001" |
        When I want to retrieve the parcels of the day
        Then I retrieve:
            | "123456789A" |
            | "123456789B" |
            | "123456789C" |
            | "123456789D" |
            | "123456789E" |
            | "123456789F" |
        When I schedule a delivery at a specific hour:
            | "123456789A" | "10:30" |
            | "123456789B" | "10:45" |
            | "123456789C" | "11:00" |
        Then the drone "000" need to charge before taking a new delivery
