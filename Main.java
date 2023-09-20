
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //инициализация списка игрушек разными методами
        ToyList toys1 = new ToyList();
      
        
          
        
        System.out.println(toys1);
        //инициализация очереди участников
        PlayerDraw player = new PlayerDraw(List.of(
                new Player("Олег"),
                new Player("Слава"),
                new Player("Вика"),
                new Player("Тамара"),
                new Player("Маша"),
                new Player("Дима"),
                new Player("Ваня"),
                new Player("Игорь"),
                new Player("Диана"),
                new Player("Олеся")
        ));
       Lotery lot = new Lotery(player,toys1);
        System.out.println(lot.currentToys.toString());
        lot.runLotery();

        System.out.println("\nРозыгрыш с вероятностью проиграть\n");
        PlayerDraw playerloss = new PlayerDraw();
        for (int i = 1; i <= 10 ; i++){
            playerloss.addPlayer(new Player());
        }
        Lotery lot2 = new Lotery(playerloss,toys1);
        lot2.setLossWeight(30);
        System.out.println(lot2.currentToys.toString());
        lot2.runLotery();

        //Можно записать финальное состояние призов для склада
        toys1.saveToFile();
    }

}