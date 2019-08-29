Необходимо реализовать интерпретатор языка, описанного ниже. 
На стандартный вход подается программа на данном языке. 
Необходимо вывести значение последнего выражения в программе в десятичной системе счисления

Язык задан следующей грамматикой:

    <character>  ::= "A" | "B" | "C" | "D" | "E" | "F" | "G" | "H" | "I" | "J" 
    | "K" | "L" | "M" | "N" | "O" | "P" | "Q" | "R" | "S" | "T" | "U" | "V" 
    | "W" | "X" | "Y" | "Z" | "a" | "b" | "c" | "d" | "e" | "f" | "g" | "h" 
    | "i" | "j" | "k" | "l" | "m" | "n" | "o" | "p" | "q" | "r" | "s" | "t" 
    | "u" | "v" | "w" | "x" | "y" | "z" | "_"
    <digit>   ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
    <number> ::= <digit> | <digit> <number>
    <identifier> ::= <character> | <identifier> <character>
    <operation> ::= "+" | "-" | "*" | "/" | "%" | ">" | "<" | "="

    <constant-expression> ::= "-" <number> | <number>
    <binary-expression> ::= "(" <expression> <operation> <expression>  ")"
    <argument-list> ::= <expression> | <expression> "," <argument-list>
    <call-expression> ::= <identifier> "(" <argument-list> ")"
    <if-expression> ::= "[" <expression> "]?{" <expression> "}:{"<expression>"}"

    <expression> ::= <identifier>
                      | <constant-expression>
                      | <binary-expression>
                      | <if-expression>
                      | <call-expression>

    <parameter-list> ::= <identifier> | <identifier> "," <parameter-list>

    <function-definition> ::= <identifier>"(" <parameter_list> ")" "={" <expression> "}"

    <function-definition-list> : ""
                                 | <function-definition> <EOL>
                                 | <function-definition> <EOL> <function-definition-list>

    <program> ::= <function-definition-list> <expression>

`<EOL>` - символ перевода строки --- `\n`, программа не содержит других пробельных символов(пробел, табуляция, и т.д.);

Семантика языка задается следующим образом:

1.  Все переменные имеют тип 32-битный Integer;
2.  Гарантируется, что вычисления не приводят к переполнению;
3.  Все арифметические операции аналогичны соответствующим операциям для 32-битного int в языке Java;
4.  Операции сравнения возвращают 1 если сравнение истинно и 0 если ложно;
5.  <if-expression> исполняет второе выражение, если первое выражение не равно 0; иначе исполняет третье;
6.  <call-expression> вызывает функцию с соответствующим именем
7.  Выражения вычисляются слева направо;
