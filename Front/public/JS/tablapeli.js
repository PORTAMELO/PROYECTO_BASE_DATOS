function obtenerDatosTabla1() {
	$.ajax({
		method: "GET",
		url: "http://localhost:8081/PeliculasController/GetAllPeliculas",
		success: function(data) {
			for (const current of data) {
				console.log(current);
				$('#tabla1').append(`
			    <tbody>
                 <tr>
                    <th scope="row">${current.idPelicula}</th>
				    <td >${current.nombrePelicula}</td>
				    <td >${current.genero}</td>
                </tr>
               </tbody> 
				`);
			}
		},
		error: function(err) {
			console.error("Error al obtener datos de la Tabla 1", err);
		}
	});
}

$(document).ready(function() {
	obtenerDatosTabla1();
});