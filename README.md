# Cluedo-Game
## This is the text based version of the Cluedo Game.
**For the GUI version, refer to my other repository **

The game consists of between three and six players who move around a board consisting of different
rooms. The aim is to deduce who the murderer was, what weapon they used and what room it
happened in.

The Cluedo board consists of nine rooms laid out in a circular fashion (see Figure 1). The board
is divided into a grid of (approx) 25x25 squares. The corner rooms each contain a stairwell which
connects to the room in the opposite corner of the board.

Players take it in turns to roll the dice and move their character token a corresponding number of
squares. When a player enters a room he/she can announce a suggestion which consists of that room,
and a character and weapon (if necessary, the character and weapon pieces are moved into the room).

When an suggestion is made, each player responds in a clock-wise fashion until either: the
suggestion is refuted; or, all players have indicated they cannot refute the suggestion. A suggestion
is refuted by a player if that player contains a matching card.

Play continues until a player believes he/she has determined the solution. At that point, he/she
makes an accusation consisting of a weapon, character and room (note: unlike suggestions, the player
does not need to be in a room to make an accusation involving it). The accusation is then checked
by the player against the solution. If it is correct the player wins, otherwise the player is eliminated
from the game.

The User Interface of the game follows the following steps:

1. Program begins by asking how many players are required.
2. At start of each player turn, a dice is rolled and the player can move their piece using w/a/s/d commands.
3. Once a player has moved their piece, they can make a suggestion/accusation or see their cards.
4. Steps 2-4 are repeated for every player until all players have lost or a winner has been determined.

###Image below shows a screenshot of the game in action where player 1 starts their turn.
![](http://gdurl.com/4tUP)
###Image below shows a screenshot after player has completed their move and can now make a decision on to suggest/accuse/list hand or end their turn.
![](http://gdurl.com/rrsD)
