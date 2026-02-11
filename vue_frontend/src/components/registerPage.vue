<template>
    <div class="register-card">
        <div class="register">
            <h2>用户注册</h2>
            <p>Please register your account</p>
        </div>

        <form>
            <div>
                <label>用户名</label>

                <input 
                type="text"
                id="register_username"
                placeholder="请输入你的用户名"
                class="register-field"
                />

            </div>
            
            <div>
                <label>账号密码</label>

                <input 
                type="text"
                id="register_password"
                placeholder="请输入你的账号密码(6位密码)"
                class="register-field"
                />

            </div>
            <button type="submit" class="register-btn" @click="register">注册</button>
        </form>

    </div>

    <div class="reg-footer-decoration">
      <div class="pinwheel">🎐 🪁 🎐</div>
    </div>
    

</template>

<script setup>
import axios from 'axios';
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

<style scoped >


</style>