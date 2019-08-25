Game description

Seabattle is a turn-based board game for 2 players.

Each player has 2 fleets of different ship types. 
Also a player has 2 boards. One board is for placement of his fleet, the other one - 
for marking shots coordinates and damaged or sunken enemy ships.

Before the game, each player places his ships on the board.
The first player makes his turn. He tries to hit an enemy ship. If a ship is hit, the player continues to shoot,
otherwise the other player makes his turn. The game continues until one of the fleets is sunk. 
The player who sunk all the enemy's fleet is the winner.

Details
----

Fleet
--
Is a collection of ships.
A fleet has information about ship types and the number of ships of each type.
Methods: isBuit(), isSunken()

Ship
--
Consists of decks.The number of decks depends on ship type. 
A ship is sunken when all it's decks are hit.

Ship type
--
Dreadnought-4 decks, amount-1
Battlecruiser-3 decks, amount-2
Destroyer-2 decks, amount-3
Submarine-1 deck, amount-4

Deck
--
Each deck has a board coordinate (x,y) and flag isHit().

Board
--
Has size 10x10. Can be players board and enemies board.

Players board
--
Shows players ships and enemy's shots:
- Each deck of ship is marked as 'S'
- Each hit deck is marked as '*'
- Sunken ships marked as 'X'
- Enemy shots marked as '.' when missed

Game
--
- Place fleets;
- Process shot. Returns shot result: hit, missed, win.

 


 