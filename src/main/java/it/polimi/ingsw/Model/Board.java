package it.polimi.ingsw.Model;

public class Board {
    private Box[][] board=new Box[5][5];

    public Board()
    {
        int x;
        int y;
        for(y=0;y<5;y++)
            for(x=0;x<5;x++)
                board[x][y]=new Box();
    }

    public void move(Worker w,int x,int y)
    {
    }
}
