package com.pnlinh.cuasotinhyeu.crawler.db;

import com.pnlinh.cuasotinhyeu.crawler.model.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class DbOperations {
    private static volatile SessionFactory mSessionFactory;

    private static SessionFactory getSessionFactory() {
        if (mSessionFactory == null) {
            synchronized (DbOperations.class) {
                if (mSessionFactory == null)
                    mSessionFactory = createSessionFactory();
            }
        }
        return mSessionFactory;
    }

    private static SessionFactory createSessionFactory() {
        Configuration configObj = new Configuration();
        configObj.configure("hibernate.cfg.xml");
        return configObj.buildSessionFactory();
    }

    public static void createRecord(Post post) {
        Session sessionObj = getSessionFactory().openSession();
        Transaction transObj = sessionObj.beginTransaction();
        sessionObj.save(post);
        transObj.commit();
        sessionObj.close();
    }


}
