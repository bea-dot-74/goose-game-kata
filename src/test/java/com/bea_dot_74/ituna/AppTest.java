package com.bea_dot_74.ituna;

import junit.framework.TestCase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    public void testApp() {
        SessionFactory sessionFactory = new Configuration().configure()
            .buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

      Player user = new Player();
      user.setName("Test player");
      user.setPosizione(0);
        session.save(user);

        session.getTransaction().commit();
        session.close();
    }
}
