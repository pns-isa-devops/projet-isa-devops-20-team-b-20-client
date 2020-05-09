Feature: All the process from adding drones to the warehouse to scheduling an order and sending the order

  Background:
    Given a warehouse client
    And an office client
    And the warehouse that received new parcels from carriers that indicated in an API what parcels have been delivered
    And a deliveries

  Scenario: Adding parcels, adding drones, scheduling deliveries, sending deliveries, charging and reviewing the drone,viewing statistics and viewing an invoice
    When Marcel adds a new drone with the ID 001 to the warehouse
    Then a new drone with the ID 001 is now available in the warehouse

    When Marcel tells the system to fetch the newly arrived parcels from the carrier API to add them to the database
    Then the system possesses new deliveries for each parcel received
    And a new invoice is created with the delivery and the carrier information

    When a customer calls Clissandre to plan a delivery at 14pm
    Then a new time slot is created in the planning at 14pm with the drone 001

    When Clissandre fills the drone 001 schedule with deliveries and she tries to add a new delivery to the schedule
    Then the system indicates that there is no more time slot available for every time slot

    When Marcel adds a new drone 002 in the system and Clissandre plans a delivery at 10:00am
    Then a new delivery is added at 10am with the drone 002

    When at 10:10 Marcel looks at his screen to have the references of the next parcel to load which is the delivery D1 that must leave at 10:15 with the drone 001
    Then the screen displays the parcel the delivery D1, 8:30 and the drone 001
    When Marcel loads the drone with the corresponding parcel and presses the button that initiates the delivery process
    Then the drone receives the launch signal with the corresponding parcel
    And the address to head to
    And the hour at which he must leave
    And the state of the drone changes to ON_DELIVERY
    And the state of the delivery changes to ONGOING

    When the drone 001 is back
    Then the status of the delivery is not ONGOING anymore
    And the drone status is AVAILABLE
    And the flight time of the drone is updated to 15 minutes

    When Charlene retrieves the drone 001 and puts it in charge
    Then


## Scénario à suivre :
#
#
#  Quand Charlène récupère le drone 001 à son retour et qu’elle le met en charge, alors le statut du drone est maintenant EN CHARGE.
#
#  Quand, à hh (juste avant une révision) Marcel initialise une nouvelle livraison avec le drone 001 et que le drone revient, Charlène le récupère et l’emmène à Garfield pour révision, alors le statut du drone est maintenant en révision.
#
#  Quand Garfield a fini de réviser le drone, il l’emmène à Marcel qui met son état à AVAILABLE, alors l’état du drone est maintenant AVAILABLE.
#
#  Quand Bob, souhaite avoir le taux d’occupation du drone 001, alors il obtient le taux d’occupation du drone 001 qui est de 15%.
#
#  Quand Bob, souhaite avoir le taux d’occupation du drone 002, alors il obtient le taux d’occupation du drone 002 qui est de 0% puisqu’il n’a jamais été lancé.
#
#  Quand Gisèle demande à voir les factures à la fin de la journée, alors elle obtient la facture qui contient les informations du transporteur qui a livré les colis ce matin et les informations des colis reçus (comparer chaque livraison dans la facture avec les colis reçus).
