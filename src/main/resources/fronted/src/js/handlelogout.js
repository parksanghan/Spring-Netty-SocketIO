const handleLogout = async () => {
    try {
        const response = await fetch('/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (response.ok) {
            // 로그아웃 성공 시 처리
            console.log('로그아웃 성공');
        } else {
            // 로그아웃 실패 시 처리
            console.error('로그아웃 실패');
        }
    } catch (error) {
        console.error('오류 발생:', error);
    }
};