package com.supportpairs;

import com.google.gson.Gson;
import java.util.HashMap;

/**
 * Created by Isaac on 9/23/2016.
 */
public class SupportService {

    public HashMap<String, Mentor> mentors;
    public HashMap<String, Mentee> mentees;


    public SupportService() {

    }

    public void createMentee(String body) throws InvalidPersonException {
        Mentee mentee = new Gson().fromJson(body, Mentee.class);
        if (!mentee.isValid(mentors, mentees)) {
            throw new InvalidPersonException();
        }
        this.mentees.put(mentee.username, mentee);
    }

    public void createMentor(String body) throws InvalidPersonException {
        Mentor mentor = new Gson().fromJson(body, Mentor.class);
        if (!mentor.isValid(mentors, mentees)) {
            throw new InvalidPersonException();
        }
        this.mentors.put(mentor.username, mentor);
    }

    public void makePair(String mentorname, String body)
            throws AddMenteeException{
        Mentor mentor = this.mentors.get(mentorname);
        Mentee tempee = new Gson().fromJson(body, Mentee.class);
        Mentee mentee = this.mentees.get(tempee.username);
        if (!mentor.addMentee(mentee)) {
            throw new AddMenteeException();
        }
        mentee.mentor = mentor;
    }


}
