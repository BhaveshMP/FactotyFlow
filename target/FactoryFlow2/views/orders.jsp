<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.FM.Entities.Register" %>
    
<!DOCTYPE html>
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="css/styles.css">
<script type="text/javascript" src="css/bootstrap.min.js"></script>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<script>
function populateForm(id, name, description,price, qty) {
	console.log( name,id, description,price, qty);
	document.getElementById("id").value = id;

	 document.getElementById('pid').style.display = 'block';
	var pid = document.getElementById("id"); 
    if (pid) {
        pid.readOnly = true;
        pid.type = "text";
    }
    document.getElementById("submitButton").innerText = "Update";

    
	document.getElementById("name").value = name;
	document.getElementById("description").value = description;
	document.getElementById("price").value = price;
	document.getElementById("quantity").value = qty;
	}

</script>
</head>
<body>
<br>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">FactoryFlow</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="index.jsp">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="productList.jsp">Products</a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Dropdown
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li><a class="dropdown-item" href="#">Action</a></li>
            <li><a class="dropdown-item" href="#">Another action</a></li>
            <li><hr class="dropdown-divider"></li>
            <li><a class="dropdown-item" href="#">Something else here</a></li>
          </ul>
        </li>
        <li class="nav-item">
          <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
        </li>
      </ul>

    </div>
    		<a href="views/register.jsp?type=update"
			class="btn btn-outline-light" style="width: 40px;" >
			<b>
<%= (session.getAttribute("user") != null) ? Character.toUpperCase(((Register) session.getAttribute("user")).getName().charAt(0)) : "" %>
			 </b>

 
        </a>
  </div>
</nav>
<hr>



<hr>
<div class="container-xl">
<table class="table table-dark table-hover">
  <thead>
    <tr>
      <th scope="col">Oid</th>
      <th scope="col">Customer Id</th>
      <th scope="col">Date</th>
      <th scope="col">Status</th>
       <th scope="col">Quantity</th>
        <th scope="col">Total Amount</th>
         <th scope="col">Shipping Address</th>

    </tr>
  </thead>
  <tbody>
  
 <c:forEach var="order" items="${OrderList}">
            <tr>
                <td>${order.orderId}</td>
                <td>${order.register.name}</td>
                <td>${order.orderDate}</td>
                <td>${order.status}</td>
                <td>${order.qtyOrder}</td>
                 <td>${order.totalAmount}</td>
                  <td>${order.shippingAddress}</td>
                
                <%-- <td><a href="../ProductServlet?action=delete&productId=${product.id}"><button class="btn btn-outline-light">Delete</button></a></td> --%>
                
    </tr>
     </c:forEach>
     <tbody>
     </table>
    
</div>

</body>
</html>