package ArbolBinario;

class Nodo {

    int valor;
    int altura;
    Nodo izquierda, derecha;

    public Nodo(int valor) {
        this.valor = valor;
        this.altura = 1;
        izquierda = derecha = null;
    }
}
