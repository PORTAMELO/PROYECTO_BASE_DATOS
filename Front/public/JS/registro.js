$(document).ready(function () {
    $('#enviar').click(function (e) {
        e.preventDefault();

        const nombre = $('#fname').val();
        const email = $('#email').val();
        const password = $('#pass').val();

        const formData = new FormData();
        formData.append('name', nombre);
        formData.append('password', password);
        formData.append('correo', email);

        $.ajax({
            method: "POST",
            url: "http://localhost:8081/AdministradorController/addAdministrador",
            data: formData,
            processData: false,
            contentType: false,
            success: function (data) {
                Swal.fire({
                    icon: 'success',
                    title: 'Registro exitoso',
                    text: 'Usuario registrado correctamente.'
                }).then(() => {
                    window.location.href = "http://localhost:8080/Front/principal.html";
                });
            },
            error: function (xhr, status, error) {
                console.log("[ERROR]");
                console.log(xhr.responseText);
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Ha ocurrido un error en el registro (' + xhr.responseText + ')'
                });
            }
        });
    });
});
