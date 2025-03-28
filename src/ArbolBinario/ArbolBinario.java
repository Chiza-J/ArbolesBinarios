package ArbolBinario;

//carpleos
import java.io.*;
import java.util.Scanner;

class ArbolBinario {

    Nodo raiz;
    private final String historialArchivo = "/home/chiza/NetBeansProjects/Arboles/src/ArbolBinario/historial.txt";

    public ArbolBinario() {
        raiz = null;

    }

    public void insertar(int valor) {

        if (buscar(valor)) {
            guardarHistorial("El valor " + valor + " ya existe en el árbol. No se insertará.");
            System.out.println("El valor " + valor + " ya existe en el árbol.");
        } else {

            raiz = insertarRecursivo(raiz, valor, 1);
            guardarHistorial("Insertado: " + valor);

            insertarDatoEnArchivo(valor);
        }
    }

    private Nodo insertarRecursivo(Nodo nodo, int valor, int cont) {
        if (nodo == null) {
            return new Nodo(valor, cont);
        }
        if (valor < nodo.valor) {
            nodo.izquierda = insertarRecursivo(nodo.izquierda, valor, cont + 1);
        } else if (valor > nodo.valor) {
            nodo.derecha = insertarRecursivo(nodo.derecha, valor, cont + 1);
        }
        return nodo;
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
        return buscarRecursivo(raiz, valor);
    }

    private boolean buscarRecursivo(Nodo raiz, int valor) {
        if (raiz == null) {
            guardarHistorial("Valor no encontrado");
            return false;
        }

        if (valor < raiz.valor) {
            System.out.println(raiz.valor);
            return buscarRecursivo(raiz.izquierda, valor);
        } else if (valor > raiz.valor) {
            System.out.println(raiz.valor);
            return buscarRecursivo(raiz.derecha, valor);
        }
        guardarHistorial("Valor encontrado " + raiz.valor + " Altura: " + raiz.altura);

        return true;

    }

    public void eliminar(int valor) {
        guardarHistorial("Intentando eliminar nodo:" + valor);
        raiz = eliminarRecursivo(raiz, valor);
        eliminarDatoDelArchivo(valor);
    }

    private Nodo eliminarRecursivo(Nodo raiz, int valor) {
        if (raiz == null) {
            guardarHistorial("El valor no esta en el arbol");
            return null;
        }
        if (valor < raiz.valor) {
            raiz.izquierda = eliminarRecursivo(raiz.izquierda, valor);
        } else if (valor > raiz.valor) {
            raiz.derecha = eliminarRecursivo(raiz.derecha, valor);
        } else {
            guardarHistorial("Nodo encontrado a eliminar: " + raiz.valor);
            if (raiz.izquierda == null && raiz.derecha == null) {
                return null;
            }

            if (raiz.izquierda == null) {
                return raiz.derecha;
            } else if (raiz.derecha == null) {
                return raiz.izquierda;
            }

            raiz.valor = obtenerMinimo(raiz.derecha);
            raiz.derecha = eliminarRecursivo(raiz.derecha, raiz.valor);
        }

        return raiz;
    }

    private int obtenerMinimo(Nodo raiz) {
        int minimo = raiz.valor;
        while (raiz.izquierda != null) {
            minimo = raiz.izquierda.valor;
            raiz = raiz.izquierda;
        }
        return minimo;
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

    private void guardarHistorial(String mensaje) {
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
