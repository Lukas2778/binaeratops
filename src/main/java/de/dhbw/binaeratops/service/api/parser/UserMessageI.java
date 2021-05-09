package de.dhbw.binaeratops.service.api.parser;

import java.util.List;

public interface UserMessageI {
    List<String> getParams();
    String getKey();
}
