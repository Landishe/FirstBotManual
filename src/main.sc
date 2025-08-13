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
        a: Добрый день, назовте ФИО или Номер заказа
        

        state: checkClient
            q!: * (\d+) *
            script:
                var foundOrder = $request.query(/\D+/g, '');
                for(var i = 0; i < clients.length; i++ ) {
                    if(clients[i].idOrder == foundOrder){
                        а: правильно
                    }
                }
                
            go!: /Goodbye

    state: Goodbye 
        q: * (прощай/пока/досвидания) *
        a: Прощай {{$session.userName}}
    
    state: NoMatch
        event: noMatch
        a: ожалуйста, укажите номер заказа цифрами, например: "мой заказ 12345"