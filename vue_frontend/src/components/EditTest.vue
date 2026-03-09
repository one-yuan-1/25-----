<!-- 这是编辑个人文本信息的组件 -->
<template>
  <div class="edit-text-page" style="padding: 30px; max-width: 500px; margin: 0 auto">
    <h2>编辑个人文本信息</h2>
<!-- 
    <el-form label-width="80px" style="margin-top: 20px">
      <el-form-item label="用户名">
        <el-input v-model="form.username" />
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.telephone" />
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form.remarks" />
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="form.address" />
      </el-form-item>


      <el-form-item>
        <el-button type="primary" @click="submitEdit">确认修改</el-button>
        <el-button @click="goBack">返回</el-button>
      </el-form-item>
    </el-form> -->
    <div><h2>这是原来的信息</h2></div>
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
  </div>
    <div class="box">
      <div class="item">
        <h6 style="color: aquamarine;">这里用户名不可以改哦</h6>
        <label>用户名：</label>
        <!-- 关键：加 v-model 绑定！！！ -->
        <input type="text" v-model="form.username" id="username">
      </div>
      <div class="item">
        <label>电话：</label>
        <input type="text" v-model="form.telephone" id="telephone">
      </div>
      <div class="item">
        <label>备注：</label>
        <input type="text" v-model="form.remarks" id="remarks">
      </div>
      <div class="item">
        <label>地址：</label>
        <input type="text" v-model="form.address" id="address">
      </div>

    <div class="btns">
        <button id="submit"@click="submitEdit">确认修改</button>
        <button id="back"@click="goBack">返回</button>
    </div>
</div>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()

// 获取路由参数
const { username, telephone, remarks, address, user } = route.query

// 正确定义表单
const form = ref({
  username: username || '',
  telephone: telephone || '',
  remarks: remarks || '',
  address: address || '',
  user: user || ''
})

// 提交修改
const submitEdit = () => {
    //后端使用的是@REquestParam,前端必须使用formdata
    const formData = new FormData()
  formData.append('username', form.value.username)
  formData.append('telephone', form.value.telephone)
  formData.append('remarks', form.value.remarks)
  formData.append('address', form.value.address)
  formData.append('user',  route.query.user)
  axios.post('/api/edit-text', formData)
    .then(res => {
      if (res.data.code === 1) {
        ElMessage.success('修改成功')
        router.back() // 返回上一页
      } else {
        ElMessage.error('失败：' + res.data.error_msg)
      }
    })
    .catch(err => {
      ElMessage.error('网络错误')
    })
}

// 返回
const goBack = () => {
  router.push('/')
}
</script>
   <style>
   @import url(EditTeest.css);
  </style>