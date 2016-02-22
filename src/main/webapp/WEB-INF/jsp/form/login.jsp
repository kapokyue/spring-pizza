<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<page:base title="Login" activeNavId="login">
  <form:form method="post" cssClass="form-horizontal" action="/login">
    <div class="form-group">
      <label for="inputUN" class="col-sm-offset-2 col-sm-2 control-label">Account</label>

      <div class="col-sm-4">
        <input id="inputUN" type="text" name="account"
               class="form-control" maxlength="10" size="15"
               placeholder="User Name"/>
      </div>
    </div>
    <div class="form-group">
      <label for="inputPW" class="col-sm-offset-2 col-sm-2 control-label">Password</label>

      <div class="col-sm-4">
        <input id="inputPW" type="password" name="password"
               class="form-control" maxlength="10" size="15" placeholder="Password"/>
      </div>
    </div>
    <div class="form-group">
      <div class="col-sm-offset-7 col-sm-1" style="text-align: center;">
        <button type="submit" class="btn btn-default">Login</button>
      </div>
    </div>
  </form:form>
</page:base>