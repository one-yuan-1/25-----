<!--这是查看别人卡片的组件-->

<template>

    <div class = "random">

        <div class="main_window">

            <!-- 三张卡片，中间是现在的，其余两张是上一张和下一张-->
            <div class="last_card view_cards" @click="click_last"></div>
            <div class="current_card view_cards" @click="click_current"></div>
            <div class="next_card view_cards" @click="click_next"></div>

        </div>
    </div>


</template>

<script setup>
import axios from 'axios';
import { onMounted } from 'vue';

// 初始化显示，发请求
function init_display(un) {
    // 这里可以添加初始化显示的逻辑
    console.log("初始化显示，发请求");
    const params = new URLSearchParams();
    params.append("user", un); // 参数名和后端一致
    axios.post('/api/viewRandom', params)//.then(response => {
        // 处理响应数据，更新页面显示
        //console.log(response.data);
    //})
}

onMounted (() => {
    //获取到用户名
    const urlParams = new URLSearchParams(window.location.search);
    const username = urlParams.get('user');
    console.log("用户名:", username);
    init_display(username);
})

//请求下一张的函数，发请求
function click_next(){
    console.log("点击了下一张卡片");
    axios.post('/api/next_card', {
        // 这里可以传递一些参数，比如当前卡片的ID或者用户ID

    }).then(response => {
        // 处理响应数据，更新页面显示
        //console.log(response.data);
    })
}

//请求上一张的函数,查看缓存
function click_last(){
    console.log("点击了上一张卡片");

}



//点击当前图片的函数，是否点赞

</script>



<style>
@import "./view_random.css";
</style>