<#include "security.ftlh">
<!-- позволяет расположить неск карточек в ряд -->
<div class="card-columns">
    <!-- обход коллекции messages во фримаркере -->
    <#list messages as message>
    <#-- для вывода сообщений изпользуем элемент "card"-->
        <div class="card my-3">
            <#-- проверка наличия поля filename -->
            <#if message.filename??>
            <#--вывод картинки-->
                <img src="/img/${message.filename}" class="card-img-top">
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