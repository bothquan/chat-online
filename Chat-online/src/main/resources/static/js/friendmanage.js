//搜索框
function addfriend(){
    $("#addfriend").toggle("slow");
    document.getElementById("respond_add").style.display="none";
}
//添加回应
function respond_add1(){
    $("#respond_add").toggle("slow");
    // $("#respond_add").load("http://10.214.118.42:8080/responed_add");
    //resposned();
    document.getElementById("addfriend").style.display="none";
}





//加好友显示
$(document).ready(function(){
   if($("#red_clock").text().length>0){
       //document.getElementById("red_clock").innerHTML="用户不存在";
        document.getElementById("red_clock").style.color="red";
        document.getElementById("addfriend").style.display="block";
   }else if($("#add_clock").text().length>0){

       document.getElementById("addfriend").style.display="block";
   }
});



//及时更新添加回应
$(document).ready(function(){
    setInterval(resposned,10000);//没10秒更新一下数据
    function resposned(){
        $.ajax({
            url: "/responed_add",
            type: "post",
            success:function(data){
                $("#table_add").html(data);
            }
        });
    }

});

$(document).ready(function(){
   // 好友
   $("#friends").click(function (){
        $("#friends_ctroller").toggle("slow");
   });
    //家人
    $("#family").click(function (){
        $("#family_ctroller").toggle("slow");
    });
    //陌生人
    $("#person").click(function (){
        $("#person_ctroller").toggle("slow");

    });

});


//建群
function respond_add2(){
    $("#add_group").toggle("slow");
    document.getElementById("addfriend").style.display="none";
    document.getElementById("respond_add").style.display="none";
};



