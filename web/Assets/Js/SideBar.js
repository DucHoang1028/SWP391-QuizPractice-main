let sidebar = document.querySelector(".marketing-sidebar");
let closeBtn = document.querySelector("#marketing-btn");


closeBtn.addEventListener("click", () => {
    sidebar.classList.toggle("open");
});

