grammar BGD;

//@header{package bgd.grammar; }


import BGDVocab;

program: init playersSection locations locationconnections? objects rounds actions startState?;


//INIT - Game settings
init		: gamename
			;

gamename	:	GAME IS text SEMI
			;

//PLAYERS - What players, forces and teams exist in the game
playersSection:	PLAYERS LBRACE playernum players RBRACE;

playernum	:	minplayers maxplayers				#playernumRange
//			|	PLAYERS IS NUM SEMI					#playernumFixed
			;
			
minplayers	:	MINPLAYERS IS NUM SEMI;
maxplayers	:	MAXPLAYERS IS NUM SEMI;

players		: 	player+
			;
		
player		:	humOrAI ID SEMI						#playerOnlyID
			|	humOrAI ID LBRACE varList RBRACE	#playerVarList
			;
			
humOrAI		:	HUMAN								#humOrAIHuman	
			|	COMPUTER							#humOrAIComputer
			;



//LOCATIONS - Places in the game
locations	:	LOCS LBRACE location* RBRACE;

location	:	locType? ID LBRACE locationVariables startingInv? supplies? RBRACE		#locationNormal
//			|	copy locationParameters copyEnd			#locationCopy
			;
			
locType		:	PLACE
			|	SUPPLY // TODO more?
			;
			
locationVariables 
			:	locVariable*;
			
locVariable	:	variable
			|	locOwner
			|	visible1
			|	visible2
//			|	startingInv
//			| 	supplies
			;

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
			
supplies	:	SUPPLIES LBRACE objectInstance RBRACE;

			
			
startingInv	:	STARTINGINV LBRACE objectInstance+ RBRACE;

	//creates new varobjects
	// ObjectType IDName, Variables
objectInstance:	ID ID SEMI
			|	ID ID LBRACE objectVariableHelp+ RBRACE
			;
			

//LOCATIONCONNECTIONS - what locations are connected to each other
locationconnections
			: 	LOCATIONCONNECTIONS LBRACE locConnection* RBRACE
			;
			
//locConnection:	DIRECTED LBRACE locList conAreLoc? varList? RBRACE		#locconnectionDirected
//			|	UNDIRECTED LBRACE locList conAreLoc? varList? RBRACE	#locconnectionUndirected
//			;

locConnection:	DIRECTED LBRACE locList  RBRACE		#locconnectionDirected
			|	UNDIRECTED LBRACE locList  RBRACE	#locconnectionUndirected
			;

// From to (perhaps multiple) locations
locList		:	LBLOCK ID COMMA ID (COMMA ID)* RBLOCK;
//conAreLoc	:	CONARELOCS IS bool SEMI conNames?;
//conNames	:	CONNECTIONNAMES IS nameList SEMI;
			

//OBJECTS - Game cards, pieces, or other "things" in the game
objects		: 	OBJECTS LBRACE object* RBRACE;

object		:	ID LBRACE objectVariableHelp* RBRACE
//			|	copy objectParameters copyEnd
			;
			
objectVariableHelp
			:	publicness objectVariable
			;
			
publicness	:	PUBLIC
			|	HIDDEN
			;	
					
objectVariable: variable								#objectVariableVariable
			|	objectOwner								#objectVariableOwner
			|	objectRandomizer 						#objectVariableRandomizer
			;
			
objectOwner	:	OWNER IS ID SEMI						#objectOwnerID
			|	OWNER IS PUBLIC SEMI					#objectOwnerPublic
			| 	OWNER IS LOCATION SEMI					#objectOwnerLocation
			;


//For adding randomness to objects, i.e. dice
objectRandomizer
			:	RANDOMIZER LBRACE randomizerValueList RBRACE			
			;
			
randomizerValueList
			:	LBLOCK value (COMMA value)* RBLOCK
			;



//rounds - How the game is played, what actions are in what order?
rounds		: 	ROUNDS LBRACE additionalRound* main RBRACE;

main		:	MAIN LPAR RPAR LBRACE codeBlock RBRACE ;
additionalRound:ID LPAR arguments? RPAR LBRACE codeBlock RBRACE;

arguments	:	argument (COMMA argument)*;
argument	:	varType LOWID;
 
//CODEBLOCK - Helper for rounds and actions
codeBlock	:	codeLine*
			;			

codeLine	:	nonReturnFunction SEMI #codeLineNonReturn
//			|	LOOP LPAR 
			|	IF LPAR codeValue RPAR LBRACE codeBlock RBRACE #codeLineIf
			|	IF LPAR codeValue RPAR LBRACE codeBlock RBRACE ELSE LBRACE codeBlock RBRACE?#codeLineifElse
			|	RETURN codeValue SEMI #codeLineReturn
			;

nonReturnFunction
			:	CHOOSEACTION codeValue		#nonReturnFunctionTakeActionPlayer 
			|	ID LPAR performActionArguments? RPAR	#nonReturnFunctionActionRoundNoReturn
//			|	MOVE2
			|	MOVE codeValue codeValue	#nonReturnFunctionMoveObjectTo
			|	ADVANCETURN					#nonReturnFunctionNextTurn
			|	TURNORDER IS codeValue		#nonReturnFunctionSetTurnOrder
			|	RANDOMIZE codeValue 		#nonReturnFunctionRandomize
			|	REPEAT						#nonReturnFunctionRepeat	
//			|	variable					#nonReturnFunctionLocalVariable
			|	WINNER codeValue			#nonReturnFunctionWin
			|	LOSER codeValue				#nonReturnFunctionLoser
			|	FINISH						#nonReturnFunctionFinishGame
			|	codeValue IS codeValue		#nonReturnFunctionSetVariable
			;
			
performActionArguments: codeValue (COMMA codeValue)?;

standardFunction
			:	LISTCOUNT LPAR codeValue RPAR #standardFunctionListCount
//			|	SEE
//			|	setVariable
//			|	NEWOBJECT
//			|	LIST[i]  -> CodeValue
			;
			
locationFunction
			:	CONTAINS LPAR RPAR 					#locFunctionContains
//			|	CONTAINS LPAR ID RPAR 				#locFunctionContainsType
			|	ISCONNECTEDTO LPAR codeValue RPAR	#locFunctionIsConnectedTo
			|	CONNECTIONS LPAR RPAR				#locFunctionConnections
			|	VALUEVISIBLE						#locFunctionValueVisible
			|	EXISTVISIBLE						#locFunctionExistVisible
			;
			
//playerFunction
//			:	ID
//			;
			
objectFunction
			:	LOCATION	#objectFunctionLocation
			|	OWNER  		#objectFunctionOwner
			|	VALUE		#objectFunctionValue
			;
			
globalVariable
			:	TURNORDER	#globalVariableTurnOrder
			;
			
codeValue	:	codeValueValue								#codeValuePlainValue
			|	codeValue DOT getVariable					#codeValueIDFromLocation
			|	standardFunction							#codeValueStandardFunction
			|	LPAR codeValue RPAR							#codeValuePar
			|	ID LPAR performActionArguments? RPAR		#codeValueActionOrRound
			|	codeValue DOT locationFunction				#codeValueLocationFunction
//			|	codeValue DOT playerFunction				#codeValuePlayerFunction
			|	codeValue DOT objectFunction				#codeValueObjectFunction
			|	codeValue boolOp codeValue					#codeValueBoolOperator //AND/OR
			|	codeValue LBLOCK codeValue RBLOCK			#codeValueListIndex
			|	EXCL codeValue								#codeValueBoolNot
			|	codeValue compareAdd codeValue				#codeValueBoolCompare // ==, !=, >, etc.
			|	LBLOCK codeValue (COMMA codeValue)* RBLOCK	#codeValueList
			|	codeValue multOp codeValue					#codeValueMultOp
			|	codeValue addOp codeValue					#codeValueAddOp
			|	globalVariable								#codeValueGlobalVariable
			;
			
		
//stringExpr	:	STRINGLITERAL					#stringExprString
//			|	ID								#stringExprID
////			|	codeValue						#stringExprCodeValue
//			;
			

//integerValue:	integer
//			|	codeValue
//			;
			
codeValueValue
			:	bool
			|	number
//			|	text
			|	ID
			|	LOWID
			;
			
getVariable 
			:	ID ;
			
			
//ACTIONS - What actions can be taken by players or other forces?
actions		:	ACTIONS LBRACE action* RBRACE; 

action		:	ID LPAR arguments? RPAR LBRACE requires effect RBRACE
			;
			
requires	:	REQUIRES LBRACE requireStatement* RBRACE
			;
			
requireStatement:
				codeValue SEMI
			;
			
effect		:	EFFECT LBRACE effectStatement* RBRACE
			;
			
effectStatement:
				codeLine;
				
				

//STARTING STATE - What non-deterministic things are done at game-start?
//TODO
startState	:	STARTSTATE LBRACE codeBlock RBRACE;  



//HELPER FUNCTIONS
//VARIABLES
varList		:	variable+;
variable	:	ID IS value SEMI;



value		:	bool		#valueBool
			|	number		#valueNumber
//			|	text		#valueText
			|	ID			#valueID
			|	list		#valueList
			;
			
list		:	LBLOCK nonListValue (COMMA nonListValue)* RBLOCK	#listFilled
			|	LBLOCK RBLOCK										#listEmpty
			;
			
nonListValue:	bool		#nonListValueBool
			|	number		#nonListValueNumber
//			|	text		#nonListValueText
			|	ID			#nonListValueID
			;			
			
bool		:	TRUE		
			|	FALSE		
			;

number		:	integer		#numberInt
			|	doublenum	#numberDouble
//			|	fraction	numberFraction
			;
//nofrac		:	integer		#nofracInt
//			|	doublenum	#nofracDouble	
//			;		
			
			
integer		:	NUM;
doublenum	:	NUM DOT NUM;
//fraction	:	nofrac SLASH nofrac;

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
//copy		:	COPY LBRACE nameList;
//copyEnd		:	RBRACE;

nameList	:	LBLOCK ID (COMMA ID)* RBLOCK;


			
addOp		:	PLUS 
			|	MINUS
			;
			
multOp		:	STAR	//Multiply
			|	SLASH	//Divide
//			|	PERCENT	//Modulo
			;			

			
boolOp		:	AND								#boolOpAnd
			|	OR								#boolOpOr
//			|	XOR								#boolOpXor
			;

//compareBool	:	EQ								
//			|	NE
//			;
			
compareAdd	:	EQ
			|	NE
			|	GT
			|	LT
			|	GE	
			|	LE
			;
			
//compareString:	EQ
//			|	NE
//			;
			

			


