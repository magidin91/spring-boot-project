<#include "security.ftlh">
<#import "pager.ftlh" as p>

<!-- позволяет расположить неск карточек в ряд -->
<div class="card-columns" id="message-list">
    <!-- обход коллекции messages во фримаркере -->
    <#list page.content as message>
    <#-- для вывода сообщений изпользуем элемент "card"-->
    <#-- добавили data-id, чтобы в тестах,проверить существование сообщения с конкретным id-->
        <div class="card my-3 " data-id="${message.id}">
            <#-- проверка наличия поля filename -->
            <#if message.filename??>
            <#--вывод картинки-->
                <img src="/img/${message.filename}" class="card-img-top"/>
            </#if>
            <div class="m-2">
                <span>${message.text}</span><br/>
                <i>${message.tag}</i>
            </div>
            <div class="card-footer text-muted">
                <a href="/user-messages/${message.author.id}">${message.authorName}</a>
                <#if message.author.id == currentUserId>
                    <a class="btn btn-primary" href="/user-messages/${message.author.id}?message=${message.id}">
                        Edit
                    </a>
                </#if>
            </div>
        </div>
        <!-- выводится в случае, если коллекция пустая -->
    <#else>
        No message
    </#list>
</div>
<@p.pager url page/>