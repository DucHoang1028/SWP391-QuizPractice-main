document.addEventListener('DOMContentLoaded', function () {

    let imageChanged = false;
    let textChanged = false;
    let genderChanged = false;

    document.getElementById('imageUpload').addEventListener('change', function (event) {
        const file = event.target.files[0];
        const preview = document.getElementById('imagePreview');
        const allowedExtensions = ['jpg', 'jpeg', 'png'];
        const extension = file.name.split('.').pop().toLowerCase();
        const messageProfile = document.getElementById('message-profile');
        messageProfile.innerText = '';
        //check if the image is jpg or png type or not
        if (!allowedExtensions.includes(extension)) {
            messageProfile.style.color = 'red';
            messageProfile.innerText = "Only JPG and PNG files are allowed.";
            return;
        }
        //check if user changed avater
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                preview.src = e.target.result;
                imageChanged = true;
            }
            reader.readAsDataURL(file);
        } else {
            preview.src = 'Assets/Styles/Images/User/default-avatar.jpg';
            imageChanged = false;
        }
    });

    const textInputs = document.querySelectorAll('.details-input');
    //check if user changed information
    textInputs.forEach(input => {
        input.addEventListener('input', function () {
            textChanged = true;
        });
    });

    const genderRadios = document.querySelectorAll('.input-radio[name="gender"]');
    genderRadios.forEach(radio => {
        radio.addEventListener('change', function () {
            genderChanged = true;
        });
    });

    document.getElementById('editProfile').addEventListener('submit', function (event) {
        event.preventDefault();
        showmodal();
        //check if user changed the profile
        if (!imageChanged && !textChanged && !genderChanged) {
            hidemodal();
            const messageProfile = document.getElementById('message-profile');
            messageProfile.style.color = 'red';
            messageProfile.innerText = "No change detected.";
            return;
        }
        const formData = new FormData(this);
        fetch('userprofile', {
            method: 'POST',
            body: formData,
        })
                .then(response => response.text())
                .then(data => {
                    hidemodal();
                    const messageProfile = document.getElementById('message-profile');
                    //message success for user if the information is updated successfully
                    if (data.trim() === "Updated successfully.") {
                        messageProfile.style.color = 'blue';
                        messageProfile.innerText = data;
                        setTimeout(() => {
                            window.location.reload();
                        }, 500);
                    } else {
                        messageProfile.style.color = 'red';
                        messageProfile.innerText = data;
                    }
                })
                .catch(error => {
                    hidemodal();
                    console.error('Error:', error);
                    const messageProfile = document.getElementById('message-profile');
                    messageProfile.style.color = 'red';
                    messageProfile.innerText = "An error occurred.";
                });
    });
});

function showmodal() {
    document.getElementById('modal-profile').style.display = 'flex';
}

function hidemodal() {
    document.getElementById('modal-profile').style.display = 'none';
}
