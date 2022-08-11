
let socket = new WebSocket("ws://localhost:8080/test/websocket");

socket.onopen = function(e) {


};

socket.onmessage = function(event) {
    alert(`[message] Data received from server: ${event.data}`);
};

socket.onclose = function(event) {

};

socket.onerror = function(error) {


};

