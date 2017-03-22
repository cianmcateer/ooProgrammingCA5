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
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
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
                
        Set<String> keySet = actorList.keySet();
        ArrayList<Person> list = new ArrayList<>();
        if (actorList.isEmpty()){
            System.out.println("There is no actor in the database");
        }
        for (String key : keySet) {
            
            list = (ArrayList<Person>) actorList.get(key);
            System.out.println(key + ":");
            for(Person p : list){
                System.out.println("\t" + p);
            }
            
        }
    }
    
    public void printActor(String actor){
        Set<String> keySet = actorList.keySet();
        ArrayList<Person> list = new ArrayList<>();
        for (String key : keySet) {
            if(actor.equals(key)){
                list = (ArrayList<Person>) actorList.get(key);
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
        for(String key : keySet){
            if(actorList.containsKey(actor)){
                System.out.println("Actor: " + actor + " has been found");
                //printActor(actor);
                
            }
        }
    }
    
    

}
