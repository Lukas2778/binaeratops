/*
 *
 *  * (c) 2015 - 2021 ENisco GmbH & Co. KG
 *  
 */

package de.dhbw.binaeratops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO JAVADOC -> Was ist der Sinn dieser Klasse?
 */
@Controller
public class Indexcontroller
{


    /**
     * TODO JAVADOC
     * @return
     */
    @RequestMapping("META-INF/resources/images/favicon.ico") public
    String favicon() {
        return "forward:/favicon.ico";
    }


}
