package bg.softuni.Jukebox.web;

import bg.softuni.Jukebox.album.AlbumService;
import bg.softuni.Jukebox.band.Band;
import bg.softuni.Jukebox.band.BandService;
import bg.softuni.Jukebox.security.AuthenticationMetadata;
import bg.softuni.Jukebox.user.Role;
import bg.softuni.Jukebox.web.dto.NewAlbum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArtistController.class)
public class ArtistControllerApiTest {
    @MockitoBean
    private AlbumService albumService;

    @MockitoBean
    private BandService bandService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAddAlbumEndpoint_returnsAddAlbum() throws Exception {
        Band band = new Band();
        band.setId(UUID.randomUUID());

        when(bandService.findById(any(UUID.class))).thenReturn(band);

        AuthenticationMetadata principal = new AuthenticationMetadata(UUID.randomUUID(), "username", "password", Role.ARTIST, false);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/artist/{id}/add/album", band.getId())
                .with(user(principal))
                .with(csrf());
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("newAlbum"))
                .andExpect(model().attributeExists("band"))
                .andExpect(view().name("album-add"));
    }

    @Test
    void postAddAlbumEndpoint_happyCase() throws Exception {
        Band band = new Band();
        band.setId(UUID.randomUUID());

        AuthenticationMetadata principal = new AuthenticationMetadata(UUID.randomUUID(), "username", "password", Role.ARTIST, false);

        when(bandService.findById(any(UUID.class))).thenReturn(band);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/artist/{id}/add/album", band.getId())
                .formField("title", "title")
                .formField("releaseDate", "2020")
                .formField("description", "description")
                .with(user(principal))
                .with(csrf());
        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/details/" + band.getId()));
        verify(albumService, times(1)).addNewAlbum(any(NewAlbum.class));
    }

    @Test
    void postRequestAddAlbumEndpointWithInvalidParameters_returnAddAlbumPage() throws Exception {
        Band band = new Band();
        band.setId(UUID.randomUUID());

        AuthenticationMetadata principal = new AuthenticationMetadata(UUID.randomUUID(), "username", "password", Role.ARTIST, false);

        when(bandService.findById(any(UUID.class))).thenReturn(band);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/artist/{id}/add/album", band.getId())
                .formField("title", "")
                .formField("releaseDate", "")
                .formField("description", "")
                .with(user(principal))
                .with(csrf());
        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/artist/" + band.getId() + "/add/album"));
        verify(albumService, times(0)).addNewAlbum(any(NewAlbum.class));
    }

}
