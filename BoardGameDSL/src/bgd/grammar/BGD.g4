grammar BGD;

//@header{package bgd.grammar; }

import BGDVocab;

program: init players locations locationconnections? objects rounds actions startState concepts?;

//INIT - Game settings
init		: gamename
			;

gamename	:	GAME IS text SEMI
			;

//PLAYERS - What players, forces and teams exist in the game
players		: 	PLAYERS LBRACE playernum humans npcs? teams? RBRACE;

playernum	:	minplayers maxplayers		#playernumRange
			|	PLAYERS IS NUM SEMI			#playernumFixed
			;
			
minplayers	:	MINPLAYERS IS NUM SEMI;
maxplayers	:	MAXPLAYERS IS NUM SEMI;
		
humans		:	HUMANS LBRACE human* RBRACE
			;
			
human		:	ID SEMI						#humanOnlyName
			|	ID LBRACE varList RBRACE	#humanWithVarList
			|	copy copyEnd				#humanOnlyNameCopy
			|	copy varList copyEnd		#humanCopyVarList
			;
			
npcs		:	NPCS LBRACE npc* RBRACE
			;
			
npc			:	ID SEMI						#npcOnlyName
			|	ID LBRACE varList RBRACE	#npcWithVarList
			|	copy copyEnd				#npcCopyOnlyName
			|	copy varList copyEnd		#npcCopyVarList
			|	specialNPC					#npcSpecial
			;

specialNPC	:	specialNPCName SEMI						#specialNPCOnlyName
			|	specialNPCName	LBRACE varList RBRACE	#specialNPCVarList
			;
			
specialNPCName:	BANK;

			
teams		:	TEAMS LBRACE team* RBRACE
			;
			
team		:	ID LBRACE members RBRACE			#teamOnlyMembers
			|	ID LBRACE members varList RBRACE	#teamWithVarList
			;
			
members		:	MEMBERS IS ID (COMMA ID)* SEMI;

	
playernames	:	LPAR ID (COMMA ID)* RPAR
			;


//LOCATIONS - Places in the game
locations	:	LOCS LBRACE location* RBRACE;

location	:	ID LBRACE locationParameters RBRACE		#locationNormal
			|	copy locationParameters copyEnd			#locationCopy
			;
			
locationParameters 
			:	locOwner? visible1? visible2? locationType varList? startingInv?;

locOwner	:	OWNER IS ID SEMI						#locOwnerID
			|	OWNER IS PUBLIC SEMI					#locOwnerPublic
			;

visible1	: 	EXISTVISIBLE IS ID SEMI					#visible1SingleName
			|	EXISTVISIBLE IS PUBLIC SEMI				#visible1Public
			|	EXISTVISIBLE IS NONE SEMI				#visible1None
			|	EXISTVISIBLE IS nameList SEMI			#visible1NameList
			;
visible2	: 	VALUEVISIBLE IS ID SEMI					#visible2SingleName
			|	VALUEVISIBLE IS PUBLIC SEMI				#visible2Public
			|	VALUEVISIBLE IS NONE SEMI				#visible2None
			|	VALUEVISIBLE IS nameList SEMI			#visible2NameList
			;
			
locationType:	LOCATIONTYPE IS PLACE SEMI				#locationTypePlace
			|	LOCATIONTYPE IS SUPPLY SEMI				#locationTypeSupply
			|	LOCATIONTYPE IS COUNTER SEMI counterType#locationTypeCounter
			;
			
counterType	:	COUNTERTYPE IS ID SEMI;

//TODO!!!!!
startingInv	:	STARTINGINV IS SEMI;

//LOCATIONCONNECTIONS - what locations are connected to each other
locationconnections
			: 	LOCATIONCONNECTIONS LBRACE locconnection* RBRACE
			;
			
locconnection:	DIRECTED LBRACE locList conAreLoc? varList? RBRACE		#locconnectionDirected
			|	UNDIRECTED LBRACE locList conAreLoc? varList? RBRACE	#locconnectionUndirected
			;

locList		:	LBLOCK ID COMMA ID (COMMA ID)* RBLOCK;
conAreLoc	:	CONARELOCS IS bool SEMI conNames?;
conNames	:	CONNECTIONNAMES IS nameList SEMI;
			

//OBJECTS - Game cards, pieces, or other "things" in the game
objects		: 	OBJECTS LBRACE object* RBRACE;

object		:	ID LBRACE objectParameters RBRACE
			|	copy objectParameters copyEnd
			;
			
objectParameters
			:	objectOwner? objectType? objectValue? objectSides? objectSidesShown? objectRandomizer? varList?
			;
			
objectOwner	:	OWNER IS ID SEMI						#objectOwnerID
			|	OWNER IS PUBLIC SEMI					#objectOwnerPublic
			| 	OWNER IS LOCATION SEMI					#objectOwnerLocation
			;
			
objectType	:	TYPE IS ID SEMI							#objectTypeID
			|	TYPE IS NONE SEMI						#objectTypeNone
			;
			
objectValue	:	VALUE IS value SEMI valueType			#objectValueNotNone
			|	VALUE IS value SEMI						#objectValueNoValueType
			|	VALUE IS NONE SEMI						#objectValueNone
			;
valueType	:	VALUETYPE IS ID SEMI;

//TODO!!!!
objectSides	:	SIDES SEMI 
			;
//TODO!!!!
objectSidesShown:	SIDESSHOWN SEMI;

//For adding randomness to objects, i.e. dice
objectRandomizer
			:	RANDOMIZER IS NONE SEMI												#objectRandomizerNone
			|	RANDOMIZER LBRACE randomizerValueList randomizerChancesList RBRACE	#objectRandomizerHasRandomizer		
			;
			
randomizerValueList
			:	LBLOCK value (COMMA value)* RBLOCK
			;

randomizerChancesList
			:	LBLOCK number (COMMA number)* RBLOCK
			|	FAIR
			;

//rounds - How the game is played, what actions are in what order?
rounds		: 	ROUNDS LPAR main additionalRound* RPAR;

main		:	MAIN LPAR RPAR LBRACE codeBlock RBRACE ;
additionalRound:ID LPAR arguments? RPAR LBRACE codeBlock RBRACE;

arguments	:	argument (COMMA argument)*;
argument	:	varType LOWID;

//CODEBLOCK - Helper for rounds and actions
codeBlock	:	codeLine*
			;

codeLine	:	nonReturnFunction SEMI
//			|	LOOP LPAR 
			|	IF LPAR codeValue RPAR LBRACE codeBlock RBRACE ELSE LBRACE codeBlock RBRACE
			|	RETURN codeValue SEMI
			;

nonReturnFunction
			:	TAKEACTION LPAR integerValue COMMA integerValue RPAR 
//			|	MOVE2
//			|	MOVE
//			|	ADVANCETURN
//			|	LOOP LPAR 
			|	RANDOMIZE LPAR idFromLocation RPAR 
//			|	FINISHGAME			
			;
			
standardFunction
			:	COMMA COMMA
//			|	SEE
//			|	NEWOBJECT
//			|	LIST[i]
			|	LISTCOUNT LPAR RPAR
			;
			
locationFunction
			:	CONTAINS LPAR RPAR 					#locFunctionContains
			|	CONTAINS LPAR ID RPAR 				#locFunctionContainsType
			|	ISCONNECTEDTO LPAR ID RPAR 			#locFunctionIsConnectedTo
			|	CONNECTIONS LPAR RPAR				#locFunctionConnections
			;
			
playerFunction
			:	ID
			;
			
objectFunction
			:	LOCATION 
			|	OWNER  
			|	VALUE
			;
			
codeValue	:	codeValueValue								#codeValuePlainID
			|	codeValue DOT idFromLocation				#codeValueIDFromLocation
			|	codeValue DOT standardFunction				#codeValueStandardFunction
			|	LPAR codeValue RPAR							#codeValuePar
			|	codeValue DOT locationFunction				#codeValueLocationFunction
//			|	codeValue DOT playerFunction				#codeValuePlayerFunction
			|	codeValue DOT objectFunction				#codeValueObjectFunction
			|	codeValue boolOp codeValue					#codeValueBoolOperator //AND/OR
			|	codeValue LBLOCK integerValue RBLOCK		#codeValueListIndex
			|	EXCL codeValue								#codeValueBoolNot
			|	codeValue compareAdd codeValue				#codeValueBoolCompare // ==, !=, >, etc.
			|	LBLOCK codeValue (COMMA codeValue)* RBLOCK	#codeValueList
			|	codeValue multOp codeValue					#codeValueMultOp
			|	codeValue addOp codeValue					#codeValueAddOp
			;
			
		
//stringExpr	:	STRINGLITERAL					#stringExprString
//			|	ID								#stringExprID
////			|	codeValue						#stringExprCodeValue
//			;
			

integerValue:	integer
			|	codeValue
			;
			
codeValueValue
			:	bool
			|	number
			|	text
			|	ID
			|	LOWID
			;
			
idFromLocation 
			:	ID (DOT ID)*;
			
			
//ACTIONS - What actions can be taken by players or other forces?
actions		:	ACTIONS; 

//STARTING STATE - What non-deterministic things are done at game-start?
startState	:	STARTSTATE;

//CONCEPTS
concepts	:	CONCEPTS VARIABLES;

//HELPER FUNCTIONS
//VARIABLES
varList		:	variable+;
variable	:	ID IS value SEMI;

value		:	bool		#valueBool
			|	number		#valueNumber
			|	text		#valueText
			|	ID			#valueID
			;
bool		:	TRUE		#boolTrue
			|	FALSE		#boolFalse
			;

number		:	integer		#numberInt
			|	doublenum	#numberDouble
			|	fraction	#numberFraction
			;
nofrac		:	integer		#nofracInt
			|	doublenum	#nofracDouble	
			;		
			
integer		:	NUM;
doublenum	:	NUM DOT NUM;
fraction	:	nofrac SLASH nofrac;

text		:	STRINGLITERAL
			;
			
varType		:	INT
			|	BOOL
			|	DOUBLE
			|	STRINGWORD
			|	OBJECTTYPE
			|	ID
			|	LOCATION
///			|	FORCE
//			|	TEAM
			;
			
//COPY -- Duplicate a Location/Player/Object/etc with only a different name
copy		:	COPY LBRACE nameList;
copyEnd		:	RBRACE;
nameList	:	LBLOCK ID (COMMA ID)* RBLOCK;


			
addOp		:	PLUS 
			|	MINUS
			;
			
multOp		:	STAR	//Multiply
			|	SLASH	//Divide
			|	PERCENT	//Modulo
			;			

			
boolOp		:	AND								#boolOpAnd
			|	OR								#boolOpOr
//			|	XOR								#boolOpXor
			;

compareBool	:	EQ								
			|	NE
			;
			
compareAdd	:	EQ
			|	NE
			|	GT
			|	LT
			|	GE	
			|	LE
			;
			
compareString:	EQ
			|	NE
			;
			

			


