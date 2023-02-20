public class ToyParam {
    final int id;
    final String name;
    final int chance;

    public ToyParam(int id, String name, int chanceValue) {
        this.id = id;
        this.name = name;
        this.chance = chanceValue;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getChance() {
        return chance;
    }

    public String toString() {
        return String.join(",", String.valueOf(this.id), this.name, String.valueOf(this.chance));
    }
}
