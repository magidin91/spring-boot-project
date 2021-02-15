<#-- макрос "login" для 2-ух форм, в которых отличается путь, для этого используем переменную path
${path} - использование этой переменной в макросе -->
<#macro login path type isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> User Name : </label>
            <div class="col-sm-6">
                <input type="text" name="username" class="form-control" placeholder="enter username"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="enter password"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if !isRegisterForm>
            <a href="registration">Add new user</a>
        </#if>
        <button class="btn btn-primary" type="submit">${type}</button>
    </form>
</#macro>

<#macro logout> <#-- макрос для логаута (в файле может быть неск макросов с разными названиями)-->
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit">Sign Out</button>
    </form>
</#macro>

