import logo from './logo.svg';
import './App.css';
import React, { useEffect, useState ,useRef} from 'react';
import { handleButtonClick } from './js/sockethandler';
import LoginComponent from './component/LoginComponent';
import JoinComponent from './component/JoinComponent';
import HelloButton from './component/HelloButton';
import LogOutComponent from './component/LogOutComponent'; // sockethandler 파일에서 함수들을 import합니다.
import { addUnloadListener, removeUnloadListener } from './js/beforeunloadhandler';
import CookieViewer from './component/CookieViewer';
import dummyLoad from "./js/dummyLoad";
import loadExternalJS from "./js/loadExternalJS";
function App() {
    const [inputValue, setInputValue] = useState('');
    const [myRoomID,setRoomId] = useState("");
    const [audioMuted, setAudioMuted] =   useState(false);
    const [videoMuted , setVideoMuted ] =   useState(false);
    const scriptRef = useRef(null);
    // var myRoomID;
    // var audioMuted;
    // var videoMuted;


    useEffect(() => {
        // 소켓 객체 초기화

        loadExternalJS();
        const script = document.createElement('script');
        addUnloadListener();



        // 컴포넌트가 언마운트될 때 소켓 연결 해제
        return () => {
            removeUnloadListener(); // 로그아웃 이벤트 리스너 제거
            document.body.removeChild(script);
        };
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);
    const handleRoomIdChange = (event) => {
        // setRoomId(event.target.value);
    };
    useEffect(() => {

    }, []);

    const handleRoomBtnClick = () => {};
    const handleInputChange = (event) => {
        setInputValue(event.target.value);
    };
    const handleClick = () => {
        // handleButtonClick(inputValue);
    };

    return (
        <>

            <div>
                <input type="text" value={inputValue} onChange={handleInputChange} />
                <button onClick={handleClick}>Send</button>
            </div>
            <LoginComponent></LoginComponent>
            <JoinComponent></JoinComponent>
            <HelloButton></HelloButton>
            <LogOutComponent></LogOutComponent>
            <CookieViewer></CookieViewer>
        </>
    );
}

export default App;
