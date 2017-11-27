# Working with Projects Subscription Operation

[[  Overview ]](#overview)  [[ Operation details ]](#operation-details)  [[  Sample configuration  ]](#sample-configuration)

### Overview 

The following operations allow you to carry out project subscription operations. Click an operation name to see details on how to use it. For a sample proxy service that illustrates how to carry out the operations, see [Sample configuration](#sample-configuration).

| Operation        | Description |
| ------------- |-------------|
| [createTopicSubscription](#create-topic-subscription)    | Creates a subscription to a given topic |
| [pullMessage](#pull-message)      | Pulls messages from the server     |

### Operation details

This section provides details on each of the operations.

#### Create Topic Subscription
This method allows you to creates a subscription to a given topic.

**createTopicSubscription**
```xml
<googlepubsub.createTopicSubscription>
    <topicName>{$ctx:topicName}</topicName>
    <projectId>{$ctx:projectId}</projectId>
    <ackDeadlineSeconds>{$ctx:ackDeadlineSeconds}</ackDeadlineSeconds>
    <subscriptionName>{$ctx:subscriptionName}</subscriptionName>
    <pushEndpoint>{$ctx:pushEndpoint}</pushEndpoint>
    <attributes>{$ctx:attributes}</attributes>
</googlepubsub.createTopicSubscription>
```

**Properties**
* topicName :- Unique name of the topic.
* projectId:- Unique value of the project (Id of the project that is created in the Google API).
* subscriptionName:- Name of the subscription.
* ackDeadlineSeconds[Optional]:- This value is the maximum time after a subscriber receives a message before the subscriber should acknowledge the message.
* pushendpoint[Optional]:- A URL locating the endpoint to which messages should be pushed.
* attributes[Optional]:- Endpoint configuration attributes.

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
  "attributes": {"key": "value1","key2":"values2"},
  "pushEndpoint": "https://example.com/push"
}
```

**Related Google pub/sub documentation**

[https://cloud.google.com/pubsub/docs/reference/rest/v1/projects.subscriptions/create](https://cloud.google.com/pubsub/docs/reference/rest/v1/projects.subscriptions/create)

#### Pull Message

This method allows you to Pulls messages from the server.

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
* topicName :- Unique name of the topic
* projectId:- Unique value of the project (Id of the project that is created in the Google API).
* subscriptionName:- Name of the subscription.
* attributes[optional]:- Optional attributes for this message.
* returnImmediately[Optional]:- If this field set to true, the system will respond immediately.

**Sample request**

Following is a sample request that can be handled by the pullMessage operation.

```json
{
  "apiUrl":"https://pubsub.googleapis.com",
  "apiVersion":"v1",
  "accessToken": "ya29.GlwABbJG2NhgX_NQhxjtF_0G9bzf0FEj_shNWgF_GXmYFpwIxjeYQF0XjcrJukforOeyTAHoFfSQW0x-OrrZ2lj47Z6k6DAYZuUv3ZhJMl-ll4mvouAbc",
  "projectId":"rising-parser-123456",
  "topicName":"topicA",
  "subscriptionName":"mysubA",
  "maxMessages":"2",
  "returnImmediately":"false"
}
```
**Related Google pub/sub documentation**

[https://cloud.google.com/pubsub/docs/reference/rest/v1/projects.subscriptions/pull](https://cloud.google.com/pubsub/docs/reference/rest/v1/projects.subscriptions/pull)

#### Sample configuration

Following is a sample proxy service that illustrates how to connect to Google pub/sub with the init operation and use the createTopicSubscription operation. The sample request for this proxy can be found in createTopicSubscription sample request. You can use this sample as a template for using other operations in this category.

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