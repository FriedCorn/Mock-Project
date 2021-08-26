$(document).ready(function() {
    $.ajax({
        url: '/report/' + list_quiz_id + "/get-account-grade",
        type: 'GET',
        success: function(data) {
            console.log("Success");
            loadData(data);
        },
        error: function(data) {
            console.log("Error");
            console.log(data);
        }
    });
})

function loadData(data) {
    let body = $("#report_body");
    console.log(data);
    body.empty();
    for (let i = 0; i < data["grades"].length; i++) {
        body.append(createBox(data["username"][i], data["grades"][i]));
    }
}

function createBox(username, grade) {
    let report_box = document.createElement("div");
    report_box.classList.add("report_box", "row");
    let user_name_col = document.createElement("div");
    user_name_col.classList.add("col-sm-1");
    user_name_col.innerHTML = username;
    let score_bar = document.createElement("div");
    score_bar.classList.add("col");
    score_bar.style.backgroundImage = `linear-gradient(to right, #73CB86 `+ grade + `%, #F06539 ` + grade + `%)`;
    let score_text = document.createElement("div");
    score_text.classList.add("col-sm-1");
    score_text.innerHTML = grade + '% Accuracy';
    report_box.appendChild(user_name_col);
    report_box.appendChild(score_bar);
    report_box.appendChild(score_text);
    return report_box
}