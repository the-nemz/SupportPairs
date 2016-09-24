package com.supportpairs;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;

/**
 * Created by Isaac on 9/23/2016.
 */
public class Person {

    public String username;
    public int age;
    public Condition[] conditions;
    public String email;
    public String password;

    public boolean isValid(Sql2o mentorDB, Sql2o menteeDB) {
        System.out.println("checker: " + this.email);
        Mentor nameOfTor = null;
        Mentor emailOfTor = null;
        try (Connection conn2 = mentorDB.open()){
            System.out.println("1");
            String sql = "SELECT * FROM MENTORS WHERE USERNAME = '" + this.username + "';";
            System.out.println(sql);
            System.out.println("2");
            nameOfTor = conn2.createQuery(sql).executeAndFetchFirst(Mentor.class);
            sql = "SELECT * FROM MENTORS WHERE EMAIL = '" + this.email + "';";
            System.out.println(sql);
            System.out.println("3");
            emailOfTor = conn2.createQuery(sql).executeAndFetchFirst(Mentor.class);
            System.out.println("4");
        } catch (Exception ex){
            System.out.println("Exception following2:");
            System.out.println(ex);
        }
        Mentee nameOfTee = null;
        Mentee emailOfTee = null;
        try (Connection conn1 = menteeDB.open()){
            String sql = "SELECT * FROM MENTEES WHERE USERNAME = '" + this.username + "';";
            System.out.println(sql);
            nameOfTee = conn1.createQuery(sql).executeAndFetchFirst(Mentee.class);
            sql = "SELECT * FROM MENTEES WHERE EMAIL = '" + this.email + "';";
            System.out.println(sql);
            emailOfTee = conn1.createQuery(sql).executeAndFetchFirst(Mentee.class);
        } catch (Exception ex){
            System.out.println("Exception following1:");
            System.out.println(ex);
        }
        if (nameOfTee != null || emailOfTee != null ||
                nameOfTor != null || emailOfTor != null) {
            return false;
        }
        System.out.println("will return true");
        return true;
    }

    //we will use toJson to write to the db for arrays

/*    public String conditionsToString() {
        String out = "[";
        for (int a = 0; a < this.conditions.length; a++) {
            out = out + this.conditions[a].toString() + ",";
        }
        return out + "]";
    }*/

}
