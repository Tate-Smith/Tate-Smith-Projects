import sys

class GridPos:
    '''
        This class creates a gridpos with the values of x,y as the coordinates, and None if a ship doesnt occupy the space and has the ships info if it does have a ship
    '''
    def __init__(self, x, y):
        self._x = x
        self._y = y
        self._ship = None
        self._guess = False
    
    #this sets the ship value to the object ship
    def set_ship(self, ship):
        self._ship = ship

    #if the spot has been guessed before then it changes guess to true
    def set_guess(self):
        self._guess = True
    
    #returns the ship object at the position if there is one, and if there isn't one then it returns none
    def get_ship(self):
        return self._ship
    
    #returns the value of guess
    def get_guess(self):
        return self._guess

    def __str__(self):
        return 'Position: (' + str(self._x) + ', ' + str(self._y) + '), Ship: ' + self._ship

class Board:
    '''
        This class creates a board object which is a 10x10 grid populated with gridpos
    '''
    def __init__(self):
        self._board = []
        for i in range(9, -1 , -1):
            list = []
            for ii in range(10):
                list.append(GridPos(ii, i))
            self._board.append(list)
        self._ships = []

    #This method adds a ship to the grid pos it falls on
    def add_ship(self, ship):
        self._ships.append(ship)
        list = ship.get_positions()
        for i in range(0, len(list), 2):
            if self._board[(list[i + 1] * -1 + 9)][list[i]].get_ship() == None:
                self._board[(list[i + 1] * -1 + 9)][list[i]].set_ship(ship)
            else:
                print('ERROR: overlapping ship: ' + ship.get_name() + ' ' + str(list[0]) + ' ' + str(list[1]) + ' ' + str(list[-2]) + ' ' + str(list[-1]))
                sys.exit(0)

    #guesses
    def guess(self, positions):
        position = self._board[(positions[1] * -1 + 9)][positions[0]]
        if position.get_ship() != None:
            if position.get_guess():
                print('hit (again)')
            else:
                position.set_guess()
                ship = position.get_ship()
                ship.hit(positions)
                if position.get_ship().is_sunk():
                    print('{} sunk'.format(position.get_ship()))
                    self._ships.remove(position.get_ship())
                    if len(self._ships) == 0:
                        print('all ships sunk: game over')
                else:
                    print('hit')
        else:
            if position.get_guess():
                print('miss (again)')
            else:
                position.set_guess()
                print('miss')
    
    #returns what ships are on the board
    def get_ships(self):
        return len(self._ships)
    
    def __str__(self):
        for i in range(len(self._board) - 1, - 1, - 1):
            for ii in self._board:
                print(self._board[i][ii], end = ' ')
            print()


class Ship:
    def __init__(self, ship, positions):
        self._ship = ship
        if self._ship == 'A':
            self._size = 5
            if positions[0] == positions[2]:
                if abs(positions[1] - positions[3]) != 4:
                    print('ERROR: incorrect ship size: ' + self._ship + ' ' + ' ' + str(positions[0]) + ' ' + str(positions[1]) + ' ' + str(positions[2]) + ' ' + str(positions[3]))
                    sys.exit(0)
                self._positions  = [positions[0], positions[1]]
                if positions[1] < positions[3]:
                    for i in range(positions[1] + 1, positions[3]):
                        self._positions.append(positions[0])
                        self._positions.append(i)
                else:
                    for i in range(positions[1] - 1, positions[3], - 1):
                        self._positions.append(positions[0])
                        self._positions.append(i)
                self._positions.append(positions[2])
                self._positions.append(positions[3])
            elif positions[1] == positions[3]:
                if abs(positions[0] - positions[2]) != 4:
                    print('ERROR: incorrect ship size: ' + self._ship + ' ' + str(positions[0]) + ' ' + str(positions[1]) + ' ' + str(positions[2]) + ' ' + str(positions[3]))
                    sys.exit(0)
                self._positions = [positions[0], positions[1]]
                if positions[0] < positions[2]:
                    for i in range(positions[0] + 1, positions[2]):
                        self._positions.append(i)
                        self._positions.append(positions[1])
                else:
                    for i in range(positions[0] - 1, positions[2], - 1):
                        self._positions.append(i)
                        self._positions.append(positions[1])
                self._positions.append(positions[2])
                self._positions.append(positions[3])
                list = self._positions
            else:
                print('ERROR: ship not horizontal or vertical: ' + self._ship + ' ' + str(positions[0]) + ' ' + str(positions[1]) + ' ' + str(positions[2]) + ' ' + str(positions[3]))
                sys.exit(0)
            self._not_hit = self._positions
        elif self._ship == 'B':
            self._size = 4
            if positions[0] == positions[2]:
                if abs(positions[1] - positions[3]) != 3:
                    print('ERROR: incorrect ship size: ' + self._ship + ' ' + str(positions[0]) + ' ' + str(positions[1]) + ' ' + str(positions[2]) + ' ' + str(positions[3]))
                    sys.exit(0)
                self._positions  = [positions[0], positions[1]]
                if positions[1] < positions[3]:
                    for i in range(positions[1] + 1, positions[3]):
                        self._positions.append(positions[0])
                        self._positions.append(i)
                else:
                    for i in range(positions[1] - 1, positions[3], - 1):
                        self._positions.append(positions[0])
                        self._positions.append(i)
                self._positions.append(positions[2])
                self._positions.append(positions[3])
            elif positions[1] == positions[3]:
                if abs(positions[0] - positions[2]) != 3:
                    print('ERROR: incorrect ship size: ' + self._ship + ' ' + str(positions[0]) + ' ' + str(positions[1]) + ' ' + str(positions[2]) + ' ' + str(positions[3]))
                    sys.exit(0)
                self._positions = [positions[0], positions[1]]
                if positions[0] < positions[2]:
                    for i in range(positions[0] + 1, positions[2]):
                        self._positions.append(i)
                        self._positions.append(positions[1])
                else:
                    for i in range(positions[0] - 1, positions[2], - 1):
                        self._positions.append(i)
                        self._positions.append(positions[1])
                self._positions.append(positions[2])
                self._positions.append(positions[3])
            else:
                print('ERROR: ship not horizontal or vertical: ' + self._ship + ' ' + str(positions[0]) + ' ' + str(positions[1]) + ' ' + str(positions[2]) + ' ' + str(positions[3]))
                sys.exit(0)
            self._not_hit = self._positions
        elif self._ship == 'S':
            self._size = 3
            if positions[0] == positions[2]:
                if abs(positions[1] - positions[3]) != 2:
                    print('ERROR: incorrect ship size: ' + self._ship + ' ' + str(positions[0]) + ' ' + str(positions[1]) + ' ' + str(positions[2]) + ' ' + str(positions[3]))
                    sys.exit(0)
                self._positions  = [positions[0], positions[1]]
                if positions[1] < positions[3]:
                    for i in range(positions[1] + 1, positions[3]):
                        self._positions.append(positions[0])
                        self._positions.append(i)
                else:
                    for i in range(positions[1] - 1, positions[3], - 1):
                        self._positions.append(positions[0])
                        self._positions.append(i)
                self._positions.append(positions[2])
                self._positions.append(positions[3])
            elif positions[1] == positions[3]:
                if abs(positions[0] - positions[2]) != 2:
                    print('ERROR: incorrect ship size: ' + self._ship + ' ' + str(positions[0]) + ' ' + str(positions[1]) + ' ' + str(positions[2]) + ' ' + str(positions[3]))
                    sys.exit(0)
                self._positions = [positions[0], positions[1]]
                if positions[0] < positions[2]:
                    for i in range(positions[0] + 1, positions[2]):
                        self._positions.append(i)
                        self._positions.append(positions[1])
                else:
                    for i in range(positions[0] - 1, positions[2], - 1):
                        self._positions.append(i)
                        self._positions.append(positions[1])
                self._positions.append(positions[2])
                self._positions.append(positions[3])
            else:
                print('ERROR: ship not horizontal or vertical: ' + self._ship + ' ' + str(positions[0]) + ' ' + str(positions[1]) + ' ' + str(positions[2]) + ' ' + str(positions[3]))
                sys.exit(0)
            self._not_hit = self._positions
        elif self._ship == 'D':
            self._size = 3
            if positions[0] == positions[2]:
                if abs(positions[1] - positions[3]) != 2:
                    print('ERROR: incorrect ship size: ' + self._ship + ' ' + str(positions[0]) + ' ' + str(positions[1]) + ' ' + str(positions[2]) + ' ' + str(positions[3]))
                    sys.exit(0)
                self._positions  = [positions[0], positions[1]]
                if positions[1] < positions[3]:
                    for i in range(positions[1] + 1, positions[3]):
                        self._positions.append(positions[0])
                        self._positions.append(i)
                else:
                    for i in range(positions[1] - 1, positions[3], - 1):
                        self._positions.append(positions[0])
                        self._positions.append(i)
                self._positions.append(positions[2])
                self._positions.append(positions[3])
            elif positions[1] == positions[3]:
                if abs(positions[0] - positions[2]) != 2:
                    print('ERROR: incorrect ship size: ' + self._ship + ' ' + str(positions[0]) + ' ' + str(positions[1]) + ' ' + str(positions[2]) + ' ' + str(positions[3]))
                    sys.exit(0)
                self._positions = [positions[0], positions[1]]
                if positions[0] < positions[2]:
                    for i in range(positions[0] + 1, positions[2]):
                        self._positions.append(i)
                        self._positions.append(positions[1])
                else:
                    for i in range(positions[0] - 1, positions[2], - 1):
                        self._positions.append(i)
                        self._positions.append(positions[1])
                self._positions.append(positions[2])
                self._positions.append(positions[3])
            else:
                print('ERROR: ship not horizontal or vertical: ' + self._ship + ' ' + str(positions[0]) + ' ' + str(positions[1]) + ' ' + str(positions[2]) + ' ' + str(positions[3]))
                sys.exit(0)
            self._not_hit = self._positions
        else:
            if positions[0] != positions[2] and positions[1] != positions[3]:
                print('ERROR: ship not horizontal or vertical: ' + self._ship + ' ' + str(positions[0]) + ' ' + str(positions[1]) + ' ' + str(positions[2]) + ' ' + str(positions[3]))
                sys.exit(0)
            if positions[1] == positions[3]:
                if abs(positions[0] - positions[2]) != 1:
                    print('ERROR: incorrect ship size: ' + self._ship + ' ' + str(positions[0]) + ' ' + str(positions[1]) + ' ' + str(positions[2]) + ' ' + str(positions[3]))
                    sys.exit(0)
            if positions[0] == positions[2]:
                if abs(positions[1] - positions[3]) != 1:
                    print('ERROR: incorrect ship size: ' + self._ship + ' ' + str(positions[0]) + ' ' + str(positions[1]) + ' ' + str(positions[2]) + ' ' + str(positions[3]))
                    sys.exit(0)
            self._size = 2
            self._positions = [positions[0], positions[1], positions[2], positions[3]]
            self._not_hit = self._positions

    def get_positions(self):
        return self._positions
    
    def hit(self, position):
        if position[0] in self._not_hit and position[1] in self._not_hit:
            self._not_hit.remove(position[0])
            self._not_hit.remove(position[1])

    def is_sunk(self):
        if self._not_hit == []:
            return True
        return False
    
    def get_name(self):
        return self._ship
    
    def __str__(self):
        return self._ship

def main():
    player_board = Board()
    aircraftcarrier = input()
    player_board.add_ship(a)
    battleship = input()
    player_board.add_ship(b)
    s = Ship('A', [submarine[0], submarine[1], submarine[2], submarine[3]])
    player_board.add_ship(s)
    destroyer = input()
    d = Ship('A', [destroyer[0], destroyer[1], destroyer[2], destroyer[3]])
    player_board.add_ship(d)
    patrolboat = input()
    p = Ship('A', [patrolboat[0], patrolboat[1], patrolboat[2], patrolboat[3]])
    player_board.add_ship(p)
    while player_board.get_ships() > 0:
        guess = input()
        positions = guess[0:]
        if len(positions) > 1:
            if int(positions[0]) > 9 or int(positions[0]) < 0 or int(positions[1]) > 9 or int(positions[1]) < 0:
                print('illegal guess')
            else: 
                player_board.guess([int(positions[0]), int(positions[1])])

main() 