package OOP.Solution;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

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
        p1.getFriends().add(p2);
        p2.getFriends().add(p1);
        return this;
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByRating(Profesor p) throws Profesor.ProfesorNotInSystemException {
        return null;
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByDist(Profesor p) throws Profesor.ProfesorNotInSystemException {
        return null;
    }

    @Override
    public boolean getRecommendation(Profesor p, CasaDeBurrito c, int t) throws Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, ImpossibleConnectionException {
        return false;
    }

    @Override
    public List<Integer> getMostPopularRestaurantsIds() {
        return null;
    }
}
