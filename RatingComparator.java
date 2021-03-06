/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooprogrammingca5;

import java.util.Comparator;

/**
 * Sorts person collection by rating
 * @author CianMcAteer
 */
public class RatingComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {        
        return Double.compare(p2.getMyRating(), p1.getMyRating());
    }
}
