<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
<div>
    <@l.logout/>
    <span><a href="/user">User list</a></span>
</div>
<!-- форма для ввода сообщений -->
<div>
    <form method="post" action="/main" enctype="multipart/form-data">
        <input type="text" name="text" placeholder="Введите сообщение" />
        <input type="text" name="tag" placeholder="Введите тэг">
        <!-- загрузка файла, создается кнопка Browse -->
        <input type="file" name="file">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit">Добавить</button>
    </form>
</div>
    <!-- форма для поиска по тегу -->
<form method="get" action="/main">
    <!-- value="$.{tag!}" - чтобы тег отображался после поиска -->
    <input type="text" name="tag" value="${tag!?ifExists}">
    <button type="submit">Найти по тэгу</button>
</form>
<div><b>Список сообщений пользователя:</b></div>
<!-- обход коллекции messages во фримаркере -->
<#list messages as message>
    <div>
       <b>${message.id}</b>
        <span>${message.text}</span>
        <i>${message.tag}</i>
        <strong>${message.authorName}</strong>
        <#--вывод картинки-->
            <div>
                <#-- проверка наличия поля filename -->
                <#if message.filename??>
                    <img src="/img/${message.filename}">
                </#if>
            </div>
    </div>
<!-- выводится в случае, если коллекция пустая -->
<#else>
No message
</#list>
</@c.page>