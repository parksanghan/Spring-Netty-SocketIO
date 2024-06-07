import io from "socket.io-client";
import {useRef} from "react";
import {Socket} from "socket.io-client";

let socket;

export const initializeSocket = () => {
    socket = io('http://localhost:8085', {
        transports: ["websocket"],
        autoConnect: false,
        reconnection: true
    });
    socket.on("connect", () => {
        console.log("cc");
    });
};
export const handleButtonClick = (message) => {
    if (socket) {
        socket.emit('message', message);
        console.log(`Message sent: ${message}`);
    }
};
export const requestJoinRoom = ()=>{
    socket.emit();
}
export const requestSocketConnect = ()=>{
    socket.connect();
}

export const disconnectSocket = () => {

        socket.disconnect();

};
export const getSocketConfig = () => socket ? socket : null;
