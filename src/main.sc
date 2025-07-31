
theme: /
    state: start
        q!: regex</start>
            script:
                $context.client = {};
        
        a: Привет, назови свое имя</a>
        go!: Hallo

    state: Hallo
        intent!: /Hallo
            q: * $Name*
            script:
                $client.userName = parseTree._Name;
            a: Привет {{$client.userName}}
        go!: Bue

    state: Bue 
        intent!: Bue
        a: Прощай {{$client.userName}}
