import sys
import random

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
        if self._ship != None:
            return self._ship.get_ship()
        elif self._guess == True:
            return 'M'
        return '.'

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

    def make_aiBoard(self):
        self.ship_values('A', 4)
        self.ship_values('B', 3)
        self.ship_values('S', 2)
        self.ship_values('D', 2)
        self.ship_values('P', 1)
    
    def ship_colisions(self, positions):
        ai_board = self.get_board()
        if positions[0] == positions[2]:
            if positions[0] < positions[2]:
                for i in range(positions[1], positions[3]):
                    if ai_board[positions[0]][i].get_ship() != None:
                        return False
                return True
            else:
                for i in range(positions[3], positions[1], -1):
                    if ai_board[positions[0]][i].get_ship() != None:
                        return False
                return True
        else:
            if positions[1] < positions[3]:
                for i in range(positions[0], positions[2]):
                    if ai_board[i][positions[1]].get_ship() != None:
                        return False
                return True
            else:
                for i in range(positions[2], positions[0], -1):
                    if ai_board[positions[1]][i].get_ship() != None:
                        return False
                return True
    
    def ship_values(self, type, len):
        rand_v = random.randint(0, 1)
        coords = False
        #x stays the same, y changes
        if rand_v == 0:
            while coords != True:
                x = random.randint(0 , 9)
                y = random.randint(0 , 9)
                if (((y * -1) + 9) + len) > 9:
                    if self.ship_colisions([((y * -1) + 9), x, (((y * -1) + 9) - len), x]):
                        ship = Ship(type, [((y * -1) + 9), x, (((y * -1) + 9) - len), x])
                        break
                elif (((y * -1) + 9) - len) < 9:
                    if self.ship_colisions([((y * -1) + 9), x, (((y * -1) + 9) + len), x]):
                        ship = Ship(type, [((y * -1) + 9), x, (((y * -1) + 9) + len), x])
                        break
                else:
                    either = random.randint(0, 1)
                    if either == 0:
                        if self.ship_colisions([((y * -1) + 9), x, (((y * -1) + 9) - len), x]):
                            ship = Ship(type, [((y * -1) + 9), x, (((y * -1) + 9) - len), x])
                            break
                    else:
                        if self.ship_colisions([((y * -1) + 9), x, (((y * -1) + 9) + len), x]):
                            ship = Ship(type, [((y * -1) + 9), x, (((y * -1) + 9) + len), x])
                            break
        #y changes, x stays the same
        else:
            while coords != True:
                x = random.randint(0 , 9)
                y = random.randint(0 , 9)
                if (x + len) > 9:
                    if self.ship_colisions([((y * -1) + 9), x, ((y * -1) + 9), (x - len)]):
                        ship = Ship(type, [((y * -1) + 9), x, ((y * -1) + 9), (x - len)])
                        break
                elif (x - len) < 9:
                    if self.ship_colisions([((y * -1) + 9), x, ((y * -1) + 9), (x + len)]):
                        ship = Ship(type,([((y * -1) + 9), x, ((y * -1) + 9), (x + len)]))
                        break
                else:
                    either = random.randint(0, 1)
                    if either == 0:
                        if self.ship_colisions([((y * -1) + 9), x, ((y * -1) + 9), (x - len)]):
                            ship = Ship(type, [((y * -1) + 9), x, ((y * -1) + 9), (x - len)])
                            break
                    else:
                        if self.ship_colisions([((y * -1) + 9), x, ((y * -1) + 9), (x + len)]):
                            ship = Ship(type,([((y * -1) + 9), x, ((y * -1) + 9), (x + len)]))
                            break
        self.add_ship(ship)

    def alive(self):
        if self._board.get_ships() == 0:
            return False
        return True

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

    def get_board(self):
        return self._board
    
    #returns what ships are on the board
    def get_ships(self):
        return len(self._ships)
    
    def __str__(self):
        for i in range(len(self._board) - 1, - 1, - 1):
            for ii in range(len(self._board[i])):
                print(self._board[i][ii], end = ' ')
            print()
            
class Ship:
    def __init__(self, ship, positions):
        self._ship = ship
        if self._ship == 'A':
            self.create_ship(positions, 4)
        elif self._ship == 'B':
            self.create_ship(positions, 3)
        elif self._ship == 'S':
            self.create_ship(positions, 2)
        elif self._ship == 'D':
            self.create_ship(positions, 2)
        else:
            self.create_ship(positions, 1)

    def create_ship(self, positions, size):
            self._size = size
            if self._size == 1:
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
            else:
                if positions[0] == positions[2]:
                    if abs(positions[1] - positions[3]) != size:
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
                    if abs(positions[0] - positions[2]) != size:
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

    def get_positions(self):
        return self._positions
    
    def get_ship(self):
        return self._ship
    
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
            
def let_to_num(string):
    letters = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J']
    return [letters.index(string[0]), int(string[1]), letters.index(string[2]), int(string[3])]

def main():
    ai_board = Board()
    ai_board.make_aiBoard()
    print(ai_board)
    player_board = Board()
    aircraftcarrier = input('Aicraft carrier coordinates: ')
    aPositions = let_to_num(aircraftcarrier)
    a = Ship('A', [aPositions[0], aPositions[1], aPositions[2], aPositions[3]])
    player_board.add_ship(a)
    print(player_board)
    battleship = input('Battleship coordinates: ')
    bPositions = let_to_num(battleship)
    b = Ship('B', [bPositions[0], bPositions[1], bPositions[2], bPositions[3]])
    player_board.add_ship(b)
    print(player_board)
    submarine = input('Submarine coordinates: ')
    sPositions = let_to_num(submarine)
    s = Ship('S', [sPositions[0], sPositions[1], sPositions[2], sPositions[3]])
    player_board.add_ship(s)
    print(player_board)
    destroyer = input('Destroyer coordinates: ')
    dPositions = let_to_num(destroyer)
    d = Ship('D', [dPositions[0], dPositions[1], dPositions[2], dPositions[3]])
    player_board.add_ship(d)
    print(player_board)
    patrolboat = input('Patrol Boat coordinates: ')
    pPositions = let_to_num(patrolboat)
    p = Ship('P', [pPositions[0], pPositions[1], pPositions[2], pPositions[3]])
    player_board.add_ship(p)
    print(player_board)
    while player_board.get_ships() > 0 and ai_board.alive():
        guess = input('Attack coordinates: ')
        positions = let_to_num(guess)
        if int(positions[0]) > 9 or int(positions[0]) < 0 or int(positions[1]) > 9 or int(positions[1]) < 0:
            print('illegal guess')
        else: 
            player_board.guess([int(positions[0]), int(positions[1])])

main() 