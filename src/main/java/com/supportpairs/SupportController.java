package com.supportpairs;

import java.util.Collections;
import static spark.Spark.*;

/**
 * Handles all routing for the Dots game.
 * Based on the to do app provided to us at
 * https://github.com/jhu-oose/todo
 * Created by Isaac on 9/18/2016.
 */
public class SupportController {

    private static final String API_CONTEXT = "/supportpairs/api";

    private SupportService service;

    public SupportController(SupportService supportService) {
        this.service = supportService;
        System.out.println("controller setup");
        setupEndpoints();
    }

    /**
     * Handles all of the routing.
     */
    private void setupEndpoints() {
        post(API_CONTEXT + "/mentee", "application/json", (request, response) -> {
            System.out.println("mentee");
            try {
                response.status(201);
                return this.service.createMentee(request.body());
            } catch (InvalidPersonException ex) {
                System.out.println(ex);
                response.status(404);
            } catch(Exception ex) {
                //unexpected exception
                System.out.println(ex);
                response.status(500);
            }
            return Collections.EMPTY_MAP;
        }, new JsonTransformer());

        post(API_CONTEXT + "/mentor", "application/json", (request, response) -> {
            System.out.println("mentor");
            try {
                response.status(201);
                return this.service.createMentor(request.body());
            } catch (InvalidPersonException ex) {
                System.out.println(ex);
                response.status(404);
            } catch(Exception ex) {
                //unexpected exception
                System.out.println(ex);
                response.status(500);
            }
            return Collections.EMPTY_MAP;
        }, new JsonTransformer());

        get(API_CONTEXT + "/mentor/:username", "application/json", (request, response) -> {
            try {
                String username = request.params(":username");
                response.status(201);
                return this.service.getMentor(username);
            } catch (InvalidPersonException ex) {
                System.out.println(ex);
                response.status(404);
            } catch(Exception ex) {
                //unexpected exception
                System.out.println(ex);
                response.status(500);
            }
            return Collections.EMPTY_MAP;
        }, new JsonTransformer());

        get(API_CONTEXT + "/mentee/:username", "application/json", (request, response) -> {
            try {
                String username = request.params(":username");
                response.status(201);
                return this.service.getMentee(username);
            } catch (InvalidPersonException ex) {
                System.out.println(ex);
                response.status(404);
            } catch(Exception ex) {
                //unexpected exception
                System.out.println(ex);
                response.status(500);
            }
            return Collections.EMPTY_MAP;
        }, new JsonTransformer());

        post(API_CONTEXT + "/mentor/:username/pair", "application/json", (request, response) -> {
            System.out.println("make pair");
            try {
                String mentorname = request.params(":username");
                response.status(201);
                this.service.makePair(mentorname, request.body());
            } catch (InvalidPersonException ex) {
                System.out.println(ex);
                response.status(404);
            } catch (AddMenteeException ex) {
                System.out.println(ex);
                response.status(400);
            } catch(Exception ex) {
                //unexpected exception
                System.out.println(ex);
                response.status(500);
            }
            return Collections.EMPTY_MAP;
        }, new JsonTransformer());

        put(API_CONTEXT + "/mentor/:username/approve", "application/json", (request, response) -> {
            try {
                String mentorname = request.params(":username");
                response.status(201);
                this.service.approveMentor(mentorname);
            } catch(Exception ex) {
                //unexpected exception
                response.status(500);
            }
            return Collections.EMPTY_MAP;
        }, new JsonTransformer());

        post(API_CONTEXT + "/mentor/:username/terminate", "application/json", (request, response) -> {
            try {
                String mentorname = request.params(":username");
                response.status(201);
                this.service.terminateMentor(mentorname, request.body());
            } catch (InvalidPersonException ex) {
                System.out.println(ex);
                response.status(404);
            } catch (TerminatePairException ex) {
                System.out.println(ex);
                response.status(400);
            } catch(Exception ex) {
                //unexpected exception
                System.out.println(ex);
                response.status(500);
            }
            return Collections.EMPTY_MAP;
        }, new JsonTransformer());

        get(API_CONTEXT + "/mentors/:condition", "application/json", (request, response) -> {
            try {
                String condname = request.params(":condition");
                response.status(201);
                return this.service.findMentors(condname);
            } catch(Exception ex) {
                //unexpected exception
                response.status(500);
            }
            return Collections.EMPTY_MAP;
        }, new JsonTransformer());

    }
}
