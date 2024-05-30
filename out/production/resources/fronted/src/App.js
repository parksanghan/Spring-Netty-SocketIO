import logo from './logo.svg';
import './App.css';
import React, { useRef, useState, useEffect } from 'react';
import { initializeSocket, disconnectSocket, handleButtonClick, getSocketConfig } from './js/sockethandler';
import LoginComponent from "./component/LoginComponent";
import JoinComponent from "./component/JoinComponent";
import HelloButton from "./component/HelloButton";
import LogOutComponent from "./component/LogOutComponent"; // sockethandler 파일에서 함수들을 import합니다.
import {addUnloadListener,removeUnloadListener} from "./js/beforeunloadhandler";
import CookieViewer from "./component/CookieViewer";
 
function App() {
  const [inputValue, setInputValue] = useState("");
  const socketRef = useRef(null);

  useEffect(() => {
    // 소켓 객체 초기화
    if (socketRef.current == null) {
      initializeSocket();
    

    }
      addUnloadListener();

    // 컴포넌트가 언마운트될 때 소켓 연결 해제
    return () => {
        removeUnloadListener();
    };

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const handleInputChange = (event) => {
    setInputValue(event.target.value);
  };

  const handleClick = () => {
    handleButtonClick(inputValue);
  };

  return (
      <>
        <div className="App">
          <header className="App-header">
            <img src={logo} className="App-logo" alt="logo" />
            <p>
              Edit <code>src/App.js</code> and save to reload.
            </p>
            <a
                className="App-link"
                href="https://reactjs.org"
                target="_blank"
                rel="noopener noreferrer"
            >
              Learn React
            </a>
          </header>
        </div>
        <div>
          <input
              type="text"
              value={inputValue}
              onChange={handleInputChange}
          />
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

