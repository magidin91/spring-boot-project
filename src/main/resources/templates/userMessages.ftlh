<#import "parts/common.ftlh" as c>
<@c.page>
    <#-- проверка, т.к. юзер может перейти не поссылке, а конкретному урл с айди др юзера -->

    <#if isCurrentUser>
        <#include "parts/messageEdit.ftlh" />
    </#if>
    <#include "parts/messageList.ftlh">
</@c.page>