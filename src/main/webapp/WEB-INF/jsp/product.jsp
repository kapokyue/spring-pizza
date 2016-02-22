<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="categorys" value="${requestScope.categorys}"/>
<c:set var="categoryActiveId" value="${requestScope.categoryActiveId}"/>
<c:set var="categoryBaseUrl" value="${requestScope.categoryBaseUrl}"/>
<c:set var="products" value="${requestScope.products}"/>
<c:set var="cartProducts" value="${requestScope.cartProducts}"/>

<page:base title="${requestScope.title}" activeNavId="productList">
  <!-- Searching Form-->
  <div>
    <form action="/product/search" method="get" target="_self"
          class="form-inline"
          style="float: none !important;text-align: center;">
      <div class="form-group">
        <label for="search-name">Searching Product</label>
        <input id="search-name" class="form-control" type="text" name="name"/>
      </div>

      <div class="form-group">
        <label for="sorter">Sort:</label>
        <select id="sorter" name="sorter" class="form-control">
          <option value=""></option>
          <option value="name-asc">Name - Start from character a</option>
          <option value="price-desc">Price - Start from higher price</option>
          <option value="price-asc">Price - Start from lower price</option>
        </select>
      </div>

      <input class="btn btn-primary" type="submit" value="Search"/>
    </form>
  </div>
  <!-- End Searching Form-->

  <hr/>

  <form:form id="delete-form" method="delete"/>
  <form:form id="cartAdd-form" method="post" action="/cart">
    <input type="hidden" id="cartAdd-form-input">
  </form:form>
  <script>
    function deleteAction( action ) {
      var deleteForm = document.getElementById( 'delete-form' );
      deleteForm.action = action;
      confirm( 'Are you sure?' ) && deleteForm.submit();
    }
    function cartAdd( $productId ) {
      var postForm = document.getElementById( 'cartAdd-form' ),
          input    = document.getElementById( 'cartAdd-form-input' );
      input.name = 'productId';
      input.value = $productId;
      postForm.submit();
    }
  </script>

  <!-- Catalog Content -->
  <div class="row">
    <div class="col-md-3">
      <h2 class="lead">Menu</h2>

      <ul class="list-group">
        <li class="list-group-item" id="category0">
          <a href="${categoryBaseUrl}" target="_self">All</a>
        </li>

        <c:forEach items="${categorys}" var="c">
          <s:url value="${categoryBaseUrl}" var="cUrl">
            <s:param name="cid" value="${c.id}"/>
          </s:url>
          <li class="list-group-item f center space-between" id="category${c.id}">
            <a href="${cUrl}" target="_self">${c.name}</a>
            <sec:authorize access="hasRole('ROLE_MANAGER')">
              <div class="f">
                <a href="/category/${c.id}/edit" class="pull-right" style="margin-right: 5px">
                  <button class="btn btn-default btn-sm">Edit</button>
                </a>
                <button class="btn btn-danger btn-sm"
                        onclick="deleteAction('/category/${c.id}')">
                  Delete
                </button>
              </div>
            </sec:authorize>
          </li>
        </c:forEach>
      </ul>
      <script>
        document.getElementById( "category${categoryActiveId}" ).classList.add( "active" );
      </script>
    </div>

    <div class="col-md-9">
      <div class="row carousel-holder">
        <div class="row">
          <c:choose>
            <c:when test="${fn:length(products) gt 0}">
              <c:forEach items="${products}" var="p">
                <div class="col-sm-4 col-lg-4 col-md-4">
                  <div class="thumbnail">
                    <img src="${p.img}" alt="${p.name}" style="width:320px;height:200px">
                    <div style="padding: 10px">
                      <div>
                        <h4><a href="javascript:void(0)">${p.name}</a></h4>
                        <h4 class="pull-right">$${p.price}</h4>

                        <p>${p.description}</p>
                      </div>
                      <sec:authorize access="isAuthenticated()">
                        <hr>
                        <sec:authorize access="hasRole('ROLE_CUSTOMER')">
                          <c:choose>
                            <c:when test="${cartProducts[p.id] != null}">
                              <div class="text-right">
                                <button class="btn btn-danger" onclick="deleteAction('/cart/${p.id}')">
                                  Delete from Shopping Cart
                                </button>
                              </div>
                            </c:when>
                            <c:otherwise>
                              <div class="text-right">
                                <button class="btn btn-default" onclick="cartAdd(${p.id})">
                                  Add to Shopping Cart
                                </button>
                              </div>
                            </c:otherwise>
                          </c:choose>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_MANAGER')">
                          <div class="f flex-end">
                            <a href="/product/${p.id}/edit" style="margin-right: 5px">
                              <button class="btn btn-default">Edit</button>
                            </a>
                            <button class="btn btn-danger"
                                    onclick="deleteAction('/product/${p.id}')">
                              Delete
                            </button>
                          </div>
                        </sec:authorize>
                      </sec:authorize>
                    </div>
                  </div>
                </div>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <p>No Product is found.</p>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div>
</page:base>