require: name/name.sc
    module = sys.zb-common

theme: /
    state: start
        q!: regex</start>
            script:
                $session = {};
        a: Привет, назови свое имя</a>
        go!: Hallo

    state: Hallo
            q: * $Name*
            script:
                $session.userName = $parseTree._Name;
            a: Привет {{$session.userName}}
        go!: goodbye

    state: goodbye 
        q: Прощай
        a: Прощай {{$session.userName}}
