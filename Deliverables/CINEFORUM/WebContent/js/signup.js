//Validazione form di registrazione
function checkSignForm(){
	var user = signForm.user;
	var email = signForm.email;
	var pass = signForm.password;
	var ripetiPass = signForm.ripetiPassword;
	var spanError; //elemento span dove inserire la scritta per l'errore
	
	var patternUser = /^[a-zA-Z0-9._-]{5,20}$/g;
	var patternMail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/g;
	var patternPass = /^[a-zA-Z0-9.]{8,20}$/g;
	
	$(".error").html("");
	
	if(!patternUser.test(user.value)){
		spanError = document.getElementById("errorUsername");
		spanError.innerHTML = "L'username: <br/>" +
				"-deve essere lungo tra i 5 e 20 caratteri; <br/>" +
				"-pu&ograve; contenere solo lettere, numeri e caratteri speciali come \".\"  \"_\"  \"-\")";
		document.getElementById("errorSign").innerHTML = "";
		user.focus();
		
		return false;
	}
	
	if(!patternMail.test(email.value)){
		spanError = document.getElementById("errorEmail");
		spanError.innerHTML = "L'email non rispetta il formato standard.";
		document.getElementById("errorSign").innerHTML = "";
		email.focus();
		
		return false;
	}
	
	if(!patternPass.test(pass.value)){
		spanError = document.getElementById("errorPassword");
		spanError.innerHTML = "La password: <br/>" +
				"-deve essere lunga tra gli 8 e 20 caratteri; <br/>" +
				"-pu&ograve; contenere solo lettere, numeri e caratteri come \".\")";
		document.getElementById("errorSign").innerHTML = "";
		pass.focus();
		
		return false;
	}
	
	if(ripetiPass.value != pass.value){
		spanError = document.getElementById("errorRipetiPassword");
		spanError.innerHTML = "Le password non corrispondono";
		document.getElementById("errorSign").innerHTML = "";
		ripetiPass.focus();
		
		return false;
	}
	
	return true;
}