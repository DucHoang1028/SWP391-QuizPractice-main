document.addEventListener('DOMContentLoaded', () => {

    let sidebar = document.querySelector(".marketing-sidebar");
    let closeBtn = document.querySelector("#marketing-btn");
    let main = document.querySelector(".main");

    closeBtn.addEventListener("click", () => {
        main.classList.toggle("main-open");
    });
});
