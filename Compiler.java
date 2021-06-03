/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.out;
import java.util.ArrayList;

/**
 *
 * @author AminAll
 */
public class Compiler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("E:\\project\\JAVA-project\\io.txt"));
            int c ;
            String text = "";
            while ((c=br.read())!=-1) {
                char character =(char) c;          //converting integer to char
                out.println(character);
                text += character;
            }
            Lexer Lex = new Lexer("io.txt", text);
            ArrayList<Token> r = Lex.make_tokens();
            for(int i=0 ; i <r.size(); i++ ) {
                out.println(r.get(i).toString());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
}
