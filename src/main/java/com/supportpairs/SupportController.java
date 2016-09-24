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
        setupEndpoints();
    }

    /**
     * Handles all of the routing.
     */
    private void setupEndpoints() {
        post(API_CONTEXT + "/mentee", "application/json", (request, response) -> {
            try {
                response.status(201);
                this.service.createMentee(request.body());
                response.status(201);
            } catch(Exception ex) {
                //unexpected exception
                response.status(500);
            }
            return Collections.EMPTY_MAP;
        }, new JsonTransformer());

        post(API_CONTEXT + "/mentor", "application/json", (request, response) -> {
            try {
                response.status(201);
                this.service.createMentor(request.body());
                response.status(201);
            } catch(Exception ex) {
                //unexpected exception
                response.status(500);
            }
            return Collections.EMPTY_MAP;
        }, new JsonTransformer());

        post(API_CONTEXT + "/mentor/:username", "application/json", (request, response) -> {
            try {
                String mentorname = request.params(":username");
                response.status(201);
                this.service.makePair(mentorname, request.body());
                response.status(201);
            } catch(Exception ex) {
                //unexpected exception
                response.status(500);
            }
            return Collections.EMPTY_MAP;
        }, new JsonTransformer());

    }
}
