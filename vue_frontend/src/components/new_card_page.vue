<template>
<el-form label-width="auto" style="width: 600px">
      <el-form-item label="态度：">
        <el-radio-group>
          <el-radio value="Sponsor">点赞</el-radio>
          <el-radio value="Venue">吐槽</el-radio>
        </el-radio-group>
      </el-form-item>      
      <el-form-item label="评论：">
        <el-input type="textarea" />
      </el-form-item>
      <el-form-item>
        <el-rate allow-half /> <el-button type="primary" @click="">提交</el-button>
      </el-form-item>
    </el-form>

</template>


<script setup>
//点击卡片跳转至新的页面，可查看别人对自己的评论和点赞

</script>




<style>
@import "./new_card_page.css";
</style>