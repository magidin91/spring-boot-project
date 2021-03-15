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
            <div class="card-footer text-muted container">
                <div class="row">
                    <#--"col align-self-center" - выравниваем по центру -->
                    <a class="col align-self-center"
                       href="/user-messages/${message.author.id}">${message.authorName}</a>
                    <a class="col align-self-center"
                       href="/messages/${message.id}/like">
                        <#if message.meLiked>
                        <#-- иконнка для лайка -->
                            <i class="fas fa-heart"></i>
                        <#else>
                            <i class="far fa-heart"></i>
                        </#if>
                        ${message.likes}
                    </a>
                    <#if message.author.id == currentUserId>
                        <a class="col btn btn-primary" href="/user-messages/${message.author.id}?message=${message.id}">
                            Edit
                        </a>
                    </#if>
                </div>
            </div>
        </div>
        <!-- выводится в случае, если коллекция пустая -->
    <#else>
        No message
    </#list>
</div>
<@p.pager url page/>