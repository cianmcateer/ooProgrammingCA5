/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooprogrammingca5;

/**
 *
 * @author CianMcAteer
 */
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ActorStore actorStore = new ActorStore();
        int menu = 0;
        boolean isNumber;
        do {
            System.out.println("Welcome to anyactor.ie! Please choose from the following options");
            displayOptions();
            do {
                if (in.hasNextInt()) {
                    menu = in.nextInt();
                    isNumber = true;
                } else {
                    System.out.println("Please enter a whole number");
                    isNumber = false;
                    in.next();
                }
            } while (!isNumber);

            switch (menu) {
                case 0:
                    System.out.println("Actors sorted by name");
                    Set<Person> set = actorStore.sortSet(new NameComparator());
                    actorStore.print(set);
                    break;
                case 1:
                    System.out.println("Actors sorted by ID");
                    set = actorStore.sortSet(new UIDComparator());
                    actorStore.print(set);
                    break;
                case 2:
                    System.out.println("Actors sorted by Rating");
                    set = actorStore.sortSet(new RatingComparator());
                    actorStore.print(set);
                    break;
                case 3:
                    String actor = "";
                    boolean isName;
                    do {
                        actor = in.nextLine();
                        if (validName(actor)) {
                            actorStore.printSearch(actor);
                            isName = true;
                        } else {
                            System.out.println("Please enter a valid name (No digits,special characters or empty spaces)");
                            isName = false;
                        }
                    } while (!isName);

                    break;
                case 4:
                    System.out.println("Update by Adding your score and comment here");
                    String actorName = "";
                    double rating = -1;
                    while (!validName(actorName)) {
                        System.out.println("Please enter actor name (No digits/Special Characters/empty spaces");
                        in.nextLine();
                        actorName = in.nextLine();
                    }
                    while (!validDouble(rating)) {
                        System.out.println("Rating (0-5)");
                        rating = in.nextDouble();
                    }

                    System.out.println("Comment");
                    in.nextLine();

                    String comment = in.nextLine();
                    actorStore.update(actorName, rating, comment);
                    break;
                case 5:

                    String query = "";
                    while (!validName(query)) {
                        System.out.println("Please add a new search term here");
                        in.nextLine();
                        query = in.nextLine();
                        actorStore.addPerson(query);
                    }
                    System.out.println(query + " has been added!");
                    break;
                case 6:
                    String actorQuery = "";
                    while (!validName(actorQuery)) {
                        System.out.println("Search for actor here (No special characters,digits or empty space)");
                        in.nextLine();
                        actorQuery = in.nextLine();
                        actorStore.search(actorQuery);
                    }
                    break;
                case 7:
                    // Save and send to file
                    System.out.println("Your data has been saved");
                    actorStore.sendToFile();
                    System.exit(0);
                    break;
                case 8:
                    // Prints Search term along with the actors it added to the hashmap
                    System.out.println("Print map");
                    actorStore.printMap();
                    break;
                case 9:
                    System.out.println("Delete search");
                    String deleteKey = "";
                    while (!validName(deleteKey)) {
                        in.nextLine();
                        deleteKey = in.nextLine();
                    }

                    actorStore.removeSearch(deleteKey);
                    break;
                case 10:
                    String deleteActor = "";
                    while (!validName(deleteActor)) {
                        System.out.println("Delete actor");
                        in.nextLine();
                        deleteActor = in.nextLine();
                    }
                    actorStore.removeActor(deleteActor);
                    break;
                case 11:
                    System.out.println("Saved to html");
                    try {
                        actorStore.demoListToHTML();
                    } catch (FileNotFoundException e) {
                    }
                    break;
                default:
                    System.out.println("Invalid command please select one of the options from the menu");
                    break;
            }
        } while (menu != -1);
    }

    private static void openPage() {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI("actors.html"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Not supported");
        }

    }

    private static boolean validName(String s) {

        //Letters and spaces only
        String regex = "[a-zA-Z][a-zA-Z ]*";

        if (!s.matches(regex) || s.equals("")) {
            return false;
        } else {
            return true;
        }
    }
    
    private static boolean validDouble(double d) { // Returns false if number is greater 5 or less than 0
        if (d < 1) {
            return false;
        } else if (d > 5) {
            return false;
        }
        return true;
    }

    
    private static void displayOptions() { // Reading from a file allows us to update the menu without having to access our code
        final String FILE = "options.txt";

        try {
            FileInputStream fis = new FileInputStream(FILE);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String option;
            // Print line as long as it has text i.e not null
            while ((option = br.readLine()) != null) {
                System.out.println(option);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }

    }
}
