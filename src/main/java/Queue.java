import java.util.ArrayDeque;

public class Queue {
    java.util.Queue<ToyParam> queue = new ArrayDeque<>();
    boolean removeAfterGetting = false;

    public Queue(Controller controller, int amount) {
        ToyParam toy = controller.nextToy(false);
        for (int i = 0; i < amount && toy != null; i++) {
            queue.add(toy);
            toy = controller.nextToy(false);
        }
    }


    public ToyParam get() {
        if (queue.isEmpty()) {
            System.out.println("Queue is empty!");
            return null;
        } else {
            return queue.poll();
        }
    }
}
