package wishlist.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wishlist.exception.DuplicateMemberException;
import wishlist.model.Member;
import wishlist.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @GetMapping("/register")
    public String showMemberRegistrationForm(Model model) {
        model.addAttribute("member", new Member());
        return "/member/member-registration";
    }

    @PostMapping("/save")
    public String registrationFormHandler(@ModelAttribute Member member, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            Member registeredMember = service.create(member);
            session.setAttribute("memberId", registeredMember.getId());
            redirectAttributes.addFlashAttribute("member", registeredMember);
            return "redirect:wishlist/list";
        } catch (DuplicateMemberException e) {
            model.addAttribute("member", member);
            model.addAttribute("errorMessage", e.getMessage());
            return "/member/member-registration";
        }
    }

    @GetMapping("/edit")
    public String showEditForm(Model model, HttpSession session) {
        Member member = service.getById((Integer) session.getAttribute("memberId"));
        model.addAttribute("member", member);
        return "/member/member-edit";
    }

    @PostMapping("/edit")
    public String editFormHandler(@ModelAttribute Member member) {
        service.update(member);
        return "redirect:/wishlist";
    }

}
