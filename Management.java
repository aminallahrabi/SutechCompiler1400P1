/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler2;

import java.util.ArrayList;
import static java.lang.System.out;
public class Management {

    ArrayList<Token> tokens = new ArrayList<Token>();
    ArrayList<Error> errors = new ArrayList<Error>();
    Management(ArrayList t) {
        tokens = t;
    }

    public void checkAssignInt(int i) {
        int index = i;
        if(tokens.get(index+1).type != Tokens.T_IDENTIFIER){
            Error e = new Error().InvalidSyntaxError(String.valueOf(tokens.get(index+1).pos_start), tokens.get(index+1).positions, "more variable"+tokens.get(index+1).value);
           out.println(e.as_string());
           index++;
        }
        if(tokens.get(index+2).type != Tokens.T_EQ){
            Error e = new Error().InvalidSyntaxError(String.valueOf(tokens.get(index+2).pos_start), tokens.get(index+2).positions, "need =");
            out.println(e.as_string());
            index++;
        }
        if(tokens.get(index+3).type != Tokens.T_INT){
            Error e = new Error().InvalidSyntaxError(String.valueOf(tokens.get(index+3).pos_start), tokens.get(index+3).positions, "check var type "+tokens.get(index+3).value);
            out.println(e.as_string());
            index++;
        }
        for (int j = index; j < tokens.size(); j++) {
             if((tokens.get(j).type != Tokens.T_ENDCOMMAND) && (tokens.get(j).type!=Tokens.T_EOF)){
                    if(tokens.get(j).type == Tokens.T_NEWLINE){
                        Error e = new Error().InvalidSyntaxError(String.valueOf(tokens.get(j).pos_start), tokens.get(j).positions,"need "+Tokens.T_ENDCOMMAND+" but "+tokens.get(j).type);
                        out.println(e.as_string());
                    }
             }
        }
    }
        public void checkAssignString(int i) {
        int index = i;

        if(tokens.get(index+1).type != Tokens.T_IDENTIFIER){
            Error e = new Error().InvalidSyntaxError(String.valueOf(tokens.get(index+1).pos_start), tokens.get(index+1).positions, "more variable"+tokens.get(index+1).value);
           out.println(e.as_string());
           index++;
        }
        if(tokens.get(index+2).type != Tokens.T_EQ){
            Error e = new Error().InvalidSyntaxError(String.valueOf(tokens.get(index+2).pos_start), tokens.get(index+2).positions, "need =");
            out.println(e.as_string());
            index++;
        }
        if(tokens.get(index+3).type != Tokens.T_STRING){
            Error e = new Error().InvalidSyntaxError(String.valueOf(tokens.get(index+3).pos_start), tokens.get(index+3).positions, "check var type "+tokens.get(index+3).value);
            out.println(e.as_string());
            index++;
        }
        for (int j = index; j < tokens.size(); j++) {
             if((tokens.get(j).type != Tokens.T_ENDCOMMAND) && (tokens.get(j).type!=Tokens.T_EOF)){
                    if(tokens.get(j).type == Tokens.T_NEWLINE){
                        Error e = new Error().InvalidSyntaxError(String.valueOf(tokens.get(j).pos_start), tokens.get(j).positions,"need "+Tokens.T_ENDCOMMAND+" but "+tokens.get(j).type);
                        out.println(e.as_string());
                    }
             }
        }
    }
    public void checkIWFstructure(int i){
        int index = i;
        if(tokens.get(index+1).type != Tokens.T_LPAREN){
            Error e = new Error().InvalidSyntaxError(String.valueOf(tokens.get(index+1).pos_start), tokens.get(index+1).positions, "need ( but "+tokens.get(index+1).value);
           out.println(e.as_string());
           index++;
        }
    }
    public void manage() {
        for (int i = 0; i < tokens.size(); i++) {
             if(tokens.get(i).type == Tokens.T_KEYWORD){
                 if(tokens.get(i).value.equals("int")){
                     checkAssignInt(i);
                 }
                 else if(tokens.get(i).value.equals("String")){
                     checkAssignString(i);
                 }
                 else if(tokens.get(i).value == "if" || tokens.get(i).value == "while" || tokens.get(i).value == "for"){
                     checkIWFstructure(i);
                 }
                 else{
                     
                 }
             }
        }
    }
    
}
