package com.FM.Servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.PrintWriter;

import java.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.FM.DAO.InventoryDAO;
import com.FM.DAO.ProductDAO;
import com.FM.Entities.Inventory;
import com.FM.Entities.Product;


/**
 * Servlet implementation class PorductServlet2
 */
@WebServlet("/ProductServlet")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2, // 2MB threshold before writing to disk
	    maxFileSize = 1024 * 1024 * 10, // 10MB maximum file size
	    maxRequestSize = 1024 * 1024 * 50 // 50MB maximum request size
	)
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductDAO productDAO = new ProductDAO();

	private InventoryDAO invDAO = new InventoryDAO();
	ReloadServlet reloadServlet;
	
    public ProductServlet() {
        super();
        reloadServlet = new ReloadServlet();
        
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	try {
//    		PrintWriter out = response.getWriter();
    	    // Code that might throw an exception
    		
    		String action="";

    	if(request.getParameter("action")!=null && !request.getParameter("action").isEmpty()) {
    		
    		action = request.getParameter("action");	

    	}
    	
        if (request.getParameter("action")!=null && "delete".equalsIgnoreCase(action)) {
            // Get productId from the request
            String productId = request.getParameter("productId");
            
            if (productId != null && !productId.isEmpty()) {
                int id = Integer.parseInt(productId);

                productDAO.deleteProduct(id);  // Call the DAO method to delete the product
                List<Product> products = productDAO.getAllProducts();

              	HttpSession session = request.getSession();
                session.setAttribute("ProductList", products);
                response.sendRedirect("views/productA.jsp");
				/* request.setAttribute("products", products); */
            }}
        else {

        List<Product> products = productDAO.getAllProducts();

      	HttpSession session = request.getSession();
      	
        session.setAttribute("ProductList", products);

        request.setAttribute("products", products);

//        request.getRequestDispatcher("views/productList.jsp").include(request, response);

        response.sendRedirect("views/productList.jsp");

        }
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
        }

    

	
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	try {
    		
    	    response.setContentType("text/plain");
    	    Part filePart = request.getPart("image"); // Get the Part (file) from the request
    	    String fileName = filePart.getSubmittedFileName().trim();
    	    fileName = fileName.replace(" ", "_");

    	    if (fileName != null && !fileName.isEmpty()) {
    	        // Process the new image
    	        String uploadDir = getServletContext().getRealPath("/uploads");
    	        System.out.println(uploadDir);
    	        File uploadDirFile = new File(uploadDir);
    	        if (!uploadDirFile.exists()) {
    	            uploadDirFile.mkdir();
    	        }
    	        File file = new File(uploadDir, fileName);
    	        try (InputStream fileContent = filePart.getInputStream()) {
    	            Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
    	        }
    	        
    	    } else {
    	        // Keep the existing file name
    	    	fileName = request.getParameter("fileName");
//    	        String existingFileName = productDAO.getProductById(Integer.parseInt(request.getParameter("id"))).getFileName();
    	    }
    	
    	
    	String name = request.getParameter("name");
    	String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int qty = Integer.parseInt(request.getParameter("qty"));
        
        
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQty(qty);
        product.setFileName(fileName); // Update the fileName field
        
        // update product / save product
        if(request.getParameter("id")!=null && !request.getParameter("id").isEmpty()) {
        	product.setId(Integer.parseInt(request.getParameter("id")));
        	productDAO.updateProduct(product);
        	
//        	List<Product> products = productDAO.getAllProducts();
//          	HttpSession session = request.getSession();
//            request.setAttribute("products", products);
            
            reloadServlet.reloadProductList(productDAO, request);
            response.sendRedirect("views/productA.jsp");
        }
        else {
        	
        int productId = productDAO.saveProduct(product);

        Inventory inv = new Inventory();
        inv.setProduct(product);
        inv.setQuantityAvailable(qty);
        inv.setType("product");
        inv.setReorderLevel(qty);
              
        InventoryDAO invDAO = new InventoryDAO();
        invDAO.saveInventory(inv);
        
        product.setInventory(inv);
        productDAO.updateProduct(product);
        
//        List<Product> products = productDAO.getAllProducts();
//      	HttpSession session = request.getSession();
//        session.setAttribute("ProductList", products);
//        request.setAttribute("products", products);
        
        reloadServlet.reloadProductList(productDAO, request);
     response.sendRedirect("views/productA.jsp");
        
        
//        RequestDispatcher dispatcher = request.getRequestDispatcher("inventory");
//        dispatcher.forward(request, response);
        }
//        response.sendRedirect("views/product.jsp");
        
    } catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
