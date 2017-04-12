/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooprogrammingca5;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import static ooprogrammingca5.ActorStore.ERRORIMAGE;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author CianMcAteer
 */
public class ActorStoreTest {

    public ActorStoreTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of printMap method, of class ActorStore.
     */
    @Test
    public void testPrintMap() {
        System.out.println("printMap");

        Map<String, ArrayList<Person>> map = new HashMap<>();

        Person john1 = new Person(25.5, "john", "John Casino", 3312, "Not available", "Not available", "jCasino.com", 0.0, "");
        Person john2 = new Person(28.5, "john", "John McArdle", 8613, "Not available", "Not available", "jMcArdle.com", 0.0, "");

        ArrayList<Person> johnList = new ArrayList<>();
        johnList.add(john1);
        johnList.add(john2);

        Person simon1 = new Person(12.5, "simon", "Simon Matthews", 512, "Not available", "Not available", "sMatthews.com", 0.0, "");
        Person simon2 = new Person(30.5, "simon", "Simon McAteer", 613, "Not available", "Not available", "sMcAteer.com", 0.0, "");

        ArrayList<Person> simonList = new ArrayList<>();
        simonList.add(simon1);
        simonList.add(simon2);

        Person craig1 = new Person(35.7, "craig", "Craig Smith", 612, "Not available", "Not available", "cSmith.com", 0.0, "");
        Person craig2 = new Person(43.2, "craig", "Craig Leggett", 5182, "Not available", "Not available", "cLeggett.com", 0.0, "");

        ArrayList<Person> craigList = new ArrayList<>();
        craigList.add(craig1);
        craigList.add(craig2);
        map.put("john", johnList);
        map.put("simon", simonList);
        map.put("craig", craigList);
        Map<String, ArrayList<Person>> sortedMap = new TreeMap<>(map);

        Set<String> keySet = sortedMap.keySet();
        for (String key : keySet) {
            System.out.println(key + ":");
            List<Person> details = (ArrayList<Person>) sortedMap.get(key);
            for (Person p : details) {
                System.out.println("\t" + p);
            }
        }

        ActorStore instance = new ActorStore();
        instance.printMap();

    }

    /**
     * Test of sortSet method, of class ActorStore.
     */
    @Test
    public void testSortSet() {
        System.out.println("sortSet");
        Map<String, ArrayList<Person>> map = new HashMap<>();

        Person john1 = new Person(25.5, "john", "John Casino", 3312, "Not available", "Not available", "jCasino.com", 0.0, "");
        Person john2 = new Person(28.5, "john", "John McArdle", 8613, "Not available", "Not available", "jMcArdle.com", 0.0, "");

        ArrayList<Person> johnList = new ArrayList<>();
        johnList.add(john1);
        johnList.add(john2);

        Person simon1 = new Person(12.5, "simon", "Simon Matthews", 512, "Not available", "Not available", "sMatthews.com", 0.0, "");
        Person simon2 = new Person(30.5, "simon", "Simon McAteer", 613, "Not available", "Not available", "sMcAteer.com", 0.0, "");

        ArrayList<Person> simonList = new ArrayList<>();
        simonList.add(simon1);
        simonList.add(simon2);

        Person craig1 = new Person(35.7, "craig", "Craig Smith", 612, "Not available", "Not available", "cSmith.com", 0.0, "");
        Person craig2 = new Person(43.2, "craig", "Craig Leggett", 5182, "Not available", "Not available", "cLeggett.com", 0.0, "");

        ArrayList<Person> craigList = new ArrayList<>();
        craigList.add(craig1);
        craigList.add(craig2);
        map.put("john", johnList);
        map.put("john", simonList);
        map.put("craig", craigList);

        Comparator<Person> C = new NameComparator();
        Set<Person> display = new TreeSet(C);

        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            List<Person> details = (ArrayList<Person>) map.get(key);
            for (Person p : details) {
                display.add(p);
            }
        }

        ActorStore instance = new ActorStore();
        instance.sortSet(C);

    }

    /**
     * Test of print method, of class ActorStore.
     */
    @Test
    public void testPrint() {
        System.out.println("print");
        Set<Person> set = new TreeSet(new NameComparator());
        Person p1 = new Person(25.5, "john", "john casino", 3312, "Not available", "Not available", "jCasino.com", 0.0, "");
        Person p2 = new Person(12.5, "simon", "simon matthews", 512, "Not available", "Not available", "sMatthews.com", 0.0, "");
        Person p3 = new Person(35.7, "craig", "craig smith", 612, "Not available", "Not available", "cSmith.com", 0.0, "");
        set.add(p1);
        set.add(p2);
        set.add(p3);
        ActorStore instance = new ActorStore();
        instance.print(set);

    }

    /**
     * Test of removeSearch method, of class ActorStore.
     */
    @Test
    public void testRemoveSearch() {
        System.out.println("removeSearch");
        String key = "";
        ActorStore instance = new ActorStore();
        instance.removeSearch(key);

    }

    /**
     * Test of removeActor method, of class ActorStore.
     */
    @Test
    public void testRemoveActor() {
        System.out.println("removeActor");
        String actor = "";
        ActorStore instance = new ActorStore();
        instance.removeActor(actor);

    }

    /**
     * Test of printSearch method, of class ActorStore.
     */
    @Test
    public void testPrintSearch() {
        System.out.println("printSearch");
        String actor = "Tom Cruise";
        ActorStore instance = new ActorStore();
        instance.printSearch(actor);

    }

    /**
     * Test of addPerson method, of class ActorStore.
     */
    @Test
    public void testAddPerson() {
        System.out.println("addPerson");
        String query = "Tom Cruise";
        ActorStore instance = new ActorStore();
        instance.addPerson(query);

    }

    /**
     * Test of update method, of class ActorStore.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        String queryName = "tom cruise";
        double rating = 4.3;
        String comment = "Very good";
        ActorStore instance = new ActorStore();
        instance.update(queryName, rating, comment);

    }

    /**
     * Test of search method, of class ActorStore.
     */
    @Test
    public void testSearch() {
        System.out.println("search");
        String actor = "john casino";
        ActorStore instance = new ActorStore();
        instance.search(actor);

    }

    /**
     * Test of sendToFile method, of class ActorStore.
     */
    @Test
    public void testSendToFile() {
        System.out.println("sendToFile");
        ActorStore instance = new ActorStore();
        instance.sendToFile();

    }

    /**
     * Test of demoListToHTML method, of class ActorStore.
     */
    @Test
    public void testDemoListToHTML() throws Exception {
        System.out.println("demoListToHTML");
        
        Set<Person> cloneSet = new TreeSet(new NameComparator());
        Person person = new Person(35.7, "craig", "craig smith", 612, "Not available", "Not available", "cSmith.com", 0.0, "");
        cloneSet.add(person);
        try {
            PrintWriter pWriter = new PrintWriter(new FileOutputStream(new File("actorTest.html"), true));
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
        } catch (Exception e) {
        }
        
        
        ActorStore instance = new ActorStore();
        instance.demoListToHTML();

    }

}
