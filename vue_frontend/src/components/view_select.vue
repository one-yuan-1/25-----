<template>

    <div class = "select">
        <div class = "select-input">
            <div>请输入您想查看的用户</div>
            <form>
                <input
                id = "user"
                placeholder="请输入您想查看的用户名"
                />

            </form>
            <button @click="init_display">提交</button>

        </div>

        <div v-for="(value,key) in lst_Each_cards" v-bind:key=key class="view_cards">
            <p>{{ value.username }}</p>
            <p>{{ value.title }}</p>
            <img :src="'data:image/png;base64,' + lst_bytes[key]"  :alt="图片" class="card_img">
            <p>{{ value.text }}</p>

        </div>

    </div>


</template>

<script setup>
import axios from 'axios';
import { ref } from 'vue';

const lst_Each_cards = ref([]);
const lst_bytes = ref([]);
// 初始化显示，发请求
function init_display() {
    //获取到用户名
    let input = document.getElementById("user").value;
    console.log("用户名:", input);
    
    // 这里可以添加初始化显示的逻辑
    console.log("初始化显示，发请求");
    const params = new URLSearchParams();
    params.append("user", input); // 参数名和后端一致
    axios.post('/api/viewRandom', params).then(response => {
        // 处理响应数据，更新页面显示
        console.log(response.data);
        lst_Each_cards.value = response.data.lst_Each_cards;
        lst_bytes.value = response.data.file_res.lst_bytes;


    })
}

 




</script>

<style>
@import "./view_select.css";
</style>