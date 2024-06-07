//
// document.getElementById("record-start").addEventListener("click", function () {
//     if (navigator.mediaDevices) {
//         console.log("getUserMedia supported.");
//         this.style.background = "red";
//         this.style.color = "black";
//
//         recordStartButton.style.color = "black";
//         recordStopButton.style.background = "white";
//
//         const constraints = {
//             audio: true,
//         };
//         let chunks = [];
//         navigator.mediaDevices
//             .getUserMedia(constraints)
//             .then((stream) => {
//                 mediaRecorder = new MediaRecorder(stream);
//
//                 chkHearMic.onchange = (e) => {
//                     if (e.target.checked == true) {
//                         audioCtx.resume();
//                         makeSound(stream);
//                     } else {
//                         audioCtx.suspend();
//                     }
//                 };
//                 mediaRecorder.start(1000);
//                 console.log(mediaRecorder.state);
//                 mediaRecorder.onstop = (e) => {
//                     console.log("onstop happen!");
//
//                     if (chunks.length >= 9) {
//                         console.log("data available after MediaRecorder.stop() called.");
//                         const bb = new Blob(chunks, { type: "audio/wav" });
//                         socket.emit("voice", bb);
//                     }
//                     chunks = [];
//                 };
//                 mediaRecorder.ondataavailable = function (e) {
//                     chunks.push(e.data);
//                     console.log("add " + String(chunks.length));
//
//                     if (chunks.length >= 10) {
//                         mediaRecorder.stop();
//                         //const bloddb = new Blob(chunks, { 'type' : 'audio/wav' })
//                         //socket.emit('voice', bloddb)
//
//                         //chunks = []
//                         mediaRecorder.start(1000);
//                         //console.log("end " + String(chunks.length))
//                     }
//
//                     mediaRecorder.sendData = function (buffer) {
//                         const bloddb = new Blob(buffer, { type: "audio/wav" });
//                         socket.emit("voice", bloddb);
//
//                         console.log("end " + String(chunks.length));
//                     };
//                 };
//
//                 // Call makeSound() function here
//             })
//             .catch((err) => {
//                 console.log("The following error occurred: " + err);
//             });
//     } else {
//         console.error("getUserMedia not supported on your browser!");
//     }
// });
//
// document.getElementById("record-stop").addEventListener("click", function () {
//     if (navigator.mediaDevices) {
//         console.log("STOP INIT!");
//         this.style.background = "red";
//         this.style.color = "black";
//
//         recordStartButton.style.background = "white";
//
//         const constraints = {
//             audio: true,
//         };
//
//         navigator.mediaDevices
//             .getUserMedia(constraints)
//             .then((stream) => {
//                 mediaRecorder.stop(1000);
//                 console.log(mediaRecorder.state);
//
//                 mediaRecorder.onstop = (e) => {
//                     console.log("STOP EXEC!");
//
//                     if (chunks.length > 1) {
//                         console.log("STOP SEND!");
//                         const bb = new Blob(chunks, { type: "audio/wav" });
//                         socket.emit("voice", bb);
//                     }
//                     chunks = [];
//                 };
//
//                 mediaRecorder.ondataavailable = (e) => {};
//                 // Call makeSound() function here
//             })
//             .catch((err) => {
//                 console.log("The following error occurred: " + err);
//             });
//     } else {
//         console.error("getUserMedia not supported on your browser!");
//     }
// });
