package com.supportpairs;

/**
 * Created by Isaac on 9/23/2016.
 */
public class Mentor extends Person {

    public String fullName;
    public char gender;
    public int max;
    public int active;
    public int past;
    public Mentee[] mentees;
    public boolean approved;

    public Mentor(String email, String username, String password, int age,
                  String[] conditions, String fullName, char gender,
                  int max) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.age = age;
        this.conditions = conditions;
        this.fullName = fullName;
        this.gender = gender;
        this.max = max;
        this.mentees = new Mentee[this.max];
        this.active = 0;
        this.past = 0;
        this.approved = false;
    }

    public boolean addMentee(Mentee mentee) {
        if ((this.max - this.active) > 0) {
            for (int a = 0; a < mentees.length; a++) {
                if (this.mentees[a] == null) {
                    this.mentees[a] = mentee;
                    return true;
                }
            }
        }
        return false;
    }
}
