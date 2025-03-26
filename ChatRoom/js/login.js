


new Vue({
    el: "#app",
    data() {
        return {
            currentWindow:0,//当前处于窗口,0为登陆,1为注册
            LoginForm: { //登陆表单
                userId: "",
                password: "",

            },
            RegisterForm: { // 注册表单
                userId: "",
                password: "",
            }
        }
    },
    methods: {
        // 登录
        login() {
            let _this = this

            axios({
                method: "POST",
                url: "http://localhost:8080/login",
                data: _this.LoginForm
            }).then(function (resp) {

                if (!(resp.data.code == 200)) {
                    _this.$message({
                        message: resp.data.msg,
                        type: 'error'
                    });
                    // 清除密码
                    _this.LoginForm.password = ""
                } else {
                    _this.$message({
                        message: resp.data.msg,
                        type: 'success'
                    });

                    me=_this.LoginForm.userId
                    // 清除表单
                    _this.LoginForm = null
                    // 保存token
                    localStorage.authorization = resp.data.data.password
                    setTimeout(function () {
                        window.location.href = "chat.html?me="+me
                    }, 500)

                }
            })


        },
        // 注册
        register() {
            let _this = this
            axios({
                method: "POST",
                url: "http://localhost:8080/register",
                data: _this.RegisterForm
            }).then(function (resp) {
                if (!(resp.data.code == 200)) {
                    _this.$message({
                        message: resp.data.msg,
                        type: 'error'
                    });
                } else {
                    _this.$message({
                        message: resp.data.msg,
                        type: 'success'
                    });
                }

            })
        },
        // 清除
        clear() {
            this.LoginForm = {}
            this.RegisterForm = {}
        }
    }
})