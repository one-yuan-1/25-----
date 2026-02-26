<template>
    <div class="register-card">
      <div class="header_decoration">
         <div class="clovericon">🍀</div>
         <h1 class="title-header">TFB 专属博客</h1>
         <p class="sub-title">Cherish every moment, share your light</p>
      </div>
      <div class="card-re">
          <div class="register">
            <h2>用户注册</h2>
            <p>Please register your account</p>
          </div>

        <form class="form-re" @submit.prevent="register">
            <div class="register-set">
                <label for="register_username">用户名</label>

                <input 
                type="text"
                id="register_username"
                placeholder="请输入你的用户名"
                class="register-field"
                />

            </div>
            
            <div class="register-set">
                <label for="register_password">账号密码</label>

                <input 
                type="password"
                id="register_password"
                placeholder="请输入你的账号密码(6位密码)"
                class="register-field"
                />
                

            </div>

            <div class="register-set">
                <label for="re-register_password">确认密码</label>

                <input 
                type="password"
                id="re-register_password"
                placeholder="请确认你的账号密码"
                class="register-field"
                />
                

            </div>
            <button type="submit" class="register-btn" >注册</button>
            <button class="register-button" @click="return_my_login">返回登录</button>

        </form>
        <button class="eye1"  id= "bt-eye1" type="button" @click="eye1"></button>
        <button class="eye2" id= "bt-eye2" type="button" @click="eye2"></button>

      </div>
    

     <div class="reg-footer-decoration">
       <div class="pinwheel">🎐 🪁 🎐</div>
     </div>
    </div>

</template>

<script setup>
import axios from 'axios';
import { useRouter } from 'vue-router';
const router = useRouter();
//点击注册后触发的函数
function register(){
  let re_password = document.getElementById("re-register_password").value;  
  let input_username = document.getElementById("register_username").value;
  let input_password = document.getElementById("register_password").value;
  if(re_password === input_password && input_username != ''){
    const params = new URLSearchParams();
    params.append("username", input_username); // 参数名和后端一致
    params.append("password", input_password);
    axios.post("/api/register", params).then(response => {
              console.log(response.data);
              if(response.data.code==0){
                alert("注册失败:"+response.data.error_msg);
              }
              else{
                alert("注册成功!");
                router.push("/")
                
              }
            })

  }else if(re_password != input_password){
    alert("您输入的两次密码不一致，请重新输入");
    document.getElementById("re-register_password").value = '';
  }else if(input_username === ''){
    alert("用户名不能为空");
  }
  

}

function return_my_login(){
  router.push("/");
}

let is_show1 = false;
function eye1(){
  
  let input1 = document.getElementById("register_password");
  if(!is_show1){
     input1.type = "text";
     is_show1 = true;
  }else{
    input1.type = "password";
     is_show1 = false;

  }
  
  
}

let is_show2 = false;
function eye2(){
  let input2 = document.getElementById("re-register_password")
  if(!is_show2){
     input2.type = "text";
     is_show2 = true;
  }else{
    input2.type = "password";
     is_show2 = false;
}
}
</script>

<style scoped >
@import"./registerPage.css";

</style>