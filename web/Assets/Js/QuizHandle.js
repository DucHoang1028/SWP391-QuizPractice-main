
function calculateTime(duration, start) {
    // this function is for calculating the remaining time of the quiz, then display to user
    // sample start format: 2024-06-15T15:46:21.493
    // duration is in minutes

    // get the current time
    var now = new Date();
    // get the start time
    var start = new Date(start);
    // get the difference
    var diff = now - start;
    // get the remaining time
    var remaining = duration * 60000 - diff;
    // if remaining time is less than 0, then the quiz is over
    if (remaining <= 0) {
        // display the time
        document.getElementById('quiz-timer').innerHTML = '00 : 00 : 00';
        // change action input to "submit"
        document.getElementById('action').value = 'submit';
        // submit the form
        document.getElementById('quiz-form').submit();
    }
    // convert to hours, minutes and seconds
    var remainingHours = Math.floor(remaining / 3600000);
    remaining = remaining - remainingHours * 3600000;
    var remainingMinutes = Math.floor(remaining / 60000);
    remaining = remaining - remainingMinutes * 60000;
    var remainingSeconds = Math.floor(remaining / 1000);
    // display the time, don't forget to add 0 if the time is less than 10
    if (remainingHours < 10) {
        remainingHours = '0' + remainingHours;
    }
    if (remainingMinutes < 10) {
        remainingMinutes = '0' + remainingMinutes;
    }
    if (remainingSeconds < 10) {
        remainingSeconds = '0' + remainingSeconds;
    }
    // display the time
    document.getElementById('quiz-timer').innerHTML = remainingHours + ' : ' + remainingMinutes + ' : ' + remainingSeconds;
}

function countUp(start) {
    // this function is for counting the time of the quiz, then display to user
    // sample start format: 2024-06-15T15:46:21.493

    // get the current time
    var now = new Date();
    // get the start time
    var start = new Date(start);
    // get the difference
    var diff = now - start;
    // convert to hours, minutes and seconds
    var diffHours = Math.floor(diff / 3600000);
    diff = diff - diffHours * 3600000;
    var diffMinutes = Math.floor(diff / 60000);
    diff = diff - diffMinutes * 60000;
    var diffSeconds = Math.floor(diff / 1000);
    // display the time, don't forget to add 0 if the time is less than 10
    if (diffHours < 10) {
        diffHours = '0' + diffHours;
    }
    if (diffMinutes < 10) {
        diffMinutes = '0' + diffMinutes;
    }
    if (diffSeconds < 10) {
        diffSeconds = '0' + diffSeconds;
    }
    // display the time
    document.getElementById('quiz-timer').innerHTML = diffHours + ' : ' + diffMinutes + ' : ' + diffSeconds;
}

function nextQuestion(current) {
    // set the input value of "quesion" = current + 1
    document.getElementById('question').value = current + 1;
    // submit the form
    document.getElementById('quiz-form').submit();
}

function prevQuestion(current) {
    // set the input value of "quesion" = current - 1
    document.getElementById('question').value = current - 1;
    // submit the form
    document.getElementById('quiz-form').submit();
}

function updateRadio() {
    // get the input type radio class answer-input that is checked
    var radios = document.getElementsByClassName('answer-input');
    // loop through the radios
    for (var i = 0; i < radios.length; i++) {
        // if the radio is checked
        if (radios[i].checked) {
            // set the value of the radio to the input value of "record-answer"
            document.getElementById('record-answer').value = radios[i].value;
        }
    }
    // set changed to true
    document.getElementById('changed').value = 1;
    document.getElementById('changed').name = 'changed';
    document.getElementById('prev').name = 'prev';
    document.getElementById('record-answer').name = 'answer';
}

function updateCheckbox() {
    // get the input type checkbox class answer-input that is checked
    var checkboxes = document.getElementsByClassName('answer-input');
    // loop through the checkboxes
    var answers = '';
    for (var i = 0; i < checkboxes.length; i++) {
        // if the checkbox is checked
        if (checkboxes[i].checked) {
            // add the value of the checkbox to the answers
            answers += checkboxes[i].value + ',';
        }
    }
    // remove the last comma
    answers = answers.slice(0, -1);
    // concate the value of the answers to the input value of "record-answer"
    document.getElementById('record-answer').value = answers;
    // set changed to true
    document.getElementById('changed').value = 1;
    document.getElementById('changed').name = 'changed';
    document.getElementById('prev').name = 'prev';
    document.getElementById('record-answer').name = 'answer';
}

function updateText() {
    // get the input type text class answer-input
    var text = document.getElementsByClassName('answer-input')[0].value.trim();
    // if text is empty, return
    if (text == '') {
        return;
    }
    // set the value of the text to the input value of "record-answer"
    document.getElementById('record-answer').value = text;
    // set changed to true
    document.getElementById('changed').value = 1;
    document.getElementById('changed').name = 'changed';
    document.getElementById('prev').name = 'prev';
    document.getElementById('record-answer').name = 'answer';
    document.getElementById('quiz-form').submit();
}

function filterQuestions(type) {
    console.log('filtering questions: ', type);
    // all the question is class questions__cell
    var questions = document.getElementsByClassName('questions__cell');
    if (type == 'all') {
        // if the type is all, then show all the questions
        for (var i = 0; i < questions.length; i++) {
            questions[i].style.display = 'flex';
        }
        // change the message to "All Questions"
        document.getElementById('question-filter-title').innerHTML = 'All Questions';
    } else {
        // if the type is not all, then hide all the questions
        for (var i = 0; i < questions.length; i++) {
            questions[i].style.display = 'none';
        }
        // show the questions with the class of the type
        var filteredQuestions = document.getElementsByClassName(type);
        for (var i = 0; i < filteredQuestions.length; i++) {
            filteredQuestions[i].style.display = 'flex';
        }
        // change the message to the type, make the first letter uppercase
        document.getElementById('question-filter-title').innerHTML = type.charAt(0).toUpperCase() + type.slice(1) + ' Questions';
    }
}

function fetchAnswer() {
    console.log('fetching answer');
    // check if any the input type radio class answer-input that is checked -> to know the question is answered or not
    var inputs = document.getElementsByClassName('answer-input');
    var answered = false;
    for (var i = 0; i < inputs.length; i++) {
        if (inputs[i].checked) {
            answered = true;
        }
    }
    // if input type is text, and the value is not empty, then the question is answered
    if (inputs[0].type == 'text' && inputs[0].value.trim() != '') {
        answered = true;
    }
    

    const question = document.getElementById('question').value;
    const question_item = document.getElementById('ques_' + question);
    // if answered, remove the class "unanswered", add the class "answered"
    if (answered) {
        question_item.classList.remove('unanswered');
        question_item.classList.add('answered');
    } else {
        // if not answered, remove the class "answered", add the class "unanswered
        question_item.classList.remove('answered');
        question_item.classList.add('unanswered');
    }


}

function Afetch() {
    const params = new URLSearchParams({
        action: 'process',
        question: document.getElementById('question').value,
        prev: document.getElementById('prev').value,
        changed: document.getElementById('changed').value,
        answer: document.getElementById('record-answer').value,
    });

    fetch(`quiz-handle?${params}`)
        .then(response => console.log(response))
        .catch(error => console.error(error));
    document.getElementById('form-post').submit();
}

function submitRecord(action) {

    const params = new URLSearchParams({
        action: 'process',
        question: document.getElementById('question').value,
        prev: document.getElementById('prev').value,
        changed: document.getElementById('changed').value,
        answer: document.getElementById('record-answer').value,
    });

    // fetch to submit the question first, then submit the form
    fetch(`quiz-handle?${params}`)
        .then(response => console.log(response))
        .catch(error => console.error(error));

    // set the input value of "action" to "submit"
    document.getElementById('action').value = action;
    // submit the form
    document.getElementById('quiz-form').submit();
}

// Simple PRNG based on a seed
function seededRandom(seed) {
    const m = 0x80000000; // 2**31
    const a = 1103515245;
    const c = 12345;

    let state = seed ? seed : Math.floor(Math.random() * (m - 1));

    return function () {
        state = (a * state + c) % m;
        return state / (m - 1);
    };
}

// Function to generate a specific order of numbers based on a seed
function generateOrder(size, seed) {
    const random = seededRandom(seed);
    const array = Array.from({ length: size + 1 }, (_, i) => i);

    // Fisher-Yates shuffle algorithm
    for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
    }

    return array;
}

function sortChildrenByOrder(parentElement, seed) {
    const children = Array.from(parentElement.children);
    const size = children.length;
    const order = generateOrder(size - 1, seed);

    // Create an array of [child, orderIndex] pairs
    const childOrderPairs = children.map((child, index) => [child, order[index]]);

    // Sort the pairs based on the order index
    childOrderPairs.sort((a, b) => a[1] - b[1]);

    // Append the children back to the parent in the new order
    childOrderPairs.forEach(pair => parentElement.appendChild(pair[0]));
}

function showPopup() {
    // get all item with class answered
    let answered = document.getElementsByClassName('answered').length;
    // get all item with class questions__cell
    let total = document.getElementsByClassName('questions__cell').length;

    let action_btn = document.getElementById('submit-action')
    let message = "";
    
    if (answered === 0){
        message = "<h3>Exit now ?</h3> You haven't answered any questions. Do you want to exit exam now? The record will not be saved.<br/>";
        // set onclick event to exit
        action_btn.setAttribute('onclick', 'exit()');
    }
    else if (answered === total){
        message = "<h3>Submit now ?</h3> Do you want to submit now? You will not be able to change any answers after this point.<br/>";
        // set onclick event to submit
        action_btn.setAttribute('onclick', 'submit()');
    }
    else {
        message = `<h3>Submit now ?</h3> You've answered ${answered}/${total} questions. Do you want to submit now? You will not be able to change any answers after this point.<br/>`;
        // set onclick event to submit
        action_btn.setAttribute('onclick', 'submit()');
    }
    
    document.getElementById('popupMessage').innerHTML = message;
    document.getElementById('submit-overlay').style.display = 'block';
    document.getElementById('submit-popup').style.display = 'block';
}

function hidePopup() {
    document.getElementById('submit-overlay').style.display = 'none';
    document.getElementById('submit-popup').style.display = 'none';
}

function submit() {
    submitRecord('submit');
}

function exit(){
    submitRecord('exit');
}

function cancel() {
    hidePopup();
}

// on load DOM
document.addEventListener('DOMContentLoaded', function () {
    // get element "answer-list"
    var answerList = document.getElementById('answer-list');
    // get seed by record_id + order (string concat)
    var seed = document.getElementById('record_id').value + document.getElementById('order').value;
    // sort the children by order
    sortChildrenByOrder(answerList, seed);
    console.log('seed: ', seed);
    console.log('order generated: ', generateOrder(answerList.children.length - 1, seed));
});





