<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
Add new user
<@l.login "/registration" "Sign up"/>
<!-- вставка переменной message во фримаркере -->
${message}
</@c.page>
