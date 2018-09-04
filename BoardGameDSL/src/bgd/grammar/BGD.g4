grammar BGD;

//@header{package bgd.grammar; }

import BGDVocab;

program: init players locations objects rounds actions startState concepts?;

//INIT - Game settings
init		: gamename
			;

gamename	:	GAME COLON name SEMI
			;
			
name:		nameWord nameWord*;
nameWord	:	NUM
			|	ID
			|	WORD
			;

//PLAYERS - What players, forces and teams exist in the game
players		: 	PLAYERS COLON playernum playernames? SEMI;

playernum	:	NUM 				#playernumSingle
			|	NUM MINUS NUM		#playernumMultiple
			;
		
nonzero		:	NUM;
	
playernames	:	LPAR ID (COMMA ID)* RPAR
			;


//LOCATIONS - Places in the game
locations:	LOCS;

//OBJECTS - Game cards, pieces, or other "things" in the game
objects: 	OBJECTS;

//ROUNDS - How the game is played, what actions are in what order?
rounds: 	ROUNDS MAIN;

//ACTIONS - What actions can be taken by players or other forces?
actions:	ACTIONS; 

//STARTING STATE - What non-deterministic things are done at game-start?
startState:	STARTSTATE;

//CONCEPTS
concepts:	CONCEPTS VARIABLES;


