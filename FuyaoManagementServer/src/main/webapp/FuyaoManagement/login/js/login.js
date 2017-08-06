$(function(){
	inputFocusChanged();
});

function inputFocusChanged(){
	$("#login-phone-input").focus(function(){
		$("#login-phone-tip").removeClass()
		                         .addClass("tip-message-invisibility");
	});
	$("#login-password-input").focus(function(){
		$("#login-password-tip").removeClass()
		                         .addClass("tip-message-invisibility");
	});
}

function login(){
	var isLogin = true;
	var phone = $("#login-phone-input").val();
	var password = $("#login-password-input").val(); 
	
	if (!(/^[A-Za-z0-9]{6,15}$/).test(password)){
		$("#login-password-tip").text("请填写正确的密码");
		$("#login-password-tip").removeClass()
		                         .addClass("tip-message-visibility");
		isLogin = false;
	}
	
	if(isLogin) {
		$("#login-button").attr("disabled", true);
		var userMessage = {
			"account" : phone,
	        "password" : password
			};
		$.ajax({
			type : "post",
            dataType : "json",
            data : JSON.stringify(userMessage),
            contentType: "application/json; charset=utf-8",
			url:"http://localhost:8080/FuyaoManagementServer/manager/login",
			async:true,
			success : function(data) {
				alert(data.result);
                if ("fault" == data.result) {
                    $("#login-phone-tip").text("用户名或密码错误");
					$("#login-phone-tip").removeClass()
		                         		 .addClass("tip-message-visibility");
		            $("#login-password-tip").text("用户名或密码错误");
					$("#login-password-tip").removeClass()
		                         			.addClass("tip-message-visibility");
		           $("#login-button").attr("disabled", false);
                } else if ("success" == data.result) {  
                	$("#login-button").attr("disabled", false);
                    var indexUrl = window.location.protocol+"//"+window.location.host+"/FuyaoManagementServer/FuyaoManagement/index.html";
                    window.location = indexUrl;
                }
            },
            error : function() {
                alert("服务器发生故障，请尝试重新登录！");
            }
		});
	}	
}
