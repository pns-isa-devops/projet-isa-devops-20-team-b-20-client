Feature: Schedule and make a delivery
    Schedule a single delivery and start it with a single drone

    Scenario: deliver a parcel with a drone
        Given a drone is added in the system
        And a parcel "123456789A"
        When I schedule a delivery at "10:30"
        Then I can start the delivery
