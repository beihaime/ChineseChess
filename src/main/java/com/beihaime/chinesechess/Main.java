package com.beihaime.chinesechess;

import com.beihaime.chinesechess.model.Board;
import com.beihaime.chinesechess.model.Position;
import com.beihaime.chinesechess.rule.RuleEngine;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.setupInitialPosition();
        board.printBoard();
//        System.out.println("移動後");
        RuleEngine ruleEngine = new RuleEngine();
        boolean legal = ruleEngine.isLegalMove(
                board,
                new Position(0,0),
                new Position(9,1));
//        board.printBoard();
        System.out.println(legal);
    }
}
