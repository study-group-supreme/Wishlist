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

import static org.mockito.Mockito.*;
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

    @Test
        //("/save)
    void shouldSaveNewWishlistAndRedirectToWishlist() throws Exception {

        mockMvc.perform(post("/wishlist/save")
                        .sessionAttr("memberId", 4)
                        .param("title", "Tester123")
                        .param("description", "Test")
                        .param("public", "false"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));

        verify(wishlistservice).createNewWishlist(any(Wishlist.class));
    }

    @Test
        //("/{wishlistId}/delete")

    void shouldDeleteWishlistByWishlistId() throws Exception {
        Wishlist wishlist = new Wishlist();
        wishlist.setId(5);
        wishlist.setTitle("Test");
        wishlist.setDescription("Test");
        wishlist.setOwner_id(4);
        wishlist.setPublic(true);
        wishlist.setItems(null);

        when(wishlistservice.getById(5)).thenReturn(wishlist);

        mockMvc.perform(post("/wishlist/5/delete")
                        .sessionAttr("memberId", 4)
                        .param("Titel", "Test")
                        .param("Description", "Test")
                        .param("public", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));

        verify(wishlistservice).deleteWishlistById(5);

    }

    @Test
    void shouldEditWishlistByWishlistId() throws Exception {

        Wishlist wishlist = new Wishlist();
        wishlist.setId(1);
        wishlist.setOwner_id(1);

        when(wishlistservice.getById(1)).thenReturn(wishlist);

        mockMvc.perform(get("/wishlist/1/edit")
                        .sessionAttr("memberId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist/edit-wishlist"))
                .andExpect(model().attribute("wishlist", wishlist));

        verify(wishlistservice).getById(1);
    }
}