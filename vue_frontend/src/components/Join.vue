<script setup lang="ts">
import { ElNotification as notify } from 'element-plus'

const onBack = () => {
  notify('Back')
}
</script>

<template>
<div aria-label="A complete example of page header">
    <el-page-header @back="onBack">
      <template #breadcrumb>
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: './page-header.html' }">
            博客主页
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            <a href=" ">我的博客</a >
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
    <img
      src="/06.png"
      style="width: 100%"
    />
  </el-card>

  <el-card style="max-width: 480px">
    <template #header>学习汇报2</template>
    <img
      src="/02.png"
      style="width: 100%"
    />
  </el-card>

  <el-card style="max-width: 480px">
    <template #header>项目展示1</template>
    <img
      src="/03.png"
      style="width: 100%"
    />
  </el-card>

  <el-card style="max-width: 480px">
    <template #header>项目展示2</template>
    <img
      src="/05.png"
      style="width: 100%"
    />
  </el-card>

  </div>

  </div>
</template>

<style scoped>

</style>