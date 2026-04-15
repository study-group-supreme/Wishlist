package wishlist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wishlist.model.Member;
import wishlist.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService service;

    public MemberController(MemberService service){
        this.service = service;
    }

    @GetMapping("/register")
    public String showMemberRegistrationForm(Model model){
        model.addAttribute("member", new Member());
        return "member-registration";
    }

    @PostMapping("/register")
    public String registrationFormHandler(@ModelAttribute Member member, RedirectAttributes redirectAttributes){
        Member registeredMember = service.create(member);
        redirectAttributes.addFlashAttribute("member", registeredMember);
        return "redirect:/auth/login";

    }

}
