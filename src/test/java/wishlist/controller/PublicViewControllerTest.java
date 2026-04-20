package wishlist.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import wishlist.service.MemberService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PublicViewController.class)
class PublicViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MemberService service;

    @Test
    void shouldDisplayPublicHomePage() throws Exception {
        mockMvc.perform(get(""))
                .andExpect(view().name("public/public-homepage"));
    }
}