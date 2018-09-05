grammar BGD;

//@header{package bgd.grammar; }

import BGDVocab;

program: init players locations locationconnections? objects rounds actions startState concepts?;

//INIT - Game settings
init		: gamename
			;

gamename	:	GAME IS text SEMI
			;
			
name:		nameWord nameWord*;
nameWord	:	NUM
			|	ID
			|	WORD
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
			
npcs		:	NPCS LBRACE npc+ RBRACE
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

			
teams		:	TEAMS LBRACE team+ RBRACE
			;
			
team		:	ID LBRACE members RBRACE			#teamOnlyMembers
			|	ID LBRACE members varList RBRACE	#teamWithVarList
			;
			
members		:	MEMBERS IS ID (COMMA ID)* SEMI;

	
playernames	:	LPAR ID (COMMA ID)* RPAR
			;


//LOCATIONS - Places in the game
locations	:	LOCS LBRACE location+ RBRACE;

location	:	ID LBRACE locationCharacteristics RBRACE		#locationNormal
			|	copy locationCharacteristics copyEnd			#locationCopy
			;
			
locationCharacteristics 
			:	owner? visible1? visible2? locationType varList? startingInv?;

owner		:	OWNER IS ID SEMI;

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
			: 	LOCATIONCONNECTIONS LBRACE locconnection+ RBRACE
			;
			
locconnection:	DIRECTED LBRACE locList conAreLoc? varList? RBRACE		#locconnectionDirected
			|	UNDIRECTED LBRACE locList conAreLoc? varList? RBRACE	#locconnectionUndirected
			;

locList		:	LBLOCK ID COMMA ID (COMMA ID)* RBLOCK;
conAreLoc	:	CONARELOCS	IS bool SEMI;
			

//OBJECTS - Game cards, pieces, or other "things" in the game
objects		: 	OBJECTS;

//ROUNDS - How the game is played, what actions are in what order?
rounds		: 	ROUNDS MAIN;

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
			;
bool		:	TRUE		#boolTrue
			|	FALSE		#boolFalse
			;

number		:	NUM			#numberInt
			|	NUM DOT NUM	#numberDouble
			;

text		:	STRINGLITERAL
			;
			
//COPY -- Duplicate a Location/Player/Object/etc with only a different name
copy		:	COPY LBRACE nameList;
copyEnd		:	RBRACE;
nameList	:	LBLOCK ID (COMMA ID)* RBLOCK;

