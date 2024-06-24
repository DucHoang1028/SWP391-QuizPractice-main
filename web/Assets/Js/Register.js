//show login popup for user
function openRegisterPopup() {
    $("#main-component").css("display", "none");
    $("#main-component-regi").css("display", "flex");
    $("#close_regibutton").click(function (event)
    {
        $("#main-component-regi").css("display", "none");
        $("#close_regibutton").off('click');
    });
}
//run when user submit form
$(document).ready(function () {
    $("#registerForm").submit(function (event) {
        //prevent default form being submitted
        event.preventDefault();
        //show loader when form is being executed
        showmodal();
        
        var fullname = $("#fullname").val();
        var gender = $('input[name="gender"]:checked').val();
        var email = $('#register-email').val();
        var mobile = $('#mobile').val();
        var password = $('#password-register').val();
        var confirmPassword = $('#confirm-password-register').val();

        //Send ajax request to servlet
        $.ajax({
            url: "register",
            type: 'POST',
            data: {
                fullname: fullname,
                gender: gender,
                email: email,
                mobile: mobile,
                password: password,
                confirmPassword : confirmPassword
            },
            success: function (response) {
                hidemodal();
                //check if the form is successfully sent in database
                if (response === "A verification link has been sent to you!") {
                    document.getElementById('message-register').style.color = 'blue';
                } else {
                    document.getElementById('message-register').style.color = 'red';
                }
                $("#message-register").text(response);
            },
            error: function (xhr, status, error) {
                hidemodal();
                console.error(xhr.responseText);
                console.log(xht.toString());
                document.getElementById('message-register').style.color = 'red';
                $("#message-register").text("An occured error.");
            }
        })
    });
});

//show loader
function showmodal()
{
    document.getElementById('modal-register').style.display = 'flex';
}

//hide loader
function hidemodal() {
    document.getElementById('modal-register').style.display = 'none';
}