//------------------------------------------
//Mostrar el video
//------------------------------------------
var tag = document.createElement('script');
tag.src = 'https://www.youtube.com/iframe_api';
var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
var player;
var videoId = 'jxsUIUd6T8U';
function onYouTubeIframeAPIReady() {
	player = new YT.Player('player', {
		height: '360',
		width: '640',
		videoId: videoId,
		playerVars: {
			'autoplay': 0,
			'controls': 1,
			'rel': 0,
			'showinfo': 0
		},
		events: {
			'onReady': onPlayerReady
		}
	});
}

function onPlayerReady(event) {
}


//------------------------------------------
//Setear fechas en reporte novedades
//------------------------------------------
document.addEventListener('DOMContentLoaded', function() {
	const today = new Date();
	const lastMonth = new Date(today.getFullYear(), today.getMonth() - 1, 1);

	const formatMonthYear = (date) => {
		const month = (date.getMonth() + 1).toString().padStart(2, '0');
		const year = date.getFullYear().toString();
		return `${year}-${month}`;
	};

	const inputF1 = document.getElementById('F1');
	const inputF2 = document.getElementById('F2');

	inputF1.value = formatMonthYear(lastMonth);
	inputF2.value = formatMonthYear(today);
});


$(document).ready(() => {
	//------------------------------------------
	//Cargar los elementos de "EMPLEADO" del XLSX
	//------------------------------------------
	document.getElementById('uploadNomina').addEventListener('click', function() {
		document.getElementById('fileNomina').click();
	});

	function handleBlankValue(value) {
		return value === null || value === undefined ? '' : value;
	}

	document.getElementById('fileNomina').addEventListener('change', function(event) {
		var file = event.target.files[0];
		var reader = new FileReader();

		reader.onload = function(e) {
			var data = new Uint8Array(e.target.result);
			var workbook = XLSX.read(data, { type: 'array' });

			var firstSheetData = XLSX.utils.sheet_to_json(workbook.Sheets[workbook.SheetNames[0]]);
			var secondSheetData = XLSX.utils.sheet_to_json(workbook.Sheets[workbook.SheetNames[1]]);

			firstSheetData.forEach(function(fila, indice) {
				const body = {
					'codigo': fila['CÓDIGO'],
					'nombreEmpleado': fila['NOMBRE DEL EMPLEADO'],
					'dependencia': fila['DEPENDENCIA'],
					'cargo': fila['CARGO'],
					'fecha': fila['FECHA INGRESO'],
					'eps': fila['EPS'],
					'arl': fila['ARL'],
					'pension': fila['PENSIÓN'],
					'sueldo': fila['SUELDO']
				};

				var aux = 0;

				$.ajax({
					method: "POST",
					url: "http://localhost:8081/EmpleadoController/AddEmpleado",
					data: body,
					success: function(data) {
						if (data === true) {
							console.log('Empleado agregado con exito');
							Swal.fire({
								icon: 'success',
								title: 'Correcto',
								text: 'Empleados cargados con exito',
								button: "Aceptar"
							});
						}
					},
					error: function(xhr, status, error) {
						Swal.fire({
							icon: 'warning',
							title: 'Error no controlado',
							text: xhr.responseText,
							text: error,
							button: "Aceptar"
						});
						console.warn('Advertencia:', xhr.responseText);
						console.error('Error:', error);
					}
				});
			});

			function convertirNumeroSerieAFecha(numeroSerie) {
				if (!numeroSerie || isNaN(numeroSerie)) {
					return '';
				}
				const fechaBase = new Date('1899-12-30');
				const fechaEnMilisegundos = (numeroSerie - 1) * 24 * 60 * 60 * 1000;
				const fechaFinal = new Date(fechaBase.getTime() + fechaEnMilisegundos);
				const year = fechaFinal.getFullYear();
				const month = String(fechaFinal.getMonth() + 1).padStart(2, '0');
				const day = String(fechaFinal.getDate()).padStart(2, '0');
				return `${year}-${month}-${day}`;
			}


			//------------------------------------------
			//Cargar los elementos de "NOVEDAD" del XLSX
			//------------------------------------------
			secondSheetData.forEach(function(fila, indice) {
				const body = {
					'codigoEmpleado': handleBlankValue(fila['CÓDIGO']),
					'novedadIncapacidad': handleBlankValue(fila['NOVEDAD INCAPACIDAD']),
					'novedadVacaciones': handleBlankValue(fila['NOVEDAD VACACIONES']),
					'diasTrabajados': handleBlankValue(fila['NUMERO DIAS TRABAJADOS EN EL MES']),
					'diasIncapacidad': handleBlankValue(fila['NUMERO DIAS INCAPACIDADES EN EL MES']),
					'diasVacaciones': handleBlankValue(fila['NUMERO DE DIAS VACACIONES']),
					'inicioVacaciones': handleBlankValue(convertirNumeroSerieAFecha(fila['FECHA DE INICIO DE VACACIONES'])),
					'finVacaciones': handleBlankValue(convertirNumeroSerieAFecha(fila['FECHA TERMINACION DE VACACIONES'])),
					'inicioIncapacidad': handleBlankValue(convertirNumeroSerieAFecha(fila['FECHA INICIO INCAPACIDAD'])),
					'finIncapacidad': handleBlankValue(convertirNumeroSerieAFecha(fila['FECHA TERMINACIÓN INCAPACIDAD'])),
					'bonificacion': handleBlankValue(fila['BONIFICACIÓN']),
					'transporte': handleBlankValue(fila['TRANSPORTE'])
				};

				$.ajax({
					method: "POST",
					url: "http://localhost:8081/NovedadesController/AddNovedad",
					data: body,
					success: function(data) {
						if (data === true) {
							console.log('Novedad agregada con exito');
							Swal.fire({
								icon: 'success',
								title: 'Correcto',
								text: 'Novedades cargadas con exito',
								button: "Aceptar"
							});
						}
					},
					error: function(xhr, status, error) {
						Swal.fire({
							icon: 'warning',
							title: 'Error no controlado',
							text: xhr.responseText,
							text: error,
							button: "Aceptar"
						});
						console.warn('Advertencia:', xhr.responseText);
						console.error('Error:', error);
					}
				});
			});
		};

		reader.readAsArrayBuffer(file);
	});


	//------------------------------------------
	//Cargar los elementos de "LIBRO" del .JSON 
	//------------------------------------------
	document.getElementById('uploadLibros').addEventListener('click', function() {
		document.getElementById('librosFile').click();
	});

	document.getElementById('librosFile').addEventListener('change', function(event) {
		const inputFile = document.getElementById('librosFile').files[0];
		const formData = new FormData();
		formData.append('file', inputFile);

		$.ajax({
			method: 'POST',
			url: 'http://localhost:8081/BookController/AddBook',
			data: formData,
			contentType: false,
			processData: false,
			success: function(data) {
				if (data === 'SZ') {
					Swal.fire({
						icon: 'success',
						title: 'Correcto',
						text: 'Libros cargados con exito',
						button: "Aceptar"
					});
				} else {
					Swal.fire({
						icon: 'warning',
						title: 'Error',
						text: 'Problema al cargar el archivo',
						button: "Aceptar"
					});
				}
			},
			error: function(xhr, status, error) {
				Swal.fire({
					icon: 'warning',
					title: 'Error no controlado',
					text: xhr.responseText,
					text: error,
					button: "Aceptar"
				});
				console.warn('Advertencia:', xhr.responseText);
				console.error('Error:', error);
			}
		});
	});


	//------------------------------------------
	//Cargar los elementos de "Pelicula" del .dat
	//------------------------------------------
	document.getElementById('uploadPeliculas').addEventListener('click', function() {
		document.getElementById('filePeliculas').click();
	});

	document.getElementById('filePeliculas').addEventListener('change', function(event) {
		const file = event.target.files[0];
		const formData = new FormData();

		formData.append('file', file);

		$.ajax({
			method: 'POST',
			url: 'http://localhost:8081/PeliculasController/AgregarPelicula',
			data: formData,
			contentType: false,
			processData: false,
			success: function(data) {
				if (data === 'SZ') {
					Swal.fire({
						icon: 'success',
						title: 'Correcto',
						text: 'Peliculas cargadas con exito',
						button: "Aceptar"
					});
				} else {
					Swal.fire({
						icon: 'warning',
						title: 'Error',
						text: 'Problema al cargar el archivo',
						button: "Aceptar"
					});
				}
			},
			error: function(xhr, status, error) {
				Swal.fire({
					icon: 'warning',
					title: 'Error no controlado',
					text: xhr.responseText,
					text: error,
					button: "Aceptar"
				});
				console.warn('Advertencia:', xhr.responseText);
				console.error('Error:', error);
			}
		});
	});


	//------------------------------------------
	//Crear informe "Nomina"
	//------------------------------------------
	$(document).ready(function() {
		$('#enviar-datos1').on('click', function(e) {
			e.preventDefault();

			var order1 = $('input[name=odern1]:checked').val() === 'as1' ? 0 : 1;
			var order2 = $('input[name=odern2]:checked').val() === 'as2' ? 0 : 1;
			var order3 = $('input[name=odern3]:checked').val() === 'as3' ? 0 : 1;

			$.ajax({
				type: 'GET',
				url: 'http://localhost:8081/PDFController/PDF1',
				data: {
					'op1': order1,
					'op2': order2,
					'op3': order3
				},
				xhrFields: {
					responseType: 'blob'
				},
				success: function(response) {
					const url = window.URL.createObjectURL(response);
					const nuevaVentana = window.open(url, '_blank');
					setTimeout(() => window.URL.revokeObjectURL(url), 1000);
				},
				error: function(xhr, status, error) {
					console.error('Error al descargar el PDF:', error);
					console.error('Respuesta del servidor:', xhr.responseText);
				}
			});
		});
	});


	//------------------------------------------
	//Crear informe "Informacion personal"
	//------------------------------------------
	document.getElementById('reporte_informacion_persona').addEventListener('click', function() {
		$.ajax({
			method: 'GET',
			url: 'http://localhost:8081/PDFController/PDF2',
			xhrFields: {
				responseType: 'blob'
			},
			success: function(response) {
				const url = window.URL.createObjectURL(response);
				const nuevaVentana = window.open(url, '_blank');
				setTimeout(() => window.URL.revokeObjectURL(url), 1000);
			},
			error: function(xhr, status, error) {
				console.error('Error al descargar el PDF:', error);
				console.error('Respuesta del servidor:', xhr.responseText);
			}
		});
	});


	//------------------------------------------
	//Crear informe "Salud y pension"
	//------------------------------------------
	$(document).ready(function() {
		$('#enviar-sp').on('click', function(e) {
			e.preventDefault();
			var order1 = $('input[name=SP1]:checked').val() === 'as-sp1' ? 0 : 1;
			var order2 = $('input[name=SP2]:checked').val() === 'as-sp2' ? 0 : 1;
			$.ajax({
				type: 'GET',
				url: 'http://localhost:8081/PDFController/PDF3',
				data: {
					'op1': order1,
					'op2': order2,
				},
				xhrFields: {
					responseType: 'blob'
				},
				success: function(response) {
					const url = window.URL.createObjectURL(response);
					const nuevaVentana = window.open(url, '_blank');
					setTimeout(() => window.URL.revokeObjectURL(url), 1000);
				},
				error: function(xhr, status, error) {
					console.error('Error al descargar el PDF:', error);
					console.error('Respuesta del servidor:', xhr.responseText);
				}
			});
		});
	});

	//------------------------------------------
	//Crear informe "Novedades"
	//------------------------------------------
	$('#enviar-nov').on('click', function(e) {
		e.preventDefault();
		const fechaInicial = new Date(document.getElementById('F1').value);
		const fechaFinal = new Date(document.getElementById('F2').value);
		if (!fechaInicial || !fechaFinal || isNaN(fechaInicial.getTime()) || isNaN(fechaFinal.getTime())) {
			Swal.fire({
				icon: 'warning',
				title: 'Campos no válidos',
				text: 'Por favor, seleccione una fecha inicial y una fecha final válidas.',
				button: 'Aceptar'
			});
		} else if (fechaInicial.getTime() > fechaFinal.getTime()) {
			Swal.fire({
				icon: 'warning',
				title: 'Fechas no válidas',
				text: 'La fecha inicial no puede ser mayor que la fecha final.',
				button: 'Aceptar'
			});
		} else if (fechaInicial.getFullYear() === fechaFinal.getFullYear() &&
			fechaInicial.getMonth() === fechaFinal.getMonth() &&
			fechaInicial.getDate() === fechaFinal.getDate()) {
			Swal.fire({
				icon: 'warning',
				title: 'Fechas no válidas',
				text: 'La fechas fechas deben ser diferentes.',
				button: 'Aceptar'
			});
		} else {
			$.ajax({
				type: 'GET',
				url: 'http://localhost:8081/PDFController/PDF4',
				data: {
					'fechaInicio': fechaInicial.toISOString().slice(0, 10),
					'fechaFin': fechaFinal.toISOString().slice(0, 10),
				},
				xhrFields: {
					responseType: 'blob'
				},
				success: function(response) {
					const url = window.URL.createObjectURL(response);
					const nuevaVentana = window.open(url, '_blank');
					setTimeout(() => window.URL.revokeObjectURL(url), 1000);
				},
				error: function(xhr, status, error) {
					console.error('Error al descargar el PDF:', error);
					console.error('Respuesta del servidor:', xhr.responseText);
				}
			});
		}
	});

	window.onclick = function(event) {
		if (event.target === modal) {
			modal.style.display = "none";
		} else if (event.target === modalSP) {
			modalSP.style.display = "none";
		} else if (event.target === modalNov) {
			modalNov.style.display = "none";
		} else if (event.target === modalEnt) {
			modalEnt.style.display = "none";
		}
	};
	// funciones para el modal de reporte de nomina
	var btnAbrirModal = document.getElementById("form-modal");
	var modal = document.getElementById("myModal");
	var span = document.getElementsByClassName("close")[0];

	btnAbrirModal.addEventListener("click", function(e) {
		modal.style.display = "block";
		e.preventDefault();
	});

	span.onclick = function() {
		modal.style.display = "none";
	};

	//funcion para el modal reporte de salud y pension 
	var btnAbrirModalSP = document.getElementById("form-sp-modal");
	var modalSP = document.getElementById("myModal-sp");
	var spanSP = document.getElementsByClassName("close-sp")[0];

	btnAbrirModalSP.addEventListener("click", function(e) {
		modalSP.style.display = "block";
		e.preventDefault();
	});

	spanSP.onclick = function() {
		modalSP.style.display = "none";
	};

	//funcion para el modal reporte de novedades 
	var btnAbrirModalNov = document.getElementById("form-nov-modal");
	var modalNov = document.getElementById("myModal-nov");
	var spanNov = document.getElementsByClassName("close-nov")[0];

	btnAbrirModalNov.addEventListener("click", function(e) {
		modalNov.style.display = "block";
		e.preventDefault();
	});

	spanNov.onclick = function() {
		modalNov.style.display = "none";
	};


	// funcion para mostar en modal de entretenimiento
	var btnAbrirModalEnt = document.getElementById("form-ent-modal-peli");
	var btnAbrirModalEnt2 = document.getElementById("form-ent-modal-libro");
	var modalEnt = document.getElementById("myModal-ent");
	var spanEnt = document.getElementsByClassName("close-ent")[0];

	btnAbrirModalEnt.addEventListener("click", function(e) {
		modalEnt.style.display = "block";
		e.preventDefault();
	});

	btnAbrirModalEnt2.addEventListener("click", function(e) {
		modalEnt.style.display = "block";
		e.preventDefault();
	});

	spanEnt.onclick = function() {
		modalEnt.style.display = "none";
	}; S
});