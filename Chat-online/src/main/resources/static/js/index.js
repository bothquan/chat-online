//开始点击登录
$(document).ready(
    function(){
        $("#login").click(function(){
            $("#logintext_id").toggle("slow");
            var regis = document.getElementById('register_id');
            // if(regis.style.display == 'block'){
            regis.style.display = 'none';
            //    document.getElementById("logintext_id").style.display='none';
            // }
        });
    });

// 注册用户，缩小登录，转化注册
$(document).ready(
    function(){
        $("#skip_register").click(function(){
            // 隐藏登录
            $("#logintext_id").toggle("slow");
            // 显示注册
            $("#register_id").toggle("slow");
        });

    });

//切换框
$(document).ready(
    function(){
        $("#skip_login").click(function(){
            // 隐藏注册
            $("#register_id").toggle("slow");
            //如果开始有提示屏蔽掉
            document.getElementById("register_check").innerHTML=null;
            //将输入框清空
            $("#username1").val("");
            $("#password1").val("");


            // 显示登录
            $("#logintext_id").toggle("slow");
            document.getElementById("login_check").innerHTML = null;
            $("#username").val("");
            $("#password").val("");
        });

    });

//账号已经登录还是不存在
$(document).ready(
    function(){

        $("#username").blur(function(){
            $.ajax({
                url: '/check_username',
                data:{"username":$("#username").val(),"password":null},
                type: 'post',
                dataType:'JSON',
                success:function(data){
                    if(data.in_login=="当前用户不存在"||data.in_login=="当前用户已经登录") {
                        document.getElementById("login_check").innerHTML = data.in_login;
                        document.getElementById("login_check").style.color = "red";
                    }else{
                        document.getElementById("login_check").innerHTML = null;
                    }
                }
            });
        });
    }
);
//验证账号
$(document).ready(function(){

  if( $("#login_check").text()=="输入错误"||$("#login_check").text()=="账号不能重复登录"){
      document.getElementById("logintext_id").style.display="block";
      document.getElementById("login_check").style.color="red";

  }
    if( $("#register_check").text().length>0){
        document.getElementById("register_id").style.display="block";
        document.getElementById("register_check").style.color="red";

    }
});


//注册

$(document).ready(
    function(){

        $("#username1").blur(function(){
            $.ajax({
                url: '/register_check',
                data:{"username":$("#username1").val(),"password":null},
                type: 'post',
                dataType:'JSON',
                success:function(data){
                    if(data.in_login == "当前名字已注册"){
                        document.getElementById("register_check").innerHTML=data.in_login;
                        document.getElementById("register_check").style.color="red";
                    }else{
                        document.getElementById("register_check").innerHTML=data.in_login;
                        document.getElementById("register_check").style.color="green";
                    }
                }
            });
        });
    }
);

//点击其他部分会隐藏登录
$(document).ready(function(){
    $("body").click(function (){
        $("#logintext_id").style.display="none"
        // 显示注册
        $("#register_id").style.display="none"
    });
});