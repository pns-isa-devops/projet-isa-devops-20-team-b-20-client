package integration;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

import org.junit.Ignore;

import api.DeliveryAPI;
import api.DeliveryScheduleAPI;
import api.DroneMaintenanceAPI;
import cli.commands.Adddrone;
import cli.commands.Bye;
import cli.commands.Command;
import cli.commands.Help;
import cli.commands.ScheduleDelivery;
import cli.framework.Shell;
import framework.ShellOffice;
import framework.ShellWarehouse;

public class schedule_and_make_a_delivery {
    private static String host = "localhost";
    private static String port = "88080";

    Shell shellOffice;
    Shell shellWarehouse;

    public void setup() throws IllegalAccessException, InstantiationException {
        shellOffice = new ShellOffice(new DeliveryScheduleAPI(host, port));
        shellWarehouse = new ShellWarehouse(new DeliveryAPI(host, port), new DroneMaintenanceAPI(host, port));
    }

    @Given("a drone {string} added in the system")
    public void a_drone_is_added_in_the_system(String id) throws IllegalAccessException, InstantiationException {
        // setup();
        // Command c = new Adddrone();
        // c.setShell(shellWarehouse);
        // c.execute(null);
    }
}
