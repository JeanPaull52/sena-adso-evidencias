import java.util.List;
import java.util.Scanner;

public class Main {

    private static final EstudianteDAO dao = new EstudianteDAO();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean continuar = true;

        while (continuar) {
            mostrarMenu();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    listarTodos();
                    break;
                case 2:
                    buscarPorId();
                    break;
                case 3:
                    insertarEstudiante();
                    break;
                case 4:
                    actualizarEstudiante();
                    break;
                case 5:
                    eliminarEstudiante();
                    break;
                case 0:
                    System.out.println("\n¡Hasta luego!");
                    continuar = false;
                    break;
                default:
                    System.out.println("\nOpción inválida. Intenta de nuevo.");
            }
        }

        sc.close();
    }

    //Métodos del Menú-----
    private static void mostrarMenu() {
        System.out.println("\n-------------------------------------");
        System.out.println("    SISTEMA DE GESTIÓN DE ESTUDIANTES   ");
        System.out.println("---------------------------------------");
        System.out.println("1. Listar todos los estudiantes");
        System.out.println("2. Buscar estudiante por ID");
        System.out.println("3. Insertar nuevo estudiante");
        System.out.println("4. Actualizar estudiante");
        System.out.println("5. Eliminar estudiante");
        System.out.println("0. Salir");
        System.out.println("----------------------------------------");
        System.out.print("Selecciona una opción: ");
    }

    private static int leerOpcion() {
        try {
            int opcion = sc.nextInt();
            sc.nextLine();
            return opcion;
        } catch (Exception e) {
            sc.nextLine();
            return -1;
        }
    }

    //Operaciones-----
    //Operación 1 - Listar todos-----
    private static void listarTodos() {
        System.out.println("\nLISTA DE ESTUDIANTES");
        System.out.println("-----------------------------");

        List<Estudiante> lista = dao.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }

        for (Estudiante e : lista) {
            System.out.println(e);
        }

        System.out.println("-----------------------------------");
        System.out.println("Total: " + lista.size() + " estudiantes");
    }

    //Opción 2 - Buscar por ID-----
    private static void buscarPorId() {
        System.out.println("\nBUSCAR ESTUDIANTE POR ID");
        System.out.print("Digite el ID del estudiante: ");
        int id = leerEntero();

        if (id == -1) {
            System.out.println("ID inválido.");
            return;
        }

        Estudiante e = dao.buscarPorId(id);

        if (e != null) {
            System.out.println("\nEstudiante encontrado:");
            System.out.println(e);
        } else {
            System.out.println("\nNo se encontró un estudiante con ID " + id);
        }
    }

    //Opción 3 - Insertar-----
    private static void insertarEstudiante() {
        System.out.println("\nINSERTAR NUEVO ESTUDIANTE");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Correo: ");
        String correo = sc.nextLine();

        System.out.print("Celular: ");
        String celular = sc.nextLine();

        System.out.print("Dirección: ");
        String direccion = sc.nextLine();

        Estudiante nuevo = new Estudiante(nombre, correo, celular, direccion);
        dao.insertar(nuevo);
    }

    //Opción 4 - Actualizar-----
    private static void actualizarEstudiante() {
        System.out.println("\nACTUALIZAR ESTUDIANTE");
        System.out.print("Digite el ID del estudiante a actualizar: ");
        int id = leerEntero();

        if (id == -1) {
            System.out.println("ID inválido.");
            return;
        }

        // Verificar que el estudiante existe antes de pedir los datos
        Estudiante actual = dao.buscarPorId(id);
        if (actual == null) {
            System.out.println("No se encontró un estudiante con ID " + id);
            return;
        }

        System.out.println("\nDatos actuales:");
        System.out.println(actual);
        System.out.println("\nDigite los nuevos datos:");

        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Nuevo correo: ");
        String correo = sc.nextLine();

        System.out.print("Nuevo celular: ");
        String celular = sc.nextLine();

        System.out.print("Nueva dirección: ");
        String direccion = sc.nextLine();

        Estudiante editado = new Estudiante(id, nombre, correo, celular, direccion);
        dao.actualizar(editado);
    }

    // ─── Opción 5: ELIMINAR ─────────────────────────────────
    private static void eliminarEstudiante() {
        System.out.println("\nELIMINAR ESTUDIANTE");
        System.out.print("Digite el ID del estudiante a eliminar: ");
        int id = leerEntero();

        if (id == -1) {
            System.out.println("ID inválido.");
            return;
        }

        // Mostrar el estudiante antes de confirmar
        Estudiante e = dao.buscarPorId(id);
        if (e == null) {
            System.out.println("No se encontró un estudiante con ID " + id);
            return;
        }

        System.out.println("\nEstudiante a eliminar:");
        System.out.println(e);
        System.out.print("\n¿Confirma la eliminación? (s/n): ");
        String confirmacion = sc.nextLine().trim().toLowerCase();

        if (confirmacion.equals("s") || confirmacion.equals("si")) {
            dao.eliminar(id);
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }

    //Método para validar las entradas del usuario
    private static int leerEntero() {
        try {
            int valor = sc.nextInt();
            sc.nextLine();
            return valor;
        } catch (Exception e) {
            sc.nextLine();
            return -1;
        }
    }
}