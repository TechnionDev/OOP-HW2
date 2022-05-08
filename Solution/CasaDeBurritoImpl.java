package OOP.Solution;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import OOP.Provided.*;

public class CasaDeBurritoImpl implements CasaDeBurrito {
    private Integer id;
    private String name;
    private Integer dist;
    private Set<String> menu;
    private Map<Profesor, Integer> rating;

    public CasaDeBurritoImpl(int id, String name, int dist, Set<String> menu) {
        this.id = id;
        this.name = name;
        this.dist = dist;
        this.menu = menu;
        this.rating = new HashMap<>();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int distance() {
        return this.dist;
    }

    @Override
    public boolean isRatedBy(Profesor p) {
        return rating.containsKey(p);
    }

    @Override
    public CasaDeBurrito rate(Profesor p, int r) throws RateRangeException {
        if (r > 5 || r < 0) throw new RateRangeException();
        this.rating.put(p, r);
        return this;
    }

    @Override
    public int numberOfRates() {
        return this.rating.size();
    }

    @Override
    public double averageRating() {
        if (this.numberOfRates() == 0) return 0;
        return ((double) this.rating.values().stream().mapToInt(Integer::intValue).sum()) / this.numberOfRates();
    }

    @Override
    public String toString() {
        return ("CasaDeBurrito: " + this.getName() + "\n" +
                "Id: " + this.getId() + "\n" +
                "Distance: " + this.distance() + "\n" +
                "Menu: " + this.menu.toString());
    }

    @Override
    public int compareTo(CasaDeBurrito o) {
        return this.getId()-o.getId();
    }
    @Override
    public int hashCode() {
       return this.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        CasaDeBurrito compCasa = (CasaDeBurrito) o;
        return compCasa.getId() == this.getId();
    }
}

