package ooprogrammingca5;

/**
 *
 * @author CianMcAteer ,Ciaran Maher
 */
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/**
* Output class
*/
public class Main {
    /**
    * Do While/Switch menu that accesses the ActorStore methods
    * @author Cian McAteer, Ciaran Maher
    * @param args
    */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ActorStore actorStore = new ActorStore();
        int menu = 0;
        boolean isNumber;
        do {
            System.out.println("Welcome to actors.ie! Please choose from the following options");
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
                case 0: // Sort actors by Name
                    System.out.println("Actors sorted by name");
                    Set<Person> set = actorStore.sortSet(new NameComparator());
                    actorStore.print(set);
                    break;
                case 1: // Sort actors by ID
                    System.out.println("Actors sorted by ID");
                    set = actorStore.sortSet(new UIDComparator());
                    actorStore.print(set);
                    break;
                case 2: // Sort actors by Rating (Will filter out ratings of 0)
                    System.out.println("Actors sorted by Rating");
                    ArrayList<Person> list = actorStore.actorList(new RatingComparator());
                    Collections.sort(list,new RatingComparator());
                    actorStore.print(list);
                    break;
                case 3: // Search for actor and print results
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
                case 4: // Update actor details 
                    System.out.println("Update by Adding your score and comment here");
                    String actorName = "";
                    double rating = -1;
                    while (!validName(actorName)) {
                        System.out.println("Please enter actor name (No digits/Special Characters/empty spaces");
                        in.nextLine();
                        actorName = in.nextLine();
                    }
                    while (!validDouble(rating)) {
                        System.out.println("Rating (1-5)");
                        rating = in.nextDouble();
                    }

                    System.out.println("Comment");
                    in.nextLine();

                    String comment = in.nextLine();
                    actorStore.update(actorName, rating, comment);
                    break;
                case 5: // Add new search term
                    String query = "";
                    while (!validName(query)) {
                        System.out.println("Please add a new search term here");
                        in.nextLine();
                        query = in.nextLine();
                        actorStore.addPerson(query);
                    }
                    System.out.println(query + " has been added!");
                    break;
                case 6: // Search for actors (if not found add search to hashmap) 
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
                case 9: // Delete search (key)
                    System.out.println("Delete search");
                    String deleteKey = "";
                    while (!validName(deleteKey)) {
                        in.nextLine();
                        deleteKey = in.nextLine();
                    }

                    actorStore.removeSearch(deleteKey);
                    break;
                case 10: //
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
                    }
                    catch (FileNotFoundException e) {
                        System.out.println("Error: " + e);
                    }
                    catch(IOException e){
                        System.out.println("Error " + e); 
                    } 
                    break;
                case 12: // Delete history
                    actorStore.clear();
                    System.out.println("Your search history has been deleted");
                    break;
                case -1: // leave without saving data
                    System.out.println("GoodBye!");                    
                    break;
                default:
                    System.out.println("Invalid number please select one of the options from the menu");
                    break;
            }
        } while (menu != -1);
    }

    /**
    * validates string, returns false if string contains digits,special characters or if it is empty
    * @author Cian McAteer
    * @param s
    * @return boolean
    */
    public static boolean validName(String s) {

        // Letters and spaces only
        String regex = "[a-zA-Z][a-zA-Z ]*";

        if (!s.matches(regex) || s.equals("")) {
            return false;
        } else {
            return true;
        }
    }
    /**
    * Only returns true if 'd' is greater than 1 and less than or equal to 5
    * @author Cian McAteer
    * @param d
    * @return boolean
    */
    public static boolean validDouble(double d) { // Returns false if number is greater than 5 or less than 1
        if (d < 1) {
            return false;
        } else if (d > 5) {
            return false;
        }
        return true;
    }

    /**
    * Reads and prints "options.txt" file by line. Ends when a line is left blank (Null)
    * @author Cian McAteer
    */
    private static void displayOptions() { // Reading from a file allows us to update the menu without having to access our code
        final String FILE = "options.txt";

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(FILE)));

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
