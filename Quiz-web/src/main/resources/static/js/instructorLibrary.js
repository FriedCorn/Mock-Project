$(document).ready(function() {
    $.ajax({
        url: '/quiz/' + list_quiz_id + "/get-all-quiz",
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
    let body = $("#quiz-body");
    body.empty();
    for (const [key, quiz] of Object.entries(data)) {
        body.append(addQuestion(quiz, key));
    }
}

function addQuestion(quiz, number) {
    let question = document.createElement("div");
    question.classList.add("question");
    let question__head = document.createElement("div");
    question__head.classList.add("question__head");
    let question_name = document.createElement("div");
    question_name.classList.add("question__name");
    question_name.innerHTML = "Question " + (parseInt(number) + 1);
    let btn__controller = document.createElement("div");
    btn__controller.classList.add("btn__controller");
    btn__controller.id = quiz["id"];
    let btn__remove = document.createElement("button");
    btn__remove.classList.add("btn__remove", "quiz_button");
    btn__remove.innerHTML = "Remove";
    btn__remove.onclick = function(event) {delete_active(event);};
    // let btn__update = document.createElement("button");
    // btn__update.classList.add("btn__update", "quiz_button");
    // btn__update.innerHTML = "Update";
    // btn__update.onclick = function(event) {update_active(event);};
    btn__controller.appendChild(btn__remove);
    // btn__controller.appendChild(btn__update);
    question__head.appendChild(question_name);
    question__head.appendChild(btn__controller);

    let question__body = document.createElement("div");
    question__body.classList.add("question__body", "ans_span");
    question__body.innerHTML = quiz["content"];

    let question__list_answer = createListAnswer(quiz["answers"]);

    question.appendChild(question__head);
    question.appendChild(question__body);
    question.appendChild(question__list_answer);
    return question;
}

function createListAnswer(list_ans) {
    let question__list_answer = document.createElement("div");
    question__list_answer.classList.add("question__list_answer");
    question__list_answer.appendChild(createGroupAnswer(list_ans[0], list_ans[1]));
    question__list_answer.appendChild(createGroupAnswer(list_ans[2], list_ans[3]));
    return question__list_answer;
}

function createGroupAnswer(ans1, ans2) {
    let group_ans = document.createElement("div");
    group_ans.classList.add("group-ans");
    group_ans.appendChild(createAnswer(ans1));
    group_ans.appendChild(createAnswer(ans2));
    return group_ans;
}

function createAnswer(ans) {
    let answer = document.createElement("div");
    answer.classList.add("answer");
    let label = document.createElement("label");
    label.classList.add("col", "col-sm-5", "check_container");
    let checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    let checkmark = document.createElement("div");
    checkmark.classList.add("answer__a", "checkmark");
    let span = document.createElement("span");
    span.classList.add("ans_span");
    span.innerHTML = ans["content"];
    if (ans["trueAnswer"]) {
        checkbox.checked = true;
    }
    label.appendChild(checkbox);
    label.appendChild(checkmark);
    answer.appendChild(label);
    answer.appendChild(span);
    return answer;
}

function delete_active (e) {
    console.log(e.target.parentElement.id);
    $(document).ready(function() {
        $.ajax({
            url: '/quiz/' + list_quiz_id + "/delete/" + e.target.parentElement.id,
            type: 'GET',
            success: function(data) {
                loadData(data);
            },
            error: function(data) {
                console.log("Error");
                console.log(data);
            }
        });
    })
}

function update_active (e) {
    // console.log(e.target.parentElement.id);
    openForm("addQuestionPopup");
}

function openForm(id) {
    document.getElementById("container-fluid").style.filter = 'blur(3px)';
    document.getElementById("container-fluid").style.pointerEvents = 'none';
    document.getElementById(id).style.display = "block";
}