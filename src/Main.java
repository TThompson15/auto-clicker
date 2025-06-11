import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Scanner;

/**
 * The class is responsible for a mouse auto-clicker, which performs a left mouse button click.
 *
 * The user can provide:
 *      - The time interval between two clicks
 *      - Whether the program should run indefinitely or stop after a certain number of clicks
 *
 * If the program is perpetual ('y'):
 *      - The code will run in an infinite loop
 *      - Clicks will occur at the interval specified by the user
 *
 * If the program is not perpetual ('n'):
 *      - The user can specify the total number of clicks
 *      - The user can specify how many clicks should occur in each clicking block (section)
 *      - The user can specify the wait time between each block of clicks
 *
 */
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
     * The integer which represents the clicks in each clicking block. Only used when the clicker is NOT infinite
     */
    private static int clicksPerBlock = 0;

    /**
     * The integer which represents the time between each clicking block. Only used when the clicker is NOT infinite
     */
    private static int waitTimeBetweenBlocks = 0;

    /**
     * The integer which represents the number of clicks.
     * If it is 0 (which is ONLY available if the user decides the clicks are perpetual), the program will run in an infinite loop.
     * */
    private static int clicks = -1;


    /**
     * The method which is responsible for calling every individual methods.
     * @param args: program arguments, not implemented in this code
     * */
    public static void main(String[] args) {

        // The time(ms) given by the user will directly go into the --milliseconds-- variable
        millisecondIntervals();

        // The number of clicks given by the user will directly go into the --clicks-- variable
        numberOfClicks();

        // Run the program
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

                // If the clicking is perpetual, we save the clicks as 0, and we break out.
                return;
            } else if (input.equalsIgnoreCase("n")) {
                System.out.println("Alright, give me the number of total clicks:");
                while (true) {
                    try {
                        String clickInput = sc.nextLine();
                        clicks = Integer.parseInt(clickInput);
                        if (clicks > 0) {
                            // Calling the next method, because we got the clicks provided by the user, and it got saved.
                            // The method is only called when the clicks arent infinite (infinite here is clicks=0)
                            waitTimeBetweenClicks();
                            return;
                        }
                        System.out.println("Please enter a positive integer!");
                    } catch (Exception e) {
                        System.out.println("Sorry, I couldn't read that! Please enter a valid number! ");
                    }
                }
            } else {
                System.out.println("Please type y(perpetual) or n(not perpetual)!");
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
                    // If we get a good input, we'll break out of the method. We got what we wanted, milliseconds have been saved
                    break;
                }
                System.out.println("Please enter a positive integer!");
            } catch (Exception e) {
                System.out.println("Sorry, I couldn't read that! Please enter a valid number: ");
            }
        }
    }


    /**
     * The method responsible for getting the waiting time between clicking blocks, got by the user through terminal.
     * */
    private static void waitTimeBetweenClicks() {
        System.out.println("Awesome! Now configure block settings (each block consists of X clicks, and Y time between blocks:");

        while (true) {
            try {
                System.out.println("How many clicks should be in one block (X)?");
                clicksPerBlock = Integer.parseInt(sc.nextLine());
                if (clicksPerBlock > 0) {
                    // We got what we wanted, no more questions here, going to waitTimeBetweenBlocks
                    break;
                } else {
                    System.out.println("Please enter a positive integer!");
                }
            } catch (Exception e) {
                System.out.println("Sorry, I couldn't read that! Please enter a valid number! ");
            }
        }

        while (true) {
            try {
                System.out.println("How long should be the break between blocks (Y)? (in milliseconds)");
                waitTimeBetweenBlocks = Integer.parseInt(sc.nextLine());
                if (waitTimeBetweenBlocks > 0) {
                    // We are finished, breaking out of method, and moving to runClicker()
                    break;
                } else {
                    System.out.println("Please enter a positive integer!");
                }
            } catch (Exception e) {
                System.out.println("Sorry, I couldn't read that! Please enter a valid number! ");
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

            // If the clicking is infinite, there are no blocks, just clicks, infinitely
            if (clicks == 0) {
                while (true) {
                    performClick(robot);
                    Thread.sleep(milliseconds);
                }
            } else {
                // Otherwise, there are blocks, which sleep from one another

                int completedClicks = 0;
                while (completedClicks < clicks) {
                    int clicksThisBlock = Math.min(clicksPerBlock, clicks - completedClicks);
                    for (int i = 0; i < clicksThisBlock; i++) {
                        performClick(robot);
                        Thread.sleep(milliseconds);
                        completedClicks++;
                    }

                    if (completedClicks < clicks) {
                        System.out.println("Block done. Waiting " + waitTimeBetweenBlocks + " ms before next block...");
                        Thread.sleep(waitTimeBetweenBlocks);
                    }
                }
            }

            System.out.println("Finished clicking!");
        } catch (Exception e) {
            System.out.println("Something went wrong! Shutting down.");
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
