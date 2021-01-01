function searchFilmEdit(){
	var jsonObj = {};
	jsonObj.titolo = $("input#titoloEdit").val();
	var jsonStr = JSON.stringify(jsonObj);
	
	
	$.ajax({
		url:"../SearchFilm",
		type: "POST",
		data: "json=" + encodeURIComponent(jsonStr),
		success: function(result){					  
				    $("#filmEdit").html("" + getHtmlFilm(result.lista));
			}
	});
	
	return false;
}


function searchFilmDelete(){
	var jsonObj = {};
	jsonObj.titolo = $("input#titoloDelete").val();
	var jsonStr = JSON.stringify(jsonObj);
	
	
	$.ajax({
		url:"../SearchFilm",
		type: "POST",
		data: "json=" + encodeURIComponent(jsonStr),
		success: function(result){					  
				    $("#filmDelete").html("" + getHtmlFilm(result.lista));
			}
	});
	
	return false;
}

function getHtmlFilm(lista){
	var html = "";
	
	lista.forEach( (element) => {
		html += "<option value='" + element.titolo + "'>";
	});
	
	return html;
}

function checkPasswordAdmin(idParent){
	if(idParent === "edit"){
		if(!checkForm('edit'))
			return false;
	}
	
	var jsonObj = {};
	jsonObj.password = $("#" + idParent + " input[name=password]").val();
	var jsonStr = JSON.stringify(jsonObj);
	
	
	$.ajax({
		url:"../CheckPasswordAdmin",
		type: "POST",
		data: "json=" + encodeURIComponent(jsonStr),
		success: function(result){					  
				   	if(result.esito === false)
				   		$("#" + idParent +" #error").html("La password non corrisponde");
				   	else if(result.esito === true)
				   		$("#" + idParent).submit();
			}
	});
	
}

function setValueInput(){
	var jsonObj = {};
	jsonObj.titolo = $("input#titoloEdit").val();
	var jsonStr = JSON.stringify(jsonObj);
	
	
	$.ajax({
		url:"../SearchFilmAdmin",
		type: "POST",
		data: "json=" + encodeURIComponent(jsonStr),
		success: function(result){
				    $("#edit input[name=codiceFilm]").val(result.codiceFilm);
					$("#edit input[name=titolo]").val(result.titolo);
				    $("#edit textarea[name=descrizione]").val(result.descrizione);
				    $("#edit input[name=genere]").val(result.genere);
				    $("#edit input[name=dataUscita]").val(result.dataUscita);
				    $("#edit input[name=durata]").val(result.durata);
			}
	});
	
	return false;
}

function checkForm(idParent){
	var titolo = $("#"+ idParent + " input[name=titolo]").val();
	var descrizione = $("#"+ idParent + " textarea[name=descrizione]").val();
	var genere = $("#"+ idParent + " input[name=genere]").val();
	var dataUscita = $("#"+ idParent + " input[name=dataUscita]").val();
	var durata = $("#"+ idParent + " input[name=durata]").val();

	var patternTitolo =  /^[a-zA-Z0-9 ]{1,30}$/g;
	var patternDescrizione =  /^.{25,255}$/g;
	var patternGenere = /^[a-zA-Z0-9]{1,20}$/g;
	var patternDataUscita =  /^[0-9]{4}[/-]{1}[0-9]{2}[/-]{1}[0-9]{2}$/g; // YYYY/MM/DD
	var patternDurata = /^[0-9]{1,3}$/g;

	
	$(".error").html("");
	
	if(!patternTitolo.test(titolo)){
		$("#" + idParent + " #errorTitolo").html("Il titolo può contenere solo lettere e numeri, inoltre deve essere lungo al massimo 30 caratteri.");
		
		return false;
	}
	
	if(!patternDescrizione.test(descrizione)){
		$("#" + idParent + " #errorDescrizione").html("La descrizione deve essere compresa tra i 25 e i 255 caratteri");
		
		return false;
	}
	
	if(!patternGenere.test(genere)){
		$("#" + idParent + " #errorGenere").html("Il genere deve contenere al massimo 20 caratteri");
		
		return false;
	}
	
	if(!patternDataUscita.test(dataUscita)){
		$("#" + idParent + " #errorDataUscita").html("La data di uscita deve avere il seguente formato: YYYY/MM/DD");
		
		return false;
	}
	
	if(!patternDurata.test(durata)){
		$("#" + idParent + " #errorDurata").html("La durata può contenere solo numeri");
		
		return false;
	}
	
	return true;
}