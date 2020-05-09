Feature: All the process from adding drones to the warehouse to scheduling an order and sending the order

  Background:
    Given a warehouse client
    And an office client

  Scenario: Adding parcels, adding drones, scheduling deliveries, sending deliveries, charging and reviewing the drone,viewing statistics and viewing an invoice
    When Marcel adds a new drone with the ID 001 to the warehouse
    Then a new drone with the ID 001 is now available in the warehouse


### Scénario à suivre :
#
#  Un nouveau drone est reçu par l’entrepôt, quand Marcel l’ajoute au système avec un ID 001 alors un nouveau drone d’ID 001 est disponible dans le système.
#
#  Quand Marcel reçoit les nouveaux colis dans l’entrepôt et les ajoutes avec toutes leurs informations dans le système puis les range dans l’entrepôt alors le système possède de nouvelles livraisons qui ont les informations des colis et une nouvelle facture est créée avec les informations de la livraison et du transporteur.
#
#  Quand le client appelle Clissandre pour prévoir un horaire de livraison à 14h et que Clissandre prévoit le créneau dans le planning alors un nouveau créneau de livraison est dans le planning à 14h avec le drone 001.
#
#  Quand Clissandre a rempli le planning du drone 001 avec des livraisons et quand elle essaye d’ajouter une nouvelle livraison sur chacun des créneaux, alors le système lui indique qu’il n’est pas possible de planifier des livraisons pour chaque créneau de la journée car aucun drone n’est disponible.
#
#  Quand Marcel ajoute un nouveau drone 002 dans le système, et que Clissandre réessaye de planifier une livraison à 15h, alors un nouveau créneau de livraison est créé sur le drone 002 à 15h.
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
