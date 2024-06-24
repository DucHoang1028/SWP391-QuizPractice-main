document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("editSliderDetails");
    const titleInput = document.getElementById("title");
    const urlInput = document.getElementById("back_link");
    const noteInput = document.getElementById("note");
    const statusInput = document.getElementById("status");

    form.addEventListener("submit", function(event) {
        if (!validateForm()) {
            event.preventDefault();
        }
    });

    function validateForm() {
        let valid = true;

        if (titleInput.value.trim() === "") {
            alert("Title is required.");
            valid = false;
        }

        if (urlInput.value.trim() === "") {
            alert("URL is required.");
            valid = false;
        }

        if (noteInput.value.trim() === "") {
            alert("Note is required.");
            valid = false;
        }

        if (statusInput.value.trim() === "" || isNaN(statusInput.value)) {
            alert("Status is required and should be a number.");
            valid = false;
        }

        return valid;
    }
});
document.getElementById('imageUpload').addEventListener('change', function() {
            const file = this.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e){
                    document.getElementById('imagePreview').src = e.target.result;
                }
                reader.readAsDataURL(file);
            }
        });
