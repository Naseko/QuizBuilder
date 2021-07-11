# API для создания и работы с опросами.

- Работа с API происходит с помощью REST API. Приведенные ниже примеры подразумевают, что API размернуто на localhost:8082

- В корне проекта находится .json файл с выгрузкой примеров запросов для Postman `'Quiz Builder.postman_collection.json'`

- В системе предусмотрен пользователь с ролью админа: `admin:password`, настроена basic authentification
- При перезапуске приложения БД настроена на обновление (`create-drop`)

### Функционал для администратора системы:
#### 1. Создание нового опроса
  * **URL:** `localhost:8082/adminui/quiz/create`
  * **Method:** `POST`

Пример Body:
```  
{
  "title": "C# quiz",
  "description": "Some simple quiz on C# knowledge",
  "startDate": "2022-07-11",
  "stopDate": "2023-09-22"
}
```
#### 2. Создание нового вопроса
* **URL:** `localhost:8082/adminui/question/create`
* **Method:** `POST`

Пример Body:
```  
{
    "qtext": "Maven in C#?",
    "type": "text"
}
```

#### 3. Изменение опроса
* **URL:** `localhost:8082/adminui/quiz/{id}`
* **Method:** `PUT`

_(`{id}` - указать id опроса, который следует изменить)_

Пример Body: _(в боди сложить атрибуты опроса, которые следует изменить)_
```  
{
    "title": "Java quiz UPD",
    "description": "Some complex quiz on java knowledge"
}
```
#### 4. Изменение вопроса
* **URL:** `localhost:8082/adminui/question/{id}`
* **Method:** `PUT`

_(`{id}` - указать id вопроса, который следует изменить)_

Пример Body: _(в боди сложить атрибуты вопроса, которые следует изменить)_
```  
{
    "qtext": "Do you like Grafana",
    "type": "YN"
}
```

#### 5. Удаление опроса
* **URL:** `localhost:8082/adminui/quiz/{id}`
* **Method:** `DELETE`

_(`{id}` - указать id опроса, который следует удалить)_

#### 6. Удаление вопроса
* **URL:** `localhost:8082/adminui/question/{id}`
* **Method:** `DELETE`

_(`{id}` - указать id вопроса, который следует удалить)_


### Функционал для пользователей системы:
#### 1. Получение списка активных опросов
* **URL:** `localhost:8082/userui/quiz/allactive`
* **Method:** `GET`

#### 2. получение пройденных пользователем опросов с детализацией по ответам
* **URL:** `localhost:8082/userui/quiz/allByUserId/{id}`
* **Method:** `GET`

_(`{id}` - указать id пользователя, для которого следует получить результаты_

#### 3. Прохождение опроса

Для прохождения опроса воспользоваться следующий последовательность методов из API

1. Получить все активные опросы `localhost:8082/userui/quiz/allActive`
2. Получить один из списка по id `localhost:8082/userui/question/{id}`
3. Взять его вопросы (в JSONе из пред. шага)
4. Сгенерировать ответы для каждого вопроса из списка (JSONы) 
5. Сохранить ответы `localhost:8082/userui/answer/create`