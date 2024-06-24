$(document).ready(function () {

    $("#review-btn").click(function (event) {
        $("#popup-container").css("display", "flex");
        $("#close_button").click(function (event) {
            $("#popup-container").css("display", "none");
            $("#close_button").off('click');
        });
    });
    $("#explain-btn").click(function (event) {
        $("#explain-container").css("display", "flex");
        $("#close_button2").click(function (event) {
            $("#explain-container").css("display", "none");
            $("#close_button2").off('click');
        });
    });
});
function menuToggle() {
    const toggleMenu = document.querySelector(".header__right-menu");
    if (toggleMenu.style.display === "block") {
        toggleMenu.style.display = "none";
    } else {
        toggleMenu.style.display = "block";
    }
}

$(document).ready(function () {
    $("#register-button").click(function (event) {
        $("#main-component-regi").css("display", "flex");
        $("#close_regibutton").click(function (event) {
            $("#main-component-regi").css("display", "none");
            $("#close_regibutton").off('click');
        });
    })
})

$(document).ready(function () {
    $("#profile-button").click(function (event) {
        $("#main-component-profile").css("display", "flex");
        $("#close_button-profile").click(function (event) {
            $("#main-component-profile").css("display", "none");
            $("#close_button-profile").off('click');
        });
    })
})

$(document).ready(function () {
    $("#changepassword-button").click(function (event) {
        $("#main-component-changepassword").css("display", "flex");
        $("#close_changebutton").click(function (event) {
            $("#main-component-changepassword").css("display", "none");
            $("#close_changebutton").off('click');
        });
    })
})
