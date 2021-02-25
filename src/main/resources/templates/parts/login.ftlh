<#-- макрос "login" для 2-ух форм, в которых отличается путь, для этого используем переменную path
${path} - использование этой переменной в макросе -->
<#macro login path type isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> User Name : </label>
            <div class="col-sm-6">
                <input type="text" name="username"
                       <#-- добавляя к классу 'is-invalid', мы связываем поле ввода и сообщение о невалидности поля -->
                       class="form-control ${(usernameError??)?string('is-invalid', '')}"
                       placeholder="enter username"
                       <#-- если ввели неправильный пароль, то логин остается введенным -->
                       value="<#if user??>${user.username}</#if>"/>
                <#-- если есть usernameError, выводим ошибку валидации -->
                <#if usernameError??>
                    <div class=" invalid-feedback">
                ${usernameError}
            </div>
            </#if>
        </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-6">
                <input type="password" name="password"
                       class="form-control ${(passwordError??)?string('is-invalid', '')}"
                       placeholder="enter password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <#-- выводим только на форме регистрации-->
        <#if isRegisterForm>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label"> Password: </label>
                <div class="col-sm-6">
                    <input type="password" name="password2"
                           class="form-control ${(passwordError??)?string('is-invalid', '')}"
                           placeholder="Retype password"/>
                    <#if password2Error??>
                        <div class="invalid-feedback">
                            ${password2Error}
                        </div>
                    </#if>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label"> Email: </label>
                <div class="col-sm-6">
                    <input type="email" name="email"
                           value="<#if user??>${user.email}</#if>"
                           class="form-control ${(emailError??)?string('is-invalid', '')}"
                           placeholder="some@some.com"/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>
            <div>
                <#-- рекапча, вставляем наш sitekey -->
                <#-- рекапча отдает ответ с данными из формы в обрабатывающий пост метод -->
                <div class="col-sm-6">
                    <div class="g-recaptcha" data-sitekey="6LdJjmYaAAAAACHngo9XevMav2JXsGAy9EA4yuK8"></div>
                    <#-- если капча неуспешна - выводим алерт -->
                    <#if captchaError??>
                        <div class="alert alert-danger" role="alert">
                            ${captchaError}
                        </div>
                    </#if>
                </div>
        </#if>

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

