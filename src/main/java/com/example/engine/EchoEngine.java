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

        String routeId = carbonMessage.getHeader("routeId") == null ? "" : carbonMessage.getHeader("routeId");

        switch (routeId) {

        case "r1":
            carbonMessage.setProperty(Constants.TO, "/services/SimpleStockQuoteService");
            carbonMessage.setProperty(Constants.HOST, "localhost");
            carbonMessage.setProperty(Constants.PORT, 9000);
            break;

        case "r2":
            carbonMessage.setProperty(Constants.TO, "/services/SimpleStockQuoteService");
            carbonMessage.setProperty(Constants.HOST, "localhost");
            carbonMessage.setProperty(Constants.PORT, 9001);
            break;

        default:
            carbonMessage.setProperty(Constants.TO, "/services/SimpleStockQuoteService");
            carbonMessage.setProperty(Constants.HOST, "localhost");
            carbonMessage.setProperty(Constants.PORT, 9004);

            break;

        }

        sender.send(carbonMessage, new CarbonCallback() {
            @Override
            public void done(CarbonMessage cMsg) {
                carbonCallback.done(cMsg);
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
