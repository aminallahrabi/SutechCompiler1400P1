# sutech-compiler-1400

this is lexer for java language.
6 class declare as name (token,tokens,lexer,position,error,compiler).


at first in the compiler class read each character of input in save them to String variable (text) and make object from lexer and give text and file name to the class parameters for initial value and call constructor .

then call make_tokens() for checking every character and detect the type and value of character(Token) , and return the array list of tokens.

in token class declare token as object and every token has attribute  : Type - value - position - start index .
has method : toString - matches .

toString : for return type + value of token.
matches : for checking the type and value is correct or not.

tokens class:
declare all the tokens and their value.

positions class:
has attribute : index - line - col - filename - text 
for store a details about the token positions.
method : advance - copy
advance : when the new character(token) set in the while loop then the value of index-col... should increase.
copy : for get copy of toksn positions.

lexer class:
initial the attribute by constructor.
methods : make_tokens - make_number - make_string - make_identifier - make_minus_or_arrow - make_not_equal - make_equal - make_less_than - make_greater_than - skip_comment - plus_or_plusplus

make_tokens : check character by while loop and call functions that needed then adding the detected token to arraylist .
make_number : for detect the digit token.
make_string : for detect the string value .
make_identifier : for detect the identifier .
make_minus_or_arrow : check the minus is only token or not(-,--),is arrow (->).
make_not_equal : by checking the token after the (!) character .
make_equal : check the token is (=) or (==).
make_less_than : detect the (=) after the (>) character .
skip_comment : check the comment is single or multy line or is div(/)token.
plus_or_plusplus : detect the token is only (+) or (++).


