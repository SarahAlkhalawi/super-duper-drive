package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;

@Controller
@RequestMapping("/badURLPage")
public class BadURLPage implements ErrorController {

    @RequestMapping("/badURLPage")
    public String theErrorHandeler(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        int code = Integer.parseInt(status.toString());

        if (code == HttpStatus.INTERNAL_SERVER_ERROR.value())
            return "500";
        else if (code == HttpStatus.NOT_FOUND.value()){
            return "404";
        }
        else if (code == HttpStatus.FORBIDDEN.value())
            return "403";

        return "Error";
    }
    @Override
    public String getErrorPath() {
        return null;
    }
}
