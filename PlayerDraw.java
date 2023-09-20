
import java.util.Collection;
import java.util.Iterator;
import java.util.PriorityQueue;


public class PlayerDraw implements Iterable<Player> {
    PriorityQueue<Player> drawQueue;


    public PlayerDraw(Collection<Player> list) {
            this.drawQueue = new PriorityQueue<>(list.size());
            this.drawQueue.addAll(list);
    }

    public PlayerDraw() {
        this.drawQueue = new PriorityQueue<>();
    }

    void addPlayer(Player player){
        this.drawQueue.add(player);
    }


    class PlayerIterator implements Iterator<Player>{
        Player current;
        public PlayerIterator(PriorityQueue<Player> players) {
            this.current = players.peek();
        }

        @Override
        public boolean hasNext() {
            return !drawQueue.isEmpty();
        }

        @Override
        public Player next() {
            return drawQueue.poll();
        }
    }


    @Override
    public Iterator<Player> iterator() {
        return new PlayerIterator(drawQueue);
    }

}