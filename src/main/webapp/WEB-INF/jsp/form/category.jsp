<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="category" value="${requestScope.category}"/>

<c:set var="title" value="Add"/>
<c:set var="method" value="post"/>
<c:set var="action" value="/category/add"/>
<c:set var="activeNavId" value="addCategory"/>
<c:if test="${category.id != 0}">
  <c:set var="title" value="Edit"/>
  <c:set var="method" value="put"/>
  <c:set var="action" value="/category/${category.id}"/>
  <c:set var="activeNavId" value="${null}"/>
</c:if>

<page:base title="${title} Category" activeNavId="addCategory">
  <form:form method="${method}" action="${action}"
             class="form-horizontal" modelAttribute="category">
    <div class="form-group">
      <label for="name" class="col-sm-offset-2 col-sm-2 control-label">Name</label>

      <div class="col-sm-4">
        <form:input id="name" cssClass="form-control"
                    path="name" placeholder="Name"/>
      </div>
    </div>

    <div class="form-group">
      <div class="col-sm-offset-7 col-sm-1" style="text-align: center;">
        <button type="submit" class="btn btn-default">Submit</button>
      </div>
    </div>
  </form:form>
</page:base>