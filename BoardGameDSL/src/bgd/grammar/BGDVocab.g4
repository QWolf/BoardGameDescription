lexer grammar BGDVocab;

//@header{package bgd.grammar; }

//INIT keywords
GAME		:	'Game';


//PLAYERS KEYWORDS
PLAYERS		:	'Players';
MINPLAYERS	:	'MinPlayers';
MAXPLAYERS	:	'MaxPlayers';
HUMANS		:	'Humans';
NPCS		:	'NPCs';
BANK		:	'Bank';
TEAMS		:	'Teams';
MEMBERS		:	'Members';


//LOCATION keywords
LOCS		: 	'Locations';
OWNER		:	'Owner';
EXISTVISIBLE:	'ExistVisible';
PUBLIC		:	'Public';
NONE		:	'None';
VALUEVISIBLE:	'ValueVisible';
LOCATIONTYPE:	'LocationType';
PLACE		: 	'Place';
SUPPLY		:	'Supply';
COUNTER		:	'Counter';
COUNTERTYPE	: 	'CounterType';
STARTINGINV	:	'StartingInventory';

//OBJECTS KEYWORDS
OBJECTS		:	'Objects';

//ROUNDS KEYWORDS
ROUNDS		:	'Rounds';
MAIN		:	'Main';

//ACTIONS KEYWORDS
ACTIONS		:	'Actions';

//STARTING STATE KEYWORDS
STARTSTATE	:	'StartState';

//CONCEPTS KEYWORDS
CONCEPTS	:	'Concepts';
VARIABLES	:	'Variables';

//HELPERFUNCTIONSKEYWORDS
//VARIABLES
TRUE		:	'True';
FALSE		:	'False';

//COPY
COPY		:	'Copy';

NUM			: 	DIGIT (DIGIT)*;
ID			: 	UPPERCASE (LETTER | DIGIT)*;
WORD		: 	(DIGIT|LOWERCASE) (LETTER|DIGIT)*;

STRINGLITERAL	
			: 	UNTERMINATEDSTRINGLITERAL '"'
			;

UNTERMINATEDSTRINGLITERAL	
			: 	'"' (~["\\\r\n] | '\\' (. | EOF))*
			;



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
DOT:	'.';
EXCL: 	'!';
AT:		'@';
PLUS:	'+';
MINUS:	'-';
IS:		'=';
BACKSL:	'\\';
SLASH:	'/';
STAR:	'*';


//Skipped token types
COMMENT: SLASH SLASH .*? '\n' -> skip;
COMMENTBLOCK: SLASH STAR .*? STAR SLASH -> skip;

WS: [ \t\r\n]+ -> skip;
//ENTER: '\r'?'\n';
SPACE: ' ';


fragment LOWERCASE: [a-z];
fragment UPPERCASE: [A-Z];
fragment LETTER: [a-zA-Z];
fragment DIGIT: [0-9];
//fragment NONZERO: [1-9];

