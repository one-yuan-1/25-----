<script setup lang="js">
// 导入 Element Plus 的通知组件并别名
import { ElNotification as notify } from 'element-plus'
import bus from '@/utils/bus'
import { onMounted, onUnmounted } from 'vue'
import { ref } from 'vue';
import axios from "axios";
//import { te } from 'element-plus/es/locale';
// 响应式
const each_text_url = ref(""); //每段文本和图片url
const global_json_url = ref(""); //全局信息url

const each_text=ref({});//每段文本和图片json


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
  each_text_url.value = data.each;
  global_json_url.value = data.global;
  //console.log(each_text.value);
  //console.log(global_json.value);
  //这里用axios访问public目录下资源
  axios.get(each_text_url.value).then(response => {
    each_text.value=response.data;
    console.log(each_text.value);
  });
  axios.get(global_json_url.value).then(response => {
    console.log("获取到的文本内容:", response.data);
    username.value = response.data.Username;
    telephone.value = response.data.Telephone;
    remarks.value = response.data.Remarks;
    address.value = response.data.Address;
    totals.value = response.data.totals;
    back_url.value = response.data.backimage;
    head_url.value=response.data.head_img;

    console.log("用户名:", username.value);
  });
}

// 挂载时监听
onMounted(() => {
  bus.on('brother-data', handleData);
  console.log("监听成功");
})

// 卸载时取消监听（避免内存泄漏）
onUnmounted(() => {
  bus.off('brother-data', handleData)
})
// 定义返回按钮的点击事件处理函数
// 修复：JS 环境下需要传完整的配置对象，而非直接传字符串
const onBack = () => {
  notify({
    title: '提示', 
    message: 'Back',
    type: 'info' 
  })
}

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
/*展示的卡片的样式*/
.card{
  margin-top: 5vh;

  width:40vh;
  height:30vh;
  background-color: white;
  opacity: 0.8;
  align-items: center;
  display: flex;
  /*垂直排布，因flex默认左右排布*/
  flex-direction: column;
  overflow-y: auto; /* 超出显示滚动条 */

  border-radius: 25px;
  /*卡片悬浮效果*/
  transition: transform 0.15s ease-out;
  transform-style: preserve-3d;
}
/*卡片的图片的样式*/
.card_img{
  width: 80%;
  height: 60%;
}

/*最外层容器的样式*/
.background{
  background-size: cover; /* 适配容器大小 */
  background-repeat: no-repeat; /* 不重复 */
  /* 全屏 */
  width: 100%;
  height: 100vh;
}

html{
  /*取消最外层的margin层，防止有白边*/ 
  margin: 0px;
}
body{
  /*取消最外层的margin层，防止有白边*/ 
  margin: 0px;
}

/* 1. 完整穿透面包屑所有样式，确保文字不被截断 */
:deep(.el-breadcrumb) {
  /* 加背景遮罩，提升文字可读性 */
  background: rgba(255, 255, 255, 0.8); 
  padding: 8px 12px;
  border-radius: 4px;
  /* 强制不换行，避免文字截断 */
  white-space: nowrap;
}

.flex {
  display: flex;
}
.items-center {
  align-items: center;
}
.flex-wrap {
  flex-wrap: wrap;
}
.gap-4 {
  gap: 16px; 
}
.ml-2 {
  margin-left: 8px;
}
.text-large {
  font-size: 18px;
}
.font-800 {
  font-weight: 800;
}
.mr-3 {
  margin-right: 12px;
}
.text-sm {
  font-size: 14px;
}
.mr-2 {
  margin-right: 8px;
}



/*添加的按钮的样式 */ 
.cssbuttons-io-button {
  margin-top: 1vh;
  display: flex;
  align-items: center;
  font-family: inherit;
  cursor: pointer;
  font-weight: 500;
  font-size: 16px;
  padding: 0.7em 1.4em 0.7em 1.1em;
  color: white;
  background: #ad5389;
  background: linear-gradient(
    0deg,
    rgba(20, 167, 62, 1) 0%,
    rgba(102, 247, 113, 1) 100%
  );
  border: none;
  box-shadow: 0 0.7em 1.5em -0.5em #14a73e98;
  letter-spacing: 0.05em;
  border-radius: 20em;
}

.cssbuttons-io-button svg {
  margin-right: 6px;
}

.cssbuttons-io-button:hover {
  box-shadow: 0 0.5em 1.5em -0.5em #14a73e98;
}

.cssbuttons-io-button:active {
  box-shadow: 0 0.3em 1em -0.5em #14a73e98;
}
/*删除的按钮的样式 */ 
.del {
  margin-top: 1vh;
  display: flex;
  align-items: center;
  font-family: inherit;
  cursor: pointer;
  font-weight: 500;
  font-size: 16px;
  padding: 0.7em 1.4em 0.7em 1.1em;
  color: rgb(202, 188, 188);
  background: #cfc3ca;
  background: linear-gradient(
    0deg,
    rgb(167, 64, 20) 0%,

    rgb(247, 102, 102) 100%
  );
  border: none;
  box-shadow: 0 0.7em 1.5em -0.5em red;
  letter-spacing: 0.05em;
  border-radius: 20em;
}
del svg {
  margin-right: 6px;
}

del:hover {
  box-shadow: 0 0.5em 1.5em -0.5em red ;
}

del:active {
  box-shadow: 0 0.3em 1em -0.5em red ;
}
</style>