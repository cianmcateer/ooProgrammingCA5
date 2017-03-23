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

import java.util.Scanner;
public class Main {
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        ActorStore actorStore = new ActorStore();
                      
        
        String[] options = {"Save and exit","Display all actors","Add Actor to database","Update Actor","List by ID","List by rating","Export to webpage"};                                

        int menu = 0;
        
        
        do{
            System.out.println("Welcome to anyactor.ie! Please choose from the following options");
            for (int i = 0; i < options.length; i++) {
                System.out.println(i + ". " + options[i]);
            }
            menu = in.nextInt();
            
            switch(menu){
                case 1:
                    System.out.println("Please enter an actor to print");
                    String actor = in.next();
                    actorStore.printActor(actor);
                    break;
                case 2:
                    actorStore.print();
                    break;
                case 3:
                    System.out.println("Please enter a name to add to database");
                    String query = in.next();
                    actorStore.addPerson(query);
                    break;
                case 4:    
                    
            }    
        }while(menu != 10);
        
        
        
        
        /**
         * 
         * Usable menu here
        */
                
    }
    
}
