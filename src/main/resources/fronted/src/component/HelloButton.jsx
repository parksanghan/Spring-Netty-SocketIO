import React from 'react';

function HelloButton() {
    const handleClick = () => {
        fetch('/hello', {
            method: 'GET'
        })
            .then(response => {
                if (response.ok) {
                    // 요청이 성공한 경우에 대한 처리
                    console.log('GET 요청이 성공했습니다.');
                } else {
                    // 요청이 실패한 경우에 대한 처리
                    console.error('GET 요청이 실패했습니다.');
                }
            })
            .catch(error => {
                // 오류 발생 시 처리
                console.error('오류 발생:', error);
            });
    };

    return (
        <button onClick={handleClick}>GET 요청 보내기</button>
    );
}

export default HelloButton;
