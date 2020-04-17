package integration;

import client.office.api.DeliveryScheduleAPI;
import client.office.framework.ShellOffice;
import client.utils.cli.commands.Command;
import client.utils.cli.framework.Shell;
import client.warehouse.api.DeliveryAPI;
import client.warehouse.api.DroneMaintenanceAPI;
import client.warehouse.cli.commands.Adddrone;
import client.warehouse.framework.ShellWarehouse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class schedule_and_make_a_delivery {
    private static String host = "localhost";
    private static String port = "8080";

    Shell shellOffice;
    Shell shellWarehouse;

    public void setup() throws IllegalAccessException, InstantiationException {
        shellOffice = new ShellOffice(new DeliveryScheduleAPI(host, port));
        shellWarehouse = new ShellWarehouse(new DeliveryAPI(host, port), new DroneMaintenanceAPI(host, port));
    }

    @Given("a drone {string} added in the system")
    public void a_drone_is_added_in_the_system(String id) throws IllegalAccessException, InstantiationException {
        setup();
        Command c = new Adddrone();
        c.setShell(shellWarehouse);
        c.execute(null);
    }

    @Given("a parcel {string}")
    public void a_parcel(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("I schedule a delivery at {string}")
    public void i_schedule_a_delivery_at(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I can start the delivery")
    public void i_can_start_the_delivery() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
