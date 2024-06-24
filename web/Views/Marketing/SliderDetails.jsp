<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Slider</title>
</head>
<body>
    <form id="editSlider" action="SliderDetailsController" method="post" enctype="multipart/form-data">
        <div class="slider-content">
            <div class="slider-icon frame">
                <div class="image-preview">
                    <img id="imagePreview" src="${currentSlider.image}" alt="Slider Image"/>
                </div>
                <input type="file" id="imageUpload" name="file" class="image-upload-input">
                <label for="imageUpload" class="image-upload-label">Choose Image</label>
            </div>
            <div class="slider-details">
                <label for="title">Title:</label>
                <input type="text" id="title" name="title" value="${currentSlider.title}" required>

                <label for="backLink">Back Link:</label>
                <input type="text" id="backLink" name="back_link" value="${currentSlider.back_link}" required>

                <label for="status">Status:</label>
                <select id="status" name="status">
                    <option value="1" ${currentSlider.status == 1 ? 'selected' : ''}>Show</option>
                    <option value="0" ${currentSlider.status == 0 ? 'selected' : ''}>Hide</option>
                </select>

                <label for="note">Note:</label>
                <textarea id="note" name="note" required>${currentSlider.note}</textarea>
            </div>
            <button type="submit">Update Slider</button>
        </div>
    </form>

</body>
</html>
