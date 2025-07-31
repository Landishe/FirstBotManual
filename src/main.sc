require: name/name.sc
    module = sys.zb-common

theme: /
    state: start
        q!: $regex</start>
        script:
            $context.session = {};
        a: Привет, назови свое имя
        go: /Hello

    state: Hello
            q: * $Name *
            script:
                var name = $parseTree._Name
                # $session.userName = $parseTree._Name;
            a: Привет {{name}}
            go: /Goodbye

    state: Goodbye 
        q: * (прощай/пока/досвидания) *
        a: Прощай {{$session.userName}}
    
    state: NoMatch
        event: noMatch
        a: Извините, я вас не понял. Попробуйте сказать "Меня зовут [ваше имя]"