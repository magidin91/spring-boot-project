<#-- кнопка, которая сворачивает форму-->
<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
   aria-controls="collapseExample">
    Message editor
</a>
<!-- по   id="collapseExample" кнопка связна со схлапывающимся элементом-->
<!--"collapse <#if message??>show</#if>" - если есть message добавляем к классу collapse show, чтобы форма не сворачивалась -->
<div class="collapse <#if message??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <!-- форма для ввода сообщений -->
        <form method="post" enctype="multipart/form-data">
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
                       value="<#if message??>${message.tag}</#if>" placeholder="enter tag"/>
                <#if tagError??>
                    <div class="invalid-feedback">
                        ${tagError}
                    </div>
                </#if>
            </div>
            <!-- загрузка файла -->
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file" id="customFile"/>
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="<#if messageFromDB??>${messageFromDB.id}</#if>"/>
            <button type="submit" class="btn btn-primary">Save message</button>
        </form>
    </div>
</div>