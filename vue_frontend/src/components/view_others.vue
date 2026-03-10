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




    <div class = "select">
        <div>
            <div>请输入您想查看的用户</div>
            <form>
                <input
                placeholder="请输入您想查看的用户名"
                />

            </form>
            <button>提交</button>

        </div>

    </div>



</template>

<script setup>
import axios from 'axios';

//请求下一张的函数
function click_next(){
    console.log("点击了下一张卡片");
    axios.post('/api/next_card', {
        // 这里可以传递一些参数，比如当前卡片的ID或者用户ID

    }).then(response => {
        // 处理响应数据，更新页面显示
        //console.log(response.data);
    })
}

//请求上一张的函数
function click_last(){
    console.log("点击了上一张卡片");
    axios.post('/api/last_card', {
        // 这里可以传递一些参数，比如当前卡片的ID或者用户ID
    }).then(response => {
        // 处理响应数据，更新页面显示
        //console.log(response.data);
    })
}



//点击当前图片的函数，是否点赞

</script>



<style>
@import "./view_others.css";
</style>