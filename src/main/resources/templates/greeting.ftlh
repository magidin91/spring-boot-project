<#-- используем макрос common.ftlh, чтобы сократить дублирвоания кода-->
<#import "parts/common.ftlh" as c>
<#-- вставляем данные -->
<@c.page>
    <div>Hello, user</div>
    <a href="/main">Main page</a>
</@c.page>
