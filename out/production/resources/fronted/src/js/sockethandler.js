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

export const disconnectSocket = () => {
    if (socket) {
        socket.disconnect();
    }
};
export const requestSocketConnect=()=>{
    if (socket) {
        socket.connect();
    }
}
export const getSocketConfig = () => socket ? socket : null;
