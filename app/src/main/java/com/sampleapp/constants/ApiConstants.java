package com.sampleapp.constants;

/**
 * contains all the constants related to API interactions
 */
public interface ApiConstants {
    int STATUS_SUCCESS = 200;
    String BASE_URL = "https://itunes.apple.com/";
    String CONTROLLER = "";
    String LOGIN = CONTROLLER + "/us/rss/topalbums/limit=25/json";
    String TOP_LIST = CONTROLLER + "/us/rss/topalbums/limit=25/json";
}
