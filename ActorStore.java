/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooprogrammingca5;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author CianMcAteer
 */
public class ActorStore {

    private Map<String, ArrayList<Person>> actorList;

    public ActorStore() {
        this.actorList = new HashMap<>();
        //this.actorList = readFromFile("persons.dat");
        
    }

    public void print() {

        //Convert actorlist to treemap so keys are sorted
        Map sortedList = new TreeMap<>(actorList);
        //Set of keys will allow us to use for each loop
        Set<String> keySet = sortedList.keySet();

        if (sortedList.isEmpty()) {
            //No actors have been added yet
            System.out.println("There is no actor in the database");
        }
        for (String key : keySet) {

            //Now we are able to access actor details
            ArrayList<Person> list = (ArrayList<Person>) sortedList.get(key);

            //Sort actor by names and scores using comparator
            Collections.sort(list, new NameComparator());
            Collections.sort(list,new ScoreComparator());
            //Print keys            
            System.out.println(key + ":");
            for (Person p : list) {
                //Print details
                System.out.println("\t" + p);
            }

        }
    }

    public void printActor(String actor) {
        Set<String> keySet = actorList.keySet();
        for (String key : keySet) {
            if (actor.equals(key)) {
                //Print specific actor
                ArrayList<Person> list = (ArrayList<Person>) actorList.get(key);
                System.out.println(key + ":");
                for (Person p : list) {
                    System.out.println("\t" + p);
                }
            }

        }
    }

    public void addPerson(String query) {

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

            for (int i = 0; i < array.size(); i++) {
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

        }catch(MalformedURLException e){
            
        } 
        catch (IOException e) {
        }

    }

    public void updateActor(String name, int id, double rating, String comment) {
        Set<String> keySet = actorList.keySet();

        for (String key : keySet) {
            if (name.equals(key)) {
                ArrayList<Person> details = (ArrayList<Person>) actorList.get(key);
                for (Person p : details) {
                    if (p.getId() == id) {
                        p.setMyRating(rating);
                        p.setMyComments(comment);
                    }
                }
            }
        }
        printActor(name);
    }

    public void searchActor(String actor) {
        Set<String> keySet = actorList.keySet();
        boolean isFound = false;

        for (String key : keySet) {
            if (key.equalsIgnoreCase(actor)) {
                isFound = true;
            } else if (actorList.containsKey(actor) == false) {
                isFound = false;
            }
        }

        if (isFound == true) {
            System.out.println(actor + " has been found");
            printActor(actor);
        } else {
            System.out.println(actor + " is not in database." + actor + " will now be added to database.");
            addPerson(actor);
            printActor(actor);
        }

    }
    
    public void sendToFile(String file){
        
        DataOutputStream dos = null;
        try {
                                    
            dos = new DataOutputStream(new FileOutputStream(new File(file)));
            
            Set<String> keySet = actorList.keySet();
            
            for(String key : keySet){
                
                
                dos.writeChars(pad(key,16));
                ArrayList<Person> details = (ArrayList<Person>) actorList.get(key);
                
                for(Person p : details){
                    dos.writeDouble(p.getScore());
                    dos.writeChars(pad(p.getQueryName(),16));
                    dos.writeChars(pad(p.getName(), 16));
                    dos.writeInt(p.getId());
                    dos.writeChars(pad(p.getImageUrls(),16));
                    dos.writeChars(pad(p.getPersonLink(),16));                                        
                    dos.writeDouble(p.getMyRating());
                }
            }
        } catch (IOException e) {
        }
        
    }
    private String pad(String value, int i)
    {
        while(value.length() < i)
        {
            value = '*' + value;  
        }
                
        
        return value;
    }
    private String depad(byte[] read)
    {
        String word = "";
        for (int i = 0; i < read.length; i += 2) {
            char c = (char) (((read[i] & 0x00FF) << 8) + (read[i + 1] & 0x00FF));

            if (c != '*') {
                word += c;
            }
        }
        return word;
    }
    private String readString(DataInputStream dis, int size) throws IOException
    {
        byte[] makeBytes = new byte[size * 2];// 2 bytes per char
        dis.read(makeBytes);  // read size characters (including padding)
        return depad(makeBytes);
    }
    
    public Map<String,ArrayList<Person>> readFromFile(String file){
        Map<String,ArrayList<Person>> fileMap = new HashMap<>();
        ArrayList<Person> fileList = new ArrayList<>();
        DataInputStream dis = null;
        File f = new File(file);
        try {
            dis = new DataInputStream(new FileInputStream(f));
            while(dis.available() > 0){
                String key = readString(dis,16);
                double score = dis.readDouble();
                String queryName = readString(dis,16);
                String name = readString(dis,16);
                int id = dis.readInt();
                String imageUrl = readString(dis,16);
                String personLink = readString(dis,16);
                double myRating = dis.readDouble();
                String myComment = readString(dis,16);
                
                Person p = new Person(score,queryName,name,id,imageUrl,personLink,myRating,myComment);
                fileList.add(p);
                fileMap.put(key, fileList);
            }
        } catch (IOException e) {
        }
//        Set<String> keySet = fileMap.keySet();
//        System.out.println("From file");
//        for(String key : keySet){
//            System.out.println(key);
//            ArrayList<Person> details = (ArrayList<Person>) fileMap.get(key);
//            for(Person p : details){
//                System.out.println(p);
//            }
//                    
//        }
//        System.out.println(fileMap);
        
        return fileMap;
    }
    

}

