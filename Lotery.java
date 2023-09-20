import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;


public class Lotery {
    ToyList currentToys;
    PlayerDraw currentPlayer;
    double lossWeight = 0;
    int lossId;

    ChanceCalc cals = new ChanceCalc();
    Lotery.QuantityCalc quantityCalc = new Lotery.QuantityCalc();

    public Lotery(PlayerDraw kids, ToyList toyList) {

        this.currentToys = cals.assignChance(toyList);
        this.currentPlayer = kids;
    }

  
    public void runLotery() {
        PlayerDraw kids = this.currentPlayer;
        ToyList list = this.currentToys;
        PriorityQueue<Toy> prizes = new PriorityQueue<>(list.toys.values());
        try {
            BufferedWriter log = Program.loteryLog();

            while(kids.iterator().hasNext()){
                double winRoll = cals.doRoll();
                Player kid = kids.iterator().next();
                try {
                    Toy win = cals.checkPrize(prizes, winRoll);
                    showRoll(kid,win,winRoll);
                    prizes = quantityCalc.adjustQuantityLeft(win,list,prizes);
                    log.write(showWin(kid, win) + "\n");
                } catch(Exception except) {
                    System.out.println(except.getMessage());
                }

            }
            log.close();

        } catch(IOException except) {
            except.printStackTrace();
        }

    }


    String showWin(Player kid, Toy prize) {
        String winLine;
        if(prize.name.equals("ничего")){
            winLine = kid.toString() + " не выиграл ничего";
        } else {
            winLine = kid.toString() + " выиграл " + prize.name;
        }
        System.out.println(winLine);
        return winLine;
    }

    void showRoll(Player kid, Toy prize, double roll) {
        System.out.printf("%s бросает на %.3f , это ниже шанса %.2f у %s%n", kid.name, roll, prize.getChance(), prize.name);
    }

  
    public void setLossWeight(double lossWeight) {
        this.lossWeight = lossWeight;
        this.lossId = this.currentToys.addToy(new Toy(lossWeight, "ничего", -1));
        cals.assignChance(currentToys);
    }

   
    class QuantityCalc {
      
        PriorityQueue<Toy> adjustQuantityLeft(Toy toy, ToyList toyList,PriorityQueue<Toy> currentQueue) {
            if(toy.quantity > 0){
                toy.quantity -= 1;
            }
            if(toy.quantity == 0){
                removeStock(toy.id, toyList);
                Lotery.this.cals.assignChance(toyList);
                currentQueue = new PriorityQueue<>(toyList.toys.values());
            }
            return currentQueue;
        }

        void removeStock(int idNum, ToyList toys) {
            toys.removeToy(idNum);
        }


    }

}

class ChanceCalc {
    Random random = new Random();
    double maxChance;
    double totalWeight;

 
    double doRoll(){
        return random.nextDouble()*maxChance;
    }

    
    Toy checkPrize(PriorityQueue<Toy> prizes, double roll) throws Exception {
       
        PriorityQueue<Toy> onePoll = new PriorityQueue<>(prizes);

        while(!onePoll.isEmpty()){
            Toy poll = onePoll.poll();
            if(roll <= poll.getChance()){
                return checkTies(onePoll,poll);
            }
        }
        throw new Exception("Приз с такой вероятностью не найден");
    }

    
     ToyList assignChance(ToyList toyList){
        this.totalWeight = 0;
        this.maxChance = 0;
        for (Toy t:toyList.toys.values()){
            this.totalWeight += t.chanceWeight;
        }

        for (Toy t:toyList.toys.values()){
            double chance = t.chanceWeight/totalWeight;
            t.setChance(chance);
            if(maxChance < chance ){
                maxChance = chance;
            }
        }
        return toyList;
    }
   
    Toy checkTies(PriorityQueue<Toy> leftovers, Toy drawn  ){
        PriorityQueue<Toy> tiePoll = new PriorityQueue<>(leftovers);
        ArrayList<Toy> sameChance = new ArrayList<>();
        while(!tiePoll.isEmpty()){
            if(drawn.getChance() == tiePoll.peek().getChance()){
                sameChance.add(tiePoll.poll());
            }else {break;}
        }
        sameChance.add(drawn);
        int pickRandom = random.nextInt(sameChance.size());
        return sameChance.get(pickRandom);
    }



}