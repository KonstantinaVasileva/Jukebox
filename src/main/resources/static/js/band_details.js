function toggleSongs(element) {

    const songsList = element.nextElementSibling;

    if (songsList.style.display === "none" || songsList.style.display === "") {
        songsList.style.display = "block";
    } else {
        songsList.style.display = "none";
    }
}