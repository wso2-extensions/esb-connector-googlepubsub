package org.wso2.carbon.connector.integration.test.googlepubsub;

/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
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

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.connector.integration.test.base.ConnectorIntegrationTestBase;
import org.wso2.connector.integration.test.base.RestResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GooglePubSubConnectorIntegrationTest extends ConnectorIntegrationTestBase {

    private Map<String, String> esbRequestHeadersMap = new HashMap<String, String>();
    private Map<String, String> apiRequestHeadersMap = new HashMap<String, String>();

    /**
     * Set up the environment.
     */
    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {
        String connectorName =
                System.getProperty("connector_name") + "-connector-" + System.getProperty("connector_version") + ".zip";
        init(connectorName);
        esbRequestHeadersMap.put("Content-Type", "application/json");
        apiRequestHeadersMap.put("Content-Type", "application/json");
        String apiEndpointUrl =
                "https://www.googleapis.com/oauth2/v3/token?grant_type=refresh_token&client_id=" + connectorProperties
                        .getProperty("clientId") + "&client_secret=" + connectorProperties.getProperty("clientSecret")
                        + "&refresh_token=" + connectorProperties.getProperty("refreshToken");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndpointUrl, "POST", apiRequestHeadersMap);
        final String accessToken = apiRestResponse.getBody().getString("access_token");
        connectorProperties.put("accessToken", accessToken);
        apiRequestHeadersMap.put("Authorization", "Bearer " + accessToken);
        apiRequestHeadersMap.putAll(esbRequestHeadersMap);
    }

    /**
     * Positive test case for createTopic method with mandatory parameters.
     */
    @Test(groups = { "wso2.esb" },
          description = "googlePubSub {createTopic} integration test with mandatory parameters.")
    public void testCreateTopicWithMandatoryParameters() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:createTopic");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "createTopicMandatory.json");
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        String name = "projects/" + connectorProperties.getProperty("projectId") + "/topics/" + connectorProperties
                .getProperty("topicName");
        Assert.assertEquals(esbRestResponse.getBody().get("name"), name);
    }

    /**
     * Positive test case for createTopic method with Negative parameters.
     */
    @Test(groups = { "wso2.esb" },
          dependsOnMethods = { "testCreateTopicWithMandatoryParameters" },
          description = "googlePubSub {createTopic} integration test with Negative parameters.")
    public void testCreateTopicWithNegativeParameters() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:createTopic");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "createTopicNegative.json");
        String apiEndPoint =
                connectorProperties.getProperty("apiUrl") + "/" + connectorProperties.getProperty("apiVersion")
                        + "/projects/invalid/topics/" + connectorProperties.getProperty("topicName");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "PUT", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), apiRestResponse.getHttpStatusCode());
        Assert.assertEquals(esbRestResponse.getBody().getJSONObject("error").get("message"),
                apiRestResponse.getBody().getJSONObject("error").get("message"));
        Assert.assertEquals(esbRestResponse.getBody().getJSONObject("error").get("status"),
                apiRestResponse.getBody().getJSONObject("error").get("status"));
    }

    /**
     * Positive test case for createTopicSubscription method with mandatory parameters.
     */
    @Test(groups = { "wso2.esb" },
          dependsOnMethods = { "testCreateTopicWithNegativeParameters" },
          description = "googlePubSub {createTopicSubscription} integration test with mandatory parameters.")
    public void testCreateTopicSubscriptionWithMandatoryParameters() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:createTopicSubscription");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "createTopicSubscriptionMandatory.json");
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        String topic = "projects/" + connectorProperties.getProperty("projectId") + "/topics/" + connectorProperties
                .getProperty("topicName");
        String subscription =
                "projects/" + connectorProperties.getProperty("projectId") + "/subscriptions/" + connectorProperties
                        .getProperty("subscriptionName");
        Assert.assertEquals(esbRestResponse.getBody().get("name"), subscription);
        Assert.assertEquals(esbRestResponse.getBody().get("topic"), topic);
    }

    /**
     * Positive test case for createTopicSubscription method with optional parameters.
     */
    @Test(groups = { "wso2.esb" },
          dependsOnMethods = { "testCreateTopicWithNegativeParameters" },
          description = "googlePubSub {createTopicSubscription} integration test with optional parameters.")
    public void testCreateTopicSubscriptionWithOptionalParameters() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:createTopicSubscription");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "createTopicSubscriptionOptional.json");
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        String topic = "projects/" + connectorProperties.getProperty("projectId") + "/topics/" + connectorProperties
                .getProperty("topicName");
        String subscription =
                "projects/" + connectorProperties.getProperty("projectId") + "/subscriptions/" + connectorProperties
                        .getProperty("subscriptionName2");
        Assert.assertEquals(esbRestResponse.getBody().get("name"), subscription);
        Assert.assertEquals(esbRestResponse.getBody().get("topic"), topic);
        Assert.assertEquals(esbRestResponse.getBody().get("ackDeadlineSeconds").toString(),
                connectorProperties.getProperty("ackDeadlineSeconds"));
    }

    /**
     * Positive test case for createTopicSubscription method with Negative parameters.
     */
    @Test(groups = { "wso2.esb" },
          description = "googlePubSub {createTopicSubscription} integration test with Negative parameters.")
    public void testCreateTopicSubscriptionWithNegativeParameters() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:createTopicSubscription");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "createTopicSubscriptionNegative.json");
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 404);
        Assert.assertTrue(esbRestResponse.getBody().has("error"));
    }

    /**
     * Positive test case for publishMessage method with mandatory parameters.
     */
    @Test(groups = { "wso2.esb" },
          dependsOnMethods = { "testCreateTopicWithNegativeParameters" },
          description = "googlePubSub {publishMessage} integration test with mandatory parameters.")
    public void testPublishMessageWithMandatoryParameters() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:publishMessage");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "publishMessageMandatory.json");
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertTrue(esbRestResponse.getBody().has("messageIds"));
        String messageId = (String) esbRestResponse.getBody().getJSONArray("messageIds").get(0);
        connectorProperties.put("messageId", messageId);
    }

    /**
     * Positive test case for publishMessage method with optional parameters.
     */
    @Test(groups = { "wso2.esb" },
          dependsOnMethods = { "testCreateTopicWithNegativeParameters" },
          description = "googlePubSub {publishMessage} integration test with optional parameters.")
    public void testpublishMessageWithOptionalParameters() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:publishMessage");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "publishMessageOptional.json");
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertTrue(esbRestResponse.getBody().has("messageIds"));
    }

    /**
     * Positive test case for publishMessage method with negative parameters.
     */
    @Test(groups = { "wso2.esb" },
          dependsOnMethods = { "testCreateTopicWithNegativeParameters" },
          description = "googlePubSub {publishMessage} integration test with negative parameters.")
    public void testPublishMessageWithNegativeParameters() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:publishMessage");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "publishMessageNegative.json");
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 400);
        Assert.assertTrue(esbRestResponse.getBody().has("error"));
    }

    /**
     * Positive test case for publishMessage method with mandatory parameters.
     */
    @Test(groups = { "wso2.esb" },
          dependsOnMethods = {
                  "testPublishMessageWithMandatoryParameters", "testCreateTopicSubscriptionWithMandatoryParameters"
          },
          description = "googlePubSub {publishMessage} integration test with mandatory parameters.")
    public void testPullMessageWithMandatoryParameters() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:pullMessage");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "pullMessageMandatory.json");
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertTrue(esbRestResponse.getBody().has("receivedMessages"));
        JSONObject messageId = (JSONObject) esbRestResponse.getBody().getJSONArray("receivedMessages").get(0);
        Assert.assertEquals(messageId.getJSONObject("message").get("messageId"),
                connectorProperties.getProperty("messageId"));
    }

    /**
     * Positive test case for publishMessage method with negative parameters.
     */
    @Test(groups = { "wso2.esb" },
          description = "googlePubSub {publishMessage} integration test with negative parameters.")
    public void testPullMessageWithNegativeParameters() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:pullMessage");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "pullMessageNegative.json");
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 404);
        Assert.assertTrue(esbRestResponse.getBody().has("error"));
    }
}
