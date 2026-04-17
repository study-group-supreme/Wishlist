package wishlist.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/save")
    public String registrationFormHandler(@ModelAttribute Member member, RedirectAttributes redirectAttributes){
        Member registeredMember = service.create(member);
        redirectAttributes.addFlashAttribute("member", registeredMember);
        return "redirect:/auth/login";

    }

    @GetMapping("/edit")
    public String showEditForm(Model model, HttpSession session){
        Member member = service.getById((Integer) session.getAttribute("memberId"));
        model.addAttribute("member", member);
        return "/member/member-edit";
    }

    @PostMapping("/edit")
    public String editFormHandler(@ModelAttribute Member member){
        service.update(member);
        return "redirect:/wishlist";
    }

}
