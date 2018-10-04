lexer grammar BGDVocab;

//@header{package bgd.grammar; }

//INIT keywords
GAME		:	'Game';


//PLAYERS KEYWORDS
PLAYERS		:	'Players';
MINPLAYERS	:	'MinPlayers';
MAXPLAYERS	:	'MaxPlayers';
HUMAN		:	'Human';
COMPUTER	:	'Computer';
//HUMANS		:	'Humans';
//NPCS		:	'NPCs';
//BANK		:	'Bank';
//TEAMS		:	'Teams';
//MEMBERS		:	'Members';


//LOCATION keywords
LOCS		: 	'Locations';
EXISTVISIBLE:	'ExistVisible';
VALUEVISIBLE:	'ValueVisible';
LOCATIONTYPE:	'LocationType';
PLACE		: 	'Place';
SUPPLY		:	'Supply';
COUNTER		:	'Counter';
COUNTERTYPE	: 	'CounterType';
STARTINGINV	:	'StartingInventory';

//LOCATIONCONNECTIONS keywords
LOCATIONCONNECTIONS : 	'LocationConnections';
DIRECTED	:	'Directed';
UNDIRECTED	: 	'Undirected';
CONARELOCS	:	'ConnectionsAreLocations';
CONNECTIONNAMES:	'ConnectionNames';

//OBJECTS KEYWORDS
OBJECTS		:	'Objects';
LOCATION	:	'Location';
TYPE		:	'Type';
VALUE		:	'Value';
VALUETYPE	:	'ValueType';
SIDES		:	'Sides';
SIDESSHOWN	:	'SidesShown';
RANDOMIZER	:	'Randomizer';

//ROUNDS KEYWORDS
ROUNDS		:	'Rounds';
MAIN		:	'Main';
//variable classifier
INT			:	'int';
BOOL		:	'bool';
DOUBLE		:	'double';
STRINGWORD	:	'string';
OBJECTTYPE	:	'objectType';
PUBLIC		:	'public';
PRIVATE		:	'private';

//CodeBlock
IF			:	'if';
ELSE		:	'else';
RETURN		:	'return';
//standardFunction
LISTCOUNT	:	'count';
//nonReturnFunction
TAKEACTION	:	'TakeAction';
RANDOMIZE	:	'Randomize';

//LocationFunction
CONTAINS	:	'contains';
ISCONNECTEDTO:	'isConnectedTo';
CONNECTIONS	:	'connections';


//ObjectFunction
//LOCATIONFUNC:	LOCATION;
//OWNERFUNC	:	OWNER;
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

//Owner Keywords
NONE		:	'None';
OWNER		:	'Owner';
HIDDEN		:	'Hidden';





NUM			: 	DIGIT (DIGIT)*;
ID			: 	UPPERCASE (LETTER | DIGIT)*;
LOWID		:	LOWERCASE (LETTER | DIGIT)*;
DIGITID		: 	DIGIT (LETTER|DIGIT)*;

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
IS:		'=';
BACKSL:	'\\';
SLASH:	'/';
STAR:	'*';
PERCENT:'%';



//Numerical operators
PLUS: 	'+';
MINUS: 	'-';
//TIMES: 	STAR;
//DIVIDE: SLASH;
//MODULO: PERCENT;

//Boolean operators
AND: 	'&&';
OR: 	'||';
//XOR:	'xor';
//NOT:	EXCL; 

//Compare
GT: 	'>';
LT:		'<';
GE:		'>=';
LE: 	'<=';
EQ: 	'==';
NE: 	'!=';



//Skipped token types
COMMENT: SLASH SLASH .*? '\n' -> skip;
COMMENTBLOCK: SLASH STAR .*? STAR SLASH -> skip;

WS: [ \t\r\n]+ -> skip;
//ENTER: '\r'?'\n';
//SPACE: ' ';


fragment LOWERCASE: [a-z];
fragment UPPERCASE: [A-Z];
fragment LETTER: [a-zA-Z];
fragment DIGIT: [0-9];
//fragment NONZERO: [1-9];

