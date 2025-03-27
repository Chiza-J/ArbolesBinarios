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
        arbol.cargarDesdeArchivo("/home/chiza/NetBeansProjects/Arboles/src/ArbolBinario/datos.txt");
        arbol.insertar(40);
        arbol.insertar(10);
        arbol.insertar(70);
        arbol.insertar(30);
        arbol.insertar(20);
        arbol.insertar(50);
        arbol.insertar(80);
        arbol.insertar(90);
        arbol.buscar(50);
        arbol.eliminar(40);
        arbol.recorridoInorden();
        arbol.recorridoPostorden();
        arbol.recorridoPreorden();
        
        
    }
}
