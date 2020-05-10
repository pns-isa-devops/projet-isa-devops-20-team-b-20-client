Feature: Schedule and make a delivery
    Schedule a single delivery and start it with a single drone

    Scenario: As a clean database, I want to deliver a parcel with a drone
        Given a drone "000" is added in the system
        And a parcel "123456789A"
        When I schedule a delivery at "10:00"
        Then I can start the delivery
