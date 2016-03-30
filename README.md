building the engines 
$> mvn clean install

starting the GW with osgi console

uncomment the `osgi.console=` in GW_HOME/conf/osgi/launch.properties



installing an engine (eg: echo-engine)

`osgi> install file:/home/senduran/projects/se1/gw-sample-engines/echo-engine/target/echo-engine-1.0.0.jar`

once installed you will get the Bundle id (eg: 48)

starting the Bundle
`osgi> start 48`


inding the default camel based engine's bundleid
`osgi> ss`
find the bundle id for org.wso2.carbon.gateway_1.0.0.SNAPSHOT

36	ACTIVE      org.wso2.carbon.gateway_1.0.0.SNAPSHOT


stopping the default camel based engine
`osgi> stop 36`

now the request is routed to echo engine




sample request for passthrough engines (the back-end is hardcoded to the sample axis2 engine's SimpleStockQuote service)
 `curl -d @req.xml http://localhost:9090/ -H "soapAction:getSimpleQuote" -H "Content-Type: text/xml"  -v`

 req.xml
 ```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://services.samples">
    <soapenv:Header/>
    <soapenv:Body>
        <ser:getSimpleQuote>
            <ser:symbol>ABC</ser:symbol>
        </ser:getSimpleQuote>
    </soapenv:Body>
</soapenv:Envelope>
```
