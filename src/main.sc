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
            $session = {};
            
        a: Добрый день, назовте ФИО или Номер заказа
        
    state: checkOrder
        q!: $regexp_i<.*\d+.*>
        script:
            var queryOrder = $request.query.replace(/[^0-9]/g, '');
            var findId = false
            log($context.queryClient)
            for(var id in clients) {
                
                var idOrder = clients[id].value.id
                if(idOrder ==  queryOrder) {
                    $reactions.answer(clients[id].value.name + "\nВаш заказ: " + idOrder + "\nВ статусе: " +  clients[id].value.status );
                    findId = true;
                    $reactions.answer("Проверим еще один заказ?");
                    $reactions.buttons({text: "Да", transition: "/start"});
                    $reactions.buttons({text: "нет", transition: "/Goodbye"});
                }
            }
                if(!findId){
                    $reactions.answer("такого заказа нет, проверьте номер заказа или ФИО");
                    $reactions.buttons({ text: "Ввести заказ еще раз", transition: "/start" });
                    $reactions.buttons({ text: "Ввести ФИО", transition: "/checkClients" });
                };
            
    state: checkClients
        a: введите ваше ФИО
        
            
    state: findClients
        q!: *
        script: 
            var queryClients = $request.query.replace(/D+/g, '');
            $session.findClients = false
            for (var i in clients) {
                var nameClients = clients[i].value.name;
                if(nameClients == queryClients) {
                    $reactions.answer(nameClients + "\nВаш заказ: " + clients[i].value.id + "\nВ статусе: " +  clients[i].value.status); 
                    $context.findClients = true
                    $reactions.buttons({text: "Да", transition: "/start"});
                    $reactions.buttons({text: "нет", transition: "/Goodbye"});
                } 
            };
            if (!$session.findClients){
                $reactions.answer("пользователь не найден 1");
                $reactions.answer("такого заказа нет, проверьте номер заказа или ФИО");
                $reactions.buttons({ text: "Ввести заказ еще раз", transition: "/start" });
                $reactions.buttons({text: "нет", transition: "/Goodbye"});
            }

    state: Goodbye 
        q!: * (прощай/пока/досвидания) *
        a: Спасибо что обратились к нам {{$session.userName}};
    
    state: NoMatch
        event!: noMatch
        a: Мы не получили ответ