package ArbolBinario;

import java.awt.Color;
import java.util.List;

class Nodo {

    int valor;
    int altura;
    Nodo izquierda, derecha;
    Color color;

    public Nodo(int valor) {
        this.valor = valor;
        this.altura = 1;
        this.color = Color.WHITE;
        izquierda = derecha = null;
    }

    //Arbol AVL
    int AlturaAVL(Nodo nodo) {
        if (nodo == null) {
            return 0;
        } else {
            return nodo.altura;
        }
    }

    int balance(Nodo nodo) {
        if (nodo == null) {
            return 0;
        } else {
            return AlturaAVL(nodo.izquierda) - AlturaAVL(nodo.derecha);
        }
    }

    void actuAlturaAVL(Nodo nodo) {
        if (nodo != null) {
            int l = AlturaAVL(nodo.izquierda);
            int r = AlturaAVL(nodo.derecha);

            nodo.altura = Math.max(l, r) + 1;
        }
    }

    public Nodo rotarIzq(Nodo x) {
        if (x == null || x.derecha == null) {
            return x;
        }

        Nodo y = x.derecha;
        Nodo T2 = y.izquierda;

        y.izquierda = x;
        x.derecha = T2;

        actuAlturaAVL(x);
        actuAlturaAVL(y);
        return y;
    }

    public Nodo rotarDer(Nodo y) {
        if (y == null || y.izquierda == null) {
            return y;
        }

        Nodo x = y.izquierda;
        Nodo T2 = x.derecha;

        x.derecha = y;
        y.izquierda = T2;

        actuAlturaAVL(y);
        actuAlturaAVL(x);
        return x;
    }

    public Nodo balancearArbol(Nodo nodo, List<String> rotaciones) {
        if (nodo == null) {
            return null;
        }

        actuAlturaAVL(nodo);
        int factorBalance = balance(nodo);

        // Caso izquierdo-izquierdo (LL)
        if (factorBalance > 1 && balance(nodo.izquierda) >= 0) {
            rotaciones.add("Rotación LL (Simple Derecha)");
            return rotarDer(nodo);
        }

        // Caso izquierdo-derecho (LR)
        if (factorBalance > 1 && balance(nodo.izquierda) < 0) {
            rotaciones.add("Rotación LR (Doble Izquierda-Derecha)");
            nodo.izquierda = rotarIzq(nodo.izquierda);
            return rotarDer(nodo);
        }

        // Caso derecho-derecho (RR)
        if (factorBalance < -1 && balance(nodo.derecha) <= 0) {
            rotaciones.add("Rotación RR (Simple Izquierda)");
            return rotarIzq(nodo);
        }

        // Caso derecho-izquierdo (RL)
        if (factorBalance < -1 && balance(nodo.derecha) > 0) {
            rotaciones.add("Rotación RL (Doble Derecha-Izquierda)");
            nodo.derecha = rotarDer(nodo.derecha);
            return rotarIzq(nodo);
        }

        return nodo;
    }
}
