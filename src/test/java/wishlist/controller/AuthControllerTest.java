package wishlist.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import wishlist.model.Member;
import wishlist.service.MemberService;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MemberService service;

    @Test
        //"(/login)"
    void showLoginForm_shouldDisplayLoginForm() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("/member/member-login"));
    }

    @Test
        //("login)"
    void loginFormHandler_shouldLoginAndRedirectToWishlist() throws Exception {

        Member member = new Member();
        member.setId(6);
        member.setUsername("Shaz");

        when(service.login("Shaz", "1234")).thenReturn(member);

        mockMvc.perform(post("/auth/login")
                        .param("username", "Shaz")
                        .param("password", "1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));

        verify(service).login("Shaz", "1234");
    }

    @Test
    void logout_ShouldEndUserSession() throws Exception {
        mockMvc.perform(get("/auth/logout")
                        .sessionAttr("memberId", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(result ->
                        assertNull(result.getRequest().getSession(false))
                );
    }
