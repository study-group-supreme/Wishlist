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

import java.security.Provider;
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
    @MockitoBean
    private MemberService memberservice;
    @MockitoBean
    private ItemService itemservice;


    @Test
        //("/wishlist)"
    void shouldDisplayAllWishlistsForLoggedInUser() throws Exception {

        mockMvc.perform(get("/wishlist")
                        .sessionAttr("memberId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("/wishlist/list"));

    }

    @Test
    void shouldShowWishlistByWishlistId() throws Exception {
        Wishlist newWishlist = new Wishlist(5, null, "Min ønskeliste", "test", true, 4);
        when(wishlistservice.getById(5)).thenReturn(newWishlist);
        when(wishlistservice.getItemsFromWishlistByWishlistId(5)).thenReturn(List.of());

        mockMvc.perform(get("/wishlist/5"))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist/details"))
                .andExpect(model().attribute("wishlist", newWishlist));

        verify(wishlistservice).getById(5);
        verify(wishlistservice).getItemsFromWishlistByWishlistId(5);

    }
    @Test
    //("/new")
    void shouldShowCreateWishlistForm() throws Exception {
        mockMvc.perform(get("/wishlist/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist/create-wishlist"))
                .andExpect(model().attributeExists("wishlist"));
    }
}