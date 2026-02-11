const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  lintOnSave: false ,// 关闭开发时的 ESLint 检查
  transpileDependencies: true,
    // 核心：配置反向代理
  devServer: {
    port:5173,
    proxy: {
      // 1. 匹配以 /api 开头的请求
      '/api': {
        // 2. 后端服务器地址
        target: 'http://localhost:8080',
        // 3. 开启跨域
        changeOrigin: true,
      }
    }
  }
})
