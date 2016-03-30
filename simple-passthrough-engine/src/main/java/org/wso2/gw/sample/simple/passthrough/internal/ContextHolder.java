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

package org.wso2.gw.sample.simple.passthrough.internal;

import org.wso2.carbon.messaging.CarbonMessageProcessor;
import org.wso2.carbon.messaging.TransportSender;

import java.util.HashMap;
import java.util.Map;

public class ContextHolder {
    private ContextHolder() {
    }

    private static ContextHolder instance = new ContextHolder();
    private Map<String, TransportSender> transportSenders = new HashMap<>();
    private CarbonMessageProcessor engine;

    public CarbonMessageProcessor getEngine() {
        return engine;
    }

    public void setEngine(CarbonMessageProcessor engine) {
        this.engine = engine;
    }

    public static ContextHolder getInstance() {
        return instance;
    }

    public void addTransportSender(TransportSender transportSender) {
        if (engine != null) {
            engine.setTransportSender(transportSender);
        }
        transportSenders.put(transportSender.getId(), transportSender);

    }

    public void removeTransportSender(TransportSender transportSender) {
        transportSenders.remove(transportSender.getId());
    }
}
