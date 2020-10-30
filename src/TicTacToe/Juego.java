package TicTacToe;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.SourceVersion;

public class Juego {

    static float[] valores = {-100, 100};
    public char[] estado = new char[9];
    public char jogador;

    /**
     * Inicia juego com MAX = x en el tablero vacio
     */
    public Juego() {
        for (int i = 0; i < estado.length; i++) {
            estado[i] = ' ';
        }
        jogador = 'x';
    }

    /**
     * Inicia juego con jugador j, e, en el tablero
     */
    public Juego(char[] e, char j) {
        estado = e;
        jogador = j;
    }

    //muestra en el tablero
    public String display() {
        String board = "-------------\n";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board += "| " + estado[i * 3 + j] + " ";
            }
            board += "|\n-------------\n";
        }
        return board;
    }

    List<Juego> prox() {
        List<Juego> resp = new ArrayList<Juego>();
        char prox_jogador;
        if (jogador == 'x') {
            prox_jogador = 'o';
        } else {
            prox_jogador = 'x';
        }
        for (int i = 1; i <= 9; i++) {
            char[] e = getTabuleiro(jogador, i);
            if (e != null) {
                resp.add(new Juego(e, prox_jogador));
            }
        }
        return resp;
    }

    //retorna al tablero de acuerdo con la posicion del jugador en la jugada
    public char[] getTabuleiro(char player, int n) {
        if (n > 0 && n < 10 && estado[n - 1] == ' ') {
            char[] resp = new char[9];
            for (int i = 0; i < 9; i++) {
                resp[i] = estado[i];
            }
            resp[n - 1] = player;
            return resp;
        } else {
            return null;
        }
    }

    
    boolean completo() {
        for (int i = 0; i < estado.length; i++) {
            if (estado[i] == ' ') {
                return false;
            }
        }
        return true;
    }

    /**
     * Realiza la jugada según el jugador, la posición de la jugada
     *
     */
    public char[] jugar(char vez, int jugada) {
        if (jugada > 0 && jugada < 10 && estado[jugada - 1] == ' ') {
            char[] resp = new char[9];
            for (int i = 0; i < 9; i++) {
                resp[i] = estado[i];
            }
            resp[jugada - 1] = vez;
            return resp;
        } else {
            return null;
        }
    }

    public void mudarVez(char vez, int jogada) {
        char[] newEstado = jugar(vez, jogada);
        if (newEstado != null) {
            estado = newEstado;
        }
        if (vez == 'x') {
            jogador = 'o';
        } else {
            jogador = 'x';
        }
    }

    /**
     * Algoritmo alpha-beta
     */
    public int alphabeta(Juego g, int alpha, int beta, char vez) {
        // si g  terminal, retorna a heuristica
        if (g.fim()) {
            char ganhador = g.getGanador();
            if (ganhador == 'x') {
                return 1;
            } else if (ganhador == 'o') {
                return -1;
            } else {
                return 0;
            }
        }
        if (vez == 'x') {
            int v = Integer.MIN_VALUE;
            for (Juego next : g.prox()) {

                v = Integer.max(v, alphabeta(next, alpha, beta, 'o'));

                alpha = Integer.max(alpha, v);

                if (beta <= alpha) {
                    break;
                }
            }
            return v;
        } else {
            int v = Integer.MAX_VALUE;
            for (Juego next : g.prox()) {
                v = Integer.min(v, alphabeta(next, alpha, beta, 'x'));
                beta = Integer.min(beta, v);
                if (beta <= alpha) {
                    break;
                }
            }
            return v;
        }
    }

    /**
     * Algoritmo minimax
     */
    public int minimax(Juego g, char vez) {
        // Se g terminal => retornar heuristica de g
        if (g.fim()) {
            char ganhador = g.getGanador();
            if (ganhador == 'x') {
                return 1;
            } else if (ganhador == 'o') {
                return -1;
            } else {
                return 0;
            }
        }
        if (vez == 'x') {
            int best = Integer.MIN_VALUE;
            // para cada filo de g
            for (Juego next : g.prox()) {
                int v = minimax(next, 'o');
                best = Integer.max(best, v);
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (Juego next : g.prox()) {
                int v = minimax(next, 'x');
                best = Integer.min(best, v);
            }
            return best;
        }
    }

    //obtiene las listas verticales , horizontales y diagonales que podemos contar valores que 
    //determinan 
    public ArrayList<char[]> linhas() {
        ArrayList<char[]> resp = new ArrayList<>();
        // horizontal
        for (int i = 0; i < 3; i++) {
            char[] row = new char[3];
            row[0] = estado[i * 3];
            row[1] = estado[i * 3 + 1];
            row[2] = estado[i * 3 + 2];
            resp.add(row);
        }

        // vertical
        for (int i = 0; i < 3; i++) {
            char[] column = new char[3];
            column[0] = estado[i];
            column[1] = estado[i + 3];
            column[2] = estado[i + 6];
            resp.add(column);
        }

        // diagonal
        char[] diag1 = {estado[0], estado[4], estado[8]};
        char[] diag2 = {estado[2], estado[4], estado[6]};

        resp.add(diag1);
        resp.add(diag2);

        return resp;
    }

    //verifica las lineas obtenidas, para saber el ganador
    char getGanador() {
        for (char[] linha : linhas()) {
            if (linha[0] == linha[1] && linha[1] == linha[2]) {
                if (linha[0] == 'x') {
                    return 'x';
                } else if (linha[0] == 'o') {
                    return 'o';
                }
            }
        }
        if (completo()) {
            return 'e';
        }
        return 0;
    }

    //verifica si existe un ganador o empate
    boolean fim() {
        char g = getGanador();
        return g != 0;
    }

}
