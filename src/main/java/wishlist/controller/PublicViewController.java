package wishlist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicViewController {

    @GetMapping
    public String showHomepage(){
        return "public/public-homepage";
    }
}
