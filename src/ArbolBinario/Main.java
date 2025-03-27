/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ArbolBinario;

/**
 *
 * @author chiza
 */
// Clase principal para probar el árbol binario
public class Main {

    public static void main(String[] args) {
        ArbolBinario arbol = new ArbolBinario();
// Insertamos algunos valores en el árbol
        arbol.insertar(50);
        arbol.insertar(30);
        arbol.insertar(20);
        arbol.insertar(40);
        arbol.insertar(70);
        arbol.insertar(60);
        arbol.insertar(80);
        arbol.insertar(10);
        arbol.buscar(10);
        arbol.buscar(30);
        arbol.buscar(80);
        arbol.buscar(15);
        System.out.println("Recorrido en Preorden:");
        arbol.recorridoPreorden(); // Raíz, Izquierda, Derecha
        System.out.println();
        System.out.println("Recorrido en Inorden:");
        arbol.recorridoInorden(); // Izquierda, Raíz, Derecha
        System.out.println();
        System.out.println("Recorrido en Postorden:");
        arbol.recorridoPostorden(); // Izquierda, Derecha, Raíz
        System.out.println();
    }
}
