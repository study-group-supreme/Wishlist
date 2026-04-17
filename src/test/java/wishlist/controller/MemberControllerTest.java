package wishlist.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import wishlist.model.Member;
import wishlist.service.MemberService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MemberService service;

    @Test
    void shouldDisplayMemberRegistrationForm() throws Exception {
        mockMvc.perform(get("/member/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("member-registration"))
                .andExpect(model().attributeExists("member"));
    }
@Test
    void shouldRegisterMemberAndRedirectToLogin() throws Exception {

        Member savedMember = new Member();
        savedMember.setUsername("mads");
        savedMember.setEmail("mads@test.dk");

        when(service.create(any(Member.class))).thenReturn(savedMember);

        mockMvc.perform(post("/member/save")
                        .param("username", "mads")
                        .param("email", "mads@test.dk")
                        .param("password", "secret"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/auth/login"))
                .andExpect(flash().attributeExists("member"));

        verify(service).create(any(Member.class));
    }
    @Test
    void shouldDisplayEditForm_whenMemberIsInSession() throws Exception{
        Member member = new Member();
        member.setId(1);
        member.setUsername("shaz");

        when(service.getById(1)).thenReturn(member);

        mockMvc.perform(get("/member/edit").sessionAttr("memberId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("/member/member-edit"));
        verify(service).getById(1);
        }
        @Test
    void shouldUpdateMemberAndRedirectToWishlist() throws Exception {
        mockMvc.perform(post("/member/edit")
                .param("id", "1")
                .param("username", "shaz")
                .param("email", "shaz@test.dk"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));
        verify(service).update(any(Member.class));

        }
    }


