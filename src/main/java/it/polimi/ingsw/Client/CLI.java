package it.polimi.ingsw.Client;


import it.polimi.ingsw.Client.Box;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Observer.Observer;

import java.util.ArrayList;

public class CLI implements Observer {
    private ClientBoard clientBoard;
    private ClientStatus clientStatus;
    private final String lengthMarker = "-";
    private final String widthMarker = "+";

    public void setClientBoard(ClientBoard clientBoard) {
        this.clientBoard = clientBoard;
    }

    public void setClientStatus(ClientStatus clientStatus) {
        this.clientStatus = clientStatus;
    }

    public void print() {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_BLUE = "\u001B[34m";

        String title = "BOARD";
        String menuFormat;
        String bodyFormat;

        String lengthMarker = "-";
        String widthMarker = "+";

        int maxLength = 18;
        Box[][] board = clientBoard.getBoard();

        menuFormat = "%-";
        menuFormat += Integer.toString(11 + maxLength / 2);
        menuFormat += "s%s%";
        menuFormat += Integer.toString(12 + maxLength / 2);
        menuFormat += "s%n";

        int row = 0;

        int length = 2 * maxLength + 6;

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");

        System.out.printf(menuFormat, widthMarker, title, widthMarker);

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");

        System.out.printf(("%s%" + (length + 3) + "s%n"), widthMarker, widthMarker);


        bodyFormat = "%-" + (maxLength - 12) + "s%6s%6s%6s%6s%6s%" + (maxLength - 8) + "s";
        System.out.printf(bodyFormat, widthMarker, 0, 1, 2, 3, 4, widthMarker);
        System.out.print("\n");

        System.out.printf(("%s%" + (length + 3) + "s%n"), widthMarker, widthMarker);


        for (int x = 0; x < 5; x++) {
            StringBuilder sRow = new StringBuilder(String.format("%-" + (maxLength - 12) + "s%s", widthMarker, row));
            for (int y = 0; y < 5; y++) {

                try {
                    switch (board[x][y].getPlayer()) {
                        case GREEN:
                            sRow.append(String.format("%4s-%s", board[x][y].getLevel(), ANSI_GREEN + board[x][y].getWorker() + ANSI_RESET));
                            break;
                        case RED:
                            sRow.append(String.format("%4s-%s", board[x][y].getLevel(), ANSI_RED + board[x][y].getWorker() + ANSI_RESET));
                            break;
                        case BLUE:
                            sRow.append(String.format("%4s-%s", board[x][y].getLevel(), ANSI_BLUE + board[x][y].getWorker() + ANSI_RESET));
                            break;
                    }
                } catch (NullPointerException e) {
                    sRow.append(String.format("%4s-%s", board[x][y].getLevel(), ANSI_RESET + 0));
                }
            }
            sRow.append(String.format("%" + (maxLength - 9) + "s", widthMarker));
            System.out.print(sRow);
            System.out.print("\n");
            System.out.printf(("%s%" + (length + 3) + "s%n"), widthMarker, widthMarker);
            row++;
        }

        System.out.printf(("%s%" + (length + 3) + "s%n"), widthMarker, widthMarker);

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");
        System.out.print("\n");

    }

    private synchronized void printActions(int length) {

        String actionsLabel = "ACTION";
        String bodyFormat;
        ArrayList<String> actions = clientStatus.getActions();

        int actionsLength = (length - actionsLabel.length()) / 2 - 1;

        System.out.print(widthMarker + " ");
        for (int i = 0; i < actionsLength; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + actionsLabel + " ");
        for (int i = 0; i < actionsLength; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");

        for (String act: actions){
            bodyFormat = "%-3s%s%" + (length + 1 - act.length() - 2) + "s%n";
            System.out.printf(bodyFormat, widthMarker, "> "+act.toUpperCase(), widthMarker);
        }

        System.out.printf(("%s%" + (length + 3) + "s%n"), widthMarker, widthMarker);

    }

    //TODO: spostare in una classe CLI
    private synchronized void printMessages(int length){
        String messagesLabel = "MESSAGES";
        String bodyFormat;

        ArrayList<String> messages = clientStatus.getMessages();

        int messagesLength = (length - messagesLabel.length()) / 2 - 1;

        System.out.print(widthMarker + " ");
        for (int i = 0; i < messagesLength; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + messagesLabel + " ");
        for (int i = 0; i < messagesLength; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");

        for(String message: messages){
            bodyFormat = "%-3s%s%" + (length + 3 - message.length() - 2) + "s%n";
            System.out.printf(bodyFormat, widthMarker, message, widthMarker);
        }

        System.out.printf(("%s%" + (length + 3) + "s%n"), widthMarker, widthMarker);
    }

    //TODO: sposatre in una classe CLI
    public synchronized void printAllCards(){
        String title = "ALL CARDS";
        String menuFormat;
        String bodyFormat;

        int maxLength = 8;

        menuFormat = "%-";
        menuFormat += Integer.toString(5 + maxLength / 2);
        menuFormat += "s%s%";
        menuFormat += Integer.toString(5 + maxLength / 2);
        menuFormat += "s%n";

        int length = maxLength / 2 + maxLength / 2 + title.length() + 6;

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");

        System.out.printf(menuFormat, widthMarker, title, widthMarker);

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");

        for(God g: God.values()){
            String name = g.name();
            bodyFormat = "%-3s%s%" + (length + 3 - name.length() - 2) + "s%n";
            System.out.printf(bodyFormat, widthMarker, name, widthMarker);
        }

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");
    }

    //TODO: spostare in una classe CLI
    public synchronized void printDeck(){
        String title = "AVAILABLE CARDS";
        String menuFormat;
        String bodyFormat;

        ArrayList<God> deck = clientStatus.getDeck();

        int maxLength = 8;

        menuFormat = "%-";
        menuFormat += Integer.toString(5 + maxLength / 2);
        menuFormat += "s%s%";
        menuFormat += Integer.toString(5 + maxLength / 2);
        menuFormat += "s%n";

        int length = maxLength / 2 + maxLength / 2 + title.length() + 6;

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");

        System.out.printf(menuFormat, widthMarker, title, widthMarker);

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");

        for(God name: deck){
            bodyFormat = "%-3s%s%" + (length + 3 - name.name().length() - 2) + "s%n";
            System.out.printf(bodyFormat, widthMarker, name, widthMarker);
        }

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");
    }

    //TODO: spostare in classe CLI
    public synchronized void printStatus(){
        String title = "STATUS";
        String menuFormat;
        String bodyFormat;

        String username = clientStatus.getUsername();
        String card = clientStatus.getCard();
        String turn = clientStatus.getTurn();
        Color color = clientStatus.getColor();
        ArrayList<String> actions = clientStatus.getActions();
        ArrayList<String> messages = clientStatus.getMessages();


        StringBuilder usernameLabel = new StringBuilder("Username: ").append(username);
        StringBuilder colorLabel = new StringBuilder("Color: ").append(color);

        StringBuilder cardLabel = null;
        if (card != null) {
            cardLabel = new StringBuilder("Card: ").append(card);
        }

        StringBuilder turnLabel = null;

        if (turn != null) {
            turnLabel = new StringBuilder("Turn: ").append(turn);
        }


        int maxLength = title.length();

        if (usernameLabel.length() > maxLength) {
            maxLength = usernameLabel.length();
        }
        if (colorLabel.length() > maxLength) {
            maxLength = colorLabel.length();
        }
        if (turnLabel != null && turnLabel.length() > maxLength) {
            maxLength = turnLabel.length();
        }
        if (cardLabel != null && cardLabel.length() > maxLength) {
            maxLength = cardLabel.length();
        }

        if(actions != null) {
            for (String act : actions) {
                if (act.length() > maxLength) {
                    maxLength = act.length();
                }
            }
        }

        menuFormat = "%-";
        menuFormat += Integer.toString(5 + maxLength / 2);
        menuFormat += "s%s%";
        menuFormat += Integer.toString(5 + maxLength / 2);
        menuFormat += "s%n";

        int length = maxLength / 2 + maxLength / 2 + title.length() + 6;

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");

        System.out.printf(menuFormat, widthMarker, title, widthMarker);

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");

        bodyFormat = "%-3s%s%" + (maxLength / 2 + maxLength / 2 + 7 + title.length() - usernameLabel.length()) + "s%n";
        System.out.printf(bodyFormat, widthMarker, usernameLabel, widthMarker);
        bodyFormat = "%-3s%s%" + (maxLength / 2 + maxLength / 2 + 7 + title.length() - colorLabel.length()) + "s%n";
        System.out.printf(bodyFormat, widthMarker, colorLabel, widthMarker);

        if (cardLabel != null) {
            bodyFormat = "%-3s%s%" + (maxLength / 2 + maxLength / 2 + 7 + title.length() - cardLabel.length()) + "s%n";
            System.out.printf(bodyFormat, widthMarker, cardLabel, widthMarker);
        }

        if (turnLabel != null) {
            bodyFormat = "%-3s%s%" + (maxLength / 2 + maxLength / 2 + 7 + title.length() - turnLabel.length()) + "s%n";
            System.out.printf(bodyFormat, widthMarker, turnLabel, widthMarker);
        }
        System.out.printf(("%s%" + (length + 3) + "s%n"), widthMarker, widthMarker);

        if(turn != null && turn.equals(username) && actions!=null) {
            printActions(length);
        }

        if(messages.size() != 0) {
            printMessages(length);
        }

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");
        System.out.print("\n");


    }

    @Override
    public void update(Object message) {
        if(message instanceof Integer) {
            if (Integer.valueOf((Integer) message) == 1) {
                this.print();
            } else if (Integer.valueOf((Integer) message) == 2) {
                this.printStatus();
            } else if (Integer.valueOf((Integer) message) == 3) {
                this.printAllCards();
            } else if (Integer.valueOf((Integer) message) == 4) {
                this.printDeck();
            }
        }else {
           System.out.println(message);
       }
    }
}
