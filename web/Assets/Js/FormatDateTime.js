
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


const datetimes = document.querySelectorAll('.datetime');

datetimes.forEach(datetime => {
    datetime.innerHTML = formatLocalDateTime(datetime.innerText);
});


