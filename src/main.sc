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
        go: /Hello

    state: Hello
        q: * $Name *
        script:
            $session.userName = $parseTree._Name.name;
        a: Привет {{$session.userName}}
        go: /checkClient

    state: checkClient
        q!: * (\d+) *
            script:
                var foundOrder  = $parseTree._orderNumber;

                for(var i = 0; i < clients.length; i++ ) {
                    # проверяется с $session.userName вместо orderNumber??
                    if(clients[i].idOrder == foundOrder){
                        var statusText = getStatusText(client[i].status)
                        return {
                            text: `Найден заказ ${foundOrder}:
                                Клиент: ${clients[i].name}
                                Статус: ${statusText}`
                                buttons: ["Назад"]
                        };
                    }
                }
                return {
                    text: `Заказ ${foundOrder} не найден`
                };
        go: Goodbye

    state: Goodbye 
        q: * (прощай/пока/досвидания) *
        a: Прощай {{$session.userName}}
    
    state: NoMatch
        event: noMatch
        a: ожалуйста, укажите номер заказа цифрами, например: "мой заказ 12345"