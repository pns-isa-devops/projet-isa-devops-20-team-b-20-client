package integration;

import client.office.api.AnalyticsAPI;
import client.office.api.DeliveryScheduleAPI;
import client.office.api.InvoiceAPI;
import client.office.framework.ShellOffice;
import client.utils.cli.commands.Command;
import client.utils.cli.framework.Shell;
import client.warehouse.api.DeliveryAPI;
import client.warehouse.api.DroneMaintenanceAPI;
import client.warehouse.cli.commands.Adddrone;
import client.warehouse.cli.commands.Checkfornewparcels;
import client.warehouse.cli.commands.Getnextdelivery;
import client.warehouse.framework.ShellWarehouse;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import stubs.delivery.Delivery;
import stubs.delivery.Parcel;
import utils.StreamCatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class drone_delivery {

    Shell shellOffice;
    Shell shellWarehouse;
    Command command;
    private StreamCatcher sc = new StreamCatcher();

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
        StringBuilder s_parcel = new StringBuilder();
        for (String s : parcels) {
            s_parcel.append(s + "\n");
        }
        sc.out().equals("Drone added to warehouse.\n" + s_parcel + "Parcels updated");

    }

    @When("Clissandre schedules {string} a at {string}")
    public void clissandre_schedules_a_at(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the client returns {string}")
    public void the_client_returns(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("Clissandre views the planning")
    public void clissandre_views_the_planning() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("a new time slot has been created in the planning at {int}:{int} with the drone {int} {string}")
    public void a_new_time_slot_has_been_created_in_the_planning_at_with_the_drone(Integer int1, Integer int2, Integer int3, String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("Clissandre fills the drone {int} schedule with deliveries:")
    public void clissandre_fills_the_drone_schedule_with_deliveries(Integer int1, io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        throw new io.cucumber.java.PendingException();
    }

    @Then("the planning does not contain {string} time slots")
    public void the_planning_does_not_contain_time_slots(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("Clissandre tries to schedule a delivery {string} at {string}")
    public void clissandre_tries_to_schedule_a_delivery_at(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the system returns {string}")
    public void the_system_returns(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("Marcel adds a new drone {int} in the system")
    public void marcel_adds_a_new_drone_in_the_system(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("Clissandre plans a delivery at {int}:{int}")
    public void clissandre_plans_a_delivery_at(Integer int1, Integer int2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("a new delivery is added for drone {string} at {string}")
    public void a_new_delivery_is_added_for_drone_at(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("at {int}:{int} Marcel looks at his screen to have the references of the next parcel to load which is the delivery {string} with the drone {int}")
    public void at_Marcel_looks_at_his_screen_to_have_the_references_of_the_next_parcel_to_load_which_is_the_delivery_with_the_drone(Integer int1, Integer int2, String string, Integer int3) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the screen displays {string}")
    public void the_screen_displays(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("Marcel loads the drone with the corresponding parcel and presses the button that initiates the delivery process {string}")
    public void marcel_loads_the_drone_with_the_corresponding_parcel_and_presses_the_button_that_initiates_the_delivery_process(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the cli displays {string}")
    public void the_cli_displays(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("at {int}:{int} Marcel launches a new delivery")
    public void at_Marcel_launches_a_new_delivery(Integer int1, Integer int2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the system displays <error the drone is not available>")
    public void the_system_displays_error_the_drone_is_not_available() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("Marcel launches {int} more deliveries {string} at {string} and {string} at {string}")
    public void marcel_launches_more_deliveries_at_and_at(Integer int1, String string, String string2, String string3, String string4) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the flight time of the drone is now {int} hours and {int} minutes")
    public void the_flight_time_of_the_drone_is_now_hours_and_minutes(Integer int1, Integer int2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("Charlene retrieves the drone {int} and puts it in charge for {int} hours as it flew {int} minutes today")
    public void charlene_retrieves_the_drone_and_puts_it_in_charge_for_hours_as_it_flew_minutes_today(Integer int1, Integer int2, Integer int3) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the client displays {string}")
    public void the_client_displays(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("Charlene tries to put the drone {int} in charge or in review while it is already on charge")
    public void charlene_tries_to_put_the_drone_in_charge_or_in_review_while_it_is_already_on_charge(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("at 9h40 Marcel initiates a new delivery with the drone {int} that flew {int} hours and {int} minutes and the drone comes back")
    public void at_9h40_Marcel_initiates_a_new_delivery_with_the_drone_that_flew_hours_and_minutes_and_the_drone_comes_back(Integer int1, Integer int2, Integer int3) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the drone has now {int} hours of flight")
    public void the_drone_has_now_hours_of_flight(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("Charlene retrieves it and brings it to Garfield for review")
    public void charlene_retrieves_it_and_brings_it_to_Garfield_for_review() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("Charlene tries to put the drone {int} in charge or in review while it is on review")
    public void charlene_tries_to_put_the_drone_in_charge_or_in_review_while_it_is_on_review(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("Garfield finished revising the drone and brings it to Marcel and Marcel puts it available")
    public void garfield_finished_revising_the_drone_and_brings_it_to_Marcel_and_Marcel_puts_it_available() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("Bob retrieves the occupation rate of the drone {int}")
    public void bob_retrieves_the_occupation_rate_of_the_drone(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("he obtains an occupation rate of XX% as the drone flew {int} times, was {int} hours n review and was put one time in charge")
    public void he_obtains_an_occupation_rate_of_XX_as_the_drone_flew_times_was_hours_n_review_and_was_put_one_time_in_charge(Integer int1, Integer int2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("he obtains an occupation rate of {int}% as the drone never flew")
    public void he_obtains_an_occupation_rate_of_as_the_drone_never_flew(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("Gisele asks the system to see the invoices at the end of the day")
    public void gisele_asks_the_system_to_see_the_invoices_at_the_end_of_the_day() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("she obtains the invoice containing the information of the carrier ....")
    public void she_obtains_the_invoice_containing_the_information_of_the_carrier() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("every deliveries received in the morning with this carrier")
    public void every_deliveries_received_in_the_morning_with_this_carrier() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("a price of of {double} HT \\({int} {double} {int} * {int})")
    public void a_price_of_of_HT(Double double1, Integer int1, Double double2, Integer int2, Integer int3) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("a price of {double} TTC")
    public void a_price_of_TTC(Double double1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("Gisele marks the invoice as paid")
    public void gisele_marks_the_invoice_as_paid() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the invoice is marked as paid")
    public void the_invoice_is_marked_as_paid() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @And("a price of of {double} HT")
    public void aPriceOfOfHT(int arg0, int arg1) {
        throw new io.cucumber.java.PendingException();
    }
}
