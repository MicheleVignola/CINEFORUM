function getFilms() {
	var jsonObj = {};
	jsonObj.titolo = $("input#titolo").val();
	var jsonStr = JSON.stringify(jsonObj);
	
	
	$.ajax({
		url:"./RetrieveFilm",
		type: "POST",
		data: "json=" + encodeURIComponent(jsonStr),
		success: function(result){
					if(result.esito === false)
						$("#films").html("nessun film presente con questo titolo oppure è già stato aggiunto alla lista");
					else{
						$("#films").html("" + getHtmlTitoli(result.lista));
						$(".modal-footer").css("visibility", "visible");
					}
			}
	});
	
	return false;
}

function getHtmlTitoli(lista){
	var html = "";
	
	lista.forEach( (element) => {
		html += "<a class='linkFilm' id='" + element.codiceFilm + "' onClick='showInsert(this.id)'>" + element.titolo + "</a><br/>";
	});
	
	return html;
}

function showInsert(codiceFilm){
	$("input[name=codiceFilm").val(codiceFilm);
	$("#searchFilm").css("display", "none");
	$("#formInsert").css("display", "block");
	return false;
}

function resetModal(){
	$("#searchFilm").css("display", "block");
	$("#formInsert").css("display", "none");
	$("#films").html("");
	$("#formInsert").trigger("reset");
}

function setEditForm(codice, titolo){
	$("input[name=codiceFilm").val(codice);
	$("#editModalLabel").html("Modifica il film " + titolo);
	
	return false;
}

$('#addModal').on('hidden.bs.modal', function () {
	$(".modal-footer").css("visibility", "hidden");
});