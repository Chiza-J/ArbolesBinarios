package ArbolBinario;

import java.awt.Color;

class Nodo {

    int valor;
    int altura;
    Nodo izquierda, derecha;
    Color color;

    public Nodo(int valor, int altura) {
        this.valor = valor;
        this.altura = altura;
        this.color = Color.WHITE;
        izquierda = derecha = null;
    }
    
    int Altura(Nodo key){
        if (key == null)
            return 0;
        else
            return key.altura;
    }
    
    void actuAltura(Nodo key){
        int l = Altura(key.izquierda);
        int r = Altura(key.derecha);
        
        key.altura = Math.max(l, r)+1;
    }
}
