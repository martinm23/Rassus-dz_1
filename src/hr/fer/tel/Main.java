package hr.fer.tel;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Sensor is ready to be started. Write further commands to control its flow:");
        System.out.println("        -start <sensor_name> -> Starts sensor and tries to register it on server side.");
        System.out.println("        -stop -> Stops measuring and closes all connections.");

        Scanner scanner = new Scanner(System.in);
        final Sensor sensor = new SensorImpl();

        while (true) {
            final String cmd = scanner.nextLine();

            if (cmd != null) {
                if (cmd.equals("")) {
                    System.out.println("Try again with your input");
                } else {
                    final String[] cmdArgs = cmd.trim().split(" ");
                    if (cmdArgs.length == 2) {
                        switch (cmdArgs[0]) {
                            case "-start":
                                sensor.start(cmdArgs[1]);
                                break;
                            case "-stop":
                                sensor.stop();
                                System.exit(-1);
                                break;
                            default:
                                System.out.println("Use commands -start, -measure, -stop.");
                        }
                    } else {
                        System.err.println("You need to write name of the sensor as an argument after command!");
                    }
                }
            } else {
                System.out.println("Invalid input. Closing sensor and ");
            }
        }
    }
}
