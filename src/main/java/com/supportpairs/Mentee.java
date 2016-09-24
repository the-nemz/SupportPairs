package com.supportpairs;

/**
 * Created by Isaac on 9/23/2016.
 */
public class Mentee extends Person {

    public transient Mentor mentor;

    public Mentee(String email, String username, String password, int age,
                  Condition[] conditions) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.age = age;
        this.conditions = conditions;
    }

    public void removeMentor() {
        this.mentor = null;
    }

}
