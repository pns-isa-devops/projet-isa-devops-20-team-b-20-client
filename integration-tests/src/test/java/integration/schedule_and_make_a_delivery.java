package integration;

import java.util.Arrays;
import java.util.Collections;

import client.office.api.DeliveryScheduleAPI;
import client.office.api.InvoiceAPI;
import client.office.api.AnalyticsAPI;
import client.office.cli.commands.Scheduledelivery;
import client.office.framework.ShellOffice;
import client.utils.cli.commands.Command;
import client.utils.cli.framework.Shell;
import client.warehouse.api.DeliveryAPI;
import client.warehouse.api.DroneMaintenanceAPI;
import client.warehouse.cli.commands.Adddrone;
import client.warehouse.cli.commands.Checkfornewparcels;
import client.warehouse.cli.commands.Startdelivery;
import client.warehouse.framework.ShellWarehouse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Ignore;
import utils.StreamCatcher;

@Ignore
public class schedule_and_make_a_delivery {

    private StreamCatcher sc = new StreamCatcher();

    Shell shellOffice;
    Shell shellWarehouse;

    String parcelId;

    public void setup() throws IllegalAccessException, InstantiationException {
        String host = System.getenv("HOST");
        String port = System.getenv("PORT");
        if (host == null) {
            host = "localhost";
        }
        if (port == null) {
            port = "8080";
        }
        sc.setUpStreams();
        shellOffice = new ShellOffice(new DeliveryScheduleAPI(host, port), new AnalyticsAPI(host, port), new InvoiceAPI(host, port));
        shellWarehouse = new ShellWarehouse(new DeliveryAPI(host, port), new DroneMaintenanceAPI(host, port));
    }

    @Given("a drone {string} is added in the system")
    public void a_drone_is_added_in_the_system(String droneID) throws IllegalAccessException, InstantiationException {
        setup();
        Command c = new Adddrone();
        c.setShell(shellWarehouse);
        c.execute(Collections.singletonList(droneID));
        sc.out().equals("Drone added to warehouse.");
    }

    @Given("a parcel {string}")
    public void a_parcel(String parcelId) {
        Command c = new Checkfornewparcels();
        c.setShell(shellWarehouse);
        c.execute(null);
        sc.out().contains(parcelId).contains("Parcels updated").clean();
        this.parcelId = parcelId;
    }

    @When("I schedule a delivery at {string}")
    public void i_schedule_a_delivery_at(String time) {
        Command c = new Scheduledelivery();
        c.setShell(shellOffice);
        // delivery has same id as parcel
        c.execute(Arrays.asList(time, parcelId));
        sc.out().equals("Scheduling delivery : " + parcelId + " for " + time + "\nDelivery scheduled!");
    }

    @Then("I can start the delivery")
    public void i_can_start_the_delivery() {
        Command c = new Startdelivery();
        c.setShell(shellWarehouse);
        c.execute(Arrays.asList(parcelId));
        sc.out().equals("Starting delivery : 123456789A.\nDrone 000 launched!");
        sc.restoreStreams();
    }

}
