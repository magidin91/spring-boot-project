<#-- общая часть всех страниц, куда будет вставлятсья вложенный <#nested> код -->
<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8"/>
        <title>Sweater</title>
        <#--использование стиля через ссылку на стиль в нашем файле -->
        <link rel="stylesheet" href="/static/style.css"/>
        <#-- сообщаем браузеру, что он должен учитывать плотность пикселей на устройстве content="width=device-width -->
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>

        <!-- подключаем стили Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
              integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
              crossorigin="anonymous"/>
        <#-- скрипт для рекапча -->
        <script src='https://www.google.com/recaptcha/api.js'></script>
        <#-- скрипт для лайков -->
        <script src="https://kit.fontawesome.com/16dc073022.js" crossorigin="anonymous"></script>
        <#-- скрипт для турболинкс -->
        <script src='https://cdnjs.cloudflare.com/ajax/libs/turbolinks/5.2.0/turbolinks.js'></script>
        <#-- Атрибут defer откладывает выполнение скрипта до тех пор, пока вся страница не будет загружена полностью -->
        <script defer src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
                integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
                crossorigin="anonymous"></script>
        <script defer src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
                integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
                crossorigin="anonymous"></script>
        <#-- добавили CSS стиль для прогрес бара турболинкс-->
        <style>
            .turbolinks-progress-bar {
                height: 2px;
                background-color: #000080;
            }
        </style>
    </head>
    <body>
    <#-- вставляем панель навигации -->
    <#include "navbar.ftlh">
    <#-- используем сетку Grid бутстрапа,чтобы строки делились на колонки -->
    <div class="container mt-5">
        <#-- <#nested>- секция, которая определяет, куда вставляется вложенный код -->
        <#nested>
    </div>

    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
            integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
            crossorigin="anonymous"></script>
    </body>
    </html>
</#macro>