package OOP.Solution;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

public class ProfesorImpl implements Profesor {
    public ProfesorImpl(int id, String name){

    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public Profesor favorite(CasaDeBurrito c) throws UnratedFavoriteCasaDeBurritoException {
        return null;
    }

    @Override
    public Collection<CasaDeBurrito> favorites() {
        return null;
    }

    @Override
    public Profesor addFriend(Profesor p) throws SameProfesorException, ConnectionAlreadyExistsException {
        return null;
    }

    @Override
    public Set<Profesor> getFriends() {
        return null;
    }

    @Override
    public Set<Profesor> filteredFriends(Predicate<Profesor> p) {
        return null;
    }

    @Override
    public Collection<CasaDeBurrito> filterAndSortFavorites(Comparator<CasaDeBurrito> comp, Predicate<CasaDeBurrito> p) {
        return null;
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByRating(int rLimit) {
        return null;
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByDist(int dLimit) {
        return null;
    }

    @Override
    public int compareTo(Profesor o) {
        return 0;
    }
}
