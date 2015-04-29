package com.mycompany.controller.seo;

import org.sparkcommerce.cms.web.controller.SparkRobotsController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Controller to retrieve robots.txt file.   
 * 
 * @author bpolster
 * @see SparkRobotsController
 */
@Controller
public class RobotsController extends SparkRobotsController {

    /**
     * Retrieves the robots.txt file     
     *  
     * @param request
     * @param response
     * @param model
     * @param fileName
     * @return
     */
    @RequestMapping(value = { "/robots.txt" })
    @ResponseBody
    public String getRobotsFile(HttpServletRequest request, HttpServletResponse response) {
        return super.getRobotsFile(request, response);
    }
}
