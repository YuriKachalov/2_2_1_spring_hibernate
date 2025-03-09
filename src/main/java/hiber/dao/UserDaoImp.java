package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    @Transactional
    public List<User> getUser(Car car) {
        String hql = "SELECT u FROM User u JOIN u.car c WHERE c.model = :modelCar OR c.series = :seriesCar";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("modelCar", car.getModel());
        query.setParameter("seriesCar", car.getSeries());
        List<User> list = query.getResultList();
        for (User user : list) {
            System.out.println(user);
        }
        return list;
    }

}
