function dummyLoad() {
    // 필요한 파일들을 동적으로 생성해줍니다.
    const scriptJqueryone = document.createElement('script');
    scriptJqueryone.src = './js/dummy.js';
    scriptJqueryone.async = true;
    // 생성된 script 요소들을 body에 붙여주세요
    scriptJqueryone.addEventListener('load',()=>{
        const DOMContentLoadedEvent = new Event('DOMContentLoaded');
        document.dispatchEvent(DOMContentLoadedEvent);
    })
    document.body.appendChild(scriptJqueryone);

}
export default dummyLoad;
