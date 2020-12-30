function checkForm(){
	var pass = editPassword.nuovaPassword;

	var patternPass = /^[a-zA-Z0-9.]{8,20}$/g;
	
	$(".error").html("");
	
	if(!patternPass.test(pass.value)){
		spanError = document.getElementById("errorPassword");
		spanError.innerHTML = "La password: <br/>" +
				"-deve essere lunga tra gli 8 e 20 caratteri; <br/>" +
				"-pu&ograve; contenere solo lettere, numeri e caratteri come \".\")";
		pass.focus();
		
		return false;
	}
		
	return true;
}