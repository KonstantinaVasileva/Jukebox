function toggleSidebar() {
    const sidebar = document.getElementById("dashboard");
    const toggleBtn = sidebar.querySelector(".toggle-btn span");

    if (sidebar.classList.contains("active")) {
        sidebar.classList.remove("active");
        toggleBtn.innerHTML = "&#9654;"; // Дясна стрелка
    } else {
        sidebar.classList.add("active");
        toggleBtn.innerHTML = "&#9664;"; // Лява стрелка
    }
}
