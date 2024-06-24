function showList(id, e) {
    const target = document.getElementById(id);
    console.log(target);
    // if style is none, make it block, else make it none
    if (target.style.display === 'block') {
        target.style.display = 'none';    
        e.innerText = '+';
    } else {
        target.style.display = 'block';
        e.innerText = '-';
    }
}

function filter(id) {
    const input = document.getElementById("input-category");
    const filter = input.value.toUpperCase();
    const div = document.getElementById(id);
    const a = div.getElementsByTagName("a");
    // loop through all the elements
    for (let i of a) {
        let txtValue = i.textContent || i.innerText;
        // check if the elements contain filter string
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            i.style.display = "";
        } else {
            i.style.display = "none";
        }
    }
}