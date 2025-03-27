



new Vue({
    el: "#app",
    data() {
        return {
            me: null, //[id,username,img]
            other: {
                "userId": 0,
                "nickName":"选择聊天对象",
                "img": "https://animationvisarts.com/wp-content/uploads/2016/10/logo-2-3.jpg"
            },//[id,username,img]
            id_me: "",// 我的id
            
            id_to: "",// 对方的id
            send_message: "",//输入的消息
            /**
             * onlineUsers:{
             *  "userId" : {"userId":"nickName":"..." , 
             *              "img":"..." , 
             *              "unread_messages":[],
             *              "num_unread_messages":0}
             * }
             */
            onlineUsers: {}, // 在线用户
            num_message:0, // 未读消息总数量
            every_user_message:[],//每个用户的消息
            scroll:null,
            myMessage: false,
            direction: 'rtl',
        }
    },
    methods: {
        // 获取自己的信息
        getMe() {
            let _this = this

            axios({
                method: "get",
                url: "http://localhost:8080/user/info/" + _this.id_me,
            }).then(function (resp) {
                if (!(resp.data.code == 200)) {
                    _this.$message({
                        message: resp.data.message,
                        type: 'error'
                    });
                    if (resp.data.code < 200) {
                        localStorage.removeItem('authorization');
                        localStorage.removeItem('id');

                        setTimeout(function () {
                            window.location.href = "./login.html"
                        }, 1500)
                    }
                } else {
                    _this.me = resp.data.data
                }

            })
        },


        // 选择聊天对象
        goChat(id,nickName,img){
            this.other.userId = id;
            this.other.nickName = nickName
            this.other.img = img
            this.myMessage = false
            $('#talk_list').empty()

            this.getChatRecord(this.id_me,id,this.me.img,img)

        },

        // 获取历史记录
        getChatRecord(from ,to , me_img , to_img){
            let _this = this
            let message = {
                "from" : from,
                "to":to
            }
            axios({
                method: "post",
                url: "http://localhost:8080/message/record" ,
                data: message
            }).then(function (resp) {
                if (!(resp.data.code == 200)) {
                    _this.$message({
                        message: resp.data.message,
                        type: 'error'
                    });

                } else {
                    chatRecord = resp.data.data
                    _this.showChatRecord(chatRecord , me_img,to_img)
                    _this.num_message-=_this.onlineUsers[to]["num_unread_messages"]
                    _this.onlineUsers[to]["num_unread_messages"]=0
                }

            })
        },

        // 聊天记录重现
        showChatRecord(records,me_img,to_img){
            text1_to= '<li class="left_word"><img class="avatar"  src='
            text1_me= '<li class="right_word"><img class="avatar"  src='
            text2= '> <span>'
            text3 = '</span></li>'

            for(let i =0 ;i < records.length; i++){
                let parts= records[i].split(":",2) // id , text
                if(parts[0] == this.id_me){
                    let text = text1_me+me_img+text2+parts[1]+text3
                    $('#talk_list').append(text)
                }else if (parts[0] == this.other.userId){
                    let text = text1_to+to_img+text2+parts[1]+text3
                    $('#talk_list').append(text)
                }
                
            }
            let scroll=document.getElementById('main')
            scroll.scrollTop=scroll.scrollHeight
        },


        // 发送消息
        send() {
            var text = $('#ipt').val().trim()
            if (text.length <= 0) {
                return $('#ipt').val('')
            }
            // 如果用户输入了聊天内容，则将聊天内容追加到页面上显示
            $('#talk_list').append('<li class="right_word"><img class="avatar"src=' + this.me.img + ' /> <span>' + text + '</span></li>')
            $('#ipt').val('')
            // 重置滚动条的位置
            // resetui();
            let scroll=document.getElementById('main')
            scroll.scrollTop=scroll.scrollHeight
            //给服务器发送消息
            
            // 消息格式
            var data = {}
            data["from"] = this.id_me
            data["to"] = this.other.userId
            data["text"] = this.send_message
            
            this.ws.send(JSON.stringify(data))

            this.send_message = ""
            

        },
        // 初始化
        init() {
            this.ws = new WebSocket("ws://localhost:8080/webSocket/"+this.id_me);
             

            
            let _this = this

            this.ws.onmessage = function(event) {
                var message=JSON.parse(event.data);
                if (message.to==-1){ // 群发消息

                }else if(message.to==0){ // 上线消息
                    onlineUsers = JSON.parse(message.text)
                    _this.onlineUsers ={}
                    console.log(onlineUsers)
                    for(let i = 0  ; i<onlineUsers.length;i++){
                        onlineUser = onlineUsers[i];
                        
                        if (onlineUser.userId == _this.id_me) continue;

                        
                        _this.onlineUsers[onlineUser.userId]=onlineUser
                        

                }
            }
                else{ // 私聊消息
                    if (message.to == _this.id_me ) {
                        


                        if(message.from == _this.other.userId){ // 接收到的消息刚好是正在聊天的对象
                            setTimeout(function () {
                                $('#talk_list').append('<li class="left_word"><img class="avatar"  src=' + _this.other.img + ' /> <span>' + message.text + '</span></li>')
                                let scroll=document.getElementById('main')
                                scroll.scrollTop=scroll.scrollHeight
                                
                            }, 2)
                        }else{
                            _this.num_message+=1 
                            if(_this.onlineUsers[message.from]["unread_messages"] == undefined){
                                _this.onlineUsers[message.from]["num_unread_messages"] =0
                            }


                            _this.onlineUsers[message.from]["num_unread_messages"]++


                            
                        }

    
                    }
                }
            }

        },
        // 获取uri中的参数
        getValue(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
            var r = window.location.search.substr(1).match(reg);  //匹配目标参数
            if (r != null) return unescape(r[2]); return null; //返回参数值
        }
    },
    mounted() {
        this.id_me = this.getValue("me");
        this.init()
        this.getMe()
    }
})









