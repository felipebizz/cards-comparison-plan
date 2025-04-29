# cards-comparison-plan

Iniciando testes com clojure


## Usage

Calculo de Milhas entre diferentes bancos baseado num gasto mensal


## HOWTO

```
(require '[br.com.miles.service :as service])
(require '[io.pedestal.http :as http])

(defonce server (http/create-server service/service))
(http/start server)
```

API Running
```
http://localhost:8080/calcular
```

### Testing via Postman

```
curl -X POST http://localhost:8080/calcular \
-H "Content-Type: application/json" \
-d '{"gasto-mensal": 10000}'
```

## License

