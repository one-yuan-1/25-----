<script setup lang="js">
// 导入 Element Plus 的通知组件并别名
//import { ElNotification as notify } from 'element-plus'
import bus from '@/utils/bus'
import { onMounted,onUnmounted } from 'vue'
import { ref } from 'vue';
import axios from "axios";
import { useRouter } from 'vue-router'

const router = useRouter();

//import { from } from 'core-js/core/array';
//import { te } from 'element-plus/es/locale';
// 响应式
const each_text = ref([]); //每段文本和图片列表
const global_json = ref({}); //全局信息对象


const username = ref('');
const telephone= ref('');
const remarks= ref('');
const address= ref('');
const totals= ref(0);
const back_url = ref('');
const head_url=ref('');

const back = ref('');
const head = ref('');
const lst_cards = ref([]);  
const btn_ref = ref();

//当前用户
const current_user = ref('');
// 监听事件
const handleData = (data) => {
  console.log('从A收到的数据:', data);
  each_text.value = data.each;
  global_json.value = data.global;
  //console.log(each_text.value);
  //console.log(global_json.value);
  username.value=global_json.value.username;
  telephone.value=global_json.value.telephone;
  remarks.value=global_json.value.remarks;
  address.value=global_json.value.address;
  totals.value=global_json.value.totals;
  back_url.value=global_json.value.back_img;
  head_url.value=global_json.value.head_img;
  //当前用户名
  current_user.value=data.un;

  //console.log(back_url);
  //console.log(head_url);
  //头像和背景图片,因为头像固定叫head.jpg,背景固定叫back.jpg,所以直接取最后一个就行了
   head.value=data.lst_bytes_picture[data.lst_bytes_picture.length-1];
   back.value=data.lst_bytes_picture[data.lst_bytes_picture.length-2];
   lst_cards.value=data.lst_bytes_picture.slice(0,data.lst_bytes_picture.length-2);
   console.log(lst_cards.value);
}

// 挂载时监听
onMounted (() => {
  bus.on('brother-data', handleData);
  console.log("监听成功");
})

// 卸载时取消监听（避免内存泄漏）
onUnmounted(() => {
  bus.off('brother-data', handleData)
})
// 定义返回按钮的点击事件处理函数
// 修复：JS 环境下需要传完整的配置对象，而非直接传字符串
//const onBack = () => {
  //notify({
    //title: '提示', 
   // message: 'Back',
    //type: 'info' 
  //})
//}

//卡片悬浮效果
//先获取鼠标位置

function cards_move(e){
    // 当前这张卡
    const card = e.currentTarget; 
    //鼠标在卡上位置
    const rect = card.getBoundingClientRect();
    const x = e.clientX - rect.left;
    const y = e.clientY - rect.top;
    //再算出倾斜角度并应用到卡片上
    const rotateY = (x / rect.width - 0.5) * 20;
    const rotateX = (0.5 - y / rect.height) * 20;
    card.style.zIndex = 10;
    // 3D transform
    card.style.transform = `
        perspective(300px)
        rotateX(${rotateX}deg)
        rotateY(${rotateY}deg)
        scale(1.05)
    `;
}
//恢复原样
function cards_leave(e) {
  const card = e.currentTarget;
  card.style.transform = `
    perspective(300px)
    rotateX(0deg)
    rotateY(0deg)
    scale(1)
  `;
}

//back回到登录页面
function onBack(){
  router.push("/");
  console.log("点击back");
}


//点击播客主页回到登录页面




//添加和删除卡片功能
function add(){

  alert("该功能暂未实现");
}

function del(){
  alert("该功能暂未实现");
}
//编辑头像和背景图片,点击之后先弹出一个框到页面中间，三个选项，改变背景/头像/文本信息
function edit_btn(){
  //先显示出来新菜单
  let menu = document.getElementsByClassName("new_menu")[0];
  menu.style.opacity = "1";
  menu.style.zIndex = "1000";
}
//退出菜单
function exit_menu(){
  let menu = document.getElementsByClassName("new_menu")[0];
  menu.style.opacity = "0";
  menu.style.zIndex = "-1";
}


let type="";
//上传图片的函数
function upload(e){
  const file = e.target.files[0];
  //防止不上传文件]\
  if (!file) {
    alert("请选择一个文件进行上传");
    return;
  };
  //前后端传文件必须用Formdata实例化出来的对象
  const formData = new FormData();
  formData.append('file', file);
  formData.append('user', current_user.value);
  formData.append('type', type);
    axios.post('/api/upload', formData).then(response => {
      console.log(response.data);
      //根据type的不同来把改完后的图片显示出来
      if(response.data.code==0){
        alert("更改失败:"+response.data.error_msg);
      }else{
          let file_res = response.data.file_res;
          if(type=="head"){
            head.value=file_res.lst_bytes[file_res.lst_bytes.length-1];
          }else if(type=="back"){
            back.value=file_res.lst_bytes[file_res.lst_bytes.length-1];
          }
           alert("更改成功!");
           //关闭菜单
           exit_menu();       
      }
    }).catch(error => {
      console.error('上传失败:', error);
      alert("上传失败，请重试");
    });
}


//背景图片上传
function edit_back(){
  type="back";
  btn_ref.value.click();
}
//头像图片上传
function edit_head(){
  type="head";
  btn_ref.value.click();
}
//文本信息编辑
function edit_text(){
  alert("该功能暂未实现");
}
</script>

<template>
  <!--这里是edit按钮弹出的新的菜单--> 
  <div class="new_menu">
    <svg t="1772096946377" @click="exit_menu" class="icon_x" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4201" ><path d="M617.92 516.096l272 272-101.824 101.824-272-272-272 272-101.856-101.824 272-272-275.008-275.04L241.056 139.2l275.04 275.04 275.04-275.04 101.824 101.824-275.04 275.04z" fill="#000000" p-id="4202"></path></svg>
    <div class="menu_item" @click="edit_back">编辑背景图片</div>
    <div class="menu_item" @click="edit_head">编辑头像</div>
    <div class="menu_item" @click="edit_text">编辑文本信息</div>
  </div>
  <!--这里是上传用的--> 
  <input type="file" @change="upload" ref="btn_ref" style="display:none">

<div aria-label="A complete example of page header" class="background" :style="{ backgroundImage: 'url(data:image/png;base64,' + back + ')' }">
    <el-page-header @back="onBack">
      <template #breadcrumb>
        <el-breadcrumb separator="/">
          
          <el-breadcrumb-item :to="{ path: head_url }">
            博客主页
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            <a href="javascript:;">{{username}}</a> 
          </el-breadcrumb-item>
        </el-breadcrumb>
      </template>
      <template #content>
        <div class="flex items-center">
          <el-avatar :size="50" v-bind:src="'data:image/png;base64,' + head" />
          <span class="text-large font-800 mr-3"> {{"欢迎来到"+username+"的博客"}}</span>
          <span
            class="text-sm mr-2"
            style="color: var(--el-text-color-regular)"
          >
          </span>
          <el-tag>大V</el-tag>
        </div>
      </template>
      <template #extra>
        <div class="flex items-center">
          <el-button type="primary" class="ml-2" @click="edit_btn">Edit</el-button>
        </div>
      </template>

      <el-descriptions :column="3" size="small">
        <el-descriptions-item label="Username">
          {{username}}
        </el-descriptions-item>
        <el-descriptions-item label="Telephone">
          {{telephone}}
        </el-descriptions-item>
        <el-descriptions-item label="Place">{{address}}</el-descriptions-item>
        <el-descriptions-item label="Remarks">
          <el-tag size="small">{{remarks}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="School">
          应急管理大学
        </el-descriptions-item>
      </el-descriptions>
    </el-page-header>


 

    <div class="flex flex-wrap gap-4">

     <el-card class="card">
      <!-- 添加的按钮 --> 
      <button class="cssbuttons-io-button" @click="add">
        <svg
          height="24"
          width="24"
          viewBox="0 0 24 24"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path d="M0 0h24v24H0z" fill="none"></path>
          <path d="M11 11V5h2v6h6v2h-6v6h-2v-6H5v-2z" fill="currentColor"></path>
        </svg>
        <span>Add</span>
      </button>
      <!-- 删除的按钮 --> 
      <button class="del" @click="del">
        <svg
          height="24"
          width="24"
          viewBox="0 0 24 24"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path d="M0 0h24v24H0z" fill="none"></path>
          <path d="M11 11V5h2v6h6v2h-6v6h-2v-6H5v-2z" fill="currentColor"></path>
        </svg>
        <span>Delete</span>
      </button>
      </el-card>
<!-- 遍历显示卡片 --> 
      <div v-for="(value,key) in each_text" class="card" v-bind:key=key @mousemove="cards_move" @mouseleave="cards_leave">
        <p>{{value.title}}</p>
        <img :src="'data:image/png;base64,' + lst_cards[value.id-1]"  :alt="图片" class="card_img">
        <p>{{value.text}}</p>
      </div> 


    </div>
  </div>


</template>

<style scoped>
@import "./JoinForm.css";
</style>