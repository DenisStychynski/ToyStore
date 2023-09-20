public class Player implements Comparable<Player> {
     String name;
    int id;
    static int totalCount = 0;

    public Player(String name) {
        this.name = name;
        this.id = totalCount++;
    }

    public Player() {
        this("Ребёнок"+ totalCount);
    }

    @Override
    public String toString() {
        return String.format("Участник %s, под номером %d", this.name, this.id);
    }

    @Override
    public int compareTo(Player other) {
        if(other.id == this.id){
            return 0;
        }
        return this.id < other.id ? -1 : 1;
    }
}