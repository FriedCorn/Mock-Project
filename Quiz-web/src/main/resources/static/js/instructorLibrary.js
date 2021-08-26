$(document).ready(function() {
    $.ajax({
        url: '/quiz/' + list_quiz_id + "/get-all-quiz",
        type: 'GET',
        success: function() {
            console.log("Success");
        },
        error: function() {
            console.log("Error");
        }
    });
})