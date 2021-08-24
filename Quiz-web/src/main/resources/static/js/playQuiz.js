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