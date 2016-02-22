<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<page:base title="Category" activeNavId="productList">
  <form:form method="put" cssClass="form-horizontal" modelAttribute="user">
    <div class="form-group">
      <label for="account" class="col-sm-offset-2 col-sm-2 control-label">Account</label>

      <div class="col-sm-4">
        <form:input id="account" disabled="true" cssClass="form-control"
                    placeholder="User Name" path="account"/>
      </div>
    </div>
    <div class="form-group">
      <label for="newPwd" class="col-sm-offset-2 col-sm-2 control-label">New Password</label>

      <div class="col-sm-4">
        <input id="newPwd" type="password" name="password"
               class="form-control" placeholder="Password"/>
      </div>
    </div>
    <div class="form-group">
      <label for="confPwd" class="col-sm-offset-2 col-sm-2 control-label">Re-enter Password</label>

      <div class="col-sm-4">
        <input id="confPwd" type="password" name="password_confirmation"
               class="form-control" placeholder="Re-enter Password"/>
      </div>
    </div>
    <div class="form-group">
      <label for="surname" class="col-sm-offset-2 col-sm-2 control-label">Surname</label>

      <div class="col-sm-4">
        <form:input id="surname" value="" cssClass="form-control"
                    placeholder="Surname" path="name"/>
      </div>
    </div>
    <div class="form-group">
      <label for="phone" class="col-sm-offset-2 col-sm-2 control-label">Contact Number</label>

      <div class="col-sm-4">
        <form:input id="phone" cssClass="form-control"
                    placeholder="Contact Number" path="phone"/>
      </div>
    </div>
    <div class="form-group">
      <div class="col-sm-offset-5 col-sm-3" style="text-align: center;">
        <button type="submit" class="btn btn-primary pull-right">Update</button>
        <button type="reset" class="btn btn-warning pull-right" style="margin-right: 10px;">Reset Form</button>
      </div>
    </div>
  </form:form>
</page:base>