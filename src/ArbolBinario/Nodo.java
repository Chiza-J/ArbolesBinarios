package ArbolBinario;

class Nodo {

    int valor;
    int height;
    Nodo izquierda, derecha;

    public Nodo(int valor) {
        this.valor = valor;
        this.height = 1;
        izquierda = derecha = null;
    }
}
