
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * Класс, призванный собирать объекты игрушек Toy из разных источников в один Hashmap вида <id игрушки,объект игрушки> для быстрого доступа.
 * Чтобы избежать дублирования, переделывает индексы на max(key)++ у тех игрушек, что добавляются вручную.
 * (контроль актуальных id в текстовом файле на совести скармливающего данные)
 */
public class ToyList {
    HashMap<Integer,Toy> toys = new HashMap<>();
    String toyFilepath;
    Program program = new Program();
    protected int maxKey;


    public ToyList(String filepath) {
        this.readFromFile(filepath);
        this.maxKey = Collections.max(toys.keySet());
    }
    public ToyList() {
        this.readFromFile("./toylist.txt");
        this.maxKey = Collections.max(toys.keySet());
    }

    void addToyList(Collection<Toy> newtoys){
        for (Toy toy: newtoys){
            this.addToy(toy);
        }
    }

    
    int addToy(Toy toy){
        int finalId = toy.id;
        if(toys.containsKey(toy.id)){
            finalId = ++maxKey;
            toy.setId(finalId);
        }
        toys.put(toy.id,toy);
        return finalId;
    }
    void removeToy(int idNum){
        toys.remove(idNum);
    }

    
    void readFromFile(String filepath){
        this.toyFilepath=filepath;
        for (String line:program.readToys(filepath)){
            String[] toyParams = line.split(" ",4);
            int toyId = Integer.parseInt(toyParams[0]);
            toys.put(toyId,new Toy(toyId,
                                    Double.parseDouble(toyParams[1]),
                                    toyParams[3],
                                    Integer.parseInt(toyParams[2])));
        }

    }

    void saveToFile(){
        program.writeToys(this.toString());
    }

    @Override
    public String toString() {
        StringBuilder builder =new StringBuilder();
        for (Toy t: toys.values()){
            builder.append(t.toString());
            builder.append("\n");
        }
        return builder.toString();
    }
}