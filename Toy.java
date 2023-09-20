
public class Toy implements Comparable<Toy> {
    int id;
    static int idCount;
    double chanceWeight;
    String name;
    int quantity;

    private double chance;

    public Toy(int id, double chanceWeight, String name, int quantity) {
        this.id = id;
        this.chanceWeight = chanceWeight;
        this.name = name;
        this.quantity = quantity;
        this.chance = 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public Toy(double chanceWeight, String name, int quantity) {
        this(idCount++, chanceWeight, name, quantity);
    }

    public Toy(double chanceWeight, String name) {
        this(idCount++, chanceWeight, name, -1);
    }

    @Override
    public String toString() {
        return String.format("id:%d %s, вероятность %.1f, кол-во %d, категория редкости %.2f", this.id, this.name,
                this.chanceWeight, this.quantity, this.chance);
    }

    @Override
    public int compareTo(Toy other) {
        if (other.chance == this.chance) {
            return 0;
        }
        return this.chance < other.chance ? -1 : 1;
    }
}