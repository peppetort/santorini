package it.polimi.ingsw.Model;

/*
    Il tuo lavoratore può spostarsi nella casella di un lavoratore avversario se la
    casella successiva nella stessa direzione è libera. Il lavoratore avversario
    è costretto a spostarsi in quella casella
 */

public class MinotaurMove implements Move {
    private Board board;
    private Player player;


    public MinotaurMove(Player player){
        this.player = player;
        this.board = player.getSession().getBoard();
    }

    @Override
    public void move(Worker worker, int x, int y) {
        try {
            int wX = worker.getXPos();
            int wY = worker.getYPos();

            Box workerBox = board.getBox(wX, wY);
            Box nextBox = board.getBox(x, y);

            if( x > wX+1 || x < wX-1 || y > wY+1 || y < wY-1 || (x == wX && y == wY)){
                throw new RuntimeException("Invalid move!");
            }else if(!workerBox.compare(nextBox)){
                throw new RuntimeException("Level not compatible!");
            }else{
                if(!nextBox.isFree()) {
                    Worker other = nextBox.getPawn();
                    // Controllo se l'altra pedina è una pedina avversaria
                    if (!other.getId().equals(player.getWorker1().getId()) && !other.getId().equals(player.getWorker2().getId())) {
                        if(x == wX && y == wY+1){                       // Movimento Nord
                            if (board.getBox(x, y+1).isFree()) {
                                board.placePawn(other, x, y+1);      //posiziono la pedina avversaria nella mia posizione
                                other.updateLastBox(nextBox);           // aggiorno l'ultima box della pedina avversaria
                                other.setPos(x, y+1);               //aggiorno coordinate pedina avversaria
                                board.placePawn(worker, x, y);         //posizione la mia pedina nella nuova posizione
                                worker.updateLastBox(workerBox);       // aggiorno l'ultima box della mia pedina
                                worker.setPos(x, y);                    //aggiorno coordinate pedona
                                workerBox.removePawn();
                            } else {
                                throw new RuntimeException("Opponent's worker can't back away!");
                            }
                        }else if(x == wX && y == wY-1){ // Movimento Sud
                            if (board.getBox(x, y-1).isFree()) {
                                board.placePawn(other, x, y-1); //posiziono la pedina avversaria nella mia posizione
                                other.updateLastBox(nextBox);           // aggiorno l'ultima box della pedina avversaria
                                other.setPos(x, y-1); //aggiorno coordinate pedina avversaria
                                board.placePawn(worker, x, y); //posizione la mia pedina nella nuova posizione
                                worker.updateLastBox(workerBox);       // aggiorno l'ultima box della mia pedina
                                worker.setPos(x, y); //aggiorno coordinate pedona
                                workerBox.removePawn();
                            } else {
                                throw new RuntimeException("Opponent's worker can't back away!");
                            }
                        }else if(x == wX+1 && y == wY){ // Movimento Est
                            if (board.getBox(x+1, y).isFree()) {
                                board.placePawn(other, x+1, y); //posiziono la pedina avversaria nella mia posizione
                                other.updateLastBox(nextBox);           // aggiorno l'ultima box della pedina avversaria
                                other.setPos(x+1, y); //aggiorno coordinate pedina avversaria
                                board.placePawn(worker, x, y); //posizione la mia pedina nella nuova posizione
                                worker.updateLastBox(workerBox);       // aggiorno l'ultima box della mia pedina
                                worker.setPos(x, y); //aggiorno coordinate pedona
                                workerBox.removePawn();
                            } else {
                                throw new RuntimeException("Opponent's worker can't back away!");
                            }
                        }else if(x == wX-1 && y == wY){ // Movimento Ovest
                            if (board.getBox(x-1, y).isFree()) {
                                board.placePawn(other, x-1, y); //posiziono la pedina avversaria nella mia posizione
                                other.updateLastBox(nextBox);           // aggiorno l'ultima box della pedina avversaria
                                other.setPos(x-1, y); //aggiorno coordinate pedina avversaria
                                board.placePawn(worker, x, y); //posizione la mia pedina nella nuova posizione
                                worker.updateLastBox(workerBox);       // aggiorno l'ultima box della mia pedina
                                worker.setPos(x, y); //aggiorno coordinate pedona
                                workerBox.removePawn();
                            } else {
                                throw new RuntimeException("Opponent's worker can't back away!");
                            }
                        }else if(x == wX+1 && y == wY+1){ // Movimento Nord-Est
                            if (board.getBox(x+1, y+1).isFree()) {
                                board.placePawn(other, x+1, y+1); //posiziono la pedina avversaria nella mia posizione
                                other.updateLastBox(nextBox);           // aggiorno l'ultima box della pedina avversaria
                                other.setPos(x+1, y+1); //aggiorno coordinate pedina avversaria
                                board.placePawn(worker, x, y); //posizione la mia pedina nella nuova posizione
                                worker.updateLastBox(workerBox);       // aggiorno l'ultima box della mia pedina
                                worker.setPos(x, y); //aggiorno coordinate pedona
                                workerBox.removePawn();
                            } else {
                                throw new RuntimeException("Opponent's worker can't back away!");
                            }
                        }else if(x == wX+1 && y == wY-1){ // Movimento Sud-Est
                            if (board.getBox(x+1, y-1).isFree()) {
                                board.placePawn(other, x+1, y-1); //posiziono la pedina avversaria nella mia posizione
                                other.updateLastBox(nextBox);           // aggiorno l'ultima box della pedina avversaria
                                other.setPos(x+1, y-1); //aggiorno coordinate pedina avversaria
                                board.placePawn(worker, x, y); //posizione la mia pedina nella nuova posizione
                                worker.updateLastBox(workerBox);       // aggiorno l'ultima box della mia pedina
                                worker.setPos(x, y); //aggiorno coordinate pedona
                                workerBox.removePawn();
                            } else {
                                throw new RuntimeException("Opponent's worker can't back away!");
                            }
                        }else if(x == wX-1 && y == wY+1){ // Movimento Nord-Ovest
                            if (board.getBox(x-1, y+1).isFree()) {
                                board.placePawn(other, x-1, y+1); //posiziono la pedina avversaria nella mia posizione
                                other.updateLastBox(nextBox);           // aggiorno l'ultima box della pedina avversaria
                                other.setPos(x-1, y+1); //aggiorno coordinate pedina avversaria
                                board.placePawn(worker, x, y); //posizione la mia pedina nella nuova posizione
                                worker.updateLastBox(workerBox);       // aggiorno l'ultima box della mia pedina
                                worker.setPos(x, y); //aggiorno coordinate pedona
                                workerBox.removePawn();
                            } else {
                                throw new RuntimeException("Opponent's worker can't back away!");
                            }
                        }else if(x == wX-1 && y == wY-1){ // Movimento Sud-Ovest
                            if (board.getBox(x-1, y-1).isFree()) {
                                board.placePawn(other, x-1, y-1); //posiziono la pedina avversaria nella mia posizione
                                other.updateLastBox(nextBox);           // aggiorno l'ultima box della pedina avversaria
                                other.setPos(x-1, y-1); //aggiorno coordinate pedina avversaria
                                board.placePawn(worker, x, y); //posizione la mia pedina nella nuova posizione
                                worker.updateLastBox(workerBox);       // aggiorno l'ultima box della mia pedina
                                worker.setPos(x, y); //aggiorno coordinate pedona
                                workerBox.removePawn();
                            } else {
                                throw new RuntimeException("Opponent's worker can't back away!");
                            }
                        }
                    }else {
                        throw new RuntimeException("Can't place pawn here");
                    }
                }else {
                    board.placePawn(worker, x, y);
                    workerBox.removePawn(); //rimuovo pedina dalla vecchia pos
                    worker.updateLastBox(workerBox);            // aggiorno l'ultima box nel worker
                    worker.setPos(x, y);   //aggiorno cordinate pedina
                }
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println("Out of board limits");
        }catch (NullPointerException e){
            System.out.println("Pawns not in board!");
        }
    }
}
