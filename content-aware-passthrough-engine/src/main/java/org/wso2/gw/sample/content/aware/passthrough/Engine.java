/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.gw.sample.content.aware.passthrough;

import org.wso2.carbon.messaging.CarbonCallback;
import org.wso2.carbon.messaging.CarbonMessage;
import org.wso2.carbon.messaging.CarbonMessageProcessor;
import org.wso2.carbon.messaging.Constants;
import org.wso2.carbon.messaging.DefaultCarbonMessage;
import org.wso2.carbon.messaging.TransportSender;

public class Engine implements CarbonMessageProcessor {

    TransportSender sender;

    public boolean receive(CarbonMessage request, CarbonCallback carbonCallback) throws Exception {

        DefaultCarbonMessage newRequest = new DefaultCarbonMessage();

        while (true) {
            newRequest.addMessageBody(request.getMessageBody());
            if (request.isEndOfMsgAdded() && request.isEmpty()) {
                newRequest.setEndOfMsgAdded(true);
                break;
            }
        }
        request.getProperties().forEach(newRequest::setProperty);
        request.getHeaders().forEach(newRequest::setHeader);

        newRequest.setProperty(Constants.TO, "/services/SimpleStockQuoteService");
        newRequest.setProperty(Constants.HOST, "localhost");
        newRequest.setProperty(Constants.PORT, 9000);

        sender.send(newRequest, new CarbonCallback() {
            @Override
            public void done(CarbonMessage response) {

                DefaultCarbonMessage newResponse = new DefaultCarbonMessage();

                while (true) {
                    newResponse.addMessageBody(response.getMessageBody());
                    if (response.isEndOfMsgAdded() && response.isEmpty()) {
                        newResponse.setEndOfMsgAdded(true);
                        break;
                    }
                }
                response.getProperties().forEach(newResponse::setProperty);
                response.getHeaders().forEach(newResponse::setHeader);

                carbonCallback.done(newResponse);
            }
        });

        return true;
    }

    public void setTransportSender(TransportSender transportSender) {
        sender = transportSender;
    }

    @Override
    public String getId() {
        return "content-aware-passthrough-engine";
    }
}