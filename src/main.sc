require: name/name.sc
    module = sys.zb-common

theme: /
    state: start
        q!: $regex</start>
            script:
                $client = {};
        a: Привет, назови свое имя</a>
        go!: Hello

        state: Hello
                q: * $Name *
                script:
                    $client.userName = $parseTree._Name;
                a: Привет {{$client.userName}}
                go!: Goodbye

        state: Goodbye 
            q: * (прощай/пока/досвидания) *
            a: Прощай {{$client.userName}}
    
    state: NoMatch
        event: noMatch
        a: Извините, я вас не понял. Попробуйте сказать "Меня зовут [ваше имя]"