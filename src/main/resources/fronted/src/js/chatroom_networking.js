
var myID;
var _peer_list = {};
var audioMuted = false;
var videoMuted = false
var myRoomID =  "1"
var recordStartButton = document.getElementById("record-start");
var recordStopButton = document.getElementById("record-stop");
var selectElement = document.getElementById('mySelect');
document.addEventListener("DOMContentLoaded", (event)=>{
    startCamera();
});
let screenStream; // 전역 변수로 선언
document.getElementById("hellobtn").addEventListener("click",()=>{
    console.log("hellobtn 클릭")
});

document.getElementById("cam-share").addEventListener("click",(event)=>{
    let local_stream = myVideo.srcObject;
    console.log(myVideo.srcObject);
    for (let peer_id in _peer_list) {
        if (_peer_list[peer_id] && peer_id !== myID) {
            _peer_list[peer_id].getSenders().forEach(sender => {
                if (sender.track.kind === "video") {
                    sender.replaceTrack(local_stream.getVideoTracks()[0]);
                }
            });
        }
    }
     console.log(myVideo.srcObject);
});

document.getElementById("screen-share").addEventListener("click", (event) => {
    navigator.mediaDevices.getUserMedia({
        audio: true
    }).then(function (audioStream) {
        // 오디오 스트림을 얻어냄
        navigator.mediaDevices.getDisplayMedia({
            audio: true,
            video: true
        }).then(function (stream) {
            // 스크린 공유 스트림을 얻어내고 여기에 오디오 스트림을 결합함
            stream.addTrack(audioStream.getAudioTracks()[0]);
            screenStream = stream; // 스크린 스트림 저장
            switchToScreenShare(); // 로컬 비디오 스트림을 화면 공유 스트림으로 변경
            addScreenStreamToPeers(); // 모든 피어에 스크린 스트림 추가
        }).catch(function (e) {
            console.error("화면 공유 스트림을 얻는 동안 오류가 발생했습니다:", e);
        });
    }).catch(function (e) {
        console.error("오디오 스트림을 얻는 동안 오류가 발생했습니다:", e);
    });
});

function addScreenStreamToPeers() {
    if (screenStream) {
        for (let peer_id in _peer_list) {
            if (_peer_list[peer_id] && peer_id !== myID) {
                _peer_list[peer_id].getSenders().forEach(sender => {
                    if (sender.track.kind === "video") {
                        sender.replaceTrack(screenStream.getVideoTracks()[0]);
                    }
                });
            }
        }
    }
}

function switchToScreenShare() {
    let localVideo = document.getElementById("myVideo");
    if (localVideo) {
        localVideo.srcObject = screenStream;
    }
}


// socketio
var protocol = window.location.protocol;
socket = io('http://localhost:8085', {
    transports: ["websocket"],
    autoConnect: true,
    reconnection: true
});
socket.on("sttanswer",(data)=>{
    console.log(data);

});
document.getElementById("mySelect").addEventListener("change",function (){
    const selectedValues = Array.from(this.selectedOptions).map(option => option.value);
    console.log("선택된 언어:", selectedValues);
    socket.emit("setlang", selectedValues[0]);
});


document.getElementById("record-start").addEventListener("click", function () {
    let chunks = []; // 이벤트 핸들러 내부에서 선언

    if (navigator.mediaDevices) {
        console.log("getUserMedia supported.");
        this.style.background = "red";
        this.style.color = "black";
        this.disabled = true;
        recordStopButton.disabled = false;
        recordStopButton.style.color = "white";
        recordStopButton.style.background = "black";
        recordStopButton.textContent = "녹음 중지";

        const constraints = { audio: true };

        navigator.mediaDevices
            .getUserMedia(constraints)
            .then((stream) => {
                const mediaRecorder = new MediaRecorder(stream); // mediaRecorder도 내부에서 선언

                chkHearMic.onchange = (e) => {
                    if (e.target.checked === true) {
                        audioCtx.resume();
                        makeSound(stream);
                    } else {
                        audioCtx.suspend();
                    }
                };
                mediaRecorder.start(1000);
                console.log(mediaRecorder.state);

                mediaRecorder.onstop = (e) => {
                    console.log("onstop happen!");
                    sendChunks(chunks); // 녹음 중지 시 잔여 청크 전송
                    chunks = []; // 청크 배열 초기화
                };

                mediaRecorder.ondataavailable = function (e) {
                    chunks.push(e.data);
                    console.log("add " + String(chunks.length));
                    if (chunks.length >= 10) {
                        sendChunks(chunks); // 10개 청크가 쌓이면 전송
                        chunks = []; // 청크 배열 초기화
                    }
                };

                // 녹음 중지 버튼 이벤트 추가
                recordStopButton.addEventListener("click", function () {
                    mediaRecorder.stop();
                });
            })
            .catch((err) => {
                console.log("The following error occurred: " + err);
            });
    } else {
        console.error("getUserMedia not supported on your browser!");
    }
});

// 청크 데이터 전송 함수
function sendChunks(buffer) {
    if (buffer.length > 0) {
        const blob = new Blob(buffer, { type: "audio/webm" });
        const reader = new FileReader();
        reader.onload = function () {
            const base64String = reader.result.split(',')[1]; // Get Base64 string
            socket.emit("voice", base64String); // Send Base64 string to the server
        };
        reader.readAsDataURL(blob);
        console.log("end " + String(buffer.length));
    }
}
var camera_allowed=false;
var mediaConstraints = {
    audio: true,
    video: {
        height: 360
    }
};

function startCamera()
{
    //
    console.log("StartCamera 호출");
    navigator.mediaDevices.getUserMedia(mediaConstraints)
        .then((stream)=>{
            myVideo.srcObject = stream;
            camera_allowed = true;
            setAudioMuteState(audioMuted);
            setVideoMuteState(videoMuted);
            //start the socketio connection

            socket.connect();
        })
        .catch((e)=>{
            console.log("getUserMedia Error! ", e);
        });
}
socket.on("test",(data)=>{
    if(data==="ERROR"){
        alert("에러발생");

    }
    else{
        const blob =  new Blob([data],{type:"audio/wav"});
        const audioURL = URL.createObjectURL(blob);
        const audio = new Audio(audioURL);
        audio.play();
    }

    // const parsedata=JSON.parse(data);
    // const mydata =  parsedata["test"];
    // console.log(parsedata);
    // console.log(mydata);
})

socket.on("connect", ()=>{
    console.log("socket connected....");
    console.log(myRoomID);
    socket.emit("join-room", {"room_id": myRoomID});
});
socket.on("user-connect", (data)=>{
    console.log("user-connect ", data);
    data=JSON.parse(data);
    console.log("useer-conmect",data)
    let peer_id = data["sid"];
    console.log("peer_id : ",peer_id);
    let display_name = data["name"];
    console.log()
    _peer_list[peer_id] = undefined; // add new user to user list
    addVideoElement(peer_id, display_name);
});
socket.on("user-disconnect", (data)=>{
    data = JSON.parse(data);
    console.log("user-disconnect ", data);
    let peer_id = data["sid"];
    closeConnection(peer_id);
    removeVideoElement(peer_id);
});
socket.on("user-list", (data)=>{
    data = JSON.parse(data);
    console.log("user list recvd ", data);
    myID = data["my_id"];
    console.log("myid",myID);
    if( "list" in data) // not the first to connect to room, existing user list recieved
    {
        let recvd_list = data["list"];
        // add existing users to user list
        let peer_id;
        for(peer_id in recvd_list)
        {
            let display_name;
            display_name = recvd_list[peer_id];
            _peer_list[peer_id] = undefined;
            addVideoElement(peer_id, display_name);
        }
        start_webrtc();
    }
});

function closeConnection(peer_id)
{
    if(peer_id in _peer_list)
    {
        _peer_list[peer_id].onicecandidate = null;
        _peer_list[peer_id].ontrack = null;
        _peer_list[peer_id].onnegotiationneeded = null;

        delete _peer_list[peer_id]; // remove user from user list
    }
}

function log_user_list()
{
    for(let key in _peer_list)
    {
        console.log(`${key}: ${_peer_list[key]}`);
    }
}

//---------------[ webrtc ]--------------------

var PC_CONFIG = {
    iceServers: [
        {
            urls: ['stun:stun.l.google.com:19302',
                'stun:stun1.l.google.com:19302',
                'stun:stun2.l.google.com:19302',
                'stun:stun3.l.google.com:19302',
                'stun:stun4.l.google.com:19302'
            ]
        },
    ]
};

function log_error(e){console.log("[ERROR] ", e);}
function sendViaServer(data){socket.emit("data", data);}

socket.on("data", (msg)=>{
    switch(msg["type"])
    {
        case "offer":
            handleOfferMsg(msg);
            break;
        case "answer":
            handleAnswerMsg(msg);
            break;
        case "new-ice-candidate":
            handleNewICECandidateMsg(msg);
            break;
    }
});

function start_webrtc()
{
    // send offer to all other members
    for(let peer_id in _peer_list)
    {
        invite(peer_id);
    }
}

// const sleep  = ms => new Promise(r => setTimeout(r, ms));
async function invite(peer_id)
{
    if(_peer_list[peer_id]){console.log("[Not supposed to happen!] Attempting to start a connection that already exists!")}
    else if(peer_id === myID){console.log("[Not supposed to happen!] Trying to connect to self!");}
    else
    {
        const sleep  = ms => new Promise(r => setTimeout(r, ms));
        console.log(`Creating peer connection for <${peer_id}> ...`);
        createPeerConnection(peer_id);
        await sleep(2000);
        let local_stream = myVideo.srcObject;
        console.log(myVideo.srcObject);
        local_stream.getTracks().forEach((track)=>{_peer_list[peer_id].addTrack(track, local_stream);});
        console.log(myVideo.srcObject);
    }
}

function createPeerConnection(peer_id)
{
    _peer_list[peer_id] = new RTCPeerConnection(PC_CONFIG);

    _peer_list[peer_id].onicecandidate = (event) => {handleICECandidateEvent(event, peer_id)};
    _peer_list[peer_id].ontrack = (event) => {handleTrackEvent(event, peer_id)};
    _peer_list[peer_id].onnegotiationneeded = () => {handleNegotiationNeededEvent(peer_id)};
}


function handleNegotiationNeededEvent(peer_id)
{
    _peer_list[peer_id].createOffer()
        .then((offer)=>{return _peer_list[peer_id].setLocalDescription(offer);})
        .then(()=>{
            console.log(`sending offer to <${peer_id}> ...`);
            sendViaServer({
                "sender_id": myID,
                "target_id": peer_id,
                "type": "offer",
                "sdp": _peer_list[peer_id].localDescription
            });
        })
        .catch(log_error);
}

function handleOfferMsg(msg)
{
    let peer_id;
    peer_id = msg['sender_id'];

    console.log(`offer recieved from <${peer_id}>`);

    createPeerConnection(peer_id);
    let desc = new RTCSessionDescription(msg['sdp']);
    _peer_list[peer_id].setRemoteDescription(desc)
        .then(()=>{
            let local_stream = myVideo.srcObject;
            local_stream.getTracks().forEach((track)=>{_peer_list[peer_id].addTrack(track, local_stream);});
        })
        .then(()=>{return _peer_list[peer_id].createAnswer();})
        .then((answer)=>{return _peer_list[peer_id].setLocalDescription(answer);})
        .then(()=>{
            console.log(`sending answer to <${peer_id}> ...`);
            sendViaServer({
                "sender_id": myID,
                "target_id": peer_id,
                "type": "answer",
                "sdp": _peer_list[peer_id].localDescription
            });
        })
        .catch(log_error);
}

function handleAnswerMsg(msg)
{
    let peer_id;
    peer_id = msg['sender_id'];
    console.log(`answer recieved from <${peer_id}>`);
    let desc = new RTCSessionDescription(msg['sdp']);
    _peer_list[peer_id].setRemoteDescription(desc)
}


function handleICECandidateEvent(event, peer_id)
{
    if(event.candidate){
        sendViaServer({
            "sender_id": myID,
            "target_id": peer_id,
            "type": "new-ice-candidate",
            "candidate": event.candidate
        });
    }
}

function handleNewICECandidateMsg(msg)
{
    let peer_id = msg['sender_id'];
    console.log(`ICE candidate recieved from <${peer_id}>`);
    var candidate = new RTCIceCandidate(msg.candidate);
    _peer_list[msg["sender_id"]].addIceCandidate(candidate)
        .catch(log_error);
}


function handleTrackEvent(event, peer_id)
{
    console.log(`track event recieved from <${peer_id}>`);

    if(event.streams)
    {
        getVideoObj(peer_id).srcObject = event.streams[0];
    }
}
