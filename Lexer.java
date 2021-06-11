/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler2;

import static java.lang.System.out;
import java.util.ArrayList;

/**
 *
 * @author Amin AllAHRABI
 */
public class Lexer {

    String fileName = null;
    String text = null;
    Position positions = null;
    String currentChar = null;

    Lexer(String fileName, String text) {
        this.fileName = fileName;
        this.text = text;
        this.positions = new Position(-1, 0, -1);
        this.currentChar = null;
        advance();
    }

    public void advance() {
        this.positions.advance(this.currentChar);
        if (this.positions.copy().index < this.text.length()) {
            char current = this.text.charAt(this.positions.index);
            this.currentChar = Character.toString(current);
        } else {
            this.currentChar = null;
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList make_tokens() {
        ArrayList<Token> tokenize = new ArrayList<Token>();
        ArrayList<Error> err = new ArrayList<>();
        while (currentChar != null) {

            
            if (this.currentChar.equals(" ") || this.currentChar.equals("\t")) {
                advance();
            } else if (Tokens.digits.contains(this.currentChar)) {
                tokenize.add(make_number());
            } else if (Tokens.LETTERS.contains(this.currentChar)) {
                
                tokenize.add(make_identifier());
                advance();
            } else if (this.currentChar.equals("\"")) {
                tokenize.add(make_string());
            } else if (this.currentChar.equals("-")) {
                tokenize.add(make_minus_or_arrow());
            } else if (this.currentChar.equals("/")) {

                Token div_or_comment = skip_comment();
                if (div_or_comment != null) {
                    tokenize.add(div_or_comment);
//                    advance();
                } else {
//                    for set error
                }
            } else if (this.currentChar.equals(";")) {
                tokenize.add(new Token(Tokens.T_ENDCOMMAND,this.positions.copy()));
                advance();
            } else if (this.currentChar.equals("\n")) {
                tokenize.add(new Token(Tokens.T_NEWLINE, this.positions.copy()));
                advance();

            } else if (this.currentChar.equals("+")) {
                tokenize.add(Plus_Or_PlusPlus());
            } else if (this.currentChar.equals("*")) {
                tokenize.add(new Token(Tokens.T_MUL,this.positions.copy()));
                advance();
            } else if (this.currentChar.equals("%")) {
                tokenize.add(new Token(Tokens.T_REMINDER, this.positions.copy()));
                advance();
            } else if (this.currentChar.equals("^")) {
                tokenize.add(new Token(Tokens.T_POW,this.positions.copy()));
                advance();
            }
            else if (this.currentChar.equals("=")) {
                tokenize.add(make_equals());
            } else if (this.currentChar.equals("<")) {
                tokenize.add(make_less_than());
            } else if (this.currentChar.equals("!")) {
                Token token = make_not_equals();
                if (token == null) {
//                    for set error
                }
                else{
                    tokenize.add(token);
                }
            } else if (this.currentChar.equals(">")) {
                    tokenize.add(make_greater_than());
            } else if (this.currentChar.equals(",")) {
                tokenize.add(new Token(Tokens.T_COMMA, this.positions.copy()));
                this.advance();
            }
            else if (this.currentChar.equals("(")) {
                tokenize.add(new Token(Tokens.T_LPAREN, this.positions.copy()));
                advance();
            }  else if (this.currentChar.equals(")")){
                tokenize.add(new Token(Tokens.T_RPAREN, this.positions.copy()));
                advance();
            }else if (this.currentChar.equals("{")) {
                tokenize.add(new Token(Tokens.T_LSQUARE, this.positions.copy()));
                advance();
            }  else if (this.currentChar.equals("}")){
                tokenize.add(new Token(Tokens.T_RSQUARE, this.positions.copy()));
                advance();
            }
            else if (this.currentChar.equals("[") ) {
                tokenize.add(new Token(Tokens.T_LBRACKET, this.positions.copy()));
                advance();
            }  
            else if (this.currentChar.equals("[")){
                tokenize.add(new Token(Tokens.T_RBRACKET, this.positions.copy()));
                advance();
            }
            else{
                advance();
                String current = this.currentChar;
                String index = String.valueOf(positions.index);
                Error e = new Error();
                err.add(e.IllegalCharError(index, positions, current));
            }
            
        }
        tokenize.add(new Token(Tokens.T_EOF, this.positions.copy()));
        return tokenize;
    }
    //////////////////////////////////////////////////////////////////////////

    public Token make_number() {

        String num_str = "";
        int dot_count = 0;
        while (this.currentChar != null && Tokens.digits.contains(this.currentChar)) {
            if (this.currentChar.equals(".")) {
                if (dot_count == 1) {
                    break;
                }
                dot_count += 1;
            }
            num_str += this.currentChar;
            advance();
        }
        if (dot_count == 0) {
            return new Token(Tokens.T_INT, num_str, this.positions.copy());
        }
        return new Token(Tokens.T_FLOAT, num_str, this.positions.copy());
    }
///////////////////////////////////////////////////////////////////////////

    public Token make_string() {
        String str = "";
        advance();
        while (this.currentChar != null && (!this.currentChar.equals("\""))) {
                if("\r\n".contains(this.currentChar)){
                    advance(); // for skip from carriage return
                    advance(); // for skip from next line ("\n")
                }
                else{
                    str += this.currentChar;
                    advance();
                }
        }
        advance();
        return new Token(Tokens.T_STRING, str, this.positions.copy().copy());
    }
/////////////////////////////////////////////////////////////////////////////////////////////////

    public Token make_identifier() {
        String id_str = "";
        String TokenType = "";
        while ((this.currentChar != null) && (Tokens.LETTERS_DIGITS.contains(this.currentChar)) && this.currentChar!="\\") {
            id_str += this.currentChar;
            this.advance();
        }
        if (Tokens.KEYWORDS2.contains(id_str)) {
            TokenType = Tokens.T_KEYWORD;
        } else {
            TokenType = Tokens.T_IDENTIFIER;
        }
        
        return new Token(TokenType, id_str, positions.copy());
    }

///////////////////////////////////////////////////////////////////////////////////

    public Token make_minus_or_arrow() {
        advance();

        if (this.currentChar.equals(">")) {
            advance();
            return new Token(Tokens.T_ARROW, this.positions.copy());
        } else if (this.currentChar.equals("-")) {
            this.advance();
            return new Token(Tokens.T_MINUSMINUS, this.positions.copy());
        } else {
            return new Token(Tokens.T_MINUS, this.positions.copy());
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////

    public Token make_not_equals() {
        advance();
        if (this.currentChar.equals("=")) {

            this.advance();
            return new Token(Tokens.T_NE, this.positions.copy());
        }
        return null;
////     ExpectedCharError(pos_start, this.positions.copy(), ""=" (after "!")");
    }
//////////////////////////////////////////////////////////////////////

    public Token make_equals() {
        String tokType = Tokens.T_EQ;
        advance();
        if (this.currentChar.equals("=")) {
            advance();
            tokType = Tokens.T_EE;
        }
        return new Token(tokType,this.positions.copy());
    }
//////////////////////////////////////////////////////////////////////////////////////////

    public Token make_less_than() {
        String tokType = Tokens.T_LT;
        advance();

        if (this.currentChar.equals("=")) {
            advance();
            tokType = Tokens.T_LTE;
        }
        return new Token(tokType, this.positions.copy());
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////                

    public Token make_greater_than() {
        String tokType = Tokens.T_GT;
        advance();

        if (this.currentChar.equals("=")) {
            tokType = Tokens.T_GTE;
            advance();
        }
        return new Token(tokType, this.positions.copy());
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////

    public Token skip_comment() {
        advance();
        if (this.currentChar.equals("/")) {
            while ((!this.currentChar.equals("\n")) && (this.currentChar != null)) {
                advance();
            }        
        } else if(this.currentChar.equals("*")) {
            advance();
            String prevChar = this.currentChar;
            String check = prevChar;
            while (!check.equals("*/")) {
                prevChar = this.currentChar;
                advance();
                if (this.currentChar.equals(null)){
                    return null;
                } else {
                    check = prevChar + this.currentChar;
                }
            }
            advance();
        } else {
            return new Token(Tokens.T_DIV,  this.positions.copy());
        }

        return new Token(Tokens.T_COMMENT, this.positions.copy());
    }
//////////////////////////////////////////////////////////////////////////////////////             

    public Token Plus_Or_PlusPlus() {
        advance();
        if (this.currentChar.equals("+")) {
            advance();
            return new Token(Tokens.T_PLUSPLUS, positions = this.positions.copy());
        }
        return new Token(Tokens.T_PLUS, positions = this.positions.copy());
    }
}
