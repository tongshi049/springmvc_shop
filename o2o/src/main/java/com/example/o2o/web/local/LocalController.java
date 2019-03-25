package com.example.o2o.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/local")
public class LocalController {
    @RequestMapping(value="/createaccount",method = RequestMethod.GET)
    private String createAccount(){
        return "local/createaccount";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    private String login(){
        return "local/login";
    }

    @RequestMapping(value = "/changepsw",method = RequestMethod.GET)
    private String changepsw(){
        return "local/changepsw";
    }



}
