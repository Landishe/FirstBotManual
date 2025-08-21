require: name/name.sc
    module = sys.zb-common
    name = Names
    var = $Names

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
        
        state: checkOrder
            q!: * *
            script:
                var queryClient = $request.query
                log(queryClient);
                var queryOrder = $request.query.replace(/[^0-9]/g, '');
                for(var id in clients) {
                    var idOrder = clients[id].value.id
                    if(idOrder ==  queryOrder) {
                        $reactions.answer("такой заказ есть");
                        break;
                    } else {
                        $reactions.answer("такого заказа нет, проверьте номер заказа или ФИО");
                        break;
                        $reactions.transition("/checkClients");
                    };
                };
            state: checkClients
                q: * *
                script: 
                    
                    log(clients["1"].alternateNames);
                    var queryClients = $request.query.replace(/D+/g, '');
                    for (var i in clients) {
                        var clientsName = clients[i].alternateNames;
                        if(queryClients ==  clientsName) {
                            $reactions.answer("сюда залетел");
                            break
                        } else {
                            $reactions.answer("дубль");
                            break
                        }
                    };

    state: Goodbye 
        q: * (прощай/пока/досвидания) *
        a: Прощай {{$session.userName}};
    
    state: NoMatch
        event: noMatch
        a: Мы не получили ответ