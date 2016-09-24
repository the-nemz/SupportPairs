package com.supportpairs;

import java.util.HashMap;

/**
 * Created by Isaac on 9/23/2016.
 */
public class Person {

    public String email;
    public String username;
    public String password;
    public int age;
    public String[] conditions;

    public boolean isValid(HashMap<String, Mentor> mentors,
                           HashMap<String, Mentee> mentees) {
        System.out.println("checker: " + this.email);
        if (mentees.containsKey(this.email) || mentors.containsKey(this.email)) {
            return false;
        }
        if (mentees.containsKey(this.username) || mentors.containsKey(this.username)) {
            return false;
        }
        System.out.println("will return true");
        return true;
    }

}
