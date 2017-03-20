/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oopca;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Scanner;
import javax.json.JsonArray;

public class Oopca {

    public static void main(String[] args) throws UnsupportedEncodingException, MalformedURLException, IOException {

        Scanner sc = new Scanner(System.in);

        System.out.println("Search Actor by Name:");
        String query = sc.next();


        try {
                    URL url = new URL("http://api.tvmaze.com/search/people?q=" + query);

            InputStream in = url.openStream();

            JsonReader reader = Json.createReader(in);

            JsonArray array = reader.readArray();  // top level object - first "["
            
            // Having consumed the first "[" and read in the JsonArray,
            // we can iterate over the elements in the array, and extract
            // each JsonObject.
            
            HashMap<String, Person> personMap = new HashMap<String, Person>();
            String imageUrls = "";
            
            for( int i = 0; i < array.size(); i++)
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

                personMap.put(query, person);
                
                
                System.out.println(personMap +"\n");
              }
            
            
            
          
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}