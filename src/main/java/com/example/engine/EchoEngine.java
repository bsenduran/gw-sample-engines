/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.example.engine;

import org.wso2.carbon.messaging.CarbonCallback;
import org.wso2.carbon.messaging.CarbonMessage;
import org.wso2.carbon.messaging.CarbonMessageProcessor;
import org.wso2.carbon.messaging.DefaultCarbonMessage;
import org.wso2.carbon.messaging.TransportSender;

import java.nio.charset.Charset;


public class EchoEngine implements CarbonMessageProcessor {
    public boolean receive(CarbonMessage carbonMessage, CarbonCallback carbonCallback) throws Exception {

        DefaultCarbonMessage echoResponse = new DefaultCarbonMessage();

        String greeting = "Hello World\n";
        byte[] bytes = greeting.getBytes(Charset.defaultCharset());


        echoResponse.setStringMessageBody(greeting);
        echoResponse.setHeader("Content-Length", String.valueOf(bytes.length));

        carbonCallback.done(echoResponse);
        return true;
    }

    public void setTransportSender(TransportSender transportSender) {

    }

    @Override
    public String getId() {
        return "echo-engine";
    }
}
