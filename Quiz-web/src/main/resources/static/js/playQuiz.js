function showRemainingTime() {
    let cur_time = $("#top__time");
    let time = cur_time.text().split(":");
    let remainder = 0;
    if (parseInt(time[2], 10) === 0) {
        time[2] = 59;
        remainder = -1;
    } else {
        time[2] = parseInt(time[2], 10) - 1;
    }
    if (remainder !== 0) {
        remainder = 0;
        if (parseInt(time[1], 10) === 0) {
            time[1] = 59;
            remainder = -1;
        }
        else {
            time[1] = parseInt(time[1], 10) - 1;
        }
    }
    if (remainder !== 0) {
        if (parseInt(time[0], 10) === 0) {
            // Todo: Summit Answer
            remainder = -1;
        }
        else {
            time[0] = parseInt(time[0], 10) - 1;
        }
    }
    cur_time.text(time[0].toString() + ":" + time[1].toString() + ":" + time[2].toString());
}
setInterval(showRemainingTime, 1000);

function updateQuiz(quiz) {
    $(".top__count:first").text(current.toString() + '/' + total.toString());
    $(".question:first").text(quiz["content"]);
    $(".answer__a:first").text(quiz["answers"][0]["content"]);
    $(".answer__b:first").text(quiz["answers"][1]["content"]);
    $(".answer__c:first").text(quiz["answers"][2]["content"]);
    $(".answer__d:first").text(quiz["answers"][3]["content"]);
}

function summitAnswer() {
    $.ajax({
        url: '/play-quiz/' + list_quiz_id,
        type: 'POST',
        data: {
            quiz_number: current - 1,
            answer_A: $("#ans__a").prop('checked'),
            answer_B: $("#ans__b").prop('checked'),
            answer_C: $("#ans__c").prop('checked'),
            answer_D: $("#ans__d").prop('checked')
        },
        success: function() {
            console.log("Success");
        },
        error: function() {
            console.log("Error");
        }
    });
}

$("#next_btn").click(function() {
    if (current < total) {
        $.ajax({
            url: '/play-quiz/' + list_quiz_id + "/quiz?quiz_number=" + current,
            type: 'GET',
            success: function(data) {
                summitAnswer();
                current += 1;
                updateQuiz(data);
            },
            error: function() {
                console.log("Error occur when sending data");
            }
        });
    }
})

$("#back_btn").click(function() {
    if (current > 1) {
        $.ajax({
            url: '/play-quiz/' + list_quiz_id + "/quiz?quiz_number=" + (current - 2),
            type: 'GET',
            success: function(data) {
                summitAnswer();
                current -= 1;
                updateQuiz(data);
            },
            error: function() {
                console.log("Error occur when sending data");
            }
        });
    }
})