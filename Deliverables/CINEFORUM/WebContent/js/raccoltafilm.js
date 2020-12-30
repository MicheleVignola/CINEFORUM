function searchFilm(){
	var jsonObj = {};
	jsonObj.titolo = $("input[name=titolo]").val();
	var jsonStr = JSON.stringify(jsonObj);
	
	
	$.ajax({
		url:"./SearchFilm",
		type: "POST",
		data: "json=" + encodeURIComponent(jsonStr),
		success: function(result){					  
				    $("#film").html("" + getHtmlFilm(result.lista));
			}
	});
	
	return false;
}

function getHtmlFilm(lista){
	var html = "";
	
	lista.forEach( (element) => {
		html += "<a href='Film?film=" + element.codiceFilm + "'> <img style='width: 100px' src='data:image/*;base64," +  window.btoa(element.immagine) + "' alt='immagine film' onerror='this.src=\"img/noimage.png\"' /> <span>" + element.titolo + "</span> </a>";
	});
	
	return html;
}