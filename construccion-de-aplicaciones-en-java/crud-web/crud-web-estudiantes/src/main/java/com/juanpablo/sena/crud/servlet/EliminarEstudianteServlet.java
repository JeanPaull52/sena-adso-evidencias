package com.juanpablo.sena.crud.servlet;

import com.juanpablo.sena.crud.dao.EstudianteDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/estudiantes/eliminar")
public class EliminarEstudianteServlet extends HttpServlet {

    /*GET: Recibe ?id=X y elimina al estudiante de la BD.*/
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Leer el ID de la URL
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            // Sin ID: volvemos al listado sin hacer nada
            response.sendRedirect(request.getContextPath() + "/estudiantes/listar");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);

            // 2. Eliminamos usando el DAO
            EstudianteDAO dao = new EstudianteDAO();
            dao.eliminar(id);

        } catch (NumberFormatException e) {
            // Si el ID no era un número válido lo ignoramos y redirigimos
        }

        // 3. Redirigir al listado
        response.sendRedirect(request.getContextPath() + "/estudiantes/listar");
    }
}