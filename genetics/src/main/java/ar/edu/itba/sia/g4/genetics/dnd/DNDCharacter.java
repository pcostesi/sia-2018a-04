package ar.edu.itba.sia.g4.genetics.dnd;

import ar.edu.itba.sia.g4.genetics.problem.Species;

import java.util.Arrays;

public class DNDCharacter implements Species {
    private final Profession profession;
    private final Item[] items;
    private final double height;

    private final double atk;
    private final double def;


    public DNDCharacter(Profession profession, Item helmet, Item weapon, Item chestplate, Item gauntlets, Item boots,
                        double height) {
        this.items = new Item[] { helmet, weapon, chestplate, gauntlets, boots };
        this.height = height;
        this.profession = profession;

        double ATM = getATM(height);
        double DEM = getDEM(height);

        double str = computeStr();
        double agi = computeAgi();
        double exp = computeExp();
        double res = computeRes();
        double hp = computeHp();

        atk = (agi + exp) * str * ATM;
        def = (res + exp) * hp * DEM;
    }

    @Override
    public double getFitness() {
        assert getStatBias() >= 0 && getStatBias() <= 1;
        return getStatBias() * atk + (1 - getStatBias()) * def;
    }

    @Override
    public Object deepCopy() {
        return new DNDCharacter(this.profession, this.items[0], this.items[1], this.items[2], this.items[3], this.items[4], this.height);
    }

    /**
     * Split between attack and def., with 0 <= bias <= 1. The
     * bias represents the percentage of attack and the complement
     * of the def.
     * @return
     */
    private double getStatBias() {
        return this.profession.getBias();
    }

    private double computeStr() {
        double sumStr = Arrays.stream(items).mapToDouble(Item::getStr).sum();
        return 100 * Math.tanh(0.01 * sumStr * profession.getStr());
    }

    private double computeAgi() {
        double sumAgi = Arrays.stream(items).mapToDouble(Item::getAgi).sum();
        return Math.tanh(0.01 * sumAgi * profession.getAgi());
    }

    private double computeExp() {
        double sumExp = Arrays.stream(items).mapToDouble(Item::getExp).sum();
        return 0.6 * Math.tanh(0.01 * sumExp * profession.getExp());
    }

    private double computeRes() {
        double sumRes = Arrays.stream(items).mapToDouble(Item::getRes).sum();
        return Math.tanh(0.01 * sumRes * profession.getRes());
    }

    private double computeHp() {
        double sumHp = Arrays.stream(items).mapToDouble(Item::getHp).sum();
        return 100 * Math.tanh(0.01 * sumHp * profession.getHp());
    }

    private static double getATM(double height) {
        return 0.5 - Math.pow(3. * height - 5, 4) + Math.pow(3. * height - 5, 2) + height * 0.5;
    }

    private static double getDEM(double height) {
        return 2. + Math.pow(3. * height - 5, 4) - Math.pow(3. * height - 5, 2) - height * 0.5;
    }

    public Item[] getItems() {
        return items;
    }

    public Profession getProfession() {
        return profession;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DNDCharacter that = (DNDCharacter) o;

        if (Double.compare(that.height, height) != 0) {
            return false;
        }
        if (profession != that.profession) {
            return false;
        }
        return Arrays.deepEquals(items, that.items);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = profession.hashCode();
        result = 31 * result + Arrays.deepHashCode(items);
        temp = Double.doubleToLongBits(height);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        double str = computeStr();
        double agi = computeAgi();
        double exp = computeExp();
        double res = computeRes();
        double hp = computeHp();

        return String.format(
          "Character %s <str %.2f, agi %.2f, exp %.2f, res %.2f, hp %.2f>\n" +
           "               Class: \t%s\n" +
           "       !       Height \t%f\n" +
           "      .-.      atk: %f \tdef: %f\n" +
           "    __|=|__    HELMET \t%s \n" +
           "   (_/`-`\\_)   \n" +
           "   //\\___/\\\\   WEAPON \t%s \n" +
           "   <>/   \\<>   CHEST. \t%s\n" +
           "    \\|_._|/    GAUNT. \t%s\n" +
           "     <_I_>     \n" +
           "      |||      BOOTS \t%s \n" +
           "     /_|_\\     Fitness: %f\n" +
           "",
         this.hashCode(),
         str, agi, exp, res, hp,
         profession.toString(), height, atk, def,
         items[0], items[1], items[2], items[3], items[4],
         getFitness());
    }

    public Object[] getChromosome() {
        return new Object[]{this.items[0], this.items[1], this.items[2], this.items[3], this.items[4], this.height};
    }
}
