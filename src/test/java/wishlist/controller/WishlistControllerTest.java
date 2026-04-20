package wishlist.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import wishlist.controller.AuthController;
import wishlist.controller.PublicViewController;
import wishlist.model.Member;
import wishlist.model.Wishlist;
import wishlist.service.ItemService;
import wishlist.service.MemberService;
import wishlist.service.WishlistService;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WishlistController.class)
class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WishlistService wishlistservice;
    @MockitoBean private MemberService memberservice;
    @MockitoBean private ItemService itemservice;


    @Test
        //("/wishlist)"
    void shouldDisplayAllWishlistsForLoggedInUser() throws Exception {

        mockMvc.perform(get("/wishlist")
                        .sessionAttr("memberId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("/wishlist/list"));

    }
}