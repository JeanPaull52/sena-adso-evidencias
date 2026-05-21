package com.juanpablo.sena.crud.servlet;

import com.juanpablo.sena.crud.dao.EstudianteDAO;
import com.juanpablo.sena.crud.model.Estudiante;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/estudiantes/editar")
public class EditarEstudianteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Leer el ID de la URL (?id=5)
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            // No vino el ID: redirigir al listado
            response.sendRedirect(request.getContextPath() + "/estudiantes/listar");
            return;
        }

        int id = Integer.parseInt(idParam);

        // 2. Buscar el estudiante en la BD
        EstudianteDAO dao = new EstudianteDAO();
        Estudiante estudiante = dao.buscarPorId(id);

        if (estudiante == null) {
            // No existe ese estudiante: redirigir al listado
            response.sendRedirect(request.getContextPath() + "/estudiantes/listar");
            return;
        }

        // 3. Pasar el estudiante al JSP del formulario
        request.setAttribute("estudiante", estudiante);

        // 4. Mostrar el formulario (el mismo que usamos para crear)
        request.getRequestDispatcher("/WEB-INF/jsp/formulario.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Leer todos los datos del formulario, incluyendo el ID
        int id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String celular = request.getParameter("celular");
        String direccion = request.getParameter("direccion");

        // 2. Crear el objeto Estudiante con el ID
        Estudiante estudiante = new Estudiante(id, nombre, correo, celular, direccion);

        // 3. Actualizar con el DAO
        EstudianteDAO dao = new EstudianteDAO();
        dao.actualizar(estudiante);

        // 4. Redirigir al listado (POST-Redirect-GET)
        response.sendRedirect(request.getContextPath() + "/estudiantes/listar");
    }
}