/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooprogrammingca5;

import java.util.Comparator;
/**
 * Sorts by id
 * @author CianMcAteer
 */
public final class UIDComparator implements Comparator<Person>{
    @Override
    public int compare(Person p1,Person p2){
        if(p1.getId() > p2.getId()){
            return 1;
        }else if(p1.getId() < p2.getId()){
            return -1;
        }
        return 0;
    }
}
