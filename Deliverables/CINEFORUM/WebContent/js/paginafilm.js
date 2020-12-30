function setCategoria(categoria){
	$("input[name=categoria]").val(categoria);
}

function checkCommento(){
	var comment = inserimentoCommento.commento;
	var spanError = document.getElementById("errorCommento"); //elemento dove inserire la scritta per l'errore
	
	var patternComment = /^.{25,255}$/gm;
	
	if(!patternComment.test(comment.value)){
		spanError.innerHTML = "Il commento deve avere tra i 25 e i 255 caratteri";
		comment.focus();
		return false;
	}
	
	return true;
}