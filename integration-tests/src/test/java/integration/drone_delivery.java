package integration;

import client.office.api.AnalyticsAPI;
import client.office.api.DeliveryScheduleAPI;
import client.office.api.InvoiceAPI;
import client.office.cli.commands.*;
import client.office.framework.ShellOffice;
import client.utils.cli.commands.Command;
import client.utils.cli.framework.Shell;
import client.warehouse.api.DeliveryAPI;
import client.warehouse.api.DroneMaintenanceAPI;
import client.warehouse.cli.commands.*;
import client.warehouse.framework.ShellWarehouse;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Ignore;
import utils.StreamCatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class drone_delivery {

    Shell shellOffice;
    Shell shellWarehouse;
    Command command;
    private StreamCatcher sc = new StreamCatcher();
    private List<String> deliveryIDs = new ArrayList<>();

    public Shell getShell(boolean office) throws IllegalAccessException, InstantiationException {
        String host = System.getenv("HOST");
        String port = System.getenv("PORT");
        if (host == null) {
            host = "localhost";
        }
        if (port == null) {
            port = "8080";
        }
        sc.setUpStreams();

        if (office) {
            return new ShellOffice(new DeliveryScheduleAPI(host, port), new AnalyticsAPI(host, port), new InvoiceAPI(host, port));
        }
        return new ShellWarehouse(new DeliveryAPI(host, port), new DroneMaintenanceAPI(host, port));
    }

    @Given("a warehouse client")
    public void a_warehouse_client() throws IllegalAccessException, InstantiationException {
        shellWarehouse = getShell(false);
    }

    @And("an office client")
    public void an_office_client() throws IllegalAccessException, InstantiationException {
        shellOffice = getShell(true);
    }

    @And("a drone with ID {string} that flew 19 hours")
    public void a_drone_with(String id) {
        command = new Adddrone();
        command.setShell(shellWarehouse);
        command.execute(Arrays.asList(id));
        sc.out().contains("Drone added to warehouse.");
    }

    @When("Marcel tells the system to fetch the newly arrived parcels from the carrier API to add them to the database")
    public void marcel_fetch_parcels(){
        command = new Checkfornewparcels();
        command.setShell(shellWarehouse);
        command.execute(new ArrayList<>());
        sc.out().contains("Drone added to warehouse."); // TODO ne tester les valeurs retournées que dans le then
    }

    @Then("the system possesses new deliveries for each parcel received :") // TODO A delivery is created for every parcels gotten from the carrier
    public void the_system_possesses_new_deliveries_for_each_parcel_received(DataTable dataTable) {
        List<String> parcels = dataTable.asList();
        deliveryIDs = new ArrayList<>(parcels);
        StringBuilder s_parcel = new StringBuilder();
        for (String s : parcels) {
            s_parcel.append(s + "\n");
        }
        sc.out().equals("Drone added to warehouse.\n" + s_parcel + "Parcels updated");
        sc.out().clean();
    }

    @When("Clissandre schedules {string} a at {string}")
    public void clissandre_schedules_a_at(String string, String string2) {
        command = new Scheduledelivery();
        command.setShell(shellOffice);
        command.execute(Arrays.asList(string2,string));

        sc.out().contains("Scheduling delivery : ").contains(string).contains(" for ")
                .contains(string2);
    }

    @Then("the client returns {string}")
    public void the_client_returns(String string) {
        sc.out().contains(string);
        sc.out().clean();
    }

    @When("Clissandre views the planning")
    public void clissandre_views_the_planning() {
        command = new Getplanning();
        command.setShell(shellOffice);
        command.execute(Collections.singletonList("001"));
    }

    @Then("a new time slot has been created in the planning at {int}:{int} with the drone {int} {string}")
    public void a_new_time_slot_has_been_created_in_the_planning_at_with_the_drone(Integer int1, Integer int2, Integer int3, String string) {
        sc.out().contains(string);
        sc.out().clean();
    }

    @When("Clissandre fills the drone {int} schedule with deliveries:")
    public void clissandre_fills_the_drone_schedule_with_deliveries(Integer int1, DataTable dataTable) {
        List<List<String>> table = dataTable.asLists();

        command = new Scheduledelivery();
        command.setShell(shellOffice);

        for(int i = 0; i < table.size(); i++){
            command.execute(Arrays.asList(table.get(i).get(1),table.get(i).get(0)));
        }
        sc.out().clean();
    }

    @Then("the planning does not contain {string} time slots")
    public void the_planning_does_not_contain_time_slots(String string) {
        command = new Getplanning();
        command.setShell(shellOffice);
        command.execute(Collections.singletonList("001"));
        sc.out().notContains("AVAILABLE").clean();
    }

    @When("Clissandre tries to schedule a delivery {string} at {string}")
    public void clissandre_tries_to_schedule_a_delivery_at(String string, String string2) {
        command = new Scheduledelivery();
        command.setShell(shellOffice);
        command.execute(Arrays.asList(string2,string));
    }

    @Then("the system returns {string}")
    public void the_system_returns(String string) {
        sc.out().contains(string).clean();
    }

    @When("Marcel adds a new drone {string} in the system")
    public void marcel_adds_a_new_drone_in_the_system(String string) {
        command = new Adddrone();
        command.setShell(shellWarehouse);
        command.execute(Collections.singletonList(string));
        sc.out().contains("Drone added to warehouse.");
    }

    @Then("a new delivery is added for drone {string} at {string}")
    public void a_new_delivery_is_added_for_drone_at(String string, String string2) {
        command = new Getplanning();
        command.setShell(shellOffice);
        command.execute(Collections.singletonList(string));
        sc.out().contains(string2).clean();
    }

    @When("at {string} Marcel looks at his screen to have the references of the next parcel to load which is the delivery {string} with the drone {string}")
    public void at_Marcel_looks_at_his_screen_to_have_the_references_of_the_next_parcel_to_load_which_is_the_delivery_with_the_drone(String string, String string2, String string3) {
        command = new Getnextdelivery();
        command.setShell(shellWarehouse);
        command.execute(Collections.singletonList(string));
    }

    @Then("the screen displays {string} {string} {string}")
    public void the_screen_displays(String string, String string2, String string3) {
        sc.out().contains(string).contains(string2).contains(string3);
        sc.out().clean();
    }

    @When("Marcel loads the drone with the corresponding parcel and presses the button that initiates the delivery process {string}")
    public void marcel_loads_the_drone_with_the_corresponding_parcel_and_presses_the_button_that_initiates_the_delivery_process(String string) {
        command = new Startdelivery();
        command.setShell(shellWarehouse);
        command.execute(Collections.singletonList(string));
    }

    @Then("the cli displays {string} {string}")
    public void the_cli_displays(String string, String string2) {
        sc.out().contains(string).contains(string2);
        sc.out().clean();
    }

    @When("at {int}:{int} Marcel launches a new delivery {string}")
    public void at_Marcel_launches_a_new_delivery(Integer integer, Integer integer2, String string) {
        command = new Startdelivery();
        command.setShell(shellWarehouse);
        command.execute(Collections.singletonList(string));
    }

    @Then("the system displays {string}")
    public void the_system_displays_error_the_drone_is_not_available(String string) throws InterruptedException {
        sc.out().contains(string).clean();
        //Thread.sleep(000);
    }

    @When("Marcel launches {int} more deliveries {string} at {string} and {string} at {string}")
    public void marcel_launches_more_deliveries_at_and_at(Integer int1, String string, String string2, String string3, String string4) throws InterruptedException {
        command = new Startdelivery();
        command.setShell(shellWarehouse);
        command.execute(Collections.singletonList(string));
//        Thread.sleep(5000);


        //set
        command = new Setavailable();
        command.setShell(shellWarehouse);
        command.execute(Collections.singletonList("001"));
        sc.out().contains("AVAILABLE").clean();

        command = new Startdelivery();
        command.setShell(shellWarehouse);
        command.execute(Collections.singletonList(string3));
//        Thread.sleep(5000);

        //set
        command = new Setavailable();
        command.setShell(shellWarehouse);
        command.execute(Collections.singletonList("001"));
        sc.out().contains("AVAILABLE").clean();

        sc.out().clean();
    }

    @Then("the flight time of the drone is now {int} hours and {int} minutes")
    public void the_flight_time_of_the_drone_is_now_hours_and_minutes(Integer int1, Integer int2) {
        //Nothing to do here, it is just a textual illustration of the situation
    }

    @When("Charlene retrieves the drone {string} and puts it in charge for {int} hours as it flew {int} minutes today")
    public void charlene_retrieves_the_drone_and_puts_it_in_charge_for_hours_as_it_flew_minutes_today(String string, Integer int2, Integer int3) {
        command = new Setincharge();
        command.setShell(shellWarehouse);
        command.execute(Collections.singletonList(string));
    }

    @Then("the client displays {string}")
    public void the_client_displays(String string) {
        sc.out().contains(string).clean();
    }

    @When("Charlene tries to put the drone {string} in charge or in review while it is already on charge")
    public void charlene_tries_to_put_the_drone_in_charge_or_in_review_while_it_is_already_on_charge(String string) {
        command = new Setincharge();
        command.setShell(shellWarehouse);
        command.execute(Collections.singletonList(string));
    }

    @When("at 9h40 Marcel initiates a new delivery with the drone {string} that flew {int} hours and {int} minutes and the drone comes back")
    public void at_9h40_Marcel_initiates_a_new_delivery_with_the_drone_that_flew_hours_and_minutes_and_the_drone_comes_back(String string, Integer int2, Integer int3) {
        command = new Startdelivery();
        command.setShell(shellWarehouse);
        command.execute(Collections.singletonList("123456789D"));
    }

    @Then("the drone has now {int} hours of flight")
    public void the_drone_has_now_hours_of_flight(Integer int1) {
        //Nothing to do here, it is just a textual illustration of the situation
    }

    @When("Charlene retrieves it and brings it to Garfield for review")
    public void charlene_retrieves_it_and_brings_it_to_Garfield_for_review() {
        //set
        command = new Setavailable();
        command.setShell(shellWarehouse);
        command.execute(Collections.singletonList("001"));
        sc.out().contains("AVAILABLE").clean();

        command = new Setinreview();
        command.setShell(shellWarehouse);
        command.execute(Collections.singletonList("001"));
    }

    @When("Charlene tries to put the drone {string} in charge or in review while it is on review")
    public void charlene_tries_to_put_the_drone_in_charge_or_in_review_while_it_is_on_review(String string) {
        command = new Setinreview();
        command.setShell(shellWarehouse);
        command.execute(Collections.singletonList(string));
    }

    @When("Garfield finished revising the drone {string} and brings it to Marcel and Marcel puts it available")
    public void garfield_finished_revising_the_drone_and_brings_it_to_Marcel_and_Marcel_puts_it_available(String string) {
        command = new Setavailable();
        command.setShell(shellWarehouse);
        command.execute(Collections.singletonList(string));
        sc.out().contains("AVAILABLE");
    }

    @When("Bob retrieves the occupation rate of the drone {string}")
    public void bob_retrieves_the_occupation_rate_of_the_drone(String string) {
        command = new Occupancy();
        command.setShell(shellOffice);
        command.execute(Collections.singletonList(string));
    }

    @Then("he obtains an occupation rate of {string} % as the drone flew {int} times, was {int} hours in review and was put one time in charge")
    public void he_obtains_an_occupation_rate_of_XX_as_the_drone_flew_times_was_hours_n_review_and_was_put_one_time_in_charge(String string, Integer int1, Integer int2) {
        sc.out().contains(string).clean();
    }

    @Then("he obtains an occupation rate of {int}% as the drone never flew")
    public void he_obtains_an_occupation_rate_of_as_the_drone_never_flew(Integer int1) {
        sc.out().contains(int1.toString()).clean();
    }

    @Ignore
    @When("Gisele asks the system to see the invoices at the end of the day")
    public void gisele_asks_the_system_to_see_the_invoices_at_the_end_of_the_day() {
        command = new Getinvoices();
        command.setShell(shellOffice);
        command.execute(new ArrayList<>());
    }

    @Ignore
    @Then("she obtains the invoice containing the information of the carrier {string}")
    public void she_obtains_the_invoice_containing_the_information_of_the_carrier(String string) {
        sc.out().contains(string);
    }

    @Ignore
    @Then("every deliveries received in the morning with this carrier {string}")
    public void every_deliveries_received_in_the_morning_with_this_carrier(String string) {
        sc.out().contains(string);
        for (String s : deliveryIDs) {
            sc.out().contains(s);
        }
    }

    @Ignore
    @Then("a price of {double} HT")
    public void a_price_of_of_HT(Double double1) {
        sc.contains("Price HT : " + double1);
    }

    @Ignore
    @Then("a price of {double} TTC")
    public void a_price_of_TTC(Double double1) {
        sc.contains("Price TTC : " + double1);
    }

    @Ignore
    @When("Gisele marks the invoice as paid")
    public void gisele_marks_the_invoice_as_paid() {
        command = new Confirminvoicepayment();
        command.setShell(shellOffice);
        command.execute(new ArrayList<>());
    }

    @Ignore
    @Then("the invoice is marked as paid")
    public void the_invoice_is_marked_as_paid() {
        sc.out().contains("PAID");
    }

    @Ignore
    @Then("the drone is back so Marcel put the drone {string} as available")
    public void theDroneIsBackSoMarcelPutTheDroneAsAvailable(String arg0) {
        command = new Setavailable();
        command.setShell(shellWarehouse);
        command.execute(Collections.singletonList(arg0));
        sc.out().contains("AVAILABLE").clean();
    }
}
