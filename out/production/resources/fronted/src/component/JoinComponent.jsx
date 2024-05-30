import React, { useState } from 'react';

function JoinComponent() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [displayName, setDisplayName] = useState('');

    const joinHandler = async () => {
        const member = {
            username: username,
            password: password,
            displayName: displayName
        };

        try {
            const response = await fetch('/join', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(member)
            });

            if (response.ok) {
                const data = await response.text();
                alert(data);
            } else {
                throw new Error('Failed to join');
            }
        } catch (error) {
            alert(error.message);
        }
    };

    return (
        <div>
            <input
                id="username"
                name="username"
                placeholder="ID"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />
            <br />
            <input
                id="password"
                name="password"
                type="password"
                placeholder="PASSWORD"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <br />
            <input
                id="displayname"
                name="displayname"
                placeholder="성함"
                value={displayName}
                onChange={(e) => setDisplayName(e.target.value)}
            />
            <br />
            <button onClick={joinHandler}>가입</button>
        </div>
    );
}

export default JoinComponent;
