function setUserDelete(user){
	$("input[name=usernameDelete]").val(user);
}

function checkPasswordAdmin(){
	var jsonObj = {};
	jsonObj.password = $("input[name=passwordAdmin]").val();
	var jsonStr = JSON.stringify(jsonObj);
	
	
	$.ajax({
		url:"./CheckPasswordAdmin",
		type: "POST",
		data: "json=" + encodeURIComponent(jsonStr),
		success: function(result){					  
				   	if(result.esito === false)
				   		$("#error").html("La password non corrisponde");
				   	else if(result.esito === true)
				   		$("#deleteForm").submit();
			}
	});
	
}

function setUserRank(username, rank){
	$("input[name=usernameRankUp]").val(username);
	$("input[name=rank]").val(rank);
}