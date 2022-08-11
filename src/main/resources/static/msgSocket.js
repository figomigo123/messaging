const JSON2 = require("./sockjs");
let socket = new WebSocket("wss://localhost:8080/test/websocket");

socket.onopen = function(e) {
    alert("[open] Connection established");
    alert("Sending to server");
    socket.send("My name is John");
};

socket.onmessage = function(event) {
    alert(`[message] Data received from server: ${event.data}`);
};

socket.onclose = function(event) {
    if (event.wasClean) {
        alert(`[close] Connection closed cleanly, code=${event.code} reason=${event.reason}`);
    } else {
        // e.g. server process killed or network down
        // event.code is usually 1006 in this case
        alert('[close] Connection died');
    }
};

socket.onerror = function(error) {
    alert(`[error] ${error.message}`);
};


function send() {
    let msg = document.getElementById("message").value;

   let  dataO = {
        message: msg
    };

    let json = JSON2.stringify(dataO);

    $.ajax({
        type: "POST",
        url: "test/sendMessagetoFirstQueue",
        data: json,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(msg) {

        }
    });


}
