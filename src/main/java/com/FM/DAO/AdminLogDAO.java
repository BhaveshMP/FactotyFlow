package com.FM.DAO;

import java.util.List;
import org.hibernate.*;
import com.FM.HibernateUtil;
import com.FM.Entities.Product;
import com.FM.Entities.AdminLog;
import com.FM.Entities.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AdminLogDAO {

   
    public List<AdminLog> fetchPendingAdminLogs() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from AdminLog", AdminLog.class).list();
        }
    	
    }
    
    public void saveAdminLog(AdminLog log) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(log);
        transaction.commit();
        session.close();
    }
    
}