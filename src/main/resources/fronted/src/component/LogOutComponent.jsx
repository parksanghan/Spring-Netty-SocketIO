import React from 'react';

function HelloButton() {
    const handleClick = () => {
        fetch('/logout', {
            method: 'POST'
        })
            .then(response => {
                if (response.ok) {
                    // 요청이 성공한 경우에 대한 처리
                    console.log('로그아웃 요청이 성공했습니다.');
                } else {
                    // 요청이 실패한 경우에 대한 처리
                    console.error('로그아웃 요청이 실패했습니다.');
                }
            })
            .catch(error => {
                // 오류 발생 시 처리
                console.error('오류 발생:', error);
            });
    };

    return (
        <button onClick={handleClick}>LOGOUT</button>
    );
}

export default HelloButton;
