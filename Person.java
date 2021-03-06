/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooprogrammingca5;

import java.io.Serializable;
import java.util.Objects;
//import org.apache.commons.lang.builder.ToStringBuilder;

/**
* Class implements Serializable which allows it to be represented as bytes, so we can use 'DataInputStream' and 'DataOutputStream' to read from and write to binary files
* 'Cloneable' allows us to use the Cloneable interface which is used in the 'demoListToHTML' method in ActorStore
*/
public class Person implements Serializable, Cloneable {
    // Instance variables
    private double score; // (value indicating the quality of match for search query)
    private String queryName; //(query name used to retrieve this Actor)
    private String name;
    private int id;
    private String mediumImage;
    private String originalImage;
    private String personLink;
    private double myRating;
    private String myComments;

    /**
     * parameterized constructor
     * 
     * @author Ciaran Maher , Cian McAteer
     * @param score
     * @param queryName
     * @param name
     * @param id
     * @param mediumImage
     * @param originalImage
     * @param personLink
     * @param myRating
     * @param myComments 
     */
    public Person(double score, String queryName, String name, int id, String mediumImage, String originalImage, String personLink, double myRating, String myComments) {
        this.score = score;
        this.queryName = queryName;
        this.name = name;
        this.id = id;
        this.mediumImage = mediumImage;
        this.originalImage = originalImage;
        this.personLink = personLink;
        this.myRating = myRating;
        this.myComments = myComments;
    }
    /**
    * Empty Constructor
    */
    public Person() {}

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMediumImage() {
        return mediumImage;
    }

    public void setMediumImage(String mediumImage) {
        this.mediumImage = mediumImage;
    }

    public String getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
    }

    public String getPersonLink() {
        return personLink;
    }

    public void setPersonLink(String personLink) {
        this.personLink = personLink;
    }

    public double getMyRating() {
        return myRating;
    }

    public void setMyRating(double myRating) {
        this.myRating = myRating;
    }

    public String getMyComments() {
        return myComments;
    }

    public void setMyComments(String myComments) {
        this.myComments = myComments;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.score) ^ (Double.doubleToLongBits(this.score) >>> 32));
        hash = 59 * hash + Objects.hashCode(this.queryName);
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.mediumImage);
        hash = 59 * hash + Objects.hashCode(this.originalImage);
        hash = 59 * hash + Objects.hashCode(this.personLink);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.myRating) ^ (Double.doubleToLongBits(this.myRating) >>> 32));
        hash = 59 * hash + Objects.hashCode(this.myComments);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (Double.doubleToLongBits(this.score) != Double.doubleToLongBits(other.score)) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.myRating) != Double.doubleToLongBits(other.myRating)) {
            return false;
        }
        if (!Objects.equals(this.queryName, other.queryName)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.mediumImage, other.mediumImage)) {
            return false;
        }
        if (!Objects.equals(this.originalImage, other.originalImage)) {
            return false;
        }
        if (!Objects.equals(this.personLink, other.personLink)) {
            return false;
        }
        if (!Objects.equals(this.myComments, other.myComments)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "score:   " + score + "   queryName:   " + queryName + "   name:   " + name + "   id:   " + id + "   mediumImage:   " + mediumImage + "   originalImage:   " + originalImage + "   personLink:   " + personLink + "   myRating:   " + myRating + "   myComments:   " + myComments;
    }

    /**
     * Used in HTML function to allow file to read back without interference
     * @return
     * @throws CloneNotSupportedException 
     */
    @Override
    public Person clone() throws CloneNotSupportedException { 
        return (Person) super.clone();
    }
    /**
    * Returns a converted string (person class) as a HTML table that can put into a web page
    * @author Cian McAteer , Ciaran Maher
    * @return String
    */
    public String toHTMLTableData() { // Convert person class to html format
        // Make it easier to implement quotes around strings
        char quote = '"';

        if (this.mediumImage == null || this.mediumImage.equals("Image Error")) {
            this.mediumImage = ActorStore.ERRORIMAGE;
        }

        return "<td>" + this.queryName + "</td>"
                + "<td>" + this.score + "</td>"
                + "<td>" + this.name + "</td>"
                + "<td>" + this.id + "</td>"
                + "<td>" + "<img src=" + quote + this.mediumImage + quote + " height=\"360\" width=\"360\">" + "</td>"
                + "<td>" + "<a href=" + quote + this.originalImage + quote + ">" + "Image Link for " + this.name + "</a>" + "</td>"
                + "<td>" + "<a href=" + quote + this.personLink + quote + ">" + "Details for " + this.name + "</a>" + "</td>"
                + "<td>" + this.myRating + "</td>"
                + "<td>" + this.myComments + "</td>";
    }

}
