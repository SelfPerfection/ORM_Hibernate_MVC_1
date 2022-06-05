package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CarDaoImp implements CarDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(Car car) {
        sessionFactory.getCurrentSession().save(car);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Car> listCars() {
        TypedQuery<Car> query = sessionFactory.getCurrentSession().createQuery("from Car");
        return query.getResultList();
    }

    @Override
    public List<User> getUsersByCar(String model, int series) {
        List<User> usersByCar = new ArrayList<>();

        String hql = "FROM User as user INNER JOIN user.car as car WHERE car.series = :series AND car.model =:model";
        List<?> result = sessionFactory.getCurrentSession().createQuery(hql)
                .setParameter("model", model)
                .setParameter("series", series)
                .list();

        for (int i = 0; i < result.size(); i++) {
            Object[] row = (Object[]) result.get(i);
            usersByCar.add((User) row[0]);
        }
        return usersByCar;
    }
}