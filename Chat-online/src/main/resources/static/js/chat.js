// 点击聊天出现好友选择
// $(document).ready(
//     function(){
//         $("#chat_online").click(function(){
//             $("#chat_online_frined").toggle("slow");
//         });
//     });

//在线人数监控
$(document).ready(function(){
    setInterval(update,4000);//4秒更新
    function update(){
        $.ajax({
            url: '/auto',
            type: 'get',
            async:false,//false代表只有在等待ajax执行完毕后才执行
            dataType:'json',
            success:function(data){
               // console.log(data);
                var obj = JSON.stringify(data);
                var num = obj.charAt(7);
                if(obj.charAt(8)!='}')
                    num+=obj.charAt(8);
                document.getElementById("onlinennum").innerText ='在线人数:'+num;
                person();
            },
        });
    }
    //setInterval(person,4000);//4秒更新
    function person(){
        $.ajax({
            url: "/refreashfri",
            type: "get",
            success:function(data){
                console.log(123);
                $("#frined_id").html(data);
            }
        })
    }
});