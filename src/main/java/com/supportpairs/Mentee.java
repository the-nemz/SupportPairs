package com.supportpairs;

/**
 * Created by Isaac on 9/23/2016.
 */
public class Mentee extends Person {

    public transient Mentor mentor;

    public Mentee(String email, String username, String password, int age,
                  Condition condition1, Condition condition2, Condition condition3) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.age = age;
        this.condition1 = condition1;
        this.condition2 = condition2;
        this.condition3 = condition3;
    }

    public void removeMentor() {
        this.mentor = null;
    }

}
