require: name/name.sc
    module = sys.zb-common

theme: /
    state: start
        q!: $regex</start>
            script:
                $context.session = {};
        a: Привет, назови свое имя</a>
        go!: /Hello

    state: Hello
            q: * $Name *
            script:
                $session.userName = $parseTree._Name;
            a: Привет {{$session.userName}}
            go!: /Goodbye

    state: Goodbye 
        q: * (прощай/пока/досвидания) *
        a: Прощай {{$session.userName}}
    
    state: NoMatch
        event: noMatch
        a: Извините, я вас не понял. Попробуйте сказать "Меня зовут [ваше имя]"