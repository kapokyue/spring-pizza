<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="cookingOrders" value="${requestScope.cookingOrders}"/>
<c:set var="deliveringOrder" value="${requestScope.deliveringOrder}"/>

<page:base title="Customers' Order" activeNavId="orderHistory">
  <form:form id="deliver-form" method="put">
    <input type="hidden" name="status" value="Delivering">
  </form:form>
  <form:form id="done-form" method="put">
    <input type="hidden" name="status" value="Done">
  </form:form>
  <script>
    function deliver( oid ) {
      jQuery( '#deliver-form' ).attr( 'action', '/order/' + oid + '/status' ).submit();
    }
    function done( oid ) {
      jQuery( '#done-form' ).attr( 'action', '/order/' + oid + '/status' ).submit();
    }
  </script>
  <h3>Cooking</h3>
  <table class="table table-bordered table-hover">
    <tr>
      <th>Order ID</th>
      <th>Customer</th>
      <th>Address</th>
      <th>Ordered Product</th>
      <th>Charge</th>
      <th>Order At</th>
      <th></th>
    </tr>
    <c:forEach items="${cookingOrders}" var="o">
      <tr>
        <td>${o.id}</td>
        <td>${o.username}</td>
        <td>${o.address}</td>
        <td>${o.items}</td>
        <td>${o.charge}</td>
        <td>${o.orderAt}</td>
        <td>${o.status}</td>
        <td>
          <button class="btn btn-primary" onclick="deliver(${o.id})">
            Deliver
          </button>
        </td>
      </tr>
    </c:forEach>
  </table>

  <h3>Delivering</h3>
  <table class="table table-bordered table-hover">
    <tr>
      <th>Order ID</th>
      <th>Customer</th>
      <th>Address</th>
      <th>Ordered Product</th>
      <th>Charge</th>
      <th>Order At</th>
      <th></th>
    </tr>
    <c:forEach items="${deliveringOrder}" var="o">
      <tr>
        <td>${o.id}</td>
        <td>${o.username}</td>
        <td>${o.address}</td>
        <td>${o.items}</td>
        <td>${o.charge}</td>
        <td>${o.orderAt}</td>
        <td>${o.status}</td>
        <td>
          <button class="btn btn-success" onclick="done(${o.id})">
            Done
          </button>
        </td>
      </tr>
    </c:forEach>
  </table>
</page:base>