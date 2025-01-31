package it.polimi.ingsw.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class AtlasBuildTest {

    @Test
    public void buildDomeOnTERRAIN() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker = player.getWorker1();
        Box box = board.getBox(0,0);
        board.placePawn(worker, 0, 1);
        Build buildAction = new AtlasBuild(player);
        ((AtlasBuild) buildAction).buildDome(worker, 0, 0);
        assertEquals(box.getBlock(), Block.DOME);
    }

    @Test(expected = RuntimeException.class)
    public void cantBuildDOMEonDOME() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker = player.getWorker1();
        Box box = board.getBox(0,0);
        board.placePawn(worker,0, 1);
        box.setBlock(Block.DOME);
        Build buildAction = new AtlasBuild(player);
        ((AtlasBuild) buildAction).buildDome(worker, 0, 0);
        assertEquals(box.getBlock(), Block.DOME);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void buildOutOfBoardLimits() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 4, 0);
        Build buildAction = new AtlasBuild(player);
        ((AtlasBuild) buildAction).buildDome(worker, 5, 0);
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                assertEquals(board.getBox(i, j).getBlock(), Block.TERRAIN);
            }
        }
    }

    @Test(expected = RuntimeException.class)
    public void buildOnTheWorkersBox() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 4, 0);
        Build buildAction = new AtlasBuild(player);
        ((AtlasBuild) buildAction).buildDome(worker, 4, 0);
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                assertEquals(board.getBox(i, j).getBlock(), Block.TERRAIN);
            }
        }
    }

    @Test(expected = RuntimeException.class)
    public void buildOnAWorkersBox() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Player player1 = game.getPlayers().get(1);
        Worker worker = player.getWorker1();
        Worker worker2 = player1.getWorker1();
        board.placePawn(worker, 4, 0);
        board.placePawn(worker2, 3, 0);
        Build buildAction = new AtlasBuild(player);
        ((AtlasBuild) buildAction).buildDome(worker, 3, 0);
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                assertEquals(board.getBox(i, j).getBlock(), Block.TERRAIN);
            }
        }
    }

    @Test(expected = NullPointerException.class)
    public void buildWorkerNotInBoard() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker = player.getWorker1();
        Box box = board.getBox(0,0);
        Build buildAction = new AtlasBuild(player);
        ((AtlasBuild) buildAction).buildDome(worker, 0, 0);
        assertEquals(box.getBlock(), Block.TERRAIN);
    }

    @Test(expected = RuntimeException.class)
    public void buildFarFromWorker() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 0);
        Build buildAction = new AtlasBuild(player);
        ((AtlasBuild) buildAction).buildDome(worker, 4, 4);
        assertEquals(board.getBox(4,4).getBlock(), Block.TERRAIN);
    }
}