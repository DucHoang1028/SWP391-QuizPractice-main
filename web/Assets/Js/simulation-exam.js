
const monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
const dayEndings = ["st", "nd", "rd, ", "th"];

//function to get ending of day
function getDayEnding(day) {
    if (day > 3 && day < 21) return 'th';
    switch (day % 10) {
        case 1: return 'st';
        case 2: return 'nd';
        case 3: return 'rd';
        default: return 'th';
    }
}

document.addEventListener('DOMContentLoaded', function () {

    loadPage()
    loadPopup();
});

function loadPage() {
    const percentageSquares = document.querySelectorAll('.percentage-square');
    percentageSquares.forEach(square => {
        const percentageText = square.querySelector('.percentage').textContent.trim();
        const percentage = parseInt(percentageText.replace('%', '').trim(), 10);
        console.log("percent: ", percentage)

        if (percentage >= 65) {
            square.classList.add('percentage-100-65');
        } else if (percentage >= 45) {
            square.classList.add('percentage-64-45');
        } else {
            square.classList.add('percentage-44-0');
        }
    });
}

function loadPopup() {
    const items_need_colored = document.querySelectorAll('.colored-text')
    // if text is easy -> green
    // if text is medium -> yellow
    // if text is hard -> red
    items_need_colored.forEach(item => {
        const text = item.textContent.trim();
        if (text === 'Easy') {
            item.classList.add('easy');
            // remove other
            item.classList.remove('medium');
            item.classList.remove('hard');
        } else if (text === 'Medium') {
            item.classList.add('medium');
            // remove other
            item.classList.remove('easy');
            item.classList.remove('hard');
        } else if (text === 'Hard') {
            item.classList.add('hard');
            // remove other
            item.classList.remove('easy');
            item.classList.remove('medium');
        } else {
            // this is percentage
            const percentage = parseInt(text.replace('%', '').trim(), 10);
            if (percentage >= 65) {
                item.classList.add('easy');
                item.classList.remove('medium');
                item.classList.remove('hard');
            } else if (percentage >= 45) {
                item.classList.add('medium');
                item.classList.remove('easy');
                item.classList.remove('hard');
            } else {
                item.classList.add('hard');
                item.classList.remove('easy');
                item.classList.remove('medium');
            }
        }
    });
}

function handleFilterChange() {
    const filterSelect = document.getElementById('filter');
    const filterOption = filterSelect.value;
    // Set the selected filter option in a hidden input for form submission
    document.getElementById('filter-input').value = filterOption;
    document.getElementById('page-input').value = 1;
    document.getElementById('form').submit();
}

function handleSearch() {
    document.getElementById('search-input').value = document.getElementById('search').value.trim();
    document.getElementById('form').submit();
}

function clearSearch() {
    document.getElementById('search').value = '';
    document.getElementById('search-input').value = '';
    document.getElementById('form').submit();
}

function handleSortChange() {
    const sortSelect = document.getElementById('sort');
    const sortOption = sortSelect.value;
    // Set the selected sort option in a hidden input for form submission
    document.getElementById('sort-input').value = sortOption;
    document.getElementById('page-input').value = 1;
    document.getElementById('form').submit();
}

function showPopup(id, subject, exam, duration, level, question_number, pass_rate, quiz_records) {

    document.getElementById('subject-name').textContent = subject;
    document.getElementById('exam-name').textContent = exam;
    document.getElementById('duration').textContent = duration + ' minutes';
    document.getElementById('level').textContent = level === '1' ? 'Easy' : level === '2' ? 'Medium' : 'Hard';
    document.getElementById('num-questions').textContent = question_number + ' questions';
    document.getElementById('pass-rate').textContent = pass_rate + '%';
    // update href for a tag
    document.getElementById('start-quiz').href = 'quiz-handle?action=start&quiz_id=' + id;

    // quiz_records is an JSON array but " is '
    quiz_records = quiz_records.replace(/'/g, '"');
    quiz_records = JSON.parse(quiz_records);

    // populate quiz records
    const quizRecordsContainer = document.getElementById('quiz-records');
    // note: the container is a table
    quizRecordsContainer.innerHTML = '';

    // if quiz_records is empty, then show no record and show message to user
    // style red, italics, center
    if (quiz_records.length === 0) {
        quizRecordsContainer.innerHTML = `
            <tr>
                <td colspan="5" style="text-align: center; color: red; font-style: italic;">
                    No records found
                </td>
            </tr>
        `;
        loadPopup();
        loadPage();
        const datetimes = document.querySelectorAll('.datetime');

        datetimes.forEach(datetime => {
            datetime.innerHTML = formatLocalDateTime(datetime.innerHTML);
        });

        $("#explain-container").css("display", "flex");
        $("#close_button2").click(function (event) {
            $("#explain-container").css("display", "none");
            $("#close_button2").off('click');
        });
        return;
    }
    quiz_records.forEach(record => {
        
        // if finished_at is null, then the quiz is not submitted
        const is_submitted = record.finished_at !== "null";

        console.log("record: ", record)
        console.log("is_submitted: ", is_submitted)

        // get created_at, finished_at and calculate the time taken
        const created_at = new Date(record.created_at);
        const finished_at = new Date(record.finished_at);
        const timeTaken = finished_at - created_at;
        const minutes = Math.floor(timeTaken / 60000);
        const seconds = Math.floor((timeTaken % 60000) / 1000);


        const content = `
                            <tr>
                                <td>
                                    <div class="datetime">
                                        ${record.created_at}
                                    </div>
                                </td>
                                <td>
                                    <div class="correct-info correct">
                                        <div class="percentage-square">
                                            <p class="percentage" hidden>
                                                ${ is_submitted ? record.score : 50} %
                                            </p>
                                            <p class="questions">
                                                ${is_submitted ? record.score : "Not Submitted"}
                                            </p>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    ${ is_submitted ? `${minutes} Mins ${seconds} Seconds` : "Not Submitted"}
                                </td>
                                <td>
                                    <div class="correct-info correct">
                                        <div class="percentage-square">
                                            <p class="percentage" hidden>
                                                ${ is_submitted ? record.status * 100 : 50} %
                                            </p>
                                            <p class="questions">
                                                ${ is_submitted ? record.status === 1 ? 'Pass' : 'Fail' : "Not Submitted"}
                                            </p>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <a class="btn view-btn" href="quiz-review?record_id=${record.record_id}&order=1">
                                        <p class="questions")">
                                            View
                                        </p>
                                    </a>
                                </td>
                            </tr>
        `
        quizRecordsContainer.innerHTML += content;
    })


    loadPopup();
    loadPage();
    const datetimes = document.querySelectorAll('.datetime');

    datetimes.forEach(datetime => {
        datetime.innerHTML = formatLocalDateTime(datetime.innerHTML);
    });

    $("#explain-container").css("display", "flex");
    $("#close_button2").click(function (event) {
        $("#explain-container").css("display", "none");
        $("#close_button2").off('click');
    });
}

function reviewRecord(quiz_id) {
    
}

//function that take hour, minute, second and return a string of time in AM/PM format
function formatTime(hour, minute, second) {
    let time = '';
    if (hour > 12) {
        time += String(hour - 12);
    } else {
        time += String(hour);
    }
    time += ':';
    time += String(minute).padStart(2, '0');
    time += hour >= 12 ? ' PM' : ' AM';
    return time;
}

function formatLocalDateTime(dateTimeString) {
    dateTimeString = dateTimeString.trim();
    const dateTime = new Date(dateTimeString);
    const year = dateTime.getFullYear();
    const monthName = monthNames[dateTime.getMonth()];
    const day = dateTime.getDate() + getDayEnding(dateTime.getDate());
    const hour = String(dateTime.getHours()).padStart(2, '0');
    const minute = String(dateTime.getMinutes()).padStart(2, '0');
    const second = String(dateTime.getSeconds()).padStart(2, '0');
    const time = formatTime(dateTime.getHours(), dateTime.getMinutes(), dateTime.getSeconds());
    return `${year} ${monthName} ${day} <br/> ${time}`;
}


