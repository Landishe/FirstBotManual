require: clients.csv
    name = pizza
    var = clients

theme: /

    state: checkClient
        event!: noMatch
        script:
            var orderNumber = $request.query;

            for(var i = 0; i < clients.length; i++ ) {
                if(clients[i].idOrder == orderNumber){
                    
                }
            }