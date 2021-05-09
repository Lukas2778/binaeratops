/*
 *
 *  * (c) 2015 - 2021 ENisco GmbH & Co. KG
 *  
 */

package de.dhbw.binaeratops.controller;

import org.hibernate.loader.custom.Return;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Indexcontroller
{



    @RequestMapping("META-INF/resources/images/favicon.ico") public
    String favicon() {
        return "forward:/favicon.ico";
    }


}
