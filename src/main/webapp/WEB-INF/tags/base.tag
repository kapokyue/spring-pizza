<%@ tag description="Base Template" pageEncoding="UTF-8" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="head" fragment="true" %>

<%@ attribute name="title" required="true" %>
<%@ attribute name="activeNavId" %>

<c:set var="errors" value="${requestScope.errors}"/>
<c:set var="succs" value="${requestScope.succs}"/>

<!doctype html>
<html>
<head>
  <title>${title} - Spring Pizza</title>
  <%-- jQuery --%>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js" type="text/javascript"></script>
  <%-- Bootstrap --%>
  <script src="https://cdn.jsdelivr.net/bootstrap/3.3.6/js/bootstrap.min.js" type="text/javascript"></script>
  <link href="https://cdn.jsdelivr.net/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
  <%-- My CSS --%>
  <link href="/css/shop.css" rel="stylesheet">
  <jsp:invoke fragment="head"/>
</head>
<body>
<nav class="navbar navbar-default" role="navigation" style="position: fixed; width: 100%; z-index: 99; top: 0;">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">Spring Pizza</a>
    </div>

    <%-- Collect the nav links, forms, and other content for toggling --%>
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li id="productList"><a href="/product">Product List</a></li>
        <li id="aboutUs"><a href="/about-us">About Us</a></li>
      </ul>

      <ul class="nav navbar-nav navbar-right">
        <sec:authorize access="isAuthenticated()" var="isAuthenticated"/>
        <c:choose>
          <c:when test="${isAuthenticated}">
            <sec:authorize access="hasRole('ROLE_CUSTOMER')">
              <li id="cart"><a href="/cart">My Shopping Cart</a></li>
              <li id="orderHistory"><a href="/order/history">Orders History</a></li>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_MANAGER')" var="isManager">
              <li id="viewOrder"><a href="/order">View Orders</a></li>
              <li id="orderHistory"><a href="/order/all">All Orders History</a></li>
              <li id="addProduct"><a href="/product/create">Add Product</a></li>
              <li id="addCategory"><a href="/category/create">Add Category</a></li>
            </sec:authorize>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button">
                <sec:authentication property="name"/>
                <c:if test="${isManager}">(Manager)</c:if>
                <span class="caret"></span>
              </a>
              <ul class="dropdown-menu" role="menu">
                <li><a href="/profile">Edit Profile</a></li>
                <li>
                  <form:form id="logout-form" method="post" action="/logout"/>
                  <a href="#" onclick="document.getElementById('logout-form').submit()">Logout</a>
                </li>
              </ul>
            </li>
          </c:when>
          <c:otherwise>
            <li id="register">
              <a href="/register">Register</a>
            </li>

            <li id="login">
              <a href="/login">Login</a>
            </li>
          </c:otherwise>
        </c:choose>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
  <c:if test="${activeNavId != null}">
    <script>
      document.getElementById( "${activeNavId}" ).classList.add( "active" );
    </script>
  </c:if>
</nav>
<div class="container">
  <noscript>
    <div class="alert alert-danger">
      JavaScrip is required
    </div>
  </noscript>
  <c:if test="${fn:length(succs) > 0}">
    <div class="alert alert-success">
      <ul>
        <c:forEach items="${succs}" var="succ">
          <li>${succ}</li>
        </c:forEach>
      </ul>
    </div>
  </c:if>
  <c:if test="${fn:length(errors) > 0}">
    <div class="alert alert-danger">
      <ul>
        <c:forEach items="${errors}" var="error">
          <li>${error}</li>
        </c:forEach>
      </ul>
    </div>
  </c:if>
  <jsp:doBody/>
</div>
</body>
</html>
