package de.dhbw.binaeratops.service.impl.parser;

import de.dhbw.binaeratops.service.api.parser.UserMessageI;

import java.util.ArrayList;
import java.util.List;

public class UserMessage implements UserMessageI {
    private final List<Object> params = new ArrayList<>();
    private final String key;

    public UserMessage(String AKey, Object... AObject) {
        key= AKey;
        params.add(AObject);
    }

    public List<Object> getParams() {
        return params;
    }

    public String getKey() {
        return key;
    }
}