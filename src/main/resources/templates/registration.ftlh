<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
Add new user
<@l.login "/registration" "Sign up"/>
<!-- ${message?ifExists}, используется, чтобы не получать ошибку,
когда не передаем в модель переменную message (пр. @GetMapping("registration")) -->
${message?ifExists}
</@c.page>
