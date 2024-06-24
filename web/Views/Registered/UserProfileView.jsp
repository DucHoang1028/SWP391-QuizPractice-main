<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./Assets/Styles/User/UserProfile.css"/>
        <script src="./Assets/Js/Profile.js"></script>
        <script src="./Assets/Js/Common/jquery.min.js"></script>
        <title>User Profile</title>
    </head>
    <body>
        <div class="main-component-profile" id="main-component-profile">
            <div class="profile">
                <div id="close_button-profile" class="close_button-profile">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
                    <path d="M18 18L12 12M12 12L6 6M12 12L18 6M12 12L6 18" stroke="#1D2026" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                </div>
                <form id="editProfile" method="post" enctype="multipart/form-data">
                    <div class="profile-content">
                        <div class="profile-icon frame">
                            <div class="image-preview">
                                <img id="imagePreview" src="${currentUser.getAvatar()}" alt="Avatar"/>
                            </div>
                            <input type="file" id="imageUpload" name="file" class="image-upload-input">
                            <label for="imageUpload" class="image-upload-label">Choose Image</label>
                        </div>
                        <div class="details">
                            <div class="details-part">
                                <label for="fullname" class="details-label">Full Name</label>
                                <input type="text" name="fullname" class="details-input" value="${currentUser.getFull_name()}" required>
                            </div>
                            <div class="details-part gender">
                                <label for="gender" class="gender-label">Gender</label>
                                <label for="gender" class="male details-radio">
                                    <input type="radio" class="input-radio" name="gender" value="1" id="male" ${currentUser.getGender() == '1' ? 'checked' : ''}>Male
                                </label>
                                <label for="gender" class='details-radio'>
                                    <input type="radio" class="input-radio" name="gender" value="0" id="female" ${currentUser.getGender() == '0' ? 'checked' : ''}>Female
                                </label>
                            </div>
                            <div class="details-part">
                                <label for="email" class="details-label email">Email</label>
                                <input type="email" name="email" class="details-input email" value="${currentUser.getEmail()}" readonly>
                            </div>
                            <div class="details-part">
                                <label for="mobile" class="details-label">Mobile</label>
                                <input type="tel" pattern="[0-9]{10}" name="mobile" class="details-input" value="${currentUser.getMobile()}" required>
                                <p id="message-profile"></p>
                            </div>
                            <button type="submit" class="submit_button-profile two-buttons">Apply Changes</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <div id="modal-profile" class="process-modal-profile">
            <div class="loader-profile"></div>
        </div>
    </body>
</html>
