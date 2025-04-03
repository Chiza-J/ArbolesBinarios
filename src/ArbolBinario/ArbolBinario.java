package ArbolBinario;

//carpleos
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ArbolBinario {

    Nodo raiz;
    private final String historialArchivo = "/home/chiza/NetBeansProjects/Arboles/src/ArbolBinario/historial.txt";

    public ArbolBinario() {
        raiz = null;

    }

    public void insertar(int valor) {
        if (buscar(valor)) {
            guardarHistorial("El valor " + valor + " ya existe en el árbol.");
        } else {
            List<String> rotaciones = new ArrayList<>();
            raiz = insertarRecursivo(raiz, valor, rotaciones);
            for (String rotacion : rotaciones) {
                guardarHistorial(rotacion);
            }
            guardarHistorial("Insertado: " + valor);
            insertarDatoEnArchivo(valor);
        }
    }

    private Nodo insertarRecursivo(Nodo nodo, int valor, List<String> rotaciones) {
        if (nodo == null) {
            return new Nodo(valor);
        }
        if (valor < nodo.valor) {
            nodo.izquierda = insertarRecursivo(nodo.izquierda, valor, rotaciones);
        } else if (valor > nodo.valor) {
            nodo.derecha = insertarRecursivo(nodo.derecha, valor, rotaciones);
        } else {
            return nodo;
        }
        return nodo.balancearArbol(nodo, rotaciones);
    }

// Recorrido en preorden (Raíz, Izquierda, Derecha)
    public void recorridoPreorden() {
        StringBuilder resultado = new StringBuilder("Recorrido Preorden: ");
        recorridoPreordenRecursivo(raiz, resultado);
        guardarHistorial(resultado.toString());
    }

    private void recorridoPreordenRecursivo(Nodo nodo, StringBuilder resultado) {
        if (nodo != null) {
            resultado.append(nodo.valor).append(" ");
            recorridoPreordenRecursivo(nodo.izquierda, resultado);
            recorridoPreordenRecursivo(nodo.derecha, resultado);
        }
    }
// Recorrido en inorden (Izquierda, Raíz, Derecha)

    public void recorridoInorden() {
        StringBuilder resultado = new StringBuilder("Recorrido Inorden: ");
        recorridoInordenRecursivo(raiz, resultado);
        guardarHistorial(resultado.toString());
    }

    private void recorridoInordenRecursivo(Nodo nodo, StringBuilder resultado) {
        if (nodo != null) {
            recorridoInordenRecursivo(nodo.izquierda, resultado);
            resultado.append(nodo.valor).append(" ");
            recorridoInordenRecursivo(nodo.derecha, resultado);
        }
    }

// Recorrido en postorden (Izquierda, Derecha, Raíz)
    public void recorridoPostorden() {
        StringBuilder resultado = new StringBuilder("Recorrido Postorden: ");
        recorridoPostordenRecursivo(raiz, resultado);
        guardarHistorial(resultado.toString());
    }

    private void recorridoPostordenRecursivo(Nodo nodo, StringBuilder resultado) {
        if (nodo != null) {
            recorridoPostordenRecursivo(nodo.izquierda, resultado);
            recorridoPostordenRecursivo(nodo.derecha, resultado);
            resultado.append(nodo.valor).append(" ");
        }
    }

    public boolean buscar(int valor) {
        List<Integer> camino = new ArrayList<>();
        boolean encontrado = buscarRecursivo(raiz, valor, camino);
        if (encontrado) {
            guardarHistorial("Búsqueda exitosa: " + valor
                    + " | Profundidad: " + camino.size()
                    + " | Camino: " + camino);
        } else {
            guardarHistorial("Búsqueda fallida: " + valor);
        }
        return encontrado;
    }

    private boolean buscarRecursivo(Nodo nodo, int valor, List<Integer> camino) {
        if (nodo == null) {
            return false;
        }

        camino.add(nodo.valor);

        if (valor == nodo.valor) {
            return true;
        } else if (valor < nodo.valor) {
            return buscarRecursivo(nodo.izquierda, valor, camino);
        } else {
            return buscarRecursivo(nodo.derecha, valor, camino);
        }
    }
    public void eliminar(int valor) {
        List<String> rotaciones = new ArrayList<>();
        raiz = eliminarRecursivo(raiz, valor, rotaciones);

        // Registrar rotaciones en el historial
        for (String rotacion : rotaciones) {
            guardarHistorial("Rotación: " + rotacion);
        }
        guardarHistorial("Eliminado: " + valor);
    }

    private Nodo eliminarRecursivo(Nodo nodo, int valor, List<String> rotaciones) {
        if (nodo == null) {
            guardarHistorial("Valor no encontrado: " + valor);
            return null;
        }
        if (valor < nodo.valor) {
            nodo.izquierda = eliminarRecursivo(nodo.izquierda, valor, rotaciones);
        } else if (valor > nodo.valor) {
            nodo.derecha = eliminarRecursivo(nodo.derecha, valor, rotaciones);
        } else {
            // Caso 1: Nodo hoja o con un hijo
            if (nodo.izquierda == null) {
                return nodo.derecha;
            }
            if (nodo.derecha == null) {
                return nodo.izquierda;
            }
            // Caso 2: Nodo con dos hijos
            nodo.valor = obtenerMinimo(nodo.derecha);
            nodo.derecha = eliminarRecursivo(nodo.derecha, nodo.valor, rotaciones);
        }
        return nodo.balancearArbol(nodo, rotaciones);
    }

    private int obtenerMinimo(Nodo nodo) {
        while (nodo.izquierda != null) {
            nodo = nodo.izquierda;
        }
        return nodo.valor;
    }

    public void cargarDesdeArchivo(String nombreArchivo) {
        try (Scanner scanner = new Scanner(new File(nombreArchivo))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] valores = linea.split(",");
                for (String valor : valores) {
                    try {
                        insertar(Integer.parseInt(valor.trim()));
                    } catch (NumberFormatException e) {
                        System.out.println("Valor no válido en archivo: " + valor);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Archivo no encontrado");
        }
    }

    public void guardarResultados(String nombreArchivo, String contenido) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write(contenido);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo");
        }
    }

    public void guardarHistorial(String mensaje) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(historialArchivo, true))) {
            writer.write(mensaje + "\n");
        } catch (IOException e) {
            System.out.println("Error al guardar el historial");
        }
    }

    public void eliminarDatoDelArchivo(int valor) {
        try {
            File archivo = new File("/home/chiza/NetBeansProjects/Arboles/src/ArbolBinario/datos.txt");
            if (!archivo.exists()) {
                System.out.println("Error: Archivo no encontrado");
                return;
            }
            StringBuilder contenidoNuevo = new StringBuilder();
            try (Scanner scanner = new Scanner(archivo)) {
                while (scanner.hasNextLine()) {
                    String linea = scanner.nextLine();
                    String[] valores = linea.split(",");
                    for (String val : valores) {
                        try {
                            if (Integer.parseInt(val.trim()) != valor) {
                                contenidoNuevo.append(val).append(",");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Dato no válido: " + val);
                        }
                    }
                }
            }
            if (contenidoNuevo.length() > 0) {
                contenidoNuevo.setLength(contenidoNuevo.length() - 1);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                writer.write(contenidoNuevo.toString());
            }

            guardarHistorial("Dato " + valor + " eliminado del archivo");
        } catch (IOException e) {
            System.out.println("Error al eliminar dato del archivo: " + e.getMessage());
        }
    }

    public void insertarDatoEnArchivo(int valor) {
        try {
            File archivo = new File("/home/chiza/NetBeansProjects/Arboles/src/ArbolBinario/datos.txt");
            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            StringBuilder contenidoActual = new StringBuilder();
            try (Scanner scanner = new Scanner(archivo)) {
                if (scanner.hasNextLine()) {
                    contenidoActual.append(scanner.nextLine());
                }
            }

            String[] valoresExistentes = contenidoActual.toString().split(",");
            for (String val : valoresExistentes) {
                if (!val.trim().isEmpty()) { // Verifica que no sea vacío
                    try {
                        if (Integer.parseInt(val.trim()) == valor) {
                            System.out.println("El valor ya existe en el archivo: " + valor);
                            return;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Dato no válido: " + val);
                    }
                }
            }

            if (contenidoActual.length() > 0 && contenidoActual.charAt(contenidoActual.length() - 1) != ',') {
                contenidoActual.append(",");
            }

            contenidoActual.append(valor);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                writer.write(contenidoActual.toString());
            }
        } catch (IOException e) {
            System.out.println("Error al insertar dato en el archivo: " + e.getMessage());
        }
    }

}
