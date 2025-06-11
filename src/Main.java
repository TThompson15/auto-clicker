import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Scanner;

/**
 * The class is responsible for a mouse auto clicker, which does a left mouse button click at the user defined time interval *
 * */
public class Main {

    /**
     * The scanner which reads from the console
     * */
    private static final Scanner sc = new Scanner(System.in);

    /**
     * The integer which represents the interval between two clicks
     * */
    private static int milliseconds = 0;

    /**
     * The integer which represents the number of clicks.
     * If it is 0 (which is only available if the user gives 0 as its clicks), the program will run perpetually.
     * */
    private static int clicks = -1;


    /**
     * The method which is responsible for calling every individual methods.
     * @param args: program arguments, not implemented in this code
     * */
    public static void main(String[] args) {
        millisecondIntervals();
        numberOfClicks();
        runClicker();
    }


    /**
     * The method responsible for getting the number of clicks from the user through the terminal.
     * */
    private static void numberOfClicks() {
        while (true) {
            System.out.println("Is it gonna be a perpetual clicker, or not (y/n): ");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("y")) {
                clicks = 0;
                return;
            } else if (input.equalsIgnoreCase("n")) {
                System.out.println("Alright, give me the number of total clicks:");
                while (true) {
                    try {
                        String clickInput = sc.nextLine();
                        clicks = Integer.parseInt(clickInput);
                        if (clicks > 0) {
                            return;
                        }
                        System.out.println("Please enter a positive integer: ");
                    } catch (Exception e) {
                        System.out.println("Sorry, I couldn't read that! Please enter a valid number: ");
                    }
                }
            } else {
                System.out.println("Try again!");
            }
        }
    }


    /**
     * The method responsible for getting the time between clicks from the user through the terminal.
     * */
    private static void millisecondIntervals() {
        System.out.println("""
                
                Hi!
                This is my autoclicker, please provide me the intervals between the clicks in milliseconds
                (1000ms = 1s):""");

        while (true) {
            try {
                String input = sc.nextLine();
                milliseconds = Integer.parseInt(input);
                if (milliseconds > 0) {
                    break;
                }
                System.out.println("Please enter a positive integer.");
            } catch (Exception e) {
                System.out.println("Error! Please provide me the intervals between milliseconds: ");
            }
        }
    }


    /**
     * The method responsible for running the clicker.
     * */
    private static void runClicker() {
        try {
            System.out.println("Clicking will start in...");
            for (int i = 10; i > 0; i--) {
                System.out.println(i + "...");
                Thread.sleep(1000);
            }
            System.out.println("Starting!");

            Robot robot = new Robot();

            if (clicks == 0) {
                while (true) {
                    performClick(robot);
                    Thread.sleep(milliseconds);
                }
            } else {
                for (int i = 0; i < clicks; i++) {
                    performClick(robot);
                    Thread.sleep(milliseconds);
                }
            }

            System.out.println("Finished clicking!");
        } catch (Exception e) {
            System.out.println("Something went wrong! Shutting down");
        }
    }


    /**
     * The method is responsible for defining the click
     * @param robot: A default robot
     * */
    private static void performClick(Robot robot) {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(100);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
}
