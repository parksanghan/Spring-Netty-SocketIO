// beforeunloadhandler.js

export function addUnloadListener() {
    const handleBeforeUnload = (e) => {
        navigator.sendBeacon('/logout', '');
    };  window.addEventListener("beforeunload", handleBeforeUnload);
    return handleBeforeUnload; }// 이벤트 핸들러 반환

export function removeUnloadListener(handler) {
    window.removeEventListener("beforeunload", handler);
}