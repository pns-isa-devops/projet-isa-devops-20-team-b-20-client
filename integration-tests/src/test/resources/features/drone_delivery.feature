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

    When at 10:10 Marcel looks at his screen to have the references of the next parcel to load which is the delivery D1 that must arrive at 10
    Then the screen displays the parcel


## Scénario à suivre :
#
#  Quand, à -10h15-(juste avant une charge), Marcel regarde son écran pour avoir les références du prochain colis à charger (la livraison d’ID L1 doit partir à 14h avec un colis d’id A8 et avec le drone 001 à l’adresse instant travel) et que Marcel attache le bon colis à ce drone puis et appuie sur le bouton Initialiser pour que le système envoie le signal de lancement au drone au bon moment alors la livraison est initialisée. Le drone et la livraison ont le statut En cours de livraison.
#
#  Quand le drone 001 est revenu, alors le statut de la livraison n’est plus ONGOING mais soit FAILED, NOT DELIVERED, DELIVERED.
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
