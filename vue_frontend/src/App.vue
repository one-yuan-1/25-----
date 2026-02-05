<template>
  <div class="login-container">
    <!-- 顶部装饰区 -->
    <div class="header-decoration">
      <div class="clover-icon">🍀</div>
      <h1 class="title">TFB 专属博客</h1>
      <p class="subtitle">Cherish every moment, share your light</p>
    </div>

    <!-- 登录卡片 -->
    <div class="login-card">
      <div class="card-header">
        <h2>用户登录</h2>
        <p class="card-subtitle">Welcome to sign in to your exclusive blog account</p>
      </div>

      <form class="login-form" @submit="handleLogin">
        <div class="input-group">
          <label for="username">用户名</label>
          <input
            type="text"
            id="username"
            placeholder="请输入你的用户名"
            class="input-field"
          />
        </div>

        <div class="input-group">
          <label for="password">密码</label>
          <input
            type="password"
            id="password"
            placeholder="请输入你的登录密码"
            class="input-field"
          />
        </div>

        <button type="submit" class="login-btn">立即登录</button>
      </form>

      <div class="extra-links">
        <a href="#" class="link">忘记密码？</a>
        <span> | </span>
        <a href="#" class="link">注册账号</a>
      </div>
    </div>

    <!-- 底部装饰 -->
    <div class="footer-decoration">
      <div class="small-clovers">🍀 💙 🍀</div>
    </div>
  </div>
</template>

<script setup>
const handleLogin = (e) => {
  e.preventDefault();
  alert('登录成功！欢迎来到你的专属博客');
};
</script>

<style scoped>
/* 全局容器 */
.login-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #e0f7fa 0%, #ffffff 50%, #e0f7fa 100%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 2rem;
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
}

/* 头部装饰 */
.header-decoration {
  text-align: center;
  margin-bottom: 2rem;
}

.clover-icon {
  font-size: 3rem;
  animation: pulse 1.5s infinite;
  color: #4fc3f7;
}

@keyframes pulse {
  0% { transform: scale(1); }
  50% { transform: scale(1.1); }
  100% { transform: scale(1); }
}

.title {
  font-size: 2.5rem;
  font-weight: 700;
  color: #0288d1;
  margin: 0.5rem 0;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.subtitle {
  color: #0288d1;
  opacity: 0.8;
  font-size: 1.1rem;
}

/* 登录卡片 */
.login-card {
  background: #fff;
  width: 100%;
  max-width: 400px;
  border-radius: 20px;
  padding: 2.5rem;
  box-shadow: 0 10px 30px rgba(79, 195, 247, 0.2);
  text-align: center;
  border: 1px solid rgba(79, 195, 247, 0.2);
}

.card-header h2 {
  color: #0288d1;
  font-size: 1.8rem;
  margin-bottom: 0.5rem;
}

.card-subtitle {
  color: #666;
  font-size: 0.9rem;
  margin-bottom: 2rem;
}

/* 表单区域 */
.input-group {
  margin-bottom: 1.5rem;
  text-align: left;
  width: 90%;
  margin: 0 auto 1.5rem;
}

.input-group label {
  display: block;
  color: #333;
  font-weight: 500;
  margin-bottom: 0.5rem;
}

.input-field {
  width: 100%;
  padding: 0.9rem 1.2rem;
  border: 2px solid #e0f7fa;
  border-radius: 25px;
  font-size: 1rem;
  transition: all 0.3s ease;
  background: #fbfdfe;
  max-width: 320px;
  margin: 0 auto;
  display: block;
  
}

.input-field:focus {
  outline: none;
  border-color: #4fc3f7;
  background: #fff;
  box-shadow: 0 0 0 3px rgba(79, 195, 247, 0.1);
}

/* 登录按钮 */
.login-btn {
  width: 100%;
  padding: 1rem;
  background: linear-gradient(45deg, #4fc3f7, #81d4fa);
  color: #fff;
  border: none;
  border-radius: 25px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  transition: transform 0.3s ease;
}

.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(79, 195, 247, 0.3);
}

/* 额外链接 */
.extra-links {
  margin-top: 1.5rem;
  font-size: 0.9rem;
}

.link {
  color: #0288d1;
  text-decoration: none;
  transition: color 0.3s ease;
}

.link:hover {
  color: #0277bd;
  text-decoration: underline;
}

/* 底部装饰 */
.footer-decoration {
  margin-top: 2rem;
  color: #0288d1;
  opacity: 0.7;
  font-size: 1.2rem;
}

.small-clovers {
  animation: float 3s ease-in-out infinite;
}

@keyframes float {
  0% { transform: translateY(0); }
  50% { transform: translateY(-10px); }
  100% { transform: translateY(0); }
}
</style>