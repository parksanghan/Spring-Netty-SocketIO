import React, { useState } from 'react';

function LoginForm() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();

        const formData = new FormData();
        formData.append('username', username);
        formData.append('password', password);

        const response = await fetch('/login', {
            method: 'POST',
            body: formData
        });

        if (response.ok) {
            // 로그인 성공
            console.log('로그인 성공!');
            // 필요한 작업 수행 (예: 페이지 이동, 상태 변경 등)
        } else {
            // 로그인 실패
            console.error('로그인 실패!');
            // 필요한 작업 수행 (예: 오류 메시지 표시 등)
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <label>
                Username:
                <input
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
            </label>
            <br />
            <label>
                Password:
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
            </label>
            <br />
            <button type="submit">Login</button>
        </form>
    );
}

export default LoginForm;
