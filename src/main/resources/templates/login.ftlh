<#import "parts/common.ftlh" as c>
<#-- импорт макроса для формы логина-->
<#import "parts/login.ftlh" as l>
<@c.page>
<#-- вставили форму логина с заданными переменными -->
<@l.login "/login" "Sign in" false/>
</@c.page>



