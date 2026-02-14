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
    springboot3.5.10，mysql，jdk17，maven 3.9.11，数据库mysql  mybatis3.0.3
    构建springboot：
        https://start.spring.io/选好下载压缩包解压
    写完后重新打包：
        mvn clean package
    运行(初次会自动下载依赖)：
        cd springboot_backend
        mvn spring-boot:run     
    查询和终止占用8080端口的进程
        netstat -ano | findstr :8080
        kill


流程图(已过时):             axios发登录请求        根据请求的url不同
    前端myLogin组件---------------->controller类---->service类----->给不同函数处理，统一返回Dto类的对象（统一发挥的结果方便前端处理）
            |                           |                                                       |
            --------------------------->                                                        |springboot自动将后端发送的对象变成了json（序列化）
                axios发送注册请求                                                                |
        根据json提供的信息确认操作是否成功以及后端发来的错误信息和成功后的结果<---前端获取到json<-------
            |                                                                                                        axios请求前端public目录下文件
            -->账号密码正确就跳转到joinform组件然后myLogin组件用bus（第三方库）发送后端传来的两个json文件的地址给joinform组件->joinform---->得到文本信息和图片的路径
                                                                                                                                                |
                                                                           joinform组件把文本和图片渲染上去<---用图片路径axios请求public下具体图片<--
    

        