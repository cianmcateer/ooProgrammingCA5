/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooprogrammingca5;

import java.util.Comparator;

/**
 *
 * @author CianMcAteer
 */
public class RatingComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
//        if (p1.getMyRating() > p2.getMyRating()) {
//            return 1;
//        } else if (p1.getMyRating() < p2.getMyRating()) {
//            return -1;
//        }
//        else{
//            return 0;
//        }      
        return Double.compare(p1.getMyRating(), p2.getMyRating());
    }
}
