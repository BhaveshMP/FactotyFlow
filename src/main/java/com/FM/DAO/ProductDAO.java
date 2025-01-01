package com.FM.DAO;

import java.util.List;
import org.hibernate.*;
import org.hibernate.query.Query;

import com.FM.HibernateUtil;
import com.FM.Entities.Order;
import com.FM.Entities.Product;

public class ProductDAO {
	
    public int saveProduct(Product product) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Integer productId = (Integer) session.save(product);
        transaction.commit();
        session.close();
        
        return productId;
        
        
    }

    public Product getProductById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Product.class, id);
        }
    }
    
    public List<Product> getAllProducts() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Product> products = session.createQuery("from Product", Product.class).list();
        session.close();
        return products;
    }
    
    public void deleteProduct(int productId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        
        // Retrieve the product from the database
        Product product = session.get(Product.class, productId);
        if (product != null) {
            session.delete(product); // Delete the product
        }
        
        transaction.commit();
        session.close();
    }
    
    public void updateProduct(Product product) {
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	Transaction transaction = session.beginTransaction();
            
            // Update the product
            session.update(product);
            
            transaction.commit();
            session.close();
    }
    
    public int getLastSavedProductId() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Product ORDER BY id DESC");
            query.setMaxResults(1); // Retrieve only the last saved product
            Product product = (Product) query.getSingleResult();
            return product.getId();
        }
    }

}
