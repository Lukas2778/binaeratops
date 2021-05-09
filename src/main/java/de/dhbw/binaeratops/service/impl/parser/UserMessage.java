package de.dhbw.binaeratops.service.impl.parser;

import de.dhbw.binaeratops.service.api.parser.UserMessageI;

import java.util.ArrayList;
import java.util.List;

public class UserMessage implements UserMessageI {
    private final List<String> params = new ArrayList<>();
    private final String key;

    public UserMessage(String AKey, String... AObject) {
        key= AKey;
        for (String s : AObject) {
            params.add(s);
        }
    }

    public List<String> getParams() {
        return params;
    }

    public String getKey() {
        return key;
    }
}