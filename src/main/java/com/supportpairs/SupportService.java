package com.supportpairs;

import com.google.gson.Gson;

import java.util.*;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import javax.sql.DataSource;

/**
 * Created by Isaac on 9/23/2016.
 */
public class SupportService {

    public Sql2o menteeDB;
    public Sql2o mentorDB;
    //public HashMap<String, Mentor> mentors;
    //public HashMap<String, Mentee> mentees;


    public SupportService(DataSource dataSource1, DataSource dataSource2) throws SupportServiceException {
        System.out.println("service setup");

        this.mentorDB = new Sql2o(dataSource2);

        //Create the schema for the database if necessary. This allows this
        //program to mostly self-contained. But this is not always what you want;
        //sometimes you want to create the schema externally via a script.
        try (Connection conn = this.mentorDB.open()) {
            String sql = "CREATE TABLE IF NOT EXISTS MENTORS (" +
                    "USERNAME       TEXT    PRIMARY KEY NOT NULL, " +
                    "EMAIL          TEXT    NOT NULL," +
                    "PASSWORD       TEXT    NOT NULL," +
                    "CONDITIONS     TEXT    NOT NULL," +
                    "AGE            INT     NOT NULL," +
                    "FULLNAME       TEXT    NOT NULL," +
                    "GENDER         TEXT    NOT NULL," +
                    "MAX            INT     NOT NULL," +
                    "MENTEES        TEXT," +
                    "ACTIVE         INT     NOT NULL," +
                    "PAST           INT     NOT NULL," +
                    "APPROVED       INT     NOT NULL)";
            conn.createQuery(sql).executeUpdate();
            System.out.println("created mentors db");
        } catch(Sql2oException ex) {
            System.out.println("Failed to create schema at startup");
            throw new SupportServiceException();
        }

        this.menteeDB = new Sql2o(dataSource1);

        //Create the schema for the database if necessary. This allows this
        //program to mostly self-contained. But this is not always what you want;
        //sometimes you want to create the schema externally via a script.
        try (Connection conn = this.menteeDB.open()) {
            String sql = "CREATE TABLE IF NOT EXISTS MENTEES (" +
                         "USERNAME      TEXT    PRIMARY KEY NOT NULL, " +
                         "EMAIL         TEXT    NOT NULL," +
                         "PASSWORD      TEXT    NOT NULL," +
                         "CONDITIONS    TEXT    NOT NULL," +
                         "AGE           INT     NOT NULL," +
                         "MENTOR        TEXT)" ;
            conn.createQuery(sql).executeUpdate();
            System.out.println("created mentees db");
        } catch(Sql2oException ex) {
            System.out.println("Failed to create schema at startup");
            throw new SupportServiceException();
        }

        //this.mentors = new HashMap<String, Mentor>();
        //this.mentees = new HashMap<String, Mentee>();
    }

    private Mentee selectMentee(String username) {
        try (Connection conn = menteeDB.open()){
            String sql = "SELECT * FROM MENTEES WHERE USERNAME = '" + username + "';";
            return conn.createQuery(sql).executeAndFetchFirst(Mentee.class);
        } catch (Exception ex){
            System.out.println("Exception following:");
            System.out.println(ex);
            return null;
        }
    }

    private Mentor selectMentor(String username) {
        try (Connection conn = mentorDB.open()){
            String sql = "SELECT * FROM MENTORS WHERE USERNAME = '" + username + "';";
            return conn.createQuery(sql).executeAndFetchFirst(Mentor.class);
        } catch (Exception ex){
            System.out.println("Exception following:");
            System.out.println(ex);
            return null;
        }
    }

    public Mentee createMentee(String body) throws InvalidPersonException {
        Mentee mentee = new Gson().fromJson(body, Mentee.class);
        if (!mentee.isValid(this.mentorDB, this.menteeDB)) {
            throw new InvalidPersonException();
        }
        String conds = new Gson().toJson(mentee.conditions);
        String sql = "INSERT INTO MENTEES (USERNAME, EMAIL, PASSWORD, CONDITIONS, AGE) " +
                     "VALUES ('" + mentee.username + "', '" + mentee.email +
                     "', '" + mentee.password + "', '" + conds +
                     "', " + String.valueOf(mentee.age) + ");";
        System.out.println(sql);
        try (Connection conn = this.menteeDB.open()){
            conn.createQuery(sql).executeUpdate();
        } catch (Exception ex){
            System.out.println("Exception following:");
            System.out.println(ex);
        }
        return mentee;
    }

    public Mentor createMentor(String body) throws InvalidPersonException {
        Mentor mentor = new Gson().fromJson(body, Mentor.class);
        if (!mentor.isValid(this.mentorDB, this.menteeDB)) {
            throw new InvalidPersonException();
        }
        String conds = new Gson().toJson(mentor.conditions);
        int approved = 0;
        if (mentor.approved) {
            approved = 1;
        } else {
            approved = 0;
        }
        String sql = "INSERT INTO MENTORS (USERNAME, EMAIL, PASSWORD, CONDITIONS, AGE, " +
                "FULLNAME, GENDER, MAX, ACTIVE, PAST, APPROVED) " +
                "VALUES ('" + mentor.username + "', '" + mentor.email +
                "', '" + mentor.password + "', '" + conds +
                "', " + String.valueOf(mentor.age) + ", '" + mentor.fullName +
                "', '" + mentor.gender + "', " + String.valueOf(mentor.max) + ", " +
                mentor.active + ", " + mentor.past + ", " + approved + ");";
        System.out.println(sql);
        try (Connection conn = this.mentorDB.open()){
            conn.createQuery(sql).executeUpdate();
        } catch (Exception ex){
            System.out.println("Exception following:");
            System.out.println(ex);
        }
        return mentor;
    }

    public Mentor getMentor(String username) throws InvalidPersonException {
        Mentor mentor = this.selectMentor(username);
        if (mentor == null) {
            throw new InvalidPersonException();
        } else {
            return mentor;
        }
    }

    public Mentee getMentee(String username) throws InvalidPersonException {
        Mentee mentee = this.selectMentee(username);
        if (mentee == null) {
            throw new InvalidPersonException();
        } else {
            return mentee;
        }
    }

    public void makePair(String mentorname, String body)
            throws InvalidPersonException, AddMenteeException {
        Mentor mentor = this.selectMentor(mentorname);
        Mentee tempee = new Gson().fromJson(body, Mentee.class);
        Mentee mentee = this.selectMentee(tempee.username);
        if (mentor == null || mentee == null) {
            throw new InvalidPersonException();
        }
        if (!mentor.addMentee(mentee)) {
            throw new AddMenteeException();
        }
        mentee.mentor = mentor;
    }

    public void terminateMentor(String mentorname, String body)
            throws InvalidPersonException, TerminatePairException {
        Mentor mentor = this.selectMentor(mentorname);
        Mentee tempee = new Gson().fromJson(body, Mentee.class);
        Mentee mentee = this.selectMentee(tempee.username);
        if (mentor == null || mentee == null) {
            throw new InvalidPersonException();
        }
        if (!mentor.removeMentee(mentee)) {
            throw new TerminatePairException();
        } else {
            mentee.removeMentor();
        }
    }

    public void approveMentor(String mentorname) {
        Mentor mentor = this.selectMentor(mentorname);
        mentor.validate();
    }

    private List<Mentor> getAllMentors() {
        try (Connection conn = mentorDB.open()){
            String sql = "SELECT * FROM MENTORS;";
            return conn.createQuery(sql).executeAndFetch(Mentor.class);
        } catch (Exception ex){
            System.out.println("Exception following:");
            System.out.println(ex);
            return null;
        }
    }

    public Mentor[] findMentors(String condname) {
        ArrayList<Mentor> matches = new ArrayList<Mentor>();
        List<Mentor> mentors = this.getAllMentors();
        for (int a = 0; a < mentors.size(); a++) {
            if (mentors.get(a).hasCondition(condname)) {
                matches.add(mentors.get(a));
            }
        }
        Object[] matchArray = matches.toArray();
        return Arrays.copyOf(matchArray, matchArray.length, Mentor[].class);
    }


}
