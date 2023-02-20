public class Main {
    public static void main(String[] args) {

        Controller.PrizesGen(15);
        Controller controller = new Controller();

        ToyParam toy_test = new ToyParam(16, "test", 4);
        controller.appendToy(toy_test);


        Queue queue1 = new Queue(controller, 10);
        for (int i = 0; i < 10; i++)
            controller.appendResults(queue1.get());
    }
}