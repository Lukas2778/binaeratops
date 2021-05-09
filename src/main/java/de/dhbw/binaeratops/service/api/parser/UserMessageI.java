package de.dhbw.binaeratops.service.api.parser;

import java.util.List;

public interface UserMessageI {
    List<Object> getParams();
    String getKey();
}
