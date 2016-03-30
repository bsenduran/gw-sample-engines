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

package org.wso2.gw.sample.simple.passthrough;

import org.wso2.carbon.messaging.CarbonCallback;
import org.wso2.carbon.messaging.CarbonMessage;
import org.wso2.carbon.messaging.CarbonMessageProcessor;
import org.wso2.carbon.messaging.Constants;
import org.wso2.carbon.messaging.TransportSender;

public class Engine implements CarbonMessageProcessor {

    TransportSender sender;

    public boolean receive(CarbonMessage carbonMessage, CarbonCallback carbonCallback) throws Exception {

        carbonMessage.setProperty(Constants.TO, "/services/SimpleStockQuoteService");
        carbonMessage.setProperty(Constants.HOST, "localhost");
        carbonMessage.setProperty(Constants.PORT, 9000);

        sender.send(carbonMessage, new CarbonCallback() {
            @Override
            public void done(CarbonMessage response) {
                carbonCallback.done(response);
            }
        });

        return true;
    }

    public void setTransportSender(TransportSender transportSender) {
        sender = transportSender;
    }

    @Override
    public String getId() {
        return "simple-passthrough-engine";
    }
}