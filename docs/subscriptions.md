# Working with Project Subscriptions in Google Pub/Sub

[[  Overview ]](#overview)  [[ Operation details ]](#operation-details)  [[  Sample configuration  ]](#sample-configuration)

### Overview 

The following operations allow you to work with project subscriptions. Click an operation name to see details on how to use it.
For a sample proxy service that illustrates how to work with project subscriptions, see [Sample configuration](#sample-configuration).

| Operation        | Description |
| ------------- |-------------|
| [createTopicSubscription](#creating-a-subscription-to-a-topic)    | Creates a subscription to a specified topic |
| [pullMessage](#retrieving-messages-published-to-a-topic)      | Retrieves messages published to a topic     |

### Operation details

This section provides more details on each of the operations.

#### Creating a subscription to a topic
The createTopicSubscription operation creates a subscription to a topic that you specify.

**createTopicSubscription**
```xml
<googlepubsub.createTopicSubscription>
    <topicName>{$ctx:topicName}</topicName>
    <projectId>{$ctx:projectId}</projectId>
    <subscriptionName>{$ctx:subscriptionName}</subscriptionName>
    <ackDeadlineSeconds>{$ctx:ackDeadlineSeconds}</ackDeadlineSeconds>
    <pushEndpoint>{$ctx:pushEndpoint}</pushEndpoint>
    <attributes>{$ctx:attributes}</attributes>
</googlepubsub.createTopicSubscription>
```

**Properties**
* topicName: The name of the topic for which you want to create a subscription.
* projectId: The unique ID of the project within which the topic is created.
* subscriptionName: The name of the subscription.
* ackDeadlineSeconds[Optional]: The maximum time a subscriber can take to acknowledge a message that is received.
* pushEndpoint[Optional]: The URL that specifies the endpoint to which messages should be pushed.
* attributes[Optional]: Additional endpoint configuration attributes.

**Sample request**

Following is a sample request that can be handled by the createTopicSubscription operation.

```json
{
  "apiUrl":"https://pubsub.googleapis.com",
  "apiVersion":"v1",
  "accessToken": "ya29.GlwAJG2NhgX_NQhxjtF_0G9bzf0FEj_shNWgF_GXmYFpwIxjeYQF0XQXukforOeyTAHoFfSQW0x-OrrZ2lj47Z6k6DAYZuUv3ZhJMl-ll4mvouAbc",
  "projectId":"rising-parser-123456",
  "topicName":"topicA",
  "subscriptionName":"mysubA",
  "ackDeadlineSeconds":"30",
  "pushEndpoint": "https://example.com/push",
  "attributes": {"key": "value1","key2":"values2"}
}
```

**Related Google Pub/Sub documentation**

[https://cloud.google.com/pubsub/docs/reference/rest/v1/projects.subscriptions/create](https://cloud.google.com/pubsub/docs/reference/rest/v1/projects.subscriptions/create)

#### Retrieving messages published to a topic

The pullMessage operation retrieves messages that are published to a topic. 

**pullMessage**
```xml
<googlepubsub.pullMessage>
    <topicName>{$ctx:topicName}</topicName>
    <projectId>{$ctx:projectId}</projectId>
    <subscriptionName>{$ctx:subscriptionName}</subscriptionName>
    <maxMessages>{$ctx:maxMessages}</maxMessages>
    <returnImmediately>{$ctx:returnImmediately}</returnImmediately>
</googlepubsub.pullMessage>
```

**Properties**
* topicName: The name of the topic to which the subscription belongs.
* projectId: The unique ID of the project within which the topic is created.
* subscriptionName: The name of the subscription from which messages should be retrieved.
* maxMessages[optional]: The maximum number of messages to retrieve.
* returnImmediately[Optional]: Set this to true if you want the server to respond immediately.

**Sample request**

Following is a sample request that can be handled by the pullMessage operation.

```json
{
  "apiUrl":"https://pubsub.googleapis.com",
  "apiVersion":"v1",
  "accessToken": "ya29.GlwABbJG2NhgX_NQhxjtF_0G9bzf0FEj_shNWgF_GXmYFpwIxjeYQF0XjcrJukforOeyTAHoFfSQW0x-OrrZ2lj47Z6k6DAYZuUv3ZhJMl-ll4mvouAbc",
  "topicName":"topicA",
  "projectId":"rising-parser-123456",
  "subscriptionName":"mysubA",
  "maxMessages":"2",
  "returnImmediately":"false"
}
```
**Related Google Pub/Sub documentation**

[https://cloud.google.com/pubsub/docs/reference/rest/v1/projects.subscriptions/pull](https://cloud.google.com/pubsub/docs/reference/rest/v1/projects.subscriptions/pull)

#### Sample configuration

Following is a sample proxy service that illustrates how to connect to Google Pub/Sub with the init operation, and then use the createTopicSubscription operation. The sample request for this proxy can be found in the createTopicSubscription sample request. You can use this sample as a template for using other operations in this category.

**Sample Proxy**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<proxy xmlns="http://ws.apache.org/ns/synapse"
 name="createTopicSubscription"
 startOnLoad="true"
 statistics="disable"
 trace="disable"
 transports="http,https">
 <target>
 <inSequence>
        <property expression="json-eval($.accessToken)" name="accessToken"/>
        <property expression="json-eval($.apiUrl)" name="apiUrl"/>
        <property expression="json-eval($.apiVersion)" name="apiVersion"/>
        <property expression="json-eval($.topicName)"  name="topicName"/>
        <property expression="json-eval($.projectId)" name="projectId"/>
        <property expression="json-eval($.subscriptionName)" name="subscriptionName"/>
        <property expression="json-eval($.ackDeadlineSeconds)"  name="ackDeadlineSeconds"/>
        <googlepubsub.init>
            <apiUrl>{$ctx:apiUrl}</apiUrl>
            <apiVersion>{$ctx:apiUrl}</apiVersion>
            <accessToken>{$ctx:accessToken}</accessToken>
        </googlepubsub.init>
        <googlepubsub.createTopicSubscription>
             <topicName>{$ctx:topicName}</topicName>
             <projectId>{$ctx:projectId}</projectId>
             <ackDeadlineSeconds>{$ctx:ackDeadlineSeconds}</ackDeadlineSeconds>
             <subscriptionName>{$ctx:subscriptionName}</subscriptionName>
        </googlepubsub.createTopicSubscription>
        <respond/>
 </inSequence>
 <outSequence>
    <log/>
    <send/>
 </outSequence>
 </target>s
</proxy>
```
