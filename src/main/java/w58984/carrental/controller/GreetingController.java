package w58984.carrental.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {


    @RequestMapping( value ="/greeting" , method = RequestMethod.GET)
     public String greeting (){
         String greeting = "Hello World!";
        return greeting;
     }
}
