package it.polimi.ingsw.Client;

import it.polimi.ingsw.CLI.CLI;
import it.polimi.ingsw.Exceptions.AlreadyExistingSessionException;
import it.polimi.ingsw.Exceptions.InvalidUsernameException;
import it.polimi.ingsw.Exceptions.SessionNotExistsException;
import it.polimi.ingsw.GUI.*;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.Actions;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * It represents the client and takes care of receiving
 * and sending messages to the server and updating the client-side status
 */
public class Client extends Observable implements Observer<Object> {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	MainController mc;

	private final String ip;
	private final int port;

	private ClientStatus status;
	private ClientBoard board;

	private CLI cli;

	private final int mode;

	public Client(String ip, int port, int mode) {
		this.ip = ip;
		this.port = port;
		this.mode = mode;
	}

	/**
	 * Instantiates useful objects and create {@link CLI} or {@link MainController} for GUI
	 * depending on the mode
	 *
	 * @throws IOException
	 */
	public void startClient() throws IOException {
		Thread reader = asyncReadFromSocket();
		socket = new Socket(ip, port);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());

		if (mode == 0) {
			mc = new MainController();
			mc.addObserver(this);
			this.addObserver(mc);

			mc.setClient(this);

			JoinMenuController.setMainController(mc);
			CreateMenuController.setMainController(mc);
			PlayingStageController.setMainController(mc);
			//WaitController.setMainController(mc);
			AllCardsMenuController.setMainController(mc);
			SelectCardMenuController.setMainController(mc);
		} else {
			cli = new CLI(this);
			cli.addObserver(this);
			this.addObserver(cli);
			notify(0);
		}

		try {
			reader.start();
		} catch (RuntimeException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Reads messages sent via sockets and changes the status of {@link ClientStatus} and update {@link ClientBoard}
	 *
	 * @return the thread that takes care of reading socket objects asynchronously
	 */
	public Thread asyncReadFromSocket() {
		return new Thread(() -> {
			Object inputObject;
			boolean connected = true;
			while (connected) {
				try {
					inputObject = in.readObject();
					if (inputObject instanceof String || inputObject instanceof SessionNotExistsException || inputObject instanceof SuccessfulJoin || inputObject instanceof SessionListMessage || inputObject instanceof InvalidChoiceMessage || inputObject instanceof SuccessfulCreate) {
						notify(inputObject);
					} else if (inputObject instanceof ClientInitMessage) {
						String username = ((ClientInitMessage) inputObject).getUsername();
						ArrayList<Color> players = ((ClientInitMessage) inputObject).getPlayers();
						status = new ClientStatus(username, players.get(0), players.size());
						board = new ClientBoard(players);

						if (mode == 0) {
							notify(0);
							status.addObserver(mc);
							board.addObserver(mc);
						} else {
							cli.setClientBoard(board);
							cli.setClientStatus(status);
							status.addObserver(cli);
							board.addObserver(cli);

						}


					} else if (inputObject instanceof TurnUpdateMessage) {
						String username = ((TurnUpdateMessage) inputObject).getUsername();
						Color color = ((TurnUpdateMessage) inputObject).getColor();
						God god = ((TurnUpdateMessage) inputObject).getGod();
						status.updateTurn(username, color, god);
					} else if (inputObject instanceof ActionsUpdateMessage) {
						ArrayList<Actions> actions = ((ActionsUpdateMessage) inputObject).getActions();
						status.updateAction(actions);
					} else if (inputObject instanceof CardUpdateMessage) {
						God card = ((CardUpdateMessage) inputObject).getCard();
						status.setCard(card);
					} else if (inputObject instanceof BoardUpdatePlaceMessage) {
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
					} else if (inputObject instanceof WinMessage) {
						String winUser = ((WinMessage) inputObject).getUsername();
						status.setWinner(winUser);
						if (this.getStatus().getUsername().equals(winUser)) {
							notify(Status.WON);
						} else {
							notify(Status.LOST);
						}
					} else if (inputObject instanceof LostMessage) {
						String loser = ((LostMessage) inputObject).getUsername();
						Color loserColor = ((LostMessage) inputObject).getColor();
						status.lose(loser);
						if (this.getStatus().getUsername().equals(loser)) {
							notify(Status.LOST);
						}
					} else if (inputObject instanceof DeckUpdateMessage) {
						ArrayList<God> deck = ((DeckUpdateMessage) inputObject).getDeck();
						status.updateDeck(deck);
					} else if (inputObject instanceof BoardUndoMessage) {
						int x = ((BoardUndoMessage) inputObject).getX();
						int y = ((BoardUndoMessage) inputObject).getY();
						Color player = ((BoardUndoMessage) inputObject).getPlayer();
						Integer worker = ((BoardUndoMessage) inputObject).getWorker();
						int level = ((BoardUndoMessage) inputObject).getLevel();
						// ristabilisce la visione della board all'inizio del turno
						board.setLevel(x, y, level);
						if (worker != null) {
							board.placePlayer(x, y, player, worker);
						}
					} else if (inputObject instanceof ChatUpdateMessage) {
						notify(inputObject);
					} else if (inputObject instanceof InvalidUsernameException || inputObject instanceof AlreadyExistingSessionException){
						notify(inputObject);
					} else if (inputObject instanceof RemovedMessage){
						this.board.lose(((RemovedMessage) inputObject).getPlayer());
					}
				} catch (IOException | ClassNotFoundException e) {
					connected = false;
				}
			}
		});
	}

	/**
	 * Sends messages via sockets to the server
	 *
	 * @param message to send
	 */
	public void send(Object message) {
		try {
			out.reset();
			out.writeObject(message);
			out.flush();
		} catch (Exception ignored) {
		}
	}

	@Override
	public void update(Object message) {
		send(message);
	}

	public ClientStatus getStatus() {
		return status;
	}

	public ClientBoard getBoard() {
		return board;
	}
}