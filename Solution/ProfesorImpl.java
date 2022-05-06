package OOP.Solution;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProfesorImpl implements Profesor {
    private int id;
    private String name;
    private Set<CasaDeBurrito> favorites;
    private Set<Profesor> friends;

    public ProfesorImpl(int id, String name) {
        this.id = id;
        this.name = name;
        this.favorites = new HashSet<>();
        this.friends = new HashSet<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Profesor favorite(CasaDeBurrito c) throws UnratedFavoriteCasaDeBurritoException {
        if (!c.isRatedBy(this)) {
            throw new UnratedFavoriteCasaDeBurritoException();
        }
        this.favorites.add(c);
        return this;
    }

    @Override
    public Collection<CasaDeBurrito> favorites() {
        return new HashSet<>(this.favorites);
    }

    @Override
    public Profesor addFriend(Profesor p) throws SameProfesorException, ConnectionAlreadyExistsException {
        if (this.equals(p)) throw new SameProfesorException();
        if (this.friends.contains(p)) throw new ConnectionAlreadyExistsException();
        this.friends.add(p);
        return this;
    }

    @Override
    public Set<Profesor> getFriends() {
        return new HashSet<>(this.friends);
    }

    @Override
    public Set<Profesor> filteredFriends(Predicate<Profesor> p) {
        return this.friends.stream().filter(p).collect(Collectors.toSet());

    }

    @Override
    public Collection<CasaDeBurrito> filterAndSortFavorites(Comparator<CasaDeBurrito> comp, Predicate<CasaDeBurrito> p) {
        return this.favorites.stream().filter(p).sorted(comp).collect(Collectors.toList());
    }

    private static Comparator<CasaDeBurrito> CompareByRating = (CasaDeBurrito c1, CasaDeBurrito c2) -> {
        double byAverageRating = c2.averageRating() - c1.averageRating();
        if (byAverageRating == 0) {
            int byDistance = c1.distance() - c2.distance();
            if (byDistance == 0) {
                return c1.getId() - c2.getId();
            }
            return byDistance;
        }
        if (byAverageRating > 0) return 1;
        return -1;
    };
    @Override
    public Collection<CasaDeBurrito> favoritesByRating(int r) {
        return this.filterAndSortFavorites(CompareByRating , (CasaDeBurrito c) -> c.averageRating() >= r);
    }
    private static Comparator<CasaDeBurrito> CompareByDistance = (CasaDeBurrito c1, CasaDeBurrito c2) -> {
        int byDistance = c1.distance() - c2.distance();
        if (byDistance == 0) {
            double byAverageRating = c2.averageRating() - c1.averageRating();
            if (byAverageRating == 0) {
                return c1.getId() - c2.getId();
            }
            if (byAverageRating > 0) return 1;
            return -1;
        }
        return byDistance;
    };

    @Override
    public Collection<CasaDeBurrito> favoritesByDist(int r) {
        return this.filterAndSortFavorites(CompareByDistance , (CasaDeBurrito c) -> c.distance() <= r);
    }

    @Override
    public int compareTo(Profesor o) {
        return this.getId() - o.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Profesor)) return false;
        return ((Profesor)o).getId() == this.getId();
    }


    @Override
    public String toString() {
        return "Profesor: " + this.name + ".\n" +
                "Id: " + this.id + ".\n" +
                "Favorites: " + this.favorites().stream().map(CasaDeBurrito::getName).sorted().collect(Collectors.joining(", ")) + ".";

    }
}
