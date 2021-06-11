/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler2;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler2;

public class Error {

    String pos_start;
    String error_name;
    String details;
    Position position = new Position(0, 0, 0);
    Error(){
        
    }
    Error(String pos_start, Position position, String error_name, String details) {
        this.pos_start = pos_start;
        this.position = position;
        this.error_name = error_name;
        this.details = details;
    }
    
    public String as_string() {
        String result = "{" + this.error_name + "} : {in: "+  this.details + "}\n";
        result += "line :{" + this.position.line  + "}";
        result += "\n" + "index :{" + position.index + "}, col :{" + position.col + "}";
        return result;
    }

    public Error ExpectedCharError(String pos_start, Position position, String details) {
        return new Error(pos_start, position, "Expected Character", details);
    }

    public Error InvalidSyntaxError(String pos_start, Position position, String details) {
        return new Error(pos_start, position, "Invalid Syntax", details);
    }

    public Error IllegalCharError(String pos_start, Position position, String details) {
        return new Error(pos_start, position, "Illegal Character", details);
    }

}


public class Error {

    String pos_start;
    String error_name;
    String details;
    Position position;
    Error(){
        
    }
    Error(String pos_start, Position position, String error_name, String details) {
        this.pos_start = pos_start;
        this.position = position;
        this.error_name = error_name;
        this.details = details;
    }
    
    public String as_string() {
        String result = "{" + this.error_name + "} : { " + this.details + "}\n";
        result += "File :{" + this.position.fileName + "}, line :{" + this.position.line + 1 + "}";
        result += "\n" + " index :{" + this.position.index + "}, col :{" + this.position.col + "}";
        return result;
    }

    public Error ExpectedCharError(String pos_start, Position position, String details) {
        return new Error(pos_start, position, "Expected Character", details);
    }

    public Error InvalidSyntaxError(String pos_start, Position position, String details) {
        return new Error(pos_start, position, "Invalid Syntax", details);
    }

    public Error IllegalCharError(String pos_start, Position position, String details) {
        return new Error(pos_start, position, "Illegal Character", details);
    }

}
