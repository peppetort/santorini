package it.polimi.ingsw.Client;


import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.God;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private final String ip;
    private final int port;
    private ClientBoard board;
    private ClientStatus status;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    private boolean active = true;

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    public Thread asyncReadFromSocket(final ObjectInputStream socketIn) {
        Thread t = new Thread(() -> {
            try {
                while (isActive()) {
                    Object inputObject = socketIn.readObject();


                    if (inputObject instanceof String) {
                        System.out.println((String) inputObject);
                    } else if (inputObject instanceof ClientInitMessage) {
                        String username = ((ClientInitMessage) inputObject).getUsername();
                        ArrayList<Color> players = ((ClientInitMessage) inputObject).getPlayers();
                        status = new ClientStatus(username, players.get(0));
                        board = new ClientBoard(players);
                    } else if (inputObject instanceof TurnUpdateMessage) {
                        String username = ((TurnUpdateMessage) inputObject).getUsername();
                        status.updateTurn(username);
                    } else if (inputObject instanceof ActionsUpdateMessage) {
                        ArrayList<String> actions = ((ActionsUpdateMessage) inputObject).getActions();
                        status.updateAction(actions);
                    } else if (inputObject instanceof CardUpdateMessage){
                        God card = ((CardUpdateMessage) inputObject).getCard();
                        status.setCard(card);
                    }else if (inputObject instanceof BoardUpdatePlaceMessage) {
                        int x = ((BoardUpdatePlaceMessage) inputObject).getX();
                        int y = ((BoardUpdatePlaceMessage) inputObject).getY();
                        Color player = ((BoardUpdatePlaceMessage) inputObject).getPlayer();
                        int worker = ((BoardUpdatePlaceMessage) inputObject).getWorker();
                        board.placePlayer(x, y, player, worker);
                    } else if (inputObject instanceof BoardUpdateBuildMessage) {
                        int x = ((BoardUpdateBuildMessage) inputObject).getX();
                        int y = ((BoardUpdateBuildMessage) inputObject).getY();
                        int level = ((BoardUpdateBuildMessage) inputObject).getLevel();
                        board.setLevel(x, y, level);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWriteToSocket(final Scanner stdin, final PrintWriter socketOut) {
        Thread t = new Thread(() -> {
            try {
                while (isActive()) {
                    String inputLine = stdin.nextLine();
                    socketOut.println(inputLine);
                    socketOut.flush();
                }
            } catch (Exception e) {
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        try {
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncWriteToSocket(stdin, socketOut);
            t0.join();
            t1.join();
        } catch (InterruptedException | NoSuchElementException e) {
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }
}
