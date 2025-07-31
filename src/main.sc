require: name/name.sc
    module: sys.zb-common

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
            a: Привет {{$session.userName}}
        go!: goodbye

    state: goodbye 
        intent!: goodbye
        a: Прощай {{$client.userName}}
