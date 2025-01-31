package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.CantGoUpException;
import org.junit.Test;

public class AthenaTurnTest {

    @Test(expected = CantGoUpException.class)
    public void athenaGoUpDefaultNo(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Player otherPlayer = session.getPlayers().get(1);
        Worker worker = player.getWorker1();
        Worker worker2 = player.getWorker2();
        Worker otherWorker = otherPlayer.getWorker1();
        Worker otherWorker2 = otherPlayer.getWorker2();
        board.placePawn(worker, 0, 0);
        board.placePawn(worker2, 3, 3);
        board.placePawn(otherWorker, 4, 4);
        board.placePawn(otherWorker2, 1, 4);
        board.build(0,1,Block.LONE);
        board.build(4,3,Block.LONE);
        player.getPlayerMenu().replace(Actions.SELECT, true);
        Turn turn = new AthenaTurn(player);
        turn.start(worker);
        turn.move(0,1);
        turn.build(0,2);
        turn.end();
        Turn otherTurn = new DefaultTurn(otherPlayer);
        otherPlayer.getPlayerMenu().replace(Actions.SELECT, true);
        otherTurn.start(otherWorker);
        otherTurn.move(4,3);
    }

    @Test(expected = CantGoUpException.class)
    public void athenaGoUpDemeterNo() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Player otherPlayer = session.getPlayers().get(1);
        Worker worker = player.getWorker1();
        Worker worker2 = player.getWorker2();
        Worker otherWorker = otherPlayer.getWorker1();
        Worker otherWorker2 = otherPlayer.getWorker2();
        board.placePawn(worker, 0, 0);
        board.placePawn(worker2, 3, 3);
        board.placePawn(otherWorker, 4, 4);
        board.placePawn(otherWorker2, 1, 4);
        board.build(0, 1,Block.LONE);
        board.build(4, 3,Block.LONE);
        player.getPlayerMenu().replace(Actions.SELECT, true);
        Turn turn = new AthenaTurn(player);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0, 2);
        turn.end();
        Turn otherTurn = new DemeterTurn(otherPlayer);
        otherPlayer.getPlayerMenu().replace(Actions.SELECT, true);
        otherTurn.start(otherWorker);
        otherTurn.move(4, 3);
    }

    @Test(expected = CantGoUpException.class)
    public void athenaGoUpHaphestusNo() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Player otherPlayer = session.getPlayers().get(1);
        Worker worker = player.getWorker1();
        Worker worker2 = player.getWorker2();
        Worker otherWorker = otherPlayer.getWorker1();
        Worker otherWorker2 = otherPlayer.getWorker2();
        board.placePawn(worker, 0, 0);
        board.placePawn(worker2, 3, 3);
        board.placePawn(otherWorker, 4, 4);
        board.placePawn(otherWorker2, 1, 4);
        board.build(0, 1,Block.LONE);
        board.build(4, 3,Block.LONE);
        player.getPlayerMenu().replace(Actions.SELECT, true);
        Turn turn = new AthenaTurn(player);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0, 2);
        turn.end();
        Turn otherTurn = new HephaestusTurn(otherPlayer);
        otherPlayer.getPlayerMenu().replace(Actions.SELECT, true);
        otherTurn.start(otherWorker);
        otherTurn.move(4, 3);
    }

    @Test(expected = CantGoUpException.class)
    public void athenaGoUpArtemisNo() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Player otherPlayer = session.getPlayers().get(1);
        Worker worker = player.getWorker1();
        Worker worker2 = player.getWorker2();
        Worker otherWorker = otherPlayer.getWorker1();
        Worker otherWorker2 = otherPlayer.getWorker2();
        board.placePawn(worker, 0, 0);
        board.placePawn(worker2, 3, 3);
        board.placePawn(otherWorker, 4, 4);
        board.placePawn(otherWorker2, 1, 4);
        board.build(0, 1,Block.LONE);
        board.build(4, 3,Block.LONE);
        Turn turn = new AthenaTurn(player);
        player.getPlayerMenu().replace(Actions.SELECT, true);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0, 2);
        turn.end();
        Turn otherTurn = new ArtemisTurn(otherPlayer);
        otherPlayer.getPlayerMenu().replace(Actions.SELECT, true);
        otherTurn.start(otherWorker);
        otherTurn.move(4, 3);
    }

    @Test(expected = CantGoUpException.class)
    public void athenaGoUpPrometheusNo() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Player otherPlayer = session.getPlayers().get(1);
        Worker worker = player.getWorker1();
        Worker worker2 = player.getWorker2();
        Worker otherWorker = otherPlayer.getWorker1();
        Worker otherWorker2 = otherPlayer.getWorker2();
        board.placePawn(worker, 0, 0);
        board.placePawn(worker2, 3, 3);
        board.placePawn(otherWorker, 4, 4);
        board.placePawn(otherWorker2, 1, 4);
        board.build(0, 1,Block.LONE);
        board.build(4, 3,Block.LONE);
        Turn turn = new AthenaTurn(player);
        player.getPlayerMenu().replace(Actions.SELECT, true);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0, 2);
        turn.end();
        Turn otherTurn = new PrometheusTurn(otherPlayer);
        otherPlayer.getPlayerMenu().replace(Actions.SELECT, true);
        otherTurn.start(otherWorker);
        otherTurn.move(4, 3);
    }

    @Test
    public void athenaGoUpAthenaNoGoUpOtherCanGoUp(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Player otherPlayer = session.getPlayers().get(1);
        Worker worker = player.getWorker1();
        Worker worker2 = player.getWorker2();
        Worker otherWorker = otherPlayer.getWorker1();
        Worker otherWorker2 = otherPlayer.getWorker2();
        board.placePawn(worker, 0, 0);
        board.placePawn(worker2, 3, 3);
        board.placePawn(otherWorker, 4, 4);
        board.placePawn(otherWorker2, 1, 4);
        board.build(0, 1,Block.LONE);
        board.build(4, 3,Block.LONE);
        Turn turn = new AthenaTurn(player);
        player.getPlayerMenu().replace(Actions.SELECT, true);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0, 0);
        turn.end();
        player.getPlayerMenu().replace(Actions.SELECT, true);
        turn.start(worker);
        turn.move(0, 0);
        turn.build(0, 1);
        turn.end();
        Turn otherTurn = new DemeterTurn(otherPlayer);
        otherPlayer.getPlayerMenu().replace(Actions.SELECT, true);
        otherTurn.start(otherWorker);
        otherTurn.move(4, 3);
        otherTurn.build(4,4);
        otherTurn.end();
    }
}