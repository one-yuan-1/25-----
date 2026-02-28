<template>

  <div class="login-container">
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
        <a href="#" class="link" @click="jump">注册账号</a>
      </div>
    </div>

    
    <div class="footer-decoration">
      <div class="small-clovers">🍀 💙 🍀</div>
    </div>
  </div>

</template>

<script setup>
//import { nextTick  } from 'vue'
//import { ElMessage } from 'element-plus'
import axios from "axios";
import bus from '@/utils/bus'
import { useRouter } from 'vue-router'
//import { push } from 'core-js/core/array';

const router = useRouter();

//阻止表单默认行为
const handleLogin = (e) => {
    e.preventDefault()
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
  let each_card = [];
  let global_text = {};
  let lst_bytes_picture = [];
  let user="";
  axios.post("/api/login", params).then(response => {
              console.log(response.data);
              each_card = response.data.lst_Each_cards;
              global_text = response.data.global_text;
              lst_bytes_picture = response.data.file_res.lst_bytes ;
              //console.log(each_card);
              //console.log(global_text);
              if(response.data.code==0){
                alert("登录失败:"+response.data.error_msg);
              }
              else{
                router.push('/Join').then(()=>{
                    setTimeout(() => {
                      bus.emit('brother-data', {
                        each:each_card,
                        global:global_text,
                        lst_bytes_picture,
                        un:input_username
                      });

                    })
                })

                alert("登录成功!");
                


              }
            })
}

function jump(){
  router.push('jump-r')
}

</script>

<style scoped >
@import "./myLogin.css";

</style>