import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class Controller {
    private final NavigableMap<Integer, ToyParam> allToys = new TreeMap<>();

    final String Data = "data.csv";
    final String Results = "prizes.txt";
    final Random r = new Random();
    int Weight = 0;

    public Controller() {
        setMap();
    }

    private void setMap() {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(this.Data), StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                this.Weight += Integer.parseInt(split[2]);
                allToys.put(this.Weight, new ToyParam(Integer.parseInt(split[0]), split[1], Integer.parseInt(split[2])));
            }
        } catch (IOException e) {
            System.out.println("IO exception. something with datafile");
            System.out.println(e.getMessage());
        }
    }

    public void appendToy(ToyParam toy) {
        if (toy == null) return;

        this.Weight += toy.getChance();
        allToys.put(this.Weight, toy);

        append(toy, this.Data);
    }

    private void append(ToyParam toy, String path) {
        try (Writer writer = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.append(toy.toString());
            writer.append('\n');
        } catch (IOException e) {
            System.out.println("IO exception in append method");
            e.printStackTrace();
        }
    }


    public void appendResults(ToyParam toy) {
        if (toy == null) return;

        append(toy, this.Results);
    }

    public ToyParam nextToy(boolean removeAfterGetting) {
        if (allToys.isEmpty()) return null;

        int rand = r.nextInt(Weight) + 1;
        ToyParam chosenToy = allToys.ceilingEntry(rand).getValue();
        if (removeAfterGetting) {
            remToy(chosenToy);
        }
        return chosenToy;
    }

    private void remToy(ToyParam toy) {
        try (Writer writer = Files.newBufferedWriter(Paths.get(this.Data), StandardCharsets.UTF_8)) {
            for (ToyParam t : allToys.values()) {
                if (t.equals(toy)) continue;
                writer.write(t.toString() + '\n');
            }
        } catch (IOException e) {
            System.out.println("IO exception in method remToy");
            e.printStackTrace();
        }

        allToys.clear();
        Weight = 0;
        setMap();
    }

    public static void PrizesGen(int quantity) {
        String[] data = new String[quantity];
        Random r = new Random();

        for (int i = 1; i <= quantity; i++) {
            data[i - 1] = String.join(",", String.valueOf(i), "Toy" + i, String.valueOf(r.nextInt(9) + 1));
        }

        try (Writer writer = Files.newBufferedWriter(Paths.get("data.csv"), StandardCharsets.UTF_8)) {
            writer.write(String.join("\n", data) + '\n');
        } catch (IOException e) {
            System.out.println("IO exception in method PrisesGen");
            e.printStackTrace();
        }
    }
}