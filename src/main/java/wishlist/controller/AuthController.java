package wishlist.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wishlist.model.Member;
import wishlist.service.MemberService;

@Controller
@RequestMapping("/auth")
public class  AuthController {
    private final MemberService memberService;

    public AuthController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "/member/member-login";
    }

    @PostMapping("/login")
    public String loginFormHandler(@RequestParam ("username") String username, @RequestParam ("password") String password,
                                   HttpSession session, Model model){
        Member member = memberService.login(username, password);
        if(member != null){
            session.setAttribute("memberId", member.getId());
            return "redirect:/wishlist";
        } else{
            model.addAttribute("wrongCredentials", true);
            return "member/member-login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}
