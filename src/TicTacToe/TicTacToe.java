package TicTacToe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TicTacToe {

    final static char MAX = 'x';
    final static char MIN = 'o';
    static int jugada;

    public static void main(String[] args) {
        Juego g = new Juego();

        // Obtener algoritmo
        int algoritmo = setAlgoritmo();

        while (!g.fim()) {
            // Imprime o tablero
            System.out.println(g.display());

            // obtiene posicion de la jugada
            getJugada();

            // revisa cual jugador pasa a otra iteracion
            g.mudarVez('x', jugada);

            if (g.fim()) {
                break;
            }

            float v = Integer.MAX_VALUE;
            Juego prox = null;

            /**
             * configuraciones: x - MAX o - MIN
             */
            for (Juego next : g.prox()) {
                int val = 0;
                // si algoritmo = 2,alphabeta
                if (algoritmo == 2) {
                    val = next.alphabeta(next, Integer.MIN_VALUE, Integer.MAX_VALUE, 'x');
                } // si algoritmo = 1,  minimax
                else if (algoritmo == 1) {
                    val = next.minimax(next, 'x');
                } else {
                    System.out.println("Error no a escogido  algoritmo");
                    System.exit(1);
                }
                if (val <= v) {
                    v = val;
                    prox = next;
                }
            }
            g = prox;
            // muestra la posicion de jugada min en el tablero 
            System.out.println(g.display());
        }

        // Mostra  tablero final
        System.out.println(g.display());
        // obtiene el vencedor
        char vencedor = g.getGanador();
        // si el vencedor = e, hay un empate
        if (vencedor == 'e') {
            System.out.println("Empate!");
        } else {
            System.out.println(" Jugador " + vencedor + " gano");
        }

    }

    //pide datos en teclado para realizar una jugada
    public static void getJugada() {

        System.out.println("Digite um numero de 1 a 9: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            jugada = Integer.parseInt(br.readLine());
        } catch (IOException ioe) {
            System.out.println("Numero inválido!");
            System.exit(1);
        }
        if (jugada < 1 || jugada > 9) {
            System.out.println("La jugada debe ser um número entre 1 e 9");
        }

    }

    //recibe desde teclado 2 valores para realizar uno de los dos algoritmos
    static int setAlgoritmo() {
        int algoritmo = 0;

        System.out.println("Digite 1 para Algoritmo MINIMAX o 2 para Poda ALPHA-BETA: ");
       
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            algoritmo = Integer.parseInt(br.readLine());
        } catch (IOException ioe) {
            System.out.println("Numero invalido!");

        }
        if (algoritmo == 1) {
            System.out.println("Has elegido Minimax");
        }
        if (algoritmo == 2) {
            System.out.println("Has elegido MInimax con poda alfa-beta");
        }

        if (algoritmo != 1 || algoritmo != 2) {
            System.out.println("Debe elegir entre 1 o 2");

            
        }
        return algoritmo;

    }
}
