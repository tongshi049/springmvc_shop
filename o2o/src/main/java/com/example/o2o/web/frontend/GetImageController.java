package com.example.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//@Controller
//@RequestMapping("/frontend")
public class GetImageController {

    //@RequestMapping(value = "/getImage/{img}")
    //@ResponseBody
    public byte[] getImage(@PathVariable String img)  {
        Path path = Paths.get("C:/codes/img" + img);
        byte[] data = new byte[0];
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
