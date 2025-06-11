//Javascript
//Al inicio creamos una constante que tiene como valor, el resultado del constructor de un nuevo cliente Stomp
const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
}, );


//Aquí ya estamos indicando qué ocurrírá cuando el stompClient se conecte al brokerURL que le pasamos al crearlo. Esto es asíncrono. Ocurrirá AUTOMÁTICAMENTE cuando la conexión sea correcta.
stompClient.onConnect = (frame) => {

    //LLama al método que cambia la apariencia en pantalla para que salga como conectado
    setConnected(true);

    //Imprime "conectado: ..."
    console.log('Connected: ' + frame);

    //Una vez conectado, se suscribe al canal /topic/ciencia (Es decir, recibirá los mensajes que lleguen a topic/ciencia)
    //En la declaración, además, se declara el CALLBACK de la subscripción.
    //No sabemos qué formato tiene el mensaje, pero se trata de un objeto JAVASCRIPT en formato JSON
    //El mensaje que recibimos es de Clase ChatMessage
    stompClient.subscribe('/topic/ciencia', (mensaje) => {

        showMensaje(
            //El objeto ChatMessage que enviamos originalmente, está en el mensaje.body.
            // Hacemos JSON Parse para poder obtener el objeto y así acceder a las propiedades.
            JSON.parse(mensaje.body).message,
            JSON.parse(mensaje.body).timestamp,
            JSON.parse(mensaje.body).usuario.username
        );
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {


    $("#connect").prop("disabled", connected);

    $("#disconnect").prop("disabled", !connected);


    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
}

function connect() {

    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    stompClient.publish({
        destination: "/app/ciencia",
        body: JSON.stringify({'message': $("#textoMensaje").val()})
    });
}

function showMensaje(message, timestamp, username) {

    //JQUERY
    //hacemos append (añadimos al final) de la tabla ` id="mensajes" una nueva linea con ` los valores que hemos recibido, dentro de unas celdas
    $("#mensajes").append("<tr><td>" + timestamp + "</td><td>" + username + "</td><td>" + message + "</td></tr>");
}



//Esta función esta englobada por $();
//Este es un modo abreviado usado por JQUERY de hacer un document.ready() que es cuando están creados todos los objetos
$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendMessage());
});





