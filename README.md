这个是项目的说明文档
.gitgnore是记录git忽略哪些文件夹
前端：
    vue3，npm10.9.3
    构建vue(我已经创建完了，你们不用再创建了)：
        1.选一个文件夹然后终端里输入npm install -g @vue/cli（安装依赖）
        2.vue create my-vue-project（构建项目）
        3.选vue3（选版本，用上下和enter键）
        4.创建.gitgnore文件并写入node_modules/让git忽略这个文件夹(文件太多了，都是第三方库)
    拉取后;
        1.先cd vue_frontend 再npm install安装node_modules/下的第三方库（之前git忽略了）
    运行：
        cd vue_frontend（如果已经在这个路径下的话就不用切换路径了）
        npm run dev
后端(我已经创建完了，你们不用再创建了)：
    springboot3.5.10，mysql，jdk17，maven 3.9.11，数据库mysql
    构建springboot：
        https://start.spring.io/选好下载压缩包解压
    写完后重新打包：
        mvn clean package
    运行(初次会自动下载依赖)：
        cd springboot_backend
        mvn spring-boot:run     


        