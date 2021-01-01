function checkForm(){
	var titolo = richiestaFilm.titolo;
	var commento = richiestaFilm.commento;
	var spanError; //elemento dove inserire la scritta per l'errore
	
	var patternTitolo = /^[a-zA-Z0-9 ]{1,30}$/g;
	var patternCommento = /^.{0,255}$/gm;
	
	if(!patternTitolo.test(titolo.value)){
		spanError = document.getElementById("errorTitolo");
		spanError.innerHTML = "Il titolo non deve essere vuoto e avere al massimo 30 caratteri";
		titolo.focus();
		return false;
	}
	
	if(!patternCommento.test(commento.value)){
		spanError = document.getElementById("errorCommento");
		spanError.innerHTML = "Il commento deve avere al massimo 255 caratteri";
		commento.focus();
		return false;
	}
	
	return true;
}