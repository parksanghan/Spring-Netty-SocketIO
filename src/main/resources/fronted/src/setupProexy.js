import {createProxyMiddleware}  from "http-proxy-middleware";

module.exports = function(app) {
    app.use(
        '/api',
        createProxyMiddleware({
            target: 'http://localhost:8080',	// 서버 URL or localhost:설정한포트번호 // 이제 /api로 요청하면 spring 서버로 요청
            changeOrigin: true,
        })
    );
};