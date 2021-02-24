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
<#-- кнопка, которая сворачивает форму-->
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Add new message
    </a>
    <!-- по   id="collapseExample" кнопка связна со схлапывающимся элементом-->
    <!--"collapse <#if message??>show</#if>" - если есть message добавляем к классу collapse show, чтобы форма не сворачивалась -->
    <div class="collapse <#if message??>show</#if>" id="collapseExample">
        <div class="form-group mt-3">
            <!-- форма для ввода сообщений -->
            <form method="post" action="/main" enctype="multipart/form-data">

                <div class="form-group">
                    <#-- добавляем к классу строку is-invalid, если есть ошибка валидации,
                   ее значение будет выведено под полем ввода  -->
                    <input type="text" class="form-control ${(textError??)?string('is-invalid', '')}"
                            <#-- делаем проверку, т.к. message приходит после обработки формы-->
                           value="<#if message??>${message.text}</#if>"
                           name="text"
                           placeholder="Введите сообщение"/>
                    <#-- если есть textError, выводим ошибку валидации -->
                    <#if textError??>
                        <div class="invalid-feedback">
                            ${textError}
                        </div>
                    </#if>
                </div>

                <div class="form-group">
                    <input type="text" name="tag" class="form-control ${(tagError??)?string('is-invalid', '')}"
                           value="<#if message??>${message.tag}</#if>" placeholder="enter tag">
                    <#if tagError??>
                        <div class="invalid-feedback">
                            ${tagError}
                        </div>
                    </#if>
                </div>

                <!-- загрузка файла -->
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file" id="customFile">
                        <label class="custom-file-label" for="customFile">choose file</label>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-primary">Add</button>
            </form>
        </div>
    </div>
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
                    <span>${message.text}</span>
                    <i>${message.tag}</i>
                </div>
                <div class="card-footer text-muted">
                    ${message.authorName}
                </div>
            </div>
            <!-- выводится в случае, если коллекция пустая -->
        <#else>
            No message
        </#list>
    </div>
</@c.page>