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
public class Main {
    
    public static void main(String[] args) {
        ActorStore actorStore = new ActorStore();
        //actorStore.addPerson("john");        
        actorStore.searchActor("john");
        actorStore.print();
    }
    
}
