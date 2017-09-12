$(function(){
	domain = "http://localhost:8080";
	imgPath = domain + ":1993/ImageResource/";
	requestIP = domain;
	inputFocusChanged();
	enterListener();
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

function enterListener(){
	document.onkeydown=keyListener;   
	function keyListener(e){   
        if(e.keyCode == 13){   
        	login();
        }
    }
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
		var data = new FormData();
		data.append("account", phone);
		data.append("password", password);
		$.ajax({
			type : "post",
            data : data,
            processData: false,
            contentType: false,
			cache: false,
			url: requestIP + "/FuyaoManagementServer/manager/login",
			async:true,
			success : function(data) {
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
                    window.location.href = "/FuyaoManagementServer/FuyaoManagement/index.html";
                }
            },
            error : function() {
                alert("服务器无响应");
            }
		});
	}	
}
