import React from 'react';

const CookieViewer = () => {
    const showJSessionId = () => {
        const cookies = document.cookie.split(';');
        let jsessionId;
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i].trim();
            if (cookie.startsWith('JSESSIONID=')) {
                jsessionId = cookie.substring('JSESSIONID='.length, cookie.length);
                break;
            }
        }
        if (jsessionId) {
            alert("JSESSIONID: " + jsessionId);
        } else {
            alert("JSESSIONID not found.");
        }
    };

    return (
        <div>
            <button onClick={showJSessionId}>Show JSESSIONID</button>
        </div>
    );
};

export default CookieViewer;
