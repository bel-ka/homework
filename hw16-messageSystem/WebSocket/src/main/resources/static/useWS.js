let stompClient = null;

const setConnected = (connected) => {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#usersList").show();
    } else {
        $("#usersList").hide();
    }

    $("#usersTable > tbody").empty();
}

const connect = () => {
    stompClient = Stomp.over(new SockJS('/websocket'));
    stompClient.connect({}, (frame) => {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/users', (greeting) => showGreeting(JSON.parse(greeting.body)));
    });
}

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMsg () {
    try {
        stompClient.send("/app/chat.addUser", {}, JSON.stringify({
            name: $("#userName").val(),
            login: $("#userLogin").val(),
            password: $("#userPassword").val()
        }));
    } catch (e) {
        console.log("stompClient is null");
        alert("Connect before add new user");
    }
    $("#create-form")[0].reset();
}

const showGreeting = (user) => $("#usersStr").append("<tr>" +
    "><td>" + user.name + "</td>" +
    "><td>" + user.login + "</td>" +
    "><td>" + user.password + "</td>" +
    "</tr>")