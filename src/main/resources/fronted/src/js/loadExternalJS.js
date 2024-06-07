function loadExternalJS() {
    // 필요한 파일들을 동적으로 생성해줍니다.
    const scripts = [
        './js/video-manager.js',
        './js/chatroom_ui.js',
        './js/chatroom_networking.js'
    ];
    let loadedCount = 0; // 로드된 스크립트 개수

    const dispatchDomContentLoaded = () => {
        const event = new Event('DOMContentLoaded');
        document.dispatchEvent(event);
    };

    scripts.forEach(src => {
        const script = document.createElement('script');
        script.src = src;
        script.async = true;
        script.onload = () => {
            loadedCount++;
            if (loadedCount === scripts.length) { // 모든 스크립트 로드 완료 시
                dispatchDomContentLoaded();
            }
        };
        script.onerror = () => {
            console.error(`Failed to load script: ${src}`);
            loadedCount++; // 오류 발생 시에도 카운트 증가
            if (loadedCount === scripts.length) {
                dispatchDomContentLoaded(); // 모든 스크립트 로드 완료 시
            }
        };
        document.body.appendChild(script);
    });

}
export default loadExternalJS;
