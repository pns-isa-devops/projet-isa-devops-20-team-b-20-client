#!/bin/bash
parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

echo "# Retrieving the new WSDL file from the web service"
echo ""
echo "Source project : "
echo "../projet-isa-devops-20-team-b-20-drone-delivery/projet-isa-devops-20-team-b-20-web-service/"
echo ""
echo "## Updating client warehouse"
cd ./projet-isa-devops-20-team-b-20-client-warehouse/src/main/java
rm -rf stubs
cd ../resources
rm -f *
cp ../../../../../projet-isa-devops-20-team-b-20-drone-delivery/projet-isa-devops-20-team-b-20-web-service/src/main/resources/wsdl/DeliveryWS.wsdl .
echo "Retrieved DeliveryWS"
cp ../../../../../projet-isa-devops-20-team-b-20-drone-delivery/projet-isa-devops-20-team-b-20-web-service/src/main/resources/wsdl/DroneMaintenanceWS.wsdl .
echo "Retrieved DroneMaintenanceWS"

echo "## Updating client office"
cd ../../../../projet-isa-devops-20-team-b-20-client-office/src/main/java
rm -rf stubs
cd ../resources
rm -f *
cp ../../../../../projet-isa-devops-20-team-b-20-drone-delivery/projet-isa-devops-20-team-b-20-web-service/src/main/resources/wsdl/DeliveryScheduleWS.wsdl .
echo "Retrieved DeliveryScheduleWS"
cp ../../../../../projet-isa-devops-20-team-b-20-drone-delivery/projet-isa-devops-20-team-b-20-web-service/src/main/resources/wsdl/InvoiceWS.wsdl .
echo "Retrieved InvoiceWS"
cp ../../../../../projet-isa-devops-20-team-b-20-drone-delivery/projet-isa-devops-20-team-b-20-web-service/src/main/resources/wsdl/AnalyticsWS.wsdl .
echo "Retrieved AnalyticsWS"
cd ../../../../
echo ""
echo "update stubs"
mvn package -pl projet-isa-devops-20-team-b-20-client-warehouse
mvn package -pl projet-isa-devops-20-team-b-20-client-office
echo "## Done"
