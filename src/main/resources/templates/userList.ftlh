<#import "parts/common.ftlh" as c>
<@c.page>
    List of users
    <table>
        <thead>
        <#-- Тег <tr> служит контейнером для создания строки таблицы-->
        <tr>
            <#--Тег <th> предназначен для создания одной ячейки таблицы, которая обозначается как заголовочная.
            Текст в такой ячейке отображается браузером обычно жирным шрифтом и выравнивается по центру.
            Тег <th> должен размещаться внутри контейнера <tr>, который в свою очередь располагается внутри тега <table>.-->
            <th>Name</th>
            <th>Role</th>
            <th>Edit</th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <#-- <#sep>, - разделитель запятая + проблел -->
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
                <td><a href="/user/${user.id}">edit</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>