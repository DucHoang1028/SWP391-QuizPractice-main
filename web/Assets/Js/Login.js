
function openLoginPopup() {
    $("#main-component").css("display", "flex");
    $("#main-component-regi").css("display", "none");
    //hide popup when user click close button
    $("#close_button").click(function (event)
    {
        $("#error__message").html("<p></p>");
        $("#main-component").css("display", "none");
        $("#close_button").off('click');
    });
}

$(document).ready(function () {
    $("#loginForm").on("submit", function (event) {
        // prevent default 
        event.preventDefault();
        // get value from from
        var username = $("#email").val();
        var password = $("#password").val();
        var remember = $("#remember").prop('checked');
        console.log(remember);
        // Create post query to servelet ( like api)
        $("#email").val("");
        $("#password").val("");
        $.ajax({
            /// handle information to request
            type: "POST",
            url: "login",
            data: {email: username, password: password, remember: remember},
            dataType: "json",
            success: function (response) {
                if (response.success) {
                    /// reload all page
                    const url = window.location.protocol + "//" + window.location.host + window.location.pathname;
                    window.history.replaceState(null, null, url);
                    console.log("<p>" + response.message + "</p>");

                    window.location.href = response.url;
                } else {
                    // show message
                    console.log($("#error__message"));
                    $("#error__message").html("<p>" + response.message + "</p>");

                }
            },
            error: function (xhr, status, error) {
                console.log("An error occurred: " + xhr.responseText);
            }
        });
    });

    $("input").keydown(function (event)
    {
        $("#error__message").html("<p></p>");

    });

    /////end of funciton main
});
function togglePassword() {
    const passwordField = document.getElementById('password');
    const eyeIcon = document.getElementById('eyeIcon');
    const eyeSlashIcon = document.getElementById('eyeSlashIcon');
    if (passwordField.type === 'password') {
        passwordField.type = 'text';
        eyeIcon.classList.add('hidden');
        eyeSlashIcon.classList.remove('hidden');
    } else {
        passwordField.type = 'password';
        eyeIcon.classList.remove('hidden');
        eyeSlashIcon.classList.add('hidden');
    }
}