package com.example.oarest.oa;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/app",headers ="Content-type=application/json;charset=UTF-8" )
public class RBody {
    @PostMapping(value = "/dd")
    @ResponseBody
    public String strBoday(@RequestBody String body)
    {
       System.out.println(body);
       return  "body 参数传入成功！";
    }
}
