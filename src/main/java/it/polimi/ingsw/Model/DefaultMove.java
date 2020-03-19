package it.polimi.ingsw.Model;

public class DefaultMove implements Move {

    private Board board;


    public DefaultMove(Player player){
        this.board = player.getSession().getBoard();
    }

    @Override
    public void move(Worker worker, int x, int y) {
        int wX = worker.getXPos();
        int wY = worker.getYPos();

        Box workerBox = board.getBox(wX, wY);
        Box nextBox = board.getBox(x, y);

        try {
            if( x > wX+1 || x < wX-1 || y > wY+1 || y < wY-1 || (x == wX && y == wY)){
                throw new RuntimeException("Invalid move!");
            }else if(!nextBox.isFree()){
                throw new RuntimeException("Box already occupied!");
            }else if(!workerBox.compare(nextBox)){
                throw new RuntimeException("Level not compatible!");
            }else{
                board.placePawn(worker, x, y);
                worker.setPos(x, y);
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println("Out of board limits");
        }

    }
}
