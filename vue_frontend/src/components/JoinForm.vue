<script setup lang="js">
// 导入 Element Plus 的通知组件并别名
import { ElNotification as notify } from 'element-plus'
import bus from '@/utils/bus'
import { onMounted, onUnmounted } from 'vue'
import { ref } from 'vue';
const img_url = ref(''); // 响应式
// 监听事件
const handleData = (data) => {
  console.log('从A收到的数据:', data);
  img_url.value = data.imgurl+"/1.png";
  console.log(img_url.value)
}

// 挂载时监听
onMounted(() => {
  bus.on('brother-data', handleData)
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
</script>

<template>
<div aria-label="A complete example of page header">
    <el-page-header @back="onBack">
      <template #breadcrumb>
        <el-breadcrumb separator="/">
          
          <el-breadcrumb-item :to="{ path: '/page-header' }">
            博客主页
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            <a href="javascript:;">我的博客</a> 
          </el-breadcrumb-item>
        </el-breadcrumb>
      </template>
      <template #content>
        <div class="flex items-center">
          <el-avatar :size="50" src="/me.jpg" />
          <span class="text-large font-800 mr-3"> 欢迎来到张承安的博客空间</span>
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

      <el-descriptions :column="3" size="small" class="mt-4">
        <el-descriptions-item label="Username">
          Zhangchengan
        </el-descriptions-item>
        <el-descriptions-item label="Telephone">
          13822223630
        </el-descriptions-item>
        <el-descriptions-item label="Place">Guangzhou</el-descriptions-item>
        <el-descriptions-item label="Remarks">
          <el-tag size="small">大学生</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="Address">
          应急管理大学
        </el-descriptions-item>
      </el-descriptions>
    </el-page-header>

    <div class="flex flex-wrap gap-4">
      <el-card style="max-width: 480px">
        <template #header>学习汇报1</template>
        <img v-bind:src="img_url" style="width: 100px;height: 50px;" />
      </el-card>

      <el-card style="max-width: 480px">
        <template #header>学习汇报2</template>
        <img src="/02.png" style="width: 100%" />
      </el-card>

      <el-card style="max-width: 480px">
        <template #header>项目展示1</template>
        <img src="/03.png" style="width: 100%" />
      </el-card>

      <el-card style="max-width: 480px">
        <template #header>项目展示2</template>
        <img src="/05.png" style="width: 100%" />
      </el-card>
    </div>
  </div>
</template>

<style scoped>

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
.mt-4 {
  margin-top: 16px;
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
</style>