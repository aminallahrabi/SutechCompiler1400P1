/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler2;

import java.util.ArrayList;
import java.util.Arrays;

public class Tokens {
    static String digits = "0123456789.";
    static String T_INT = "INTEGER";
    static String T_FLOAT = "FLOAT";
    static String T_STRING = "STRING";
    static String T_IDENTIFIER = "IDENTIFIER";
    static String T_KEYWORD = "KEYWORD";
    static String T_PLUS = "PLUS";
    static String T_PLUSPLUS = "PLUSPLUS";
    static String T_MINUS = "MINUS";
    static String T_MINUSMINUS = "MINUSMINUS";
    static String T_AND = "&&";
    static String T_OR = "||";
    static String T_REMINDER = "%";
    static String T_MUL = "MUL";
    static String T_DIV = "DIV";
    static String T_POW = "POW";
    static String T_EQ = "ASSIGN";
    static String T_LPAREN = "LPAREN";
    static String T_RPAREN = "RPAREN";
    static String T_LBRACKET = "LBRACKET";
    static String T_RBRACKET = "RBRACKET";
    static String T_LSQUARE = "LSQUARE";
    static String T_RSQUARE = "RSQUARE";
    static String Boolean_literal_t = "true";
    static String Boolean_literal_f = "false";
    static String T_EE = "EE";
    static String T_NE = "NE";
    static String T_LT = "LT";
    static String T_GT = "GT";
    static String T_LTE = "LTE";
    static String T_GTE = "GTE";
    static String T_COMMA = "COMMA";
    static String T_COMMENT = "COMMENT";
    static String T_ARROW = "ARROW";
    static String T_ENDCOMMAND = "ENDCOMMAND";
    static String T_NEWLINE = "NEWLINE";
    static String T_EOF = "EOF";
    static String LETTERS = "abcdefghijklmnopqrstuvxywzABCDEFGHIJKLMNOPQRSTUVXYWZ_";
    static String LETTERS_DIGITS = LETTERS + digits;
    static String KEYWORDS []={
    "int",
    "float",
    "String",
    "double",
    "boolean",
    "long",
    "if",
    "elseif",
    "else",
    "for",
    "static",
    "case",
    "while",
    "switch",
    "void",
    "then",
    "function",
    "return",
    "continue",
    "break",
    "public",
    "case",
    "default",
    "else if",
    };
    static ArrayList<String> KEYWORDS2 = new ArrayList<>(Arrays.asList(KEYWORDS));
    
}
