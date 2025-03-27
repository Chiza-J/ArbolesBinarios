package ArbolBinario;

//carpleos
class ArbolBinario {

    Nodo raiz;

    public ArbolBinario() {
        raiz = null;
    }

    public void insertar(int valor) {
        raiz = insertarRecursivo(raiz, valor, 1);
    }

    private Nodo insertarRecursivo(Nodo nodo, int valor, int cont) {
        if (nodo == null) {
            return new Nodo(valor, cont);
        }
        if (valor < nodo.valor) {
            nodo.izquierda = insertarRecursivo(nodo.izquierda, valor, cont+1);
        } else if (valor > nodo.valor) {
            nodo.derecha = insertarRecursivo(nodo.derecha, valor,cont+1);
        }
        return nodo;
    }
// Recorrido en preorden (Raíz, Izquierda, Derecha)

    public void recorridoPreorden() {
        recorridoPreordenRecursivo(raiz);
    }

    private void recorridoPreordenRecursivo(Nodo nodo) {
        if (nodo != null) {
            System.out.print(nodo.valor + " ");
            recorridoPreordenRecursivo(nodo.izquierda);
            recorridoPreordenRecursivo(nodo.derecha);
        }
    }
// Recorrido en inorden (Izquierda, Raíz, Derecha)

    public void recorridoInorden() {
        recorridoInordenRecursivo(raiz);
    }

    private void recorridoInordenRecursivo(Nodo nodo) {
        if (nodo != null) {
            recorridoInordenRecursivo(nodo.izquierda);
            System.out.print(nodo.valor + " ");
            recorridoInordenRecursivo(nodo.derecha);
        }
    }
// Recorrido en postorden (Izquierda, Derecha, Raíz)

    public void recorridoPostorden() {
        recorridoPostordenRecursivo(raiz);
    }

    private void recorridoPostordenRecursivo(Nodo nodo) {
        if (nodo != null) {
            recorridoPostordenRecursivo(nodo.izquierda);
            recorridoPostordenRecursivo(nodo.derecha);
            System.out.print(nodo.valor + " ");
        }
    }

    public boolean buscar(int valor) {
        return buscarRecursivo(raiz, valor);
    }

    private boolean buscarRecursivo(Nodo raiz, int valor) {
        if (raiz == null) {
            System.out.println("Valor no encontrado");
            return false;
        }

        if (valor < raiz.valor) {
            System.out.println(raiz.valor);
            return buscarRecursivo(raiz.izquierda, valor);
        } else if (valor > raiz.valor) {
            System.out.println(raiz.valor);
            return buscarRecursivo(raiz.derecha, valor);
        }
        System.out.println("Valor encontrado " + raiz.valor);
        System.out.println("Altura: "+raiz.altura);
        return true;

    }

}
