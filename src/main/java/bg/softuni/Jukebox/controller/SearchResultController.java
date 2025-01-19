//package bg.softuni.Jukebox.controller;
//
//import bg.softuni.Jukebox.model.dto.SearchRequest;
//import bg.softuni.Jukebox.model.entity.Album;
//import bg.softuni.Jukebox.model.entity.Band;
//import bg.softuni.Jukebox.model.entity.Song;
//import bg.softuni.Jukebox.service.AlbumService;
//import bg.softuni.Jukebox.service.BandService;
//import bg.softuni.Jukebox.service.GenreService;
//import bg.softuni.Jukebox.service.SongService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Controller
//public class SearchResultController {
//
//    private final GenreService genreService;
//    private final BandService bandService;
//    private final AlbumService albumService;
//    private final SongService songService;
//
//    public SearchResultController(GenreService genreService, BandService bandService, AlbumService albumService, SongService songService) {
//        this.genreService = genreService;
//        this.bandService = bandService;
//        this.albumService = albumService;
//        this.songService = songService;
//    }
//
//    @ModelAttribute("searchRequest")
//    public SearchRequest searchRequest() {
//        return new SearchRequest();
//    }
//
//    @GetMapping("/genre")
//    public String genre(SearchRequest searchRequest,
//                        Model model) {
//        List<Band> bandByGenre = genreService.findBandByGenre(searchRequest.getTitle());
//        model.addAttribute("bands", bandByGenre);
//        return "redirect:/bands";
//    }
//
//    @GetMapping("/bands")
//    public String bands(SearchRequest searchRequest,
//                             Model model) {
//        List<Album> albumsByBandName = bandService.findAlbumsByBandName(searchRequest.getTitle());
//        model.addAttribute("albums", albumsByBandName);
//        return "redirect:/albums";
//    }
//
//    @GetMapping("/album")
//    public String albums(SearchRequest searchRequest,
//                         Model model) {
//        List<Album> albumByTitle = albumService.findAlbumByTitle(searchRequest.getTitle());
//        model.addAttribute("albums", albumByTitle);
//        return "redirect:/albums";
//    }
//
//    @GetMapping("/song")
//    public String songs(SearchRequest searchRequest,
//                        Model model) {
//        List<Song> songByTitle = songService.findSongByTitle(searchRequest.getTitle());
//        model.addAttribute("songs", songByTitle);
//        return "redirect:/songs";
//    }
//
//}
