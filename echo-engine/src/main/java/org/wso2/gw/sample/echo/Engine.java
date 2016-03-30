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

package org.wso2.gw.sample.echo;

import org.wso2.carbon.messaging.CarbonCallback;
import org.wso2.carbon.messaging.CarbonMessage;
import org.wso2.carbon.messaging.CarbonMessageProcessor;
import org.wso2.carbon.messaging.DefaultCarbonMessage;
import org.wso2.carbon.messaging.TransportSender;

public class Engine implements CarbonMessageProcessor {

    public boolean receive(CarbonMessage carbonMessage, final CarbonCallback carbonCallback) throws Exception {

        DefaultCarbonMessage echoResponse = new DefaultCarbonMessage();

        while (true) {
            echoResponse.addMessageBody(carbonMessage.getMessageBody());
            if (carbonMessage.isEndOfMsgAdded() && carbonMessage.isEmpty()) {
                echoResponse.setEndOfMsgAdded(true);
                break;
            }
        }

        carbonMessage.getProperties().forEach(echoResponse::setProperty);
        carbonMessage.getHeaders().forEach(echoResponse::setHeader);

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