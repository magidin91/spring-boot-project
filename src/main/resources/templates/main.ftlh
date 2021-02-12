<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
<div>
    <@l.logout/>
</div>

<!-- форма для ввода сообщений -->
<div>
    <form method="post" action="/main">
        <input type="text" name="text" placeholder="Введите сообщение" />
        <input type="text" name="tag" placeholder="Введите тэг">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit">Добавить</button>
    </form>
</div>
    <!-- форма для поиска по тегу -->
<form method="get" action="/main">
    <!-- value="$.{tag!}" - чтобы тег отображался после поиска -->
    <input type="text" name="tag" value="${tag!}">
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
    </div>
<!-- выводится в случае, если коллекция пустая -->
<#else>
No message
</#list>
</@c.page>