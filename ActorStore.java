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
 * @author Cian McAteer , Ciaran Maher
 */
public class  ActorStore {

    private Map<String, ArrayList<Person>> actorMap; // Local Storage
    private Set<Person> display; // Used for displaying values of 'actorList'

    private static final String SAVEFILE = "persons.dat";
    private static final String HTLMFILE = "actors.html";

    // If actor does not have an image, this will display instead
    public static final String ERRORIMAGE = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/300px-No_image_available.svg.png";

    /**
     * Constructor
     */
    public ActorStore() {
        this.actorMap = new HashMap<>();
        readFromFile();
    }

    /**
     * Prints out TreeMap sorted by name in both keys and values
     * @author Cian McAteer , Ciaran Maher
     */
    public void printMap() {

        //Convert actorlist to treemap so keys are sorted
        Map sortedMap = new TreeMap<>(actorMap);
        // Set of keys will allow us to use for each loop
        Set<String> keySet = sortedMap.keySet();

        if (sortedMap.isEmpty()) {
            //No actors have been added yet
            System.out.println("There is no actor in the database");
        }
        for (String key : keySet) {
            //Now we are able to access actor details
            List<Person> list = (ArrayList<Person>) sortedMap.get(key);

            //Sort actor by names using comparator
            Collections.sort(list, new NameComparator());

            //Print keys
            System.out.println(key + ":");
            for (Person p : list) {
                if (p.getMediumImage() == null) {
                    p.setMediumImage("Image not available");
                }
                if (p.getOriginalImage() == null) {
                    p.setOriginalImage("Image not available");
                }
                //Print details
                System.out.println("\t" + p);
            }
        }
    }
    
    public ArrayList<Person> list(){
        Set<String> keySet = actorMap.keySet();
        ArrayList<Person> list = new ArrayList<>();
        ArrayList<Person> printList = new ArrayList<>();
        for(String key : keySet){
            list = (ArrayList<Person>) actorMap.get(key);
            for(Person p : list){
                printList.add(p);
            }
        }
        
        return printList;
    } 
    
    public void printList(ArrayList<Person> list,Comparator<Person> c){
        Collections.sort(list,c);
        for(Person p : list){
            System.out.println(p);
        }
    }
    public <T> void print(T list){
        //T<Person> list;
        
    }
    /**
     * Returns a tree set of type person sorted by a comparator of type person
     * @author Cian McAteer
     * @param C
     * @return display
     */
    public Set<Person> sortSet(final Comparator<Person> C) { //Use any type of person comparator to sort Tree Set
        Set<String> keySet = actorMap.keySet();
        //Display is converted from set to treeset
        display = new TreeSet<>(C);
        for (String key : keySet) {
            //Get person list from hashmap
            List<Person> details = (ArrayList<Person>) actorMap.get(key);
            // If Comparator is type RatingComparator we need to filter out the actors who have not been rated
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

    /**
     * Prints person set if set is not empty
     * @author Cian McAteer
     * @param set
     */
    public void print(Set<Person> set) { // Print out any set of type Person
        if (set.isEmpty()) {
            System.out.println("No actors have been added.");
        } else {
            for (Person p : set) {
                System.out.println(p);
            }
        }
    }

    /**
     * Deletes a search query (key of hashmap) and all of it's contents(values)
     * @author Cian McAteer
     * @param key
     */
    public void removeSearch(String key) { // Remove unwanted search queries and all there results
        if (actorMap.isEmpty()) {
            System.out.println("Actor Database is empty!");
        }
        if (actorMap.containsKey(key)) {
            actorMap.remove(key);
        } else {
            System.out.println(key + " was not found.");
        }

    }

    /**
     * Deletes a specific actor in hashmap
     * @author Cian McAteer
     * @param actor
     */
    public void removeActor(String actor) { // Remove specific actor
        Set<String> keySet = actorMap.keySet();

        for (String key : keySet) {
            List<Person> list = (ArrayList<Person>) actorMap.get(key);
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

    public void clear(){
        this.actorMap.clear();
    }

    /**
     * Prints values that match with query
     * @param query
     * @author Cian McAteer , Ciaran Maher
     */
    public void printSearch(String query) {
        Set<String> keySet = actorMap.keySet();
        String message = "";
        boolean flag = false;
        for (String key : keySet) {
            //Print specific query
            List<Person> list = (ArrayList<Person>) actorMap.get(key);
            for (Person p : list) {
                if (p.getName().equalsIgnoreCase(query)) {
                    message = query + " found";
                    System.out.println(p);
                    flag = true;
                } else if (!flag) {
                    message = query + " has not been found";
                }
            }
        }
        System.out.println(message);
    }

    /**
     * Pulls JSON data from tvmaze api and puts it into hashmap
     * @author Ciaran Maher
     * @param query
     */
    public void addPerson(String query) throws ClassCastException{
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
                    //System.out.println("ERROR: " + ex);
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
                    actorMap.put(query, personList);
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

    /**
     * Allows user to rate and add comments on a specific actor
     * @author Cian McAteer
     * @param queryName
     * @param rating
     * @param comment
     */
    public void update(String queryName, double rating, String comment) { // Update the rating and comment of a specific actor
        if (actorMap.isEmpty()) {
            System.out.println("Sorry there are no actors in the database");
        } else { // If hashmap has values
            Set<String> keySet = actorMap.keySet();
            for (String key : keySet) {
                List<Person> details = (ArrayList<Person>) actorMap.get(key);
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

    /**
     * Queries the actorMap hashmap if the name is in database and prints the result, addPerson method is called and pulls JSON data if name is not found
     * @author Cian McAteer , Ciaran Maher
     * @param query
     */
    public void search(String query) {
        Set<String> keySet = actorMap.keySet();

        ArrayList<Person> foundList = new ArrayList<>();        
        for (String key : keySet) {
            List<Person> details = (ArrayList<Person>) actorMap.get(key);
            for (Person p : details) {
                if (p.getName().toLowerCase().startsWith(query.toLowerCase()) || p.getName().toLowerCase().endsWith(query)) {
                    foundList.add(p);
                }
            }
        }
        if(!foundList.isEmpty()){
            printList(foundList,new ScoreComparator());
        }else{
            addPerson(query);
        }

    }

    /**
     * Send the data from actorList hashmap to binary file (persons.dat) using DataOutputStream
     * @author Cian McAteer
     */
    public void sendToFile() {

        DataOutputStream dos = null;
        final int SMALLCHUNK = 16;          // Larger Chunks require more bytes, if not then
        final int MIDCHUNK = 32;            // the readFromFile method would not be able to read them properly
        final int LARGECHUNK = 100;
        try {

            dos = new DataOutputStream(new FileOutputStream(new File(SAVEFILE)));

            Set<String> keySet = actorMap.keySet();

            for (String key : keySet) {
                // "Pad" method required set byte size to string values
                dos.writeChars(pad(key, SMALLCHUNK));

                List<Person> details = (ArrayList<Person>) actorMap.get(key);

                // write out to file  valueCount of number of person objects in array
                // Will allow us to use for loop to iterate through array list
                // when we read it back in from the file
                dos.writeInt(details.size());

                // Write data to file
                for (Person p : details) {
                    dos.writeDouble(p.getScore());
                    dos.writeChars(pad(p.getQueryName(), SMALLCHUNK));
                    dos.writeChars(pad(p.getName(), MIDCHUNK));
                    dos.writeInt(p.getId());
                    if (p.getMediumImage() != null) {
                        dos.writeChars(pad(p.getMediumImage(), LARGECHUNK));
                    } else { // Null handler
                        dos.writeChars(pad("Image not available", LARGECHUNK));
                    }
                    if (p.getOriginalImage() != null) {
                        dos.writeChars(pad(p.getOriginalImage(), LARGECHUNK));
                    } else {
                        dos.writeChars(pad("Image not available", LARGECHUNK));
                    }

                    dos.writeChars(pad(p.getPersonLink(), LARGECHUNK));
                    dos.writeDouble(p.getMyRating());
                    dos.writeChars(pad(p.getMyComments(), LARGECHUNK));

                    dos.flush();
                }
            }
            dos.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    private String pad(String value, int i) { // Insert '*' to make up the desired byte size
        while (value.length() < i) {
            value = '*' + value;
        }
        return value;
    }

    private String depad(byte[] read) { // Used to get rid of padding so it can be readable again
        String word = "";
        for (int i = 0; i < read.length; i += 2) {
            char c = (char) (((read[i] & 0x00FF) << 8) + (read[i + 1] & 0x00FF));
            if (c != '*') {
                word += c;
            }
        }
        return word;
    }

    private String readString(DataInputStream dis, int size) throws IOException { // Used to convert string bytes back into human readable form
        byte[] makeBytes = new byte[size * 2];// 2 bytes per char
        dis.read(makeBytes);  // read size characters (including padding)
        return depad(makeBytes);
    }

    /**
     * Reads the data from the persons.dat file using DataInputStream
     * @author Cian McAteer
     */
    private void readFromFile() {
        final int SMALLCHUNK = 16;
        final int MIDCHUNK = 32;
        final int LARGECHUNK = 100;

        ArrayList<Person> fileList = new ArrayList<>();
        DataInputStream dis = null;
        File f = new File(SAVEFILE);
        try {
            dis = new DataInputStream(new FileInputStream(f));
            if (dis.available() > 0) {
                while (dis.available() > 0) {

                    fileList = new ArrayList<>();

                    String key = readString(dis, SMALLCHUNK);

                    int valueCount = dis.readInt();  // number of Person objects for this key

                    for (int i = 1; i <= valueCount; i++) {

                        double score = dis.readDouble();
                        String queryName = readString(dis, SMALLCHUNK);
                        String name = readString(dis, MIDCHUNK);
                        int id = dis.readInt();
                        String mediumImage = readString(dis, LARGECHUNK);
                        String originalImage = readString(dis, LARGECHUNK);
                        String personLink = readString(dis, LARGECHUNK);
                        double myRating = dis.readDouble();
                        String myComment = readString(dis, LARGECHUNK);

                        Person p = new Person(score, queryName, name, id, mediumImage, originalImage, personLink, myRating, myComment);

                        fileList.add(p);

                    }
                    actorMap.put(key, fileList);
                }
            }
            else { // If file is empty
                actorMap = new HashMap<>();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
    /**
     * Creates and returns a clone of the display set
     * @author Ciaran Maher , Cian McAteer
     * @return cloneSet
     */
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

    /**
     * Creates html file that contain has a table of all the actor details
     * @author Ciaran Maher
     * @throws FileNotFoundException
     */
    public void demoListToHTML() throws FileNotFoundException {
        Set<Person> cloneSet = cloneSet();
        char quote = '"';
        try {
            PrintWriter pWriter = new PrintWriter(new FileOutputStream(new File(HTLMFILE), true));
            pWriter.println("<!DOCTYPE html>");
            pWriter.println("<html>");
            pWriter.println("<head>");
            pWriter.println("<title>Actors</title>");
            pWriter.println("<link rel='stylesheet' type='text/css' href='index.css'>");
            pWriter.println("<link href=\"https://fonts.googleapis.com/css?family=Anton\" rel=\"stylesheet\">");
            pWriter.println("</head>");
            pWriter.println("<body>");
            pWriter.println("<h1>Actors.ie</h1>");
            pWriter.println("<img src=" + quote + "http://static.tvmaze.com/images/tvm-header-logo.png" + quote + "alt = TV maze>");
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
            System.out.println("ERROR: " + e);
        }
    }
}
