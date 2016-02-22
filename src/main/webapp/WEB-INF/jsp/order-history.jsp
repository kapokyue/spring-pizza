<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orders" value="${requestScope.orders}"/>

<page:base title="Order History" activeNavId="orderHistory">
  <table class="table table-bordered table-hover">
    <tr>
      <th>Order ID</th>
      <th>Customer</th>
      <th>Address</th>
      <th>Ordered Product</th>
      <th>Charge</th>
      <th>Order At</th>
      <th>Status</th>
    </tr>
    <c:forEach items="${orders}" var="o">
      <tr>
        <td>${o.id}</td>
        <td><sec:authentication property="name"/></td>
        <td>${o.address}</td>
        <td>${o.items}</td>
        <td>${o.charge}</td>
        <td>${o.orderAt}</td>
        <td>${o.status}</td>
      </tr>
    </c:forEach>
  </table>
</page:base>