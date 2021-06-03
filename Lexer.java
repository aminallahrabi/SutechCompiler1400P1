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
    public static Position positions = null;
    String currentChar = null;

    Lexer(String fileName, String text) {
        this.fileName = fileName;
        this.text = text;
        this.positions = new Position(-1, 0, -1, fileName, text);
        this.currentChar = null;
        advance();
    }

    public void advance() {
        this.positions.advance(this.currentChar);
        if (this.positions.index < this.text.length()) {
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
            out.println("while cuurent =" + this.currentChar);
            
            if (this.currentChar.equals(" ") || this.currentChar.equals("\t")) {
                advance();
                out.println("if"+currentChar);
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
                tokenize.add(new Token(Tokens.T_ENDCOMMAND,this.positions));
                advance();
            } else if (this.currentChar.equals("\n")) {
                tokenize.add(new Token(Tokens.T_NEWLINE, this.positions));
                advance();

            } else if (this.currentChar.equals("+")) {
                tokenize.add(Plus_Or_PlusPlus());
            } else if (this.currentChar.equals("*")) {
                tokenize.add(new Token(Tokens.T_MUL,this.positions));
                advance();
            } else if (this.currentChar.equals("%")) {
                tokenize.add(new Token(Tokens.T_REMINDER, this.positions));
                advance();
            } else if (this.currentChar.equals("^")) {
                tokenize.add(new Token(Tokens.T_POW,this.positions));
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
                tokenize.add(new Token(Tokens.T_COMMA, this.positions));
                this.advance();
            }
            else if (this.currentChar.equals("(")) {
                tokenize.add(new Token(Tokens.T_LPAREN, this.positions));
                advance();
            }  else if (this.currentChar.equals(")")){
                tokenize.add(new Token(Tokens.T_RPAREN, this.positions));
                advance();
            }else if (this.currentChar.equals("{")) {
                tokenize.add(new Token(Tokens.T_LSQUARE, this.positions));
                advance();
            }  else if (this.currentChar.equals("}")){
                tokenize.add(new Token(Tokens.T_RSQUARE, this.positions));
                advance();
            }
            else if (this.currentChar.equals("[") ) {
                tokenize.add(new Token(Tokens.T_LBRACKET, this.positions));
                advance();
            }  
            else if (this.currentChar.equals("[")){
                tokenize.add(new Token(Tokens.T_RBRACKET, this.positions));
                advance();
            }
            else{
                advance();
                String current = this.currentChar;
                out.println("else"+current);
                String index = String.valueOf(positions.index);
                Error e = new Error();
                err.add(e.IllegalCharError(index, positions, current));
            }
            
        }
        tokenize.add(new Token(Tokens.T_EOF, this.positions));
        return tokenize;
    }
    //////////////////////////////////////////////////////////////////////////
//check
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
            return new Token(Tokens.T_INT, num_str, this.positions);
        }
        return new Token(Tokens.T_FLOAT, num_str, this.positions);
    }
///////////////////////////////////////////////////////////////////////////
//check
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
        return new Token(Tokens.T_STRING, str, this.positions);
    }
/////////////////////////////////////////////////////////////////////////////////////////////////
//check
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
        return new Token(TokenType, id_str, this.positions);
    }

///////////////////////////////////////////////////////////////////////////////////
//check
    public Token make_minus_or_arrow() {
        advance();

        if (this.currentChar.equals(">")) {
            advance();
            return new Token(Tokens.T_ARROW, positions = this.positions);
        } else if (this.currentChar.equals("-")) {
            this.advance();
            return new Token(Tokens.T_MINUSMINUS, positions = this.positions);
        } else {
            return new Token(Tokens.T_MINUS, positions = this.positions);
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////
//CHECK
    public Token make_not_equals() {
        advance();
        if (this.currentChar.equals("=")) {

            this.advance();
            return new Token(Tokens.T_NE, this.positions);
        }
        return null;
////     ExpectedCharError(pos_start, this.positions, ""=" (after "!")");
    }
//////////////////////////////////////////////////////////////////////
//check
    public Token make_equals() {
        String tokType = Tokens.T_EQ;
        advance();
        if (this.currentChar.equals("=")) {
            advance();
            tokType = Tokens.T_EE;
        }
        return new Token(tokType, positions = this.positions);
    }
//////////////////////////////////////////////////////////////////////////////////////////
//check
    public Token make_less_than() {
        String tokType = Tokens.T_LT;
        advance();

        if (this.currentChar.equals("=")) {
            advance();
            tokType = Tokens.T_LTE;
        }
        return new Token(tokType, positions = this.positions);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////                
//check
    public Token make_greater_than() {
        String tokType = Tokens.T_GT;
        advance();

        if (this.currentChar.equals("=")) {
            tokType = Tokens.T_GTE;
            advance();
        }
        return new Token(tokType, positions = this.positions);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
//check
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
            return new Token(Tokens.T_DIV, positions = this.positions);
        }

        return new Token(Tokens.T_COMMENT, positions = this.positions);
    }
//////////////////////////////////////////////////////////////////////////////////////             
//check
    public Token Plus_Or_PlusPlus() {
        advance();
        if (this.currentChar.equals("+")) {
            advance();
            return new Token(Tokens.T_PLUSPLUS, positions = this.positions);
        }
        return new Token(Tokens.T_PLUS, positions = this.positions);
    }
}
