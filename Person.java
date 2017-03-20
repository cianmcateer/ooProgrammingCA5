/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oopca;

import java.util.Objects;


public class Person {
    
private double score; // (value indicating the quality of match for search query)
private String queryName; //(query name used to retrieve this Actor)
private String name;
private int id;
private String imageUrls;// (2 or more)
private String personLink;
private double myRating; 
private String myComments;

    public Person(double score, String queryName, String name, int id, String imageUrls, String personLink, double myRating, String myComments) {
        this.score = score;
        this.queryName = queryName;
        this.name = name;
        this.id = id;
        this.imageUrls = imageUrls;
        this.personLink = personLink;
        this.myRating = myRating;
        this.myComments = myComments;
    }

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

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
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
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.score);
        hash = 83 * hash + Objects.hashCode(this.queryName);
        hash = 83 * hash + Objects.hashCode(this.name);
        hash = 83 * hash + this.id;
        hash = 83 * hash + Objects.hashCode(this.imageUrls);
        hash = 83 * hash + Objects.hashCode(this.personLink);
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.myRating) ^ (Double.doubleToLongBits(this.myRating) >>> 32));
        hash = 83 * hash + Objects.hashCode(this.myComments);
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
        if (this.score != other.score) {
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
        if (!Objects.equals(this.imageUrls, other.imageUrls)) {
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
        return "Person{" + "score=" + score + ", queryName=" + queryName + ", name=" + name + ", id=" + id + ", imageUrls=" + imageUrls + ", personLink=" + personLink + ", myRating=" + myRating + ", myComments=" + myComments + '}';
    }



    
}
