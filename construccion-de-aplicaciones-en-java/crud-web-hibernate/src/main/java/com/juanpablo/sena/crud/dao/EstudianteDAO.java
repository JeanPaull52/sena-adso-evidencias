package com.juanpablo.sena.crud.dao;

import com.juanpablo.sena.crud.model.Estudiante;
import com.juanpablo.sena.crud.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/*
 * DAO de Estudiante usando Hibernate.
 * Reemplaza la versión antigua basada en JDBC + Conexion.java.
 */
public class EstudianteDAO {

    // CREATE: insertar un nuevo estudiante
    public void insertar(Estudiante estudiante) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(estudiante);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    // READ: listar todos los estudiantes
    public List<Estudiante> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Estudiante> query = session.createQuery(
                "SELECT e FROM Estudiante e", Estudiante.class
            );
            return query.list();
        }
    }

    // READ con ID: buscar un estudiante por su ID
    public Estudiante buscarPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Estudiante.class, id);
        }
    }

    // UPDATE: actualizar un estudiante
    public void actualizar(Estudiante estudiante) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.merge(estudiante);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    // DELETE: eliminar un estudiante por su ID
    public void eliminar(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Estudiante estudiante = session.get(Estudiante.class, id);
                if (estudiante != null) {
                    session.remove(estudiante);
                }
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }
}