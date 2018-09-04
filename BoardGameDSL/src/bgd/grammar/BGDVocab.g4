lexer grammar BGDVocab;

//@header{package bgd.grammar; }

//INIT keywords
GAME:	'Game';


//PLAYERS KEYWORDS
PLAYERS:'Players';

//LOCATION keywords
LOCS: 	'Locations';

//OBJECTS KEYWORDS
OBJECTS:'Objects';

//ROUNDS KEYWORDS
ROUNDS:	'Rounds';
MAIN:	'Main';

//ACTIONS KEYWORDS
ACTIONS:'Actions';

//STARTING STATE KEYWORDS
STARTSTATE:	'StartState';

//CONCEPTS KEYWORDS
CONCEPTS:	'Concepts';
VARIABLES:	'Variables';


NUM	: 	DIGIT (DIGIT)*;
ID: UPPERCASE (LETTER | DIGIT)*;
WORD: (DIGIT|LOWERCASE) (LETTER|DIGIT)*;




//Punctuation
LPAR: 	'(';
RPAR:	')';
LBRACE:	'{';
RBRACE:	'}';
SEMI:	';';
COLON:	':';
LBLOCK:	'[';
RBLOCK:	']';
QUOTE:	'\'';
DQUOTE: '"';
COMMA:	',';
EXCL: 	'!';
AT:		'@';
PLUS:	'+';
MINUS:	'-';


//Skipped token types
//COMMENT: EXCL EXCL .*? '\n' -> skip;
//COMMENTBLOCK: EXCL AT .*? AT EXCL -> skip;

WS: [ \t\r\n]+ -> skip;
//ENTER: '\r'?'\n';
SPACE: ' ';


fragment LOWERCASE: [a-z];
fragment UPPERCASE: [A-Z];
fragment LETTER: [a-zA-Z];
fragment DIGIT: [0-9];
fragment NONZERO: [1-9];

