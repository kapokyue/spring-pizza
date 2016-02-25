<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="products" value="${requestScope.products}"/>

<page:base title="Cart" activeNavId="cart">
  <form:form id="delete-form" method="delete"/>
  <script>
    function deleteAction( action ) {
      var deleteForm = document.getElementById( 'delete-form' );
      deleteForm.action = action;
      confirm( 'Are you sure?' ) && deleteForm.submit();
    }
  </script>

  <div style="width: 80%; margin: auto;">
    <c:choose>
      <c:when test="${fn:length(products) gt 0}">
        <div>
          <p>You have ${fn:length(products)} item(s) in your shopping cart</p>

          <form:form method="post" action="order" class="form-horizontal">
            <table class="table table-bordered table-hover">
              <tr>
                <th>Name</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Action</th>
              </tr>
              <c:forEach items="${products}" var="p">
                <tr>
                  <td>${p.name}</td>
                  <td>${p.price}</td>
                  <td>
                    <input name="qty[${p.id}]" type="text" value="1" title="quantity"
                           class="form-control qty-input" data-price="${p.price}"/>
                  </td>
                  <td>
                    <button class="btn btn-danger" style="margin-right: 5px" type="button"
                            onclick="deleteAction('/cart/${p.id}')">
                      Cancel
                    </button>
                  </td>
                </tr>
              </c:forEach>
            </table>

            Address:<br>
            <textarea name="address" class="form-control"
                      placeholder="Address"></textarea>
            Note:<br>
            <textarea name="note" class="form-control"
                      placeholder="Any note you want to say"></textarea>
            <hr>
            <p>Total Price: $<span id="total"></span></p><br/>
            <script>
              var $chargeView = jQuery( '#total' ),
                  $qtyInput = jQuery( '.qty-input' ),
                  updatePrice = function () {
                    var charge = 0;
                    $qtyInput.each( function () {
                      var price = parseFloat( jQuery( this ).data( 'price' ) ),
                          qty   = parseInt( this.value );
                      charge += price * qty;
                    } );
                    $chargeView.html( charge );
                  };
              updatePrice();
              $qtyInput.change( updatePrice );
            </script>

            <input type="submit" value="Buy" class="btn btn-success"/>
            <button onclick="history.go( -1 );" class="btn btn-default">Back</button>
          </form:form>
        </div>
      </c:when>
      <c:otherwise>
        <div>
          <p>You have no product in shopping cart</p>
        </div>
      </c:otherwise>
    </c:choose>
  </div>
</page:base>