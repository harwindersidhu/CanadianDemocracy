package com.example.assignment2_canadiandemocracy;

import java.io.Serializable;
import java.util.HashMap;

public class RepresentativeSet implements Serializable {
    private String name;
    private HashMap<String, String> related;

    public String getName() {
        return name;
    }

    public HashMap<String, String> getRelated() {
        return related;
    }
}
