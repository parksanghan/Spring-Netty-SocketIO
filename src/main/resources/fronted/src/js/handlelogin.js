const handleLogin = async () => {
    try {
        const response = await fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username: 'user', password: 'password' }),
        });

        if (response.ok) {
            // 로그인 성공 시 처리
            response.json().then(userinfo=>{
                console.log(userinfo);
            })
            console.log('로그인 성공');
        } else {
            // 로그인 실패 시 처리
            console.error('로그인 실패');
        }
    } catch (error) {
        console.error('오류 발생:', error);
    }
};