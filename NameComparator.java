
package ooprogrammingca5;

import java.util.Comparator;
/**
 * Sort Persons by actor name in alphabetical order
 * @author CianMcAteer
 */
public class NameComparator implements Comparator<Person>{    
    @Override
    public int compare(Person p1,Person p2){
        return p1.getName().toLowerCase().compareTo(p2.getName().toLowerCase());
    }
}
