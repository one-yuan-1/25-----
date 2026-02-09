<template>
  <div v-if="!isLogin" class="login-container">
    <div class="bg-bubbles">
      <div class="bubble bubble-1"></div>
      <div class="bubble bubble-2"></div>
      <div class="bubble bubble-3"></div>
    </div>
    
    <div class="header-decoration">
      <div class="clover-icon">🍀</div>
      <h1 class="title">TFB 专属博客</h1>
      <p class="subtitle">Cherish every moment, share your light</p>
    </div>

    
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

        <button type="submit" class="login-btn" @click="login">立即登录</button>
      </form>

      <div class="extra-links">
        <a href="#" class="link">忘记密码？</a>
        <span> | </span>
        <a href="#" class="link" @click="register">注册账号</a>
      </div>
    </div>

    
    <div class="footer-decoration">
      <div class="small-clovers">🍀 💙 🍀</div>
    </div>
  </div>

  <JoinForm v-else />

</template>

<script setup>
import { ref } from 'vue'
import JoinForm from './components/JoinForm.vue' 
import { ElMessage } from 'element-plus'
import axios from "axios";
import bus from '@/utils/bus'
// 控制页面切换的变量
const isLogin = ref(false)

const handleLogin = (e) => {
  e.preventDefault()
  ElMessage.success({
    message: '登录成功！欢迎来到你的专属博客',
    center: true, 
    duration: 1500, // 1.5秒后自动关闭
    customClass: 'custom-login-message' // 自定义样式类
    
  })
  
  isLogin.value = true
}
//点击登录后触发的函数
function login(){
    //给后端发请求
  //构建表单格式参数（适配后端的@RequestParam）
  const params = new URLSearchParams();
  let input_username = document.getElementById("username").value;
  let input_password = document.getElementById("password").value;
  params.append("username", input_username); // 参数名和后端一致
  params.append("password", input_password);

  //console.log("用户名:", input_username);
  //console.log("密码:", input_password);
  let img = '';
  let text = '';
  axios.post("/api/login", params).then(response => {
              console.log(response.data);
              img = response.data.addressImg;
              text = response.data.addressJson;
              if(response.data.code==0){
                alert("登录失败:"+response.data.error_msg);
              }
              else{
                alert("登录成功!");
                bus.emit('brother-data', {
                  imgurl:img,
                  texturl:text
                })
              }
            })
}
//点击注册后触发的函数
function register(){
  const params = new URLSearchParams();
  let input_username = document.getElementById("username").value;
  let input_password = document.getElementById("password").value;
  params.append("username", input_username); // 参数名和后端一致
  params.append("password", input_password);
    axios.post("/api/register", params).then(response => {
              console.log(response.data);
              if(response.data.code==0){
                alert("注册失败:"+response.data.error_msg);
              }
              else{
                alert("注册成功!");
              }
            })

}
</script>

<style scoped>

.login-container {
  min-height: 100vh;
  background: 
    linear-gradient(135deg, rgba(224, 247, 250, 0.8) 0%, rgba(255, 255, 255, 0.9) 50%, rgba(224, 247, 250, 0.8) 100%),
    linear-gradient(45deg, #e0f7fa, #ffffff);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 2rem;
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  position: relative;
  overflow: hidden;
}

.bg-bubbles {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0;
  pointer-events: none;
}
.bubble {
  position: absolute;
  border-radius: 50%;
  background: rgba(79, 195, 247, 0.1);
  animation: bubble-float 8s infinite ease-in-out;
}
.bubble-1 {
  width: 120px;
  height: 120px;
  top: 20%;
  left: 10%;
  animation-delay: 0s;
}
.bubble-2 {
  width: 80px;
  height: 80px;
  top: 60%;
  right: 15%;
  animation-delay: 2s;
}
.bubble-3 {
  width: 100px;
  height: 100px;
  bottom: 20%;
  left: 30%;
  animation-delay: 4s;
}
@keyframes bubble-float {
  0% { transform: translateY(0) scale(1); opacity: 0.2; }
  50% { transform: translateY(-20px) scale(1.1); opacity: 0.4; }
  100% { transform: translateY(0) scale(1); opacity: 0.2; }
}


.header-decoration {
  text-align: center;
  margin-bottom: 2rem;
}

.clover-icon {
  font-size: 3rem;
  animation: pulse 2s infinite ease-in-out;
  color: #4fc3f7;
  text-shadow: 0 4px 8px rgba(79, 195, 247, 0.2);
}

@keyframes pulse {
  0% { transform: scale(1); }
  50% { transform: scale(1.1); }
  100% { transform: scale(1); }
}

.title {
  font-size: 2.5rem;
  font-weight: 700;
  background: linear-gradient(45deg, #0288d1, #4fc3f7);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  margin: 0.5rem 0;
  text-shadow: none;
}

.subtitle {
  color: #0288d1;
  opacity: 0.7;
  font-size: 1.1rem;
}


.login-card {
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  width: 100%;
  max-width: 400px;
  border-radius: 28px;
  padding: 2.5rem;
  box-shadow: 0 12px 40px rgba(79, 195, 247, 0.15);
  text-align: center;
  border: 1px solid rgba(79, 195, 247, 0.1);
  position: relative;
  z-index: 1;
  animation: card-float 6s infinite ease-in-out;
}
@keyframes card-float {
  0% { transform: translateY(0); }
  50% { transform: translateY(-5px); }
  100% { transform: translateY(0); }
}

.card-header h2 {
  background: linear-gradient(45deg, #0288d1, #4fc3f7);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  font-size: 1.8rem;
  margin-bottom: 0.5rem;
}

.card-subtitle {
  color: #666;
  font-size: 0.9rem;
  margin-bottom: 2rem;
}


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
  border-radius: 30px;
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


.login-btn {
  width: 100%;
  padding: 1rem;
  background: linear-gradient(45deg, #4fc3f7, #81d4fa);
  background-size:200% 200%;
  color: #fff;
  border: none;
  border-radius: 25px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  transition: transform 0.3s ease;
  animation: gradient-shift 3s ease-in-out infinite;
}
@keyframes gradient-shift {
  0% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
}

.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(79, 195, 247, 0.3);
}


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

:deep(.custom-login-message) {
  background: linear-gradient(135deg, #e0f7fa 0%, #ffffff 100%);
  border: 1px solid rgba(79, 195, 247, 0.3);
  border-radius: 16px;
  box-shadow: 0 8px 24px rgba(79, 195, 247, 0.15);
  color: #6fbee9;
  font-weight: 500;
  position: fixed !important;
  top: 50% !important;
  left: 50% !important;
  transform: translate(-50%, -50%) !important;
  z-index: 9999 !important;
  width: auto !important;
  min-width: 320px !important;
  text-align: center !important;
}


:deep(.custom-login-message .el-message__icon) {
  color: #67c3ee;
  margin-right: 8px;
}
</style>