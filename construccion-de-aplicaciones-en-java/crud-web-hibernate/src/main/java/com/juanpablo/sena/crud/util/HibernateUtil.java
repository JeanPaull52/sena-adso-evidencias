package com.juanpablo.sena.crud.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/* Clase que gestiona el ciclo de vida del SessionFactory de Hibernate.
 * Implementa el patrón Singleton: una sola SessionFactory por toda la aplicación,
 * creada al cargar la clase y reutilizada por todos los DAOs.
 */
public class HibernateUtil {

    // Instancia única de SessionFactory (el "Singleton")
    private static final SessionFactory sessionFactory = buildSessionFactory();

    /*
     * Método privado que construye el SessionFactory.
     * Se ejecuta UNA SOLA VEZ, cuando Java carga la clase por primera vez.
     */
    private static SessionFactory buildSessionFactory() {
    try {
        // 1. Configuration lee el archivo hibernate.cfg.xml
        Configuration configuration = new Configuration().configure();

        // 2. Registramos EXPLÍCITAMENTE las clases anotadas
        configuration.addAnnotatedClass(com.juanpablo.sena.crud.model.Estudiante.class);

        // 3. Construye el "service registry"
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        // 4. Con la config + el registry, construye la SessionFactory
        return configuration.buildSessionFactory(serviceRegistry);

    } catch (Throwable ex) {
        System.err.println("Error al inicializar SessionFactory: " + ex);
        throw new ExceptionInInitializerError(ex);
    }
}

    /*
     * Único método público: devuelve la SessionFactory para que los DAOs la usen.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // Método para cerrar la SessionFactory cuando la app se apaga.
    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            System.out.println("SessionFactory cerrada correctamente.");
        }
    }
}