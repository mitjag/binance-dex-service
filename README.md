Binance DEX REST signing service
================================

Service is similar to XML digital signature services for signing with X509 certificates.

Service exposes GET methods to generate payload containing Binance DEX StdTx: message, signature

Payload from GET methods can be broadcasted to Binance DEX block chain.

Service also exposes conviniece GET method for broadcasting to Binance DEX chain (/broadcast).

Program languages capable of integrating with REST services can easily integrate with REST signing service (e.g. Javascript and call it from browser).

Building
========

Service is written in Java language using Binance DEX Java SDK (https://github.com/binance-chain/java-sdk) and Spring Boot Framework (https://spring.io/).

Maven build: mvn clean package

Execute .jar file.

Example: java -jar binance-dex-service-1.0.0.jar --wallet.name=wallet ---wallet.pin=1234 --wallet.ipWhitelist=127.0.0.1 --wallet.phrase="rural tell tuna elephant coast clap nut hollow mother impulse reunion hurt smoke peanut lunar teach lonely goddess frog access just gas dice shock"

Service starts on localhost port 8080 browse to URL: http://127.0.0.1:8080/

Command line arguments
======================

--env=PROD otherwise --env=TEST_NET is used

Single wallet mode
------------------

--wallet.name=wallet

--wallet.pin=2211

--wallet.ipWhitelist=127.0.0.1

--wallet.phrase="rural tell tuna elephant coast clap nut hollow mother impulse reunion hurt smoke peanut lunar teach lonely goddess frog access just gas dice shock"

or

--wallet.privateKey=f0e87ed55fa3d86f62b38b405cbb5f632764a7d5bc690da45a32aa3f2fc81a36

Paramter wallet.phrase has priority.

Multiple wallets mode
---------------------

Reads from file on disk. Format is JSON

--wallet.file=wallets.json

Example configuration file:
``` JSON
[
    {
        "name":"wallet1",
        "pin":1234,
        "ipWhitelist":"127.0.0.1",
        "phrase":"rural tell tuna elephant coast clap nut hollow mother impulse reunion hurt smoke peanut lunar teach lonely goddess frog access just gas dice shock"},
    {
        "name":"wallet2",
        "pin":4321,
        "ipWhitelist":"127.0.0.1,192.168.0.10",
        "privateKey":"f0e87ed55fa3d86f62b38b405cbb5f632764a7d5bc690da45a32aa3f2fc81a36"
    }
]
```

HTTP GET payload methods
========================

/newOrder
---------
Query parameters:

    String name
    Integer pin
    String symbol
    String orderType
    String side
    String price
    String quantity
    String timeInForce
    optional String memo
    optional Long source
    optional String data

/vote
-----
Query parameters:

    String name
    Integer pin
    Long proposalId
    Integer option
    optional String memo
    optional Long source
    optional String data

/cancelOrder
------------
Query parameters:

    String name
    Integer pin
    String symbol
    String refId
    optional String memo
    optional Long source
    optional String data

/transfer
---------
Query parameters:

    String name
    Integer pin
    String coin
    String toAddress
    String amount
    optional String memo
    optional Long source
    optional String data

/multiTransfer
--------------
Query parameters:

    String name
    Integer pin
    String multiTransfer, expected string format is (+ splits different addresses, after address repeat coin and amount pairs): address,coin,amount,coin,amount+address,coin,amount,...
    optional String memo
    optional Long source
    optional String data

/freeze
-------
Query parameters:

    String name
    Integer pin
    String symbol
    String amount
    optional String memo
    optional Long source
    optional String data

/unfreeze
---------
Query parameters:

    String name
    Integer pin
    String symbol
    String amount
    optional String memo
    optional Long source
    optional String data

HTTP GET broadcast method
=========================

/broadcast
----------
Query parameters:

    String name
    Integer pin
    String payload



Example
=======

HTTP GET request for /transfer:
```
http://127.0.0.1:8080/transfer?name=testWallet&pin=1234&coin=PND-943&toAddress=tbnb18086qc9yxtk5ufddple8upf0k3072vvagpm2ml&amount=0.1
```

HTTP GET request returns signed message ready to for broadcast to Binance DEX chain node

```
ce01f0625dee0a542a2c87fa0a260a149f93198fa769ece70f604aa18447d437600fdc7c120e0a07504e442d3934331080ade20412260a143bcfa060a432ed4e25ad0ff27e052fb45fe5319d120e0a07504e442d3934331080ade20412700a26eb5ae98721026804663998b70b88e916232cfe22337f5a2d0c3e8fb64ca656314128e9fd89db1240ef51400ba6d6cdcae44b31e0a251e24c2ed290045dd2a3114c67c8398cad8d46752e2170bafce74b03c5503c754f6d92688c45a2be74780d998c1187e342e96e18c40620cd042003
```

Signing service also implements broadcast method

HTTP GET request for /broadcast:
```
127.0.0.1:8080/broadcast?name=myPhrase&pin=1234&payload=ce01f0625dee0a542a2c87fa0a260a149f93198fa769ece70f604aa18447d437600fdc7c120e0a07504e442d3934331080ade20412260a143bcfa060a432ed4e25ad0ff27e052fb45fe5319d120e0a07504e442d3934331080ade20412700a26eb5ae98721026804663998b70b88e916232cfe22337f5a2d0c3e8fb64ca656314128e9fd89db1240ef51400ba6d6cdcae44b31e0a251e24c2ed290045dd2a3114c67c8398cad8d46752e2170bafce74b03c5503c754f6d92688c45a2be74780d998c1187e342e96e18c40620cd042003
```

Binance chain returns:
``` JSON
[{"code":0,"data":null,"hash":"F65306A58D6BDC7E12DF7C2FE6B9A516F041D7D324DC267526C8A109BD54F855","log":"Msg 0: ","ok":true}]
```