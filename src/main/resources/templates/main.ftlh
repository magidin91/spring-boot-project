<#-- импортируем файл, затем вставляем конкретный макрос из файла в нужное место: <@c.page>...</@c.page> -->
<#-- include в свою очередь сразу вставляет содержимое файла в конкретное место: <#include "navbar.ftlh"> -->
<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="form-row">
        <div class="form-group col-md-6">
            <!-- форма для поиска по тегу -->
            <#-- class="form-inline" - поля будут в линию -->
            <form method="get" action="/main" class="form-inline">
                <!-- value="$.{tag!}" - чтобы тег отображался после поиска -->
                <input type="text" name="tag" class="form-control" value="${tag!?ifExists}" placeholder="search by tag">
                <button type="submit" class="btn btn-primary ml-2">Search</button>
            </form>
        </div>
    </div>

    <#include "parts/messageEdit.ftlh">
    <#include "parts/messageList.ftlh">

</@c.page>