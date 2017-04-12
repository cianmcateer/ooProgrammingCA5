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
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
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
    private Set<Person> display;
    private static final String SAVEFILE = "persons.dat";
    private static final String HTLMFILE = "actors.html";

    public static final String ERRORIMAGE = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/300px-No_image_available.svg.png";

    public ActorStore() {
        this.actorList = new HashMap<>();
        readFromFile();
    }

    public void printMap() {

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

            //Print keys            
            System.out.println(key + ":");
            for (Person p : list) {
                if (p.getMediumImage() == null) {
                    p.setMediumImage("No medium image available");
                }
                if (p.getOriginalImage() == null) {
                    p.setOriginalImage("No original image available");
                }
                //Print details                      
                System.out.println("\t" + p);
            }
        }
    }

    public Set<Person> sortSet(final Comparator<Person> C) { //Use any type of person comparator to sort Tree Set        
        Set<String> keySet = actorList.keySet();
        //Display is converted from set to treeset
        display = new TreeSet<>(C);
        for (String key : keySet) {
            //Get person list from hashmap
            List<Person> details = (ArrayList<Person>) actorList.get(key);
            // if Comparator is type RatingComparator we need to filter out the actors who have not been rated
            if (C instanceof RatingComparator) {
                //add to display treeset
                for (Person p : details) {
                    if (p.getMyRating() != 0) {
                        display.add(p);
                    }

                }
            } else { //Print as normal
                //add to display treeset
                for (Person p : details) {
                    display.add(p);
                }
            }

        }
        return display;
    }

    public void print(Set<Person> set) { // Print out any set of type Person
        if (set.isEmpty()) {
            System.out.println("No actors have been added.");
        } else {
            for (Person p : set) {
                System.out.println(p);
            }
        }
    }

    public void removeSearch(String key) { // Remove unwanted search queries and all there results
        if (actorList.isEmpty()) {
            System.out.println("Actor Database is empty!");
        }
        if (actorList.containsKey(key)) {
            actorList.remove(key);
        } else {
            System.out.println(key + " was not found.");
        }

    }

    public void removeActor(String actor) { // Remove specific actor
        Set<String> keySet = actorList.keySet();

        for (String key : keySet) {
            List<Person> list = (ArrayList<Person>) actorList.get(key);
            // Iterator is used as we can't remove objects with foreach loop
            Iterator<Person> iter = list.iterator();
            while (iter.hasNext()) {
                if (iter.next().getName().equalsIgnoreCase(actor)) {
                    iter.remove();
                    System.out.println(actor + " has been removed.");
                }
            }
        }
    }

    public void printSearch(String actor) {
        Set<String> keySet = actorList.keySet();
        String message = "";
        boolean flag = false;
        for (String key : keySet) {
            //Print specific actor
            List<Person> list = (ArrayList<Person>) actorList.get(key);
            for (Person p : list) {
                if (p.getName().equalsIgnoreCase(actor)) {
                    message = actor + " found";
                    System.out.println(p);
                    flag = true;
                } else if (!flag) {
                    message = actor + " has not been found";
                }
            }
        }
        System.out.println(message);
    }

    public void addPerson(String query) {
        String message = "";
        try {
            // Get JSON file where our data comes from
            URL url = new URL("http://api.tvmaze.com/search/people?q=" + query);

            InputStream in = url.openStream();

            JsonReader reader = Json.createReader(in);

            JsonArray array = reader.readArray();  // top level object - first "["

            // Having consumed the first "[" and read in the JsonArray,
            // we can iterate over the elements in the array, and extract
            // each JsonObject.
            ArrayList<Person> personList = new ArrayList<>();

            for (int i = 0; i < array.size(); i++) {
                JsonObject object = array.getJsonObject(i);

                JsonObject personObject = object.getJsonObject("person");
                JsonObject linksObject = personObject.getJsonObject("_links");
                JsonObject selfObject = linksObject.getJsonObject("self");

                JsonObject imageObject = null;
                try {
                    imageObject = personObject.getJsonObject("image");
                } catch (ClassCastException ex) {

                }

                double score = object.getJsonNumber("score").doubleValue();
                String queryName = query;
                String name = personObject.getJsonString("name").getString();
                int id = personObject.getJsonNumber("id").intValue();

                String personLink = selfObject.getJsonString("href").getString();

                String imageMedium = null;
                String imageOriginal = null;
                if (imageObject != null) {
                    imageMedium = imageObject.getString("medium", "null");
                    imageOriginal = imageObject.getString("original", "null");

                }

                double myRating = 0.0;
                String myComments = "No comment";

                Person person = new Person(score, queryName, name, id, imageMedium, imageOriginal, personLink, myRating, myComments);
                if (person.getName() == null) {
                    message = query + " did not return anything";
                } else {
                    message = person.getQueryName() + " query added to search";
                    personList.add(person);

                    actorList.put(query, personList);

                }

            }

        } catch (ConcurrentModificationException e) {
            System.out.println("Error " + e);
        } catch (MalformedURLException e) {
            System.out.println("Error: " + e);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error: " + e);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        System.out.println(message);
    }

    public void update(String queryName, double rating, String comment) { // Update the rating and comment of a specific actor
        if (actorList.isEmpty()) {
            System.out.println("Sorry there are no actors in the database");
        } else { // If hashmap has values
            Set<String> keySet = actorList.keySet();
            for (String key : keySet) {
                List<Person> details = (ArrayList<Person>) actorList.get(key);
                for (Person p : details) {
                    // If name is found
                    if (queryName.equalsIgnoreCase(p.getName())) {
                        // Re set values
                        p.setMyRating(rating);
                        p.setMyComments(comment);
                    }
                }
            }
        }
    }


    public void search(String query) {
        Set<String> keySet = actorList.keySet();
        
        Set<Person> foundSet = new TreeSet<>(new ScoreComparator());        
        for (String key : keySet) {
            List<Person> details = (ArrayList<Person>) actorList.get(key);
            for (Person p : details) {
                if (p.getName().toLowerCase().startsWith(query.toLowerCase()) || p.getName().toLowerCase().endsWith(query)) {
                    foundSet.add(p);                    
                }
            }
        }
        if(!foundSet.isEmpty()){
            print(foundSet);
        }else{
            addPerson(query);
        }
         
    }

    public void sendToFile() {

        DataOutputStream dos = null;
        try {

            dos = new DataOutputStream(new FileOutputStream(new File(SAVEFILE)));

            Set<String> keySet = actorList.keySet();

            for (String key : keySet) {
                // "Pad" method required set byte size to string values
                dos.writeChars(pad(key, 16));

                List<Person> details = (ArrayList<Person>) actorList.get(key);

                // write out to file  count of number of person objects in array
                // Will allow us to use for loop to iterate through array list 
                // when we read it back in from the file
                dos.writeInt(details.size());
                
                // Write data to file
                for (Person p : details) {
                    dos.writeDouble(p.getScore());                    
                    dos.writeChars(pad(p.getQueryName(), 16));
                    dos.writeChars(pad(p.getName(), 16));
                    dos.writeInt(p.getId());
                    if (p.getMediumImage() != null) { 
                        dos.writeChars(pad(p.getMediumImage(), 100));
                    } else { // Null handler
                        dos.writeChars(pad("Image not available", 100));
                    }
                    if (p.getOriginalImage() != null) {
                        dos.writeChars(pad(p.getOriginalImage(), 100));
                    } else {
                        dos.writeChars(pad("Image not available", 100));
                    }

                    dos.writeChars(pad(p.getPersonLink(), 100));
                    dos.writeDouble(p.getMyRating());
                    dos.writeChars(pad(p.getMyComments(), 100));

                    dos.flush();

                }
                
            }
            dos.close();
        } catch (IOException e) {
        }

    }

    private String pad(String value, int i) {
        while (value.length() < i) {
            value = '*' + value;
        }
        return value;
    }

    private String depad(byte[] read) {
        String word = "";
        for (int i = 0; i < read.length; i += 2) {
            char c = (char) (((read[i] & 0x00FF) << 8) + (read[i + 1] & 0x00FF));
            if (c != '*') {
                word += c;
            }
        }
        return word;
    }

    private String readString(DataInputStream dis, int size) throws IOException {
        byte[] makeBytes = new byte[size * 2];// 2 bytes per char
        dis.read(makeBytes);  // read size characters (including padding)
        return depad(makeBytes);
    }

    private void readFromFile() {
        ArrayList<Person> fileList = new ArrayList<>();
        DataInputStream dis = null;
        File f = new File(SAVEFILE);
        try {
            dis = new DataInputStream(new FileInputStream(f));
            if (dis.available() > 0) {
                while (dis.available() > 0) {

                    fileList = new ArrayList<>();

                    String key = readString(dis, 16);

                    int count = dis.readInt();  // number of Person objects for this key

                    for (int i = 1; i <= count; i++) {

                        double score = dis.readDouble();
                        String queryName = readString(dis, 16);
                        String name = readString(dis, 16);
                        int id = dis.readInt();
                        String mediumImage = readString(dis, 100);
                        String originalImage = readString(dis, 100);
                        String personLink = readString(dis, 100);
                        double myRating = dis.readDouble();
                        String myComment = readString(dis, 100);

                        Person p = new Person(score, queryName, name, id, mediumImage, originalImage, personLink, myRating, myComment);

                        fileList.add(p);

                    }
                    actorList.put(key, fileList);
                }
            } // If file is empty
            else {
                actorList = new HashMap<>();
            }

        } catch (IOException e) {
        }

    }

    private Set<Person> cloneSet() { // returns a clone of display set to use for creating html page
        Person clone = new Person();
        Set<Person> cloneSet = new TreeSet<>(new NameComparator());
        try {
            for (Person p : display) {
                clone = p.clone();
                cloneSet.add(clone);
            }
        } catch (CloneNotSupportedException e) {
            System.out.println("Error " + e);
        }

        return cloneSet;
    }

    public void demoListToHTML() throws FileNotFoundException {
        Set<Person> cloneSet = cloneSet();
        try {
            PrintWriter pWriter = new PrintWriter(new FileOutputStream(new File(HTLMFILE), true));
            pWriter.println("<!DOCTYPE html>");
            pWriter.println("<html>");
            pWriter.println("<head>");
            pWriter.println("<title>Actors</title>");
            pWriter.println("<link rel='stylesheet' type='text/css' href='index.css'>");
            pWriter.println("</head>");
            pWriter.println("<body>");
            pWriter.println("<center><h1>Actors.ie</h1></center>");
            pWriter.println("<table border='1'>");
            pWriter.println("<tr><td>Query</td><td>Name</td><td>ID</td><td>Medium Image</td><td>Original Image</td><td>Link</td><td>Rating</td><td>Comment</td></tr>");

            for (Person p : cloneSet) {
                if(p.getMediumImage().equals("Image not available")){
                    p.setMediumImage(ERRORIMAGE);
                }
                if(p.getOriginalImage().equals("Image not available")){
                    p.setOriginalImage(ERRORIMAGE);
                }
                pWriter.println("<tr>");
                pWriter.println(p.toHTMLTableData());
                pWriter.println("</tr>");
            }

            pWriter.println("</table>");
            pWriter.println("<br><br><br>");
            pWriter.println("</body>");
            pWriter.println("</html>");
            pWriter.flush();
            pWriter.close();
        } catch (FileNotFoundException e) {
        }

    }

}
