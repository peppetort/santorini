package it.polimi.ingsw.Client;

import it.polimi.ingsw.Model.Color;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ClientStatusTest {

    @Test
    public void printMenuNoTurn() throws InterruptedException {
        ClientStatus status = new ClientStatus("PIPPO", Color.BLUE);
        status.updateTurn("PLUTO");
    }


    @Test
    public void printMenuMyTurnNoActions() throws InterruptedException {
        ClientStatus status = new ClientStatus("PIPPO", Color.BLUE);
        status.updateTurn("PIPPO");
    }

    @Test
    public void printMenuMyTurnActions() throws InterruptedException {
        ClientStatus status = new ClientStatus("PIPPO", Color.BLUE);
        status.updateTurn("PIPPO");
        ArrayList<Actions> actions = new ArrayList<>();
        actions.add(Actions.PLACE);
        status.updateAction(actions);
    }

    @Test
    public void printMenuMyTurnNoneActions() throws InterruptedException {
        ClientStatus status = new ClientStatus("PIPPO", Color.BLUE);
        status.updateTurn("PIPPO");
        ArrayList<Actions> actions = new ArrayList<>();
        actions.add(Actions.MOVE);
        status.updateAction(actions);
    }
}