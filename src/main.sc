require: name/name.sc
    module = sys.zb-common
require: clients.csv
name = clients
var = clients
 

theme: /
    state: start
        q!: $regex</start>
        script:
            $context.session = {};
            $context.temp = {}
        a: Добрый день, назовте ФИО
        go: /Hello

    state: Hello
        q: * $Name *
        script:
            $session.userName = $parseTree._Name.name;
        a: Привет {{$session.userName}}
        go: /checkClient

    state: checkClient
        q: 
        script:
            var orderNumber = $request.query;

            for(var i = 0; i < clients.length; i++ ) {
                # проверяется с $session.userName вместо orderNumber??
                if(clients[i].idOrder == orderNumber){
                    
                }
            }

    state: Order clarification
        a: {{session.userName}} уточните номер заказа
        q: *


    state: Goodbye 
        q: * (прощай/пока/досвидания) *
        a: Прощай {{$session.userName}}
    
    state: NoMatch
        event: noMatch
        a: Извините, я вас не понял. Попробуйте сказать "Меня зовут [ваше имя]" 