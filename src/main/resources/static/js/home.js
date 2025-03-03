function search() {

    console.log("Search function triggered");

    const query = document.getElementById("searchQuery").value;
    const category = document.getElementById("category").value;

    if (!query) {
        alert("Please enter a search query!");
        return;
    }

    fetch(`/search?query=${encodeURIComponent(query)}&category=${encodeURIComponent(category)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Error fetching search results");
            }
            return response.json();
        })
        .then(data => {
            console.log(data)
            console.log("Data received:", data);
            const resultsList = document.getElementById("results");
            resultsList.innerHTML = "";

            if (data.length === 0) {
                console.log("Executing empty result condition...");
                resultsList.innerHTML = "<li>No results found</li>";
                return;
            }

            data.forEach(item => {
                const li = document.createElement("li");
                li.innerHTML = `<a href="/details/${item.id}">${item.name || item.title || item}</a>`;
                resultsList.appendChild(li);

            });
        })
        .catch(error => console.error("Error fetching search results:", error));
}