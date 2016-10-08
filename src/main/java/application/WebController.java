package application;


import model.MarkovChain;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@RestController
public class WebController {

    MarkovChain hillary = new MarkovChain("hillary.txt");
    MarkovChain trump = new MarkovChain("donald.txt");

    @RequestMapping("/")
    public String index() throws IOException{
        hillary.buildModel();
        trump.buildModel();
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/trump")
    public String generateTrumpArgument()  {
        return trump.print();
    }



}
