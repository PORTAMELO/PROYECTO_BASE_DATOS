function obtenerDatosTabla1() {
	$.ajax({
		method: "GET",
		url: "http://localhost:8081/EmpleadoController/GetAllEmpleado",
		success: function(data) {
			for (const current of data) {
				$('#tabla1').append(`
				<tbody>
				 <tr>
				    <th scope="row">${current.codigo}</th>
				    <th >${current.nombreEmpleado}</th>
				    <th >${current.dependencia}</th>
				    <th >${current.cargo}</th>
				    <th >${current.fecha}</th>
				    <th >${current.eps}</th>
				    <th >${current.arl}</th>
				    <th >${current.pension}</th>
				    <th >${current.sueldo}</th>
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

function obtenerDatosTabla2() {
	$.ajax({
		method: "GET",
		url: "http://localhost:8081/NovedadesController/GetAllNovedad",
		success: function(data) {
			for (const current of data) {
				$('#tabla2').append(`
				<tbody>
				 <tr>
				    <th scope="row">${current.empleado.codigo}</th>
				    <th >${current.novedadIncapacidad}</th>
				    <th >${current.novedadVacaciones}</th>
				    <th >${current.diasTrabajados}</th>
				    <th >${current.diasIncapacidad}</th>
				    <th >${current.diasVacaciones}</th>
				    <th >${current.inicioVacaciones}</th>
				    <th >${current.finVacaciones}</th>
				    <th >${current.inicioIncapacidad}</th>
				    <th >${current.finIncapacidad}</th>
				    <th >${current.bonificacion}</th>
				    <th >${current.transporte}</th>
				 </tr>
				</tbody>
				`);
			}
		},
		error: function(err) {
			console.error("Error al obtener datos de la Tabla 2", err);
		}
	});
}

$(document).ready(function() {
	obtenerDatosTabla2();
	obtenerDatosTabla1();
});