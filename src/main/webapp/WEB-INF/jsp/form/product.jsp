<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="product" value="${requestScope.product}"/>
<c:set var="categorys" value="${requestScope.categorys}"/>

<c:set var="title" value="Add"/>
<c:set var="action" value="/product/add"/>
<c:set var="activeNavId" value="addProduct"/>
<c:if test="${product.id != 0}">
  <c:set var="title" value="Edit"/>
  <c:set var="action" value="/product/${product.id}/update"/>
  <c:set var="activeNavId" value="${null}"/>
</c:if>

<page:base title="${title} Product" activeNavId="${activeNavId}">
  <form:form method="post" action="${action}" modelAttribute="product"
             cssClass="form-horizontal" enctype="multipart/form-data">
    <div class="form-group">
      <label for="name" class="col-sm-offset-2 col-sm-2 control-label">Name</label>

      <div class="col-sm-4">
        <form:input id="name" type="text" name="name"
                    class="form-control" placeholder="Name" path="name"/>
      </div>
    </div>

    <div class="form-group">
      <label for="price" class="col-sm-offset-2 col-sm-2 control-label">Price</label>

      <div class="col-sm-4">
        <form:input id="price" type="text" name="price"
                    class="form-control" placeholder="Price" path="price"/>
      </div>
    </div>

    <div class="form-group">
      <label for="category" class="col-sm-offset-2 col-sm-2 control-label">Category:</label>

      <div class="col-sm-4">
        <form:select id="category" class="form-control" path="category.id" items="${categorys}"/>
      </div>
    </div>

    <div class="form-group">
      <label class="col-sm-offset-2 col-sm-2 control-label">Image Option</label>

      <div class="col-sm-4">
        <label>Url:<input name="img-type" type="radio" onclick="useUrl()" checked></label>
        <label>Upload:<input name="img-type" type="radio" onclick="useUpload()"></label>
      </div>
    </div>

    <div class="form-group">
      <label for="img" class="col-sm-offset-2 col-sm-2 control-label">Image</label>

      <div class="col-sm-4">
        <form:input id="img" class="form-control"
                    placeholder="Image" path="img" accept="image/*"/>
      </div>
    </div>

    <script>
      var imgInput = document.getElementById( 'img' );
      function useUrl() {
        imgInput.type = "text";
        imgInput.name = "imgUrl";
      }
      function useUpload() {
        imgInput.type = "file";
        imgInput.name = "imgFile";
      }
      useUrl();
    </script>

    <div class="form-group">
      <label for="description" class="col-sm-offset-2 col-sm-2 control-label">Description</label>

      <div class="col-sm-4">
        <form:textarea id="description" name="description" class="form-control" rows="3"
                       placeholder="Description" path="description"/>
      </div>
    </div>

    <div class="form-group">
      <div class="col-sm-offset-7 col-sm-1" style="text-align: center;">
        <button type="submit" class="btn btn-default">Submit</button>
      </div>
    </div>
  </form:form>
</page:base>