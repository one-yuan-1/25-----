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
</script>



<template>
  <!-- 唯一的 template 根标签，用 div 包裹所有内容 -->
  <div class="app-container">
    <!-- 悬浮窗页面头部 -->
    <div class="floating-page-header">
      <div 
        class="page-header-card" 
        :style="{ backgroundImage: 'url(data:image/png;base64,' + back + ')' }"
      >
        <el-page-header @back="onBack" class="custom-page-header">
          <!-- 面包屑区域 -->
          <template #breadcrumb>
            <el-breadcrumb separator="/" class="custom-breadcrumb">
              <el-breadcrumb-item :to="{ path: head_url }">
                博客主页
              </el-breadcrumb-item>
              <el-breadcrumb-item>
                <a href=" " class="username-link">{{ username }}</a >
              </el-breadcrumb-item>
            </el-breadcrumb>
          </template>

          <!-- 内容区域 -->
          <template #content>
            <div class="content-wrapper flex items-center">
              <el-avatar :size="50" :src="'data:image/png;base64,' + head" class="user-avatar" />
              <span class="welcome-text text-large font-800 mr-3">
                {{ "欢迎来到" + username + "的博客" }}
              </span>
              <el-tag class="vip-tag">大V</el-tag>
            </div>
          </template>

          <!-- 额外操作区域 -->
          <template #extra>
            <div class="extra-wrapper flex items-center">
              <el-button class="print-btn">Print</el-button>
              <el-button type="primary" class="edit-btn ml-2">Edit</el-button>
            </div>
          </template>

          <!-- 描述信息区域 -->
          <el-descriptions :column="3" size="small" class="custom-descriptions">
            <el-descriptions-item label="Username">
              {{ username }}
            </el-descriptions-item>
            <el-descriptions-item label="Telephone">
              {{ telephone }}
            </el-descriptions-item>
            <el-descriptions-item label="Place">{{ address }}</el-descriptions-item>
            <el-descriptions-item label="Remarks">
              <el-tag size="small">{{ remarks }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="School">
              应急管理大学
            </el-descriptions-item>
          </el-descriptions>
        </el-page-header>
      </div>
    </div>

    <!-- 卡片操作区域 -->
    <div class="card-operation-container">
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
        <div v-for="(value, key) in each_text" class="card" :key="key" @mousemove="cards_move" @mouseleave="cards_leave">
          <p>{{ value.title }}</p >
          < img :src="'data:image/png;base64,' + lst_cards[value.id - 1]" :alt="'图片'" class="card_img">
          <p>{{ value.text }}</p >
        </div> 
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 悬浮窗容器样式 */
.floating-page-header {
  position: fixed; /* 固定悬浮 */
  top: 20px;       /* 距离顶部距离 */
  right: 20px;     /* 距离右侧距离 */
  z-index: 9999;   /* 确保悬浮窗在最上层 */
  width: 800px;    /* 悬浮窗宽度 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); /* 柔和阴影提升层次感 */
  border-radius: 12px; /* 圆角 */
  overflow: hidden;
}

/* 页面头部卡片样式 - 高透明度白色底色 */
.page-header-card {
  background-color: rgba(255, 255, 255, 0.9); /* 白色 + 90% 透明度 */
  background-size: cover;
  background-position: center;
  padding: 16px;
  backdrop-filter: blur(8px); /* 毛玻璃效果，增强视觉体验 */
}

/* 自定义页面头部 */
.custom-page-header {
  --el-page-header-text-color: #303133;
  font-family: "Microsoft YaHei", "PingFang SC", "Helvetica Neue", Arial, sans-serif; /* 美化字体 */
}

/* 面包屑样式 */
.custom-breadcrumb {
  font-size: 14px;
  margin-bottom: 12px;
}

/* 用户名链接样式 */
.username-link {
  color: #409eff;
  text-decoration: none;
  transition: color 0.3s;
}
.username-link:hover {
  color: #66b1ff;
}

/* 内容区域样式 */
.content-wrapper {
  margin-bottom: 16px;
  align-items: center;
}

/* 用户头像样式 */
.user-avatar {
  margin-right: 12px;
  border: 2px solid #f0f2f5;
}

/* 欢迎文本样式 */
.welcome-text {
  font-weight: 600;
  font-size: 18px;
  color: #1f2937;
  letter-spacing: 0.5px;
}

/* VIP标签样式 */
.vip-tag {
  background-color: #f59e0b;
  color: #fff;
  border: none;
}

/* 额外操作按钮样式 */
.extra-wrapper {
  margin-bottom: 16px;
}
.print-btn {
  border-color: #d1d5db;
  color: #4b5563;
}
.edit-btn {
  background-color: #409eff;
  border-color: #409eff;
}

/* 描述信息样式 */
.custom-descriptions {
  --el-descriptions-label-color: #6b7280;
  --el-descriptions-content-color: #1f2937;
  font-size: 14px;
  line-height: 1.6;
}
.custom-descriptions .el-descriptions-item__label {
  font-weight: 500;
}

/* 响应式适配 */
@media (max-width: 900px) {
  .floating-page-header {
    width: calc(100% - 40px);
    left: 20px;
    right: 20px;
  }
  .custom-descriptions {
    --el-descriptions-column: 2;
  }
}

/* 卡片操作区域间距（避免被悬浮窗遮挡） */
.card-operation-container {
  padding-top: 320px; /* 给悬浮窗留出空间 */
  padding-left: 20px;
  padding-right: 20px;
}
</style>

<!-- 引入外部样式 -->
<style scoped>
@import "./JoinForm.css";
</style>


<script setup rangl>
import { ref } from 'vue';

// 悬浮窗相关变量
const back = ref(''); // 背景图base64
const head = ref(''); // 头像base64
const head_url = ref('/blog'); // 博客主页路径
const username = ref('张三'); // 用户名
const telephone = ref('13800138000'); // 电话
const address = ref('北京'); // 地址
const remarks = ref('资深博主'); // 备注

// 卡片操作相关变量
const each_text = ref([
  { id: 1, title: "卡片1", text: "卡片1内容" },
  { id: 2, title: "卡片2", text: "卡片2内容" }
]);
const lst_cards = ref(['', '']); // 卡片图片base64数组

// 返回按钮事件
const onBack = () => {
  window.history.back();
};

// 卡片操作方法
const add = () => {
  console.log("添加卡片");
};

const del = () => {
  console.log("删除卡片");
};

const cards_move = (e) => {
  console.log("鼠标移入卡片", e);
};

const cards_leave = () => {
  console.log("鼠标离开卡片");
};
</script>