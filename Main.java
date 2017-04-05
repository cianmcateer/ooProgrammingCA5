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

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        ActorStore actorStore = new ActorStore();

        String[] options = {"Display all actors by name",
            "Display all actors by ID",
            "Display all actors by Rating",
            "Print an actor",
            "Update Actor",
            "Add new search term",
            "Search for actor",
            "Save to binary file and add to html page",
            "Print hash map"
        };

        int menu = 0;
        boolean isNumber;
        boolean isString;
        do {
            System.out.println("Welcome to anyactor.ie! Please choose from the following options");
            for (int i = 0; i < options.length; i++) {
                System.out.println(i + ". " + options[i]);
            }
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
                    Set<Person> nameSet = actorStore.sortSet(new NameComparator());
                    actorStore.print(nameSet);
                    break;
                case 1:
                    System.out.println("Actors sorted by ID");
                    Set<Person> idSet = actorStore.sortSet(new UIDComparator());
                    actorStore.print(idSet);
                    break;
                case 2:
                    System.out.println("Actors sorted by Rating");
                    Set<Person> ratingSet = actorStore.sortSet(new RatingComparator());
                    actorStore.print(ratingSet);
                    break;
                case 3:
                    System.out.println("Please enter an actor to print");
                    in.nextLine();
                    String actor = in.nextLine();
                                       
                    actorStore.printSearch(actor);
                    break;
                case 4:
                    System.out.println("Update by Adding your score and comment here");
                    System.out.println("Name");
                    in.nextLine();
                    String actorName = in.nextLine();
                    System.out.println("Rating (Out of 5)");
                    double rating = in.nextDouble();
                    System.out.println("Comment");
                    in.nextLine();
                    String comment = in.nextLine();

                    actorStore.updateActor(actorName, rating, comment);
                    break;
                case 5:
                    System.out.println("Please add a new search term here");
                    in.nextLine();
                    String query = in.nextLine();
                    actorStore.addPerson(query);
                    System.out.println(query + " has been added!");
                    break;

                case 6:
                    System.out.println("Search for actor here");
                    in.nextLine();
                    String actorQuery = in.nextLine();
                    actorStore.searchActor(actorQuery);
                    break;
                case 7:
                    //Save and send to file
                    System.out.println("Your data has been saved and added to our web page!");
                    try {
                        Set<Person> sortedSet = actorStore.sortSet(new NameComparator());
                        actorStore.demoListToHTML(sortedSet);
                    } catch (FileNotFoundException e) {
                    }

                    actorStore.sendToFile();
                    System.exit(0);
                    break;
                case 8:
                    System.out.println("Print map");
                    actorStore.printMap();
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        } while (menu != -1);
    }
}
