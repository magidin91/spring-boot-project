<#include "security.ftlh">
<#import "login.ftlh" as l>

<#-- Панель навигации -->
<#-- если размер экрана меньше large, то navbar в свернутом виде -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
<#-- ссылка, которая ведет на корень проекта -->
    <a class="navbar-brand" href="/">Sweater</a>
<#-- кнопка меню при свернутой панели навигации -->
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
<#-- само меню
collapse значит, меню может сворачиваться -->
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                    <#-- кнопка Home -->
                <a class="nav-link" href="/">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/main">Messages</a>
            </li>
            <#-- показываем ссылку только если пользователь - админ-->
            <#if isAdmin>
            <li class="nav-item">
                <a class="nav-link" href="/user">User List</a>
            </li>
            </#if>
        </ul>
        <#-- Выводим в навбаре имя юзера, используя объявленную в "security.ftlh" переменную name
        class="mr-3" - отступ справа 3 единицы -->
        <div class="navbar-text mr-3">${name}</div>
        <@l.logout/>
    </div>
</nav>