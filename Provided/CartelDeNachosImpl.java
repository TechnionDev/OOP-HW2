package OOP.Solution;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import java.util.*;

public class CartelDeNachosImpl implements CartelDeNachos {
    private Set<Profesor> setProfessor;
    private Set<CasaDeBurrito> setRestaurants;

    public CartelDeNachosImpl() {
        this.setProfessor = new HashSet<>();
        this.setRestaurants = new HashSet<>();
    }

    @Override
    public Profesor joinCartel(int id, String name) throws Profesor.ProfesorAlreadyInSystemException {
        Profesor p = new ProfesorImpl(id, name);
        if (this.setProfessor.contains(p)) throw new Profesor.ProfesorAlreadyInSystemException();
        this.setProfessor.add(p);
        return p;
    }

    @Override
    public CasaDeBurrito addCasaDeBurrito(int id, String name, int dist, Set<String> menu) throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException {
        CasaDeBurrito c = new CasaDeBurritoImpl(id, name, dist, menu);
        if (this.setRestaurants.contains(c)) throw new CasaDeBurrito.CasaDeBurritoAlreadyInSystemException();
        this.setRestaurants.add(c);
        return c;
    }

    @Override
    public Collection<Profesor> registeredProfesores() {
        return new HashSet<>(this.setProfessor); //TODO:: check if the new set is independent from the original one.
    }

    @Override
    public Collection<CasaDeBurrito> registeredCasasDeBurrito() {
        return new HashSet<>(this.setRestaurants); //TODO:: check if the new set is independent from the original one.
    }

    @Override
    public Profesor getProfesor(int id) throws Profesor.ProfesorNotInSystemException {
        for (Profesor p : this.setProfessor) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new Profesor.ProfesorNotInSystemException();
    }

    @Override
    public CasaDeBurrito getCasaDeBurrito(int id) throws CasaDeBurrito.CasaDeBurritoNotInSystemException {
        for (CasaDeBurrito c : this.setRestaurants) {
            if (c.getId() == id) {
                return c;
            }
        }
        throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
    }

    @Override
    public CartelDeNachos addConnection(Profesor p1, Profesor p2) throws Profesor.ProfesorNotInSystemException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {

        if (p1.getId() == p2.getId()) {
            throw new Profesor.SameProfesorException();
        }
        try {
            this.getProfesor(p1.getId());
            this.getProfesor(p2.getId());
        } catch (Profesor.ProfesorNotInSystemException e) {
            throw new Profesor.ProfesorNotInSystemException();
        }
        if (p1.getFriends().contains(p2)) {
            throw new Profesor.ConnectionAlreadyExistsException();
        }
        //each professor has a set of friends, so all we need to do is to add p1 & p2 to both of their friend set's
        p1.addFriend(p2);
        p2.addFriend(p1);
        return this;
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByRating(Profesor p) throws Profesor.ProfesorNotInSystemException {
        try{
            this.getProfesor(p.getId());
        } catch (Profesor.ProfesorNotInSystemException e) {
            throw new Profesor.ProfesorNotInSystemException();
        }
        List<CasaDeBurrito> favorites = new LinkedList<>();
        for (Profesor f : p.getFriends().stream().sorted().toList()) {
            favorites.addAll(f.favoritesByRating(0));
        }
        return favorites.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByDist(Profesor p) throws Profesor.ProfesorNotInSystemException {
        try{
            this.getProfesor(p.getId());
        } catch (Profesor.ProfesorNotInSystemException e) {
            throw new Profesor.ProfesorNotInSystemException();
        }
        List<CasaDeBurrito> favorites = new LinkedList<>();
        for (Profesor p1 : p.getFriends().stream().sorted().toList()) {
            favorites.addAll(p1.favoritesByDist(Integer.MAX_VALUE));
        }
        return favorites.stream().distinct().collect(Collectors.toList());
    }
        private static boolean getRecommendationAux(Profesor p, CasaDeBurrito c, int t) {

            if (t < 0) return false;
            if (p.favorites().contains(c)) return true;
            if (t == 0) return false;
            for (Profesor p1 : p.getFriends()) {
                if (getRecommendationAux(p1, c, t - 1)) return true;
        }
        return false;
    }
    @Override
    public boolean getRecommendation(Profesor p, CasaDeBurrito c, int t) throws Profesor.ProfesorNotInSystemException,
    CasaDeBurrito.CasaDeBurritoNotInSystemException, ImpossibleConnectionException {
        try{
            this.getProfesor(p.getId());
        } catch (Profesor.ProfesorNotInSystemException e) {
            throw new Profesor.ProfesorNotInSystemException();
        }
        try{
            this.getCasaDeBurrito(c.getId());
        } catch (CasaDeBurrito.CasaDeBurritoNotInSystemException e) {
            throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
        }
        if (t < 0) throw new ImpossibleConnectionException();
        return getRecommendationAux(p, c, t);
    }




    @Override
    public List<Integer> getMostPopularRestaurantsIds() {
        Map<Integer, Integer> res = new Hashtable<>();
        setRestaurants.forEach(c -> res.put(c.getId(), 0));
       // int max = 0;
        for (Profesor p : setProfessor) {
            for (Profesor curr : p.getFriends()) {
                for (CasaDeBurrito c : curr.favorites()) {
                    res.replace(c.getId(), res.get(c.getId()) + 1);
                   // if(res.get(c.getId()) + 1 > max ) max = res.get(c.getId()) + 1;
                }
            }
        }
        int max = res.values().stream().reduce(0, Integer::max);
        List<Map.Entry<Integer, Integer>> final_res = new LinkedList<>(res.entrySet());
        return final_res.stream()
                .filter(entry -> entry.getValue() == max)
                .map(Map.Entry::getKey).sorted().collect(Collectors.toList());

    }

    public String toString() {
        StringBuilder res = new StringBuilder("Registered profesores: ");
        res.append(setProfessor
                .stream()
                .map(Profesor::getId)
                .sorted()
                .map(Object::toString)
                .collect(Collectors.joining(", ")));
        res.append(".\nRegistered casas de burrito: ");
        res.append(setRestaurants
                .stream()
                .map(CasaDeBurrito::getId)
                .sorted()
                .map(Object::toString)
                .collect(Collectors.joining(", ")));
        res.append(".\nProfesores:\n");
        for (Profesor p : setProfessor) {
            res.append(p.getId()).append(" -> [");
            res.append(p.getFriends()
                    .stream()
                    .map(Profesor::getId)
                    .sorted()
                    .map(Object::toString)
                    .collect(Collectors.joining(", ")));
            res.append("].\n");
        }
        res.append("End profesores.");
        return res.toString();
    }
}
