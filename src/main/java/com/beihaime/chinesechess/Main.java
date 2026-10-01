package com.beihaime.chinesechess;

import com.beihaime.chinesechess.game.Game;
import com.beihaime.chinesechess.model.Position;

public class Main {
    static void main(String[] args) {
        Game game = new Game();
        System.out.println("移动后：");

        System.out.println(
                game.move(
                        new Position(1,9),
                        new Position(2,7)
                )
        );
        game.printBoard();
        System.out.println("移动后：");
        System.out.println(
                game.move(
                        new Position(1,3),
                        new Position(2,5)
                )
        );
        game.printBoard();
    }
}
