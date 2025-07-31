
theme: /
    state: start
        q!: regex</start>
            script:
                $context.client = {};
        
        a: Привет, назови свое имя</a>
        go!: Приветствие

    state: Приветствие
        intent!:/Приветствие
            q: * $Name*
            script:
                $client.userName = parseTree._Name;
            a: Привет {{$client.userName}}
        go!: Прощание

    state: Прощание 
        intent!: Прощание
        a: Прощай {{$client.userName}}
