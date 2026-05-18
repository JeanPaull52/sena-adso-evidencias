package com.juanpablo.sena.crud.servlet;

import com.juanpablo.sena.crud.dao.EstudianteDAO;
import com.juanpablo.sena.crud.model.Estudiante;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/estudiantes/crear")
public class CrearEstudianteServlet extends HttpServlet {

    /* GET: Muestra el formulario en blanco para crear al estudiante.*/
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/formulario.jsp").forward(request, response);
    }

    /* POST: Recibe los datos del formulario y crea el estudiante en la BD.*/
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Leer los datos del formulario
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String celular = request.getParameter("celular");
        String direccion = request.getParameter("direccion");

        // 2. Crear un objeto Estudiante con esos datos
        Estudiante estudiante = new Estudiante(nombre, correo, celular, direccion);

        // 3. Llamar al DAO para guardarlo en la BD
        EstudianteDAO dao = new EstudianteDAO();
        dao.insertar(estudiante);

        // 4. Redirigir al listado actualizado
        response.sendRedirect(request.getContextPath() + "/estudiantes/listar");
    }
}