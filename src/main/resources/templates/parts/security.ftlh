<#-- определение переменных в шаблоне фримаркера-->
<#assign
<#-- обявили переменную known, положили в нее проверку существования Session.SPRING_SECURITY_CONTEXT?? -->
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
        <#-- получаем аутентифицрованного юзера из контекста секьюрити -->
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    isAdmin = user.isAdmin()
    currentUserId = user.getId()
    >
<#else>
    <#assign
    name = "unknown"
    isAdmin = false
    currentUserId = -1
    >
</#if>