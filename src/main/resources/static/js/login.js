// Este es el script login.js

async function iniciarSesion() {
  const user = document.getElementById("user").value.trim();
  const password = document.getElementById("password").value.trim();
  const mensaje = document.getElementById("mensaje");
  const spinner = document.getElementById("spinner");

  if (!user || !password) {
    // Si los campos están vacíos, mostrar mensaje de error
    mostrarMensaje("Por favor ingresa usuario y contraseña.", "error");
    return;
  }

  // Mostrar el spinner mientras procesamos la solicitud
  spinner.style.display = "block";
  mensaje.textContent = "";

  try {
    // Solicitar datos al backend
    const response = await fetch("http://localhost:8082/api/usuarios/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ user, password })
    });

    // Depurar la respuesta del backend
    const data = await response.json();
    spinner.style.display = "none";
    console.log(data);

    // Verificar si la respuesta contiene un mensaje de éxito
    if (data.rol) {
      // Guardamos el rol en localStorage
      localStorage.setItem('rol', data.rol);

      // Mostrar mensaje de éxito y redirigir al dashboard
      mostrarMensaje(data.mensaje || "¡Inicio de sesión exitoso!", "success");

      setTimeout(() => {
        window.location.href = "dashboard.html";  // Redirigir al dashboard
      }, 1200);
    } else {
      // Si hay error, mostramos el mensaje de error
      mostrarMensaje(data.mensaje || "Error en el login", "error");
    }

  } catch (error) {
    spinner.style.display = "none";  // Ocultar el spinner
    mostrarMensaje("Error al conectar con el servidor.", "error");
    console.error("Error al iniciar sesión:", error);
  }
}

// Función para mostrar mensajes con colores dinámicos
function mostrarMensaje(texto, tipo) {
  const mensaje = document.getElementById("mensaje");
  mensaje.textContent = texto;
  mensaje.className = `mensaje ${tipo}`;
}
