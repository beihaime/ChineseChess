package com.beihaime.chinesechess;

import com.beihaime.chinesechess.game.Game;

public class Main {
    static void main(String[] args) {
        Game game = new Game();
        game.printBoard();
        game.printMoveHistory();
        System.out.println(game.getStatus());
    }
}
