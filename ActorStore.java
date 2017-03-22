/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooprogrammingca5;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;


/**
 *
 * @author CianMcAteer
 */
public class ActorStore {
    
    private Map<String,ArrayList<Person>> actorList;
    
    public ActorStore(){
        this.actorList = new HashMap<>();
    }    
    
    public void print() {
                
        //Convert actorlist to treemap so keys are sorted
        Map sortedList = new TreeMap<>(actorList);
        //Set of keys will allow us to use for each loop
        Set<String> keySet = sortedList.keySet();
                        
        if (sortedList.isEmpty()){
            //No actors have been added yet
            System.out.println("There is no actor in the database");
        }
        for (String key : keySet) {
            
            //Now we are able to access actor details
            ArrayList<Person> list = (ArrayList<Person>) sortedList.get(key);
            
            //Sort actor names using comparator
            Collections.sort(list, new NameComparator());
            //Print keys            
            System.out.println(key + ":");
            for(Person p : list){
                //Print details
                System.out.println("\t" + p);
            }
            
        }
    }
    
    public void printActor(String actor){
        Set<String> keySet = actorList.keySet();         
        for (String key : keySet) {
            if(actor.equals(key)){
                //Print specific actor
                ArrayList<Person> list = (ArrayList<Person>) actorList.get(key);
                System.out.println(key + ":");
                for(Person p : list){
                    System.out.println("\t" + p);
                }
            }
            
            
        }
    }
    public void addPerson(String query){
        
        try {
            URL url = new URL("http://api.tvmaze.com/search/people?q=" + query);
            
            InputStream in = url.openStream();

            JsonReader reader = Json.createReader(in);

            JsonArray array = reader.readArray();  // top level object - first "["
            
            // Having consumed the first "[" and read in the JsonArray,
            // we can iterate over the elements in the array, and extract
            // each JsonObject.
            
            
            ArrayList<Person> personList = new ArrayList<>();
            String imageUrls = "";
            
            for(int i = 0; i < array.size(); i++)
            {
                JsonObject object = array.getJsonObject(i);
                
                JsonObject personObject = object.getJsonObject("person");
                JsonObject linksObject = personObject.getJsonObject("_links");
                JsonObject selfObject = linksObject.getJsonObject("self");
                
                double score = object.getJsonNumber("score").doubleValue();
                String queryName = query;
                String name = personObject.getJsonString("name").getString();
                int id = personObject.getJsonNumber("id").intValue();
                
                String personLink = selfObject.getJsonString("href").getString();
                double myRating = 0; 
                String myComments = "";
                
                Person person = new Person(score, queryName, name, id, imageUrls, personLink, myRating, myComments);
                personList.add(person);

                actorList.put(query, personList);
            }
            
        } catch (IOException e) {
        }
        
    }
    
    public void searchActor(String actor){
        Set<String> keySet = actorList.keySet();
        boolean isFound = false;
        
        for(String key : keySet){
            if(key.equalsIgnoreCase(actor)){
                isFound = true;                
            }
            else if(actorList.containsKey(actor) == false){
                isFound = false;
            }                                
        }
        
        if(isFound == true){
            System.out.println(actor + " has been found");
            printActor(actor);
        }else{
            System.out.println(actor + " is not in database." + actor + " will now be added to database.");
            addPerson(actor);
            printActor(actor);
        }
        
    }
     
}
