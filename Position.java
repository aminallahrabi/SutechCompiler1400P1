/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler2;

import static java.lang.System.out;

public class Position {

    public int index = 0;
    int line = 0;
    int col = 0;
//    String fileName = null;
//    String text = null;

    Position(int index, int line, int col) {
        this.index = index;
        this.line = line;
        this.col = col;
//        this.fileName = fileName;
//        this.text = text;
    }

    public void advance(String current_char) {
        this.index += 1;
        this.col += 1;

        if (current_char == "\n") {
            this.line += 1;
            this.col = 0;
            }
    }
    public Position copy(){
        return new Position(index, line, col);
    }

}
