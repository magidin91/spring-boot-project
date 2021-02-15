<#-- определение переменных в шаблоне фримаркера-->
<#assign
<#-- обявили переменную known, положили в нее проверку существования Session.SPRING_SECURITY_CONTEXT?? -->
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
        <#-- аутентифицрованный пользователь -->
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    isAdmin = user.isAdmin()
    >
<#else>
    <#assign
    name = "unknown"
    isAdmin = false
    >
</#if>