<#import "parts/common.ftlh" as c>
<@c.page>
    User editor

    <form action="/user" method="post">
        <#-- сразу передали значение из модели в value -->
        <input type="text" name="username" value="${user.username}"/>
        <#list roles as role>
            <div>
                <#-- Сделали чекбокс для выбора ролей юзера-->
                <#-- ${user.roles?seq_contains(role)?string("checked", "")} - показывает галкой, выбрана ли роль уже -->
                <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}/>${role}</label>
            </div>
        </#list>
        <#--получаем user.id из модели и передаем его скрытым полем на контроллер-->
        <input type="hidden" value="${user.id}" name="userId"/>
        <input type="hidden" value="${_csrf.token}" name="_csrf"/>
        <button type="submit">Save</button>
    </form>
</@c.page>