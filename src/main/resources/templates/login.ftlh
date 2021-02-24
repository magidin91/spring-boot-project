<#import "parts/common.ftlh" as c>
<#-- импорт макроса для формы логина-->
<#import "parts/login.ftlh" as l>
<@c.page>
<#-- вставляем сообщение, если оно есть -->
    ${message?ifExists}
<#-- мы можем получить данные спринг секьюрити в шаблоне
Сессия юзера и последнее искл-ие -->
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
<#-- вставили форму логина с заданными переменными -->
    <@l.login "/login" "Sign in" false/>
</@c.page>



