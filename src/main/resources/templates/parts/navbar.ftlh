<#include "security.ftlh"> <#-- используем файл с объявлением переменных -->
<#import "login.ftlh" as l>

<#-- Панель навигации -->
<#-- если размер экрана меньше large, то navbar в свернутом виде -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
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
            <#if user??>
            <li class="nav-item">
                <a class="nav-link" href="/main">Messages</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/user-messages/${currentUserId}">My messages</a>
            </li>
            </#if>
            <#-- показываем ссылку только если пользователь - админ-->
            <#if isAdmin>
            <li class="nav-item">
                <a class="nav-link" href="/user">User List</a>
            </li>
            </#if>
            <#-- получаем переменную юзер из security.ftlh <#assign> объявления -->
            <#-- т.е. ссылка выводится, только если юзер авторизован и не равен налл -->
            <#if user??>
                <li class="nav-item">
                    <a class="nav-link" href="/user/profile">Profile</a>
                </li>
            </#if>
        </ul>
        <#-- Выводим в навбаре имя юзера, используя объявленную в "security.ftlh" переменную name
        class="mr-3" - отступ справа 3 единицы -->
        <div class="navbar-text mr-3"><#if user??>${name}<#else>Please, login</#if></div>
        <@l.logout/>
    </div>
</nav>