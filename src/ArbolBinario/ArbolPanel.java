/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ArbolBinario;

/**
 *
 * @author chiza
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

class ArbolPanel extends JPanel {

    private ArbolBinario arbol;
    private List<Nodo> recorridoNodos;
    private int indiceActual = -1; 

    public ArbolPanel(ArbolBinario arbol) {
        this.arbol = arbol;
        recorridoNodos = new ArrayList<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Nodo nodoSeleccionado = detectarNodo(e.getX(), e.getY(), arbol.raiz, getWidth() / 2, 30, getWidth() / 4);
                if (nodoSeleccionado != null) {
                    int valor = nodoSeleccionado.valor;
                    int respuesta = JOptionPane.showConfirmDialog(
                            null,
                            "¿Estás seguro de que quieres eliminar el nodo con valor " + valor + "?",
                            "Confirmación de eliminación",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (respuesta == JOptionPane.YES_OPTION) {
                        arbol.eliminar(valor);
                        repaint(); 
                    }
                }
            }
        });
    }

    private void generarRecorridoInorden(Nodo nodo) {
        recorridoNodos.clear();
        if (nodo != null) {
            generarRecorridoInordenRecursivo(nodo);
        }
    }

    private void generarRecorridoInordenRecursivo(Nodo nodo) {
        if (nodo != null) {
            generarRecorridoInordenRecursivo(nodo.izquierda);
            recorridoNodos.add(nodo);
            generarRecorridoInordenRecursivo(nodo.derecha);
        }
    }

    private void generarRecorridoPreorden(Nodo nodo) {
        recorridoNodos.clear();
        if (nodo != null) {
            generarRecorridoPreordenRecursivo(nodo);
        }
    }

    private void generarRecorridoPreordenRecursivo(Nodo nodo) {
        if (nodo != null) {
            recorridoNodos.add(nodo);
            generarRecorridoPreordenRecursivo(nodo.izquierda);
            generarRecorridoPreordenRecursivo(nodo.derecha);
        }
    }

    private void generarRecorridoPostorden(Nodo nodo) {
        recorridoNodos.clear();
        if (nodo != null) {
            generarRecorridoPostordenRecursivo(nodo);
        }
    }

    private void generarRecorridoPostordenRecursivo(Nodo nodo) {
        if (nodo != null) {
            generarRecorridoPostordenRecursivo(nodo.izquierda);
            generarRecorridoPostordenRecursivo(nodo.derecha);
            recorridoNodos.add(nodo);
        }
    }

    public void iniciarAnimacionRecorrido(String tipoRecorrido) {
        switch (tipoRecorrido.toLowerCase()) {
            case "inorden":
                generarRecorridoInorden(arbol.raiz);
                break;
            case "preorden":
                generarRecorridoPreorden(arbol.raiz);
                break;
            case "postorden":
                generarRecorridoPostorden(arbol.raiz);
                break;
            default:
                throw new IllegalArgumentException("Tipo de recorrido no válido: " + tipoRecorrido);
        }

        // Temporizador para animación
        Timer timer = new Timer(1000, e -> {
            indiceActual++;
            if (indiceActual < recorridoNodos.size()) {
                recorridoNodos.get(indiceActual).color = Color.RED; 
                repaint();
            } else {
                ((Timer) e.getSource()).stop();
                mostrarRecorridoPopUp(tipoRecorrido); 
            }
        });

        indiceActual = -1; 
        timer.start(); 
    }

    private void mostrarRecorridoPopUp(String tipoRecorrido) {
        StringBuilder recorrido = new StringBuilder("Recorrido ").append(tipoRecorrido).append(": ");
        for (Nodo nodo : recorridoNodos) {
            recorrido.append(nodo.valor).append(" ");
        }
        JOptionPane.showMessageDialog(this, recorrido.toString(), "Recorrido Finalizado", JOptionPane.INFORMATION_MESSAGE);
        for (Nodo nodo : recorridoNodos) {
            nodo.color = Color.WHITE;
        }
        repaint();
    }

    public void buscarNodoConPopUp(int valor) {
        Nodo nodoBuscado = buscarNodoRecursivo(arbol.raiz, valor);
        if (nodoBuscado != null) {
            nodoBuscado.color = Color.GREEN;
            repaint();
            JOptionPane.showMessageDialog(this,
                    "Valor encontrado: " + nodoBuscado.valor + "\nAltura del nodo: " + nodoBuscado.altura,
                    "Búsqueda Exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
            nodoBuscado.color = Color.WHITE;
            repaint();
        } else {
            JOptionPane.showMessageDialog(this,
                    "El valor " + valor + " no fue encontrado en el árbol.",
                    "Búsqueda Fallida",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private Nodo buscarNodoRecursivo(Nodo nodo, int valor) {
        if (nodo == null) {
            return null;
        }

        if (valor < nodo.valor) {
            return buscarNodoRecursivo(nodo.izquierda, valor);
        } else if (valor > nodo.valor) {
            return buscarNodoRecursivo(nodo.derecha, valor);
        } else {
            return nodo; 
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (arbol.raiz != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(2.5f));
            dibujarArbol(g2d, arbol.raiz, getWidth() / 2, 30, getWidth() / 4);
        }
    }

    private void dibujarArbol(Graphics2D g, Nodo nodo, int x, int y, int separacion) {
        if (nodo != null) {
            int radioNodo = 40; 
            int ajusteLinea = radioNodo / 2;
            // Dibujar líneas hacia los hijos
            g.setColor(Color.BLACK);
            if (nodo.izquierda != null) {
                int destinoX = x - separacion;
                int destinoY = y + 50 - ajusteLinea;
                g.drawLine(x, y + ajusteLinea, destinoX, destinoY);
            }
            if (nodo.derecha != null) {
                int destinoX = x + separacion;
                int destinoY = y + 50 - ajusteLinea;
                g.drawLine(x, y + ajusteLinea, destinoX, destinoY);
            }

            // Dibujar nodo
            g.setColor(nodo.color != null ? nodo.color : Color.WHITE);
            g.fillOval(x - radioNodo / 2, y - radioNodo / 2, radioNodo, radioNodo);
            g.setColor(Color.BLACK);
            g.drawOval(x - radioNodo / 2, y - radioNodo / 2, radioNodo, radioNodo);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            String valorNodo = Integer.toString(nodo.valor);
            FontMetrics fm = g.getFontMetrics();
            int textoAncho = fm.stringWidth(valorNodo);
            int textoAlto = fm.getAscent();
            g.drawString(valorNodo, x - textoAncho / 2, y + textoAlto / 4);

            // Dibujar subárboles
            dibujarArbol(g, nodo.izquierda, x - separacion, y + 50, separacion / 2);
            dibujarArbol(g, nodo.derecha, x + separacion, y + 50, separacion / 2);
        }
    }

    private Nodo detectarNodo(int mouseX, int mouseY, Nodo nodo, int x, int y, int separacion) {
        if (nodo != null) {
            int radioNodo = 40; 
            int distanciaX = mouseX - x;
            int distanciaY = mouseY - y;
            // Comprobar si el clic está dentro del nodo
            if (Math.sqrt(distanciaX * distanciaX + distanciaY * distanciaY) <= radioNodo / 2) {
                return nodo;
            }

            // Buscar en los subárboles
            Nodo nodoIzquierdo = detectarNodo(mouseX, mouseY, nodo.izquierda, x - separacion, y + 50, separacion / 2);
            if (nodoIzquierdo != null) {
                return nodoIzquierdo;
            }
            return detectarNodo(mouseX, mouseY, nodo.derecha, x + separacion, y + 50, separacion / 2);
        }
        return null;
    }
}
