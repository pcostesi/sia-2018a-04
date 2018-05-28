package ar.edu.itba.sia.g4.genetics.dnd.crossers;

import ar.edu.itba.sia.g4.genetics.dnd.DNDCharacter;
import ar.edu.itba.sia.g4.genetics.dnd.Item;
import ar.edu.itba.sia.g4.genetics.problem.Combinator;
import ar.edu.itba.sia.g4.genetics.problem.Couple;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SuppressWarnings("ALL")
public class UniformCrosser implements Combinator<DNDCharacter> {
    @Override
    public Couple<DNDCharacter> breed(Couple<DNDCharacter> couple) {
        DNDCharacter papi = couple.getHead();
        DNDCharacter mami = couple.getTail();

        Random random = new Random();
        Object[] offspring1Chromosome = papi.getChromosome();
        Object[] offspring2Chromosome = mami.getChromosome();
        IntStream.range(0, 6).parallel().forEach(i -> {
            if(random.nextDouble() >= 0.5){
                offspring1Chromosome[i] = offspring2Chromosome[i];
                offspring2Chromosome[i] = offspring1Chromosome[i];
            }
        });
        DNDCharacter offspring1  = new DNDCharacter(papi.getProfession(), (Item) offspring1Chromosome[0],
                (Item) offspring1Chromosome[1],(Item) offspring1Chromosome[2], (Item) offspring1Chromosome[3],
                (Item) offspring1Chromosome[4], (double) offspring1Chromosome[5]);
        DNDCharacter offspring2  = new DNDCharacter(mami.getProfession(), (Item) offspring2Chromosome[0],
                (Item) offspring2Chromosome[1],(Item) offspring2Chromosome[2], (Item) offspring2Chromosome[3],
                (Item) offspring2Chromosome[4], (double) offspring2Chromosome[5]);
        return new Couple<>(offspring1, offspring2);
    }

    @Override
    public boolean shouldBreed(Couple<DNDCharacter> parents, long generation) {
        return true;
    }

    @Override
    public List<Couple<DNDCharacter>> pickCouples(List<DNDCharacter> p, int couples) {
        Random random = new Random();
        return random.ints(0, p.size()).parallel()
                .mapToObj(i -> new Couple<>(p.get(i), p.get((i + 1) % p.size())))
                .limit(couples)
                .collect(Collectors.toList());
    }
}

