(ns br.com.miles.service
      (:require [io.pedestal.http :as http]
                [io.pedestal.http.route :as route]
                [cheshire.core :as json]))

  (defn calcular-milhas [gasto-mensal-cartao]
        (let [cambio-usd 5.0
              bonus-transferencia 1.8
              cashback-nu 0.01

              gasto-anual (* gasto-mensal-cartao 12)
              gasto-anual-usd (/ gasto-anual cambio-usd)

              pontos-c6-itau (* gasto-anual-usd 2.5)
              milhas-c6-itau (* pontos-c6-itau bonus-transferencia)

              cashback-total (* gasto-anual cashback-nu)
              milhas-nu (/ cashback-total 0.035)]

          {:gasto-anual gasto-anual
           :c6-carbon {:milhas (int milhas-c6-itau)
                       :viagens "1x EUA ou 2x América do Sul"}
           :itau-infinite {:milhas (int milhas-c6-itau)
                           :viagens "1x EUA ou 2x América do Sul (mais promoções)"}
           :nu-ultravioleta {:milhas (int milhas-nu)
                             :viagens "1x viagem nacional econômica"}}))

  (defn calcular-handler [request]
        (let [body (slurp (:body request))
              {:keys [gasto-mensal]} (json/parse-string body true)
              resultado (calcular-milhas gasto-mensal)]
          {:status 200
           :headers {"Content-Type" "application/json"}
           :body (json/generate-string resultado)}))

  (def routes
       (route/expand-routes
         #{["/calcular" :post calcular-handler :route-name :calcular]}))

  (def service
       {:env :prod
        ::http/routes routes
        ::http/type :jetty
        ::http/port 8080})

