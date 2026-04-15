package wishlist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wishlist.model.Member;
import wishlist.service.MemberService;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final MemberService memberService;

    public AuthController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }
}
