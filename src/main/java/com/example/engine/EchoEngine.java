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
import org.wso2.carbon.messaging.Constants;
import org.wso2.carbon.messaging.DefaultCarbonMessage;
import org.wso2.carbon.messaging.TransportSender;

public class EchoEngine implements CarbonMessageProcessor {
    TransportSender sender;

    public boolean receive(CarbonMessage carbonMessage, final CarbonCallback carbonCallback) throws Exception {

        final DefaultCarbonMessage echoRequest = new DefaultCarbonMessage();

        while (true) {
            echoRequest.addMessageBody(carbonMessage.getMessageBody());
            if (carbonMessage.isEomAdded() && carbonMessage.isEmpty()) {
                echoRequest.setEomAdded(true);
                break;
            }
        }

        carbonMessage.getProperties().forEach(echoRequest::setProperty);
        carbonMessage.getHeaders().forEach(echoRequest::setHeader);

        echoRequest.setProperty(Constants.TO, "/services/SimpleStockQuoteService");
        echoRequest.setProperty(Constants.HOST, "localhost");
        echoRequest.setProperty(Constants.PORT, 9000);

        sender.send(echoRequest, new CarbonCallback() {
            @Override
            public void done(CarbonMessage cMsg) {
                DefaultCarbonMessage defaultResponse = new DefaultCarbonMessage();

                while (true) {
                    defaultResponse.addMessageBody(cMsg.getMessageBody());
                    if (cMsg.isEomAdded() && cMsg.isEmpty()) {
                        defaultResponse.setEomAdded(true);
                        break;
                    }
                }

                cMsg.getHeaders().forEach(defaultResponse::setHeader);
                defaultResponse.setHeader("hello", "world");
                cMsg.getProperties().forEach(defaultResponse::setProperty);
                carbonCallback.done(defaultResponse);
            }
        });

        return true;
    }

    public void setTransportSender(TransportSender transportSender) {
        sender = transportSender;
    }

    @Override
    public String getId() {
        return "echo-engine";
    }
}
