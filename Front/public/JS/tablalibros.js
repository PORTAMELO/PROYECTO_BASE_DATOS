function obtenerDatosTabla1() {
	$.ajax({
		method: "GET",
		url: "http://localhost:8081/BookController/GetAllBook",
		success: function(data) {
			for (const current of data) {
				$('#tabla1').append(`
			    <tbody>
                 <tr>
                    <th scope="row">${current.bookID}</th>
				    <td >${current.title}</td>
				    <td >${current.authors}</td>
				    <td >${current.average_rating}</td>
				    <td >${current.isbn}</td>
				    <td >${current.isbn13}</td>
				    <td >${current.language_code}</td>
				    <td >${current.num_pages}</td>
				    <td >${current.ratings_count}</td>
				    <td >${current.text_reviews_count}</td>
				    <td >${current.publication_date}</td>
				    <td >${current.publisher}</td>
				    <td >${current.field13}</td>
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