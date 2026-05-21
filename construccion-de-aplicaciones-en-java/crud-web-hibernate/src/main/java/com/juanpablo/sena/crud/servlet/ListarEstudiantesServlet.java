package com.juanpablo.sena.crud.servlet;

import com.juanpablo.sena.crud.dao.EstudianteDAO;
import com.juanpablo.sena.crud.model.Estudiante;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/estudiantes/listar")
public class ListarEstudiantesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Obtener la lista de estudiantes usando el DAO
        EstudianteDAO dao = new EstudianteDAO();
        List<Estudiante> estudiantes = dao.listarTodos();

        // 2. Guardar la lista en el request para que el JSP la pueda leer
        request.setAttribute("estudiantes", estudiantes);

        // 3. Reenviar la petición al JSP que mostrará la lista
        request.getRequestDispatcher("/WEB-INF/jsp/lista.jsp").forward(request, response);
    }
}