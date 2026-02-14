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

  console.log(back_url);
  console.log(head_url);
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
</script>

<template>


<div aria-label="A complete example of page header" class="background" :style="{ backgroundImage: 'url(' + back_url + ')' }">
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
          <el-avatar :size="50" v-bind:src="head_url" />
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
          <el-button>Print</el-button>
          <el-button type="primary" class="ml-2">Edit</el-button>
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
        <img :src=value.image :alt="图片" class="card_img">
        <p>{{value.text}}</p>
      </div> 


    </div>
  </div>


</template>

<style scoped>
@import "./JoinForm.css";
</style>