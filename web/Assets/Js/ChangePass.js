$(document).ready(function () {
    var passwordChanged = false;
    $("#changePasswordForm").submit(function (event) {
        event.preventDefault();
        // Prevent form submission if password has already changed successfully
        if (passwordChanged) {
            return;
        }
        showmodal();
        var currentPassword = $("#currentpassword").val();
        var newPassword = $("#newpassword").val();
        var confirmPassword = $("#confirmpassword").val();
        console.log("currentpass = " + currentPassword);
        // Send AJAX request to servlet
        $.ajax({
            url: "changepassword",
            type: 'POST',
            data: {
                currentPassword: currentPassword,
                newPassword: newPassword,
                confirmPassword: confirmPassword
            },
            success: function (response) {
                hidemodal();
                // Display response message
                $("#message-change").text(response);
                // Set flag to true if password change is successful
                if (response === "Password changed successfully.") {
                    const messageChange = document.getElementById('message-change');
                    messageChange.style.color = 'blue';
                    passwordChanged = true;
                    setTimeout(() => {
                        window.location.reload(); // Reload page to get updated session data
                    }, 500);
                } else {
                    // Reset the flag if password change is unsuccessful
                    passwordChanged = false;
                }
            },
            error: function (xhr, status, error) {
                hidemodal();
                console.error(xhr.responseText);
                $("#message-change").text("An error occurred.");
            }
        });
    });
});

function showmodal() {
    document.getElementById('modal-profile').style.display = 'flex';
}

function hidemodal() {
    document.getElementById('modal-profile').style.display = 'none';
}
