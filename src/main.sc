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
        q!: $regexp_i<.*\d+.*>
        script:
            $context.queryClient = $request.query
            var queryOrder = $request.query.replace(/[^0-9]/g, '');
            var findId = false
            log($context.queryClient)
            log(queryOrder)
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
            var findClients = false
            for (var i in clients) {
                var nameClients = clients[i].value.name;
                if(nameClients === queryClients) {
                    $reactions.answer(nameClients + "\nВаш заказ: " + clients[i].value.id + "\nВ статусе: " +  clients[i].value.status); 
                    findClients = true
                } 
            };
            if (!findClients){
                $reactions.answer("пользователь не найден");}
        if $session.findClients = true
            a: Пользователь не найден
        else:
            a: $reactions.answer(nameClients + "\nВаш заказ: " + clients[i].value.id + "\nВ статусе: " +  clients[i].value.status);

    state: Goodbye 
        q!: * (прощай/пока/досвидания) *
        a: Спасибо что обратились к нам {{$session.userName}};
    
    state: NoMatch
        event!: noMatch
        a: Мы не получили ответ