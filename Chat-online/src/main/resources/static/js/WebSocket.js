var websocket = null;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket('ws://10.214.118.42:8080/websocket');
    }
    else {
        alert('当前浏览器 Not support websocket')
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("WebSocket连接发生错误");
    };

    //连接成功建立的回调方法
    websocket.onopen = function () {

    }

    //接收到消息的回调方法
    websocket.onmessage = function (event) {

        var send_message=document.getElementById("chat_middle_item");
        var date=new Date();
        var hour=date.getHours();
        var mm=date.getMinutes();
        var time=hour+':'+mm;
        var ans='<div class="chat_left_item_1 clearfix">好友</div>'+
        '<div class="chat_left_item_2">'+
        '<div class="chat_time clearfix">'+time+'</div>'+
        '<div class="chat_left_content clearfix">'+event.data+'</div>'
        +'</div>';
        var oLi=document.createElement("div");
        oLi.setAttribute("class","chat_right");
        oLi.innerHTML=ans;
        send_message.append(oLi);
    }

    //连接关闭的回调方法
    websocket.onclose = function () {

    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }

    //将消息显示在网页上
    // function setMessageInnerHTML(innerHTML) {
//     document.getElementById('message').innerHTML += innerHTML + '<br/>';
// }

//关闭WebSocket连接
function closeWebSocket() {
    websocket.close();
}
    // 朋友id
    var friend_id = "";
    //    双击控制
    var double_click=0;
    var type = "";
    //向服务器发送消息
    function send() {
    var data = document.getElementById('chat_context_item').value;
    //以json格式传输{接收者，消息}
    //alert(friend_id);
    console.log(friend_id);
    console.log(type)
    var json = {"toName":friend_id,"message":data,"type":type};
    websocket.send(JSON.stringify(json));

    }
