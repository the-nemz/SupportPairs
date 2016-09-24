package com.supportpairs;

/**
 * Created by Isaac on 9/23/2016.
 */
public class Mentor extends Person {

    public String fullName;
    public String gender;
    public int max;
    public int active;
    public int past;
    public Mentee[] mentees;
    public boolean approved;

    public Mentor(String email, String username, String password, int age,
                  Condition[] conditions, String fullName, String gender,
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

    public void validate() {
        this.approved = true;
    }

    public boolean addMentee(Mentee mentee) {
        if ((this.max - this.active) > 0) {
            for (int a = 0; a < this.mentees.length; a++) {
                if (this.mentees[a] == null) {
                    this.mentees[a] = mentee;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasCondition(String condname) {
        for (int a = 0; a < this.conditions.length; a++) {
            if (this.conditions[a].name.equals(condname)) {
                return true;
            }
        }
        return false;
    }

    public boolean removeMentee(Mentee mentee) {
        boolean removed = false;
        for (int a = 0; a < this.mentees.length; a++) {
            if (removed) {
                this.mentees[a - 1] = this.mentees[a];
                this.mentees[a] = null;
            } else if (this.mentees[a].equals(mentee)) {
                this.mentees[a] = null;
            }
        }
        return removed;
    }

}
