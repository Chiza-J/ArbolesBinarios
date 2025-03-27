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
    
    public void eliminar(int valor){
        System.out.println("Intentando eliminar nodo:"+valor);
        raiz = eliminarRecursivo(raiz, valor);
    }
    
    private Nodo eliminarRecursivo(Nodo raiz, int valor){
        if(raiz==null){
            System.out.println("El valor no esta en el arbol");
            return null;
        }
        if (valor < raiz.valor){
            raiz.izquierda = eliminarRecursivo(raiz.izquierda, valor);
        }
        else if(valor >raiz.valor){
            raiz.derecha = eliminarRecursivo(raiz.derecha, valor);
        }
        else{
            System.out.println("Nodo eliminado: "+raiz.valor);
            if (raiz.izquierda == null && raiz.derecha==null){
                return null;
            }
            
            if(raiz.izquierda == null){
                return raiz.derecha;
            } else if(raiz.derecha == null){
                return raiz.izquierda;
            }
            
            raiz.valor = obtenerMinimo(raiz.derecha);
            raiz.derecha=eliminarRecursivo(raiz.derecha, raiz.valor);
        }
        
        return raiz;
    }
    
    private int obtenerMinimo(Nodo raiz){
        int minimo = raiz.valor;
        while(raiz.izquierda != null){
            minimo = raiz.izquierda.valor;
            raiz = raiz.izquierda;
        }
        return minimo;
    }

}
