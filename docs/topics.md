# Working with Project Topics in Google Pub/Sub

[[  Overview ]](#overview)  [[ Operation details ]](#operation-details)  [[  Sample configuration  ]](#sample-configuration)

### Overview 

The following operations allow you to work with project topics. Click an operation name to see details on how to use it.
For a sample proxy service that illustrates how to work with project topics, see [Sample configuration](#sample-configuration).

| Operation        | Description |
| ------------- |-------------|
| [createTopic](#creating-a-topic)    | Creates a new topic. |
| [publishMessage](#publishing-messages)      | Publishes messages to a specified topic. |

### Operation details

This section provides more details on each of the operations.

#### Creating a topic
The createTopic operation creates a new topic with a name that you specify.

**createTopic**
```xml
<googlepubsub.createTopic>
    <topicName>{$ctx:topicName}</topicName>
    <projectId>{$ctx:projectId}</projectId>
</googlepubsub.createTopic>
```

**Properties**
* topicName: The name of the topic that you are creating.
* projectId: The unique ID of the project within which you want to create the topic.

**Sample request**

Following is a sample request that can be handled by the createTopic operation.

```json
{
  "apiUrl":"https://pubsub.googleapis.com",
  "apiVersion":"v1",
  "accessToken": "ya29.GlwG2NhgX_NQhxjtF_0G9bzf0FEj_shNWgF_GXmjeYQF0XQXrBjjcrJukforOeyTAHoFfSQW0x-OrrZ2lj47Z6k6DAYZuUv3ZhJMl-ll4mvouAbc",
  "topicName":"topicA",
  "projectId":"rising-parser-123456"
}
```

**Related Google Pub/Sub documentation**

[https://cloud.google.com/pubsub/docs/reference/rest/v1/projects.topics/create](https://cloud.google.com/pubsub/docs/reference/rest/v1/projects.topics/create)

#### Publishing messages

The publishMessage operation publishes messages to a specified topic.

**publishMessage**
```xml
<googlepubsub.publishMessage>
    <topicName>{$ctx:topicName}</topicName>
    <projectId>{$ctx:projectId}</projectId>
    <data>{$ctx:data}</data>
    <attributes>{$ctx:attributes}</attributes>
</googlepubsub.publishMessage>
```

**Properties**
* topicName: The unique name of the topic to which messages should be published.
* projectId: The unique ID of the project within which the topic is created.
* data: The message payload.
* attributes[optional]: Additional attributes of the message.

**Sample request**

Following is a sample request that can be handled by the publishMessage operation.

```json
{
  "apiUrl":"https://pubsub.googleapis.com",
  "apiVersion":"v1",
  "accessToken": "ya29.GlwAJG2NhgX_NQhxjtF_0G9bzf0FEj_shNWgF_GXmYFjeYQF0XQXrBjjcrJukforOeyTAHoFfSQW0x-OrrZ2lj47Z6k6DAYZuUv3ZhJMl-ll4mvouAbc",
  "topicName":"topicA",
  "projectId":"rising-parser-123456",
  "data":"hi",
  "attributes":{"key": "value1","key2":"values2"}
}
```
**Related Google Pub/Sub documentation**

[https://cloud.google.com/pubsub/docs/reference/rest/v1/projects.topics/publish](https://cloud.google.com/pubsub/docs/reference/rest/v1/projects.topics/publish)

#### Sample configuration

Following is a sample proxy service that illustrates how to connect to Google Pub/Sub with the init operation, and then use the createTopic operation. The sample request for this proxy can be found in the createTopic sample request. You can use this sample as a template for using other operations in this category.

**Sample Proxy**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<proxy xmlns="http://ws.apache.org/ns/synapse"
 name="createTopic"
 startOnLoad="true"
 statistics="disable"
 trace="disable"
 transports="http,https">
 <target>
 <inSequence>
        <property expression="json-eval($.accessToken)" name="accessToken"/>
        <property expression="json-eval($.apiUrl)" name="apiUrl"/>
        <property expression="json-eval($.apiVersion)" name="apiVersion"/>
        <property name="topicName" expression="json-eval($.topicName)"/>
        <property name="projectId" expression="json-eval($.projectId)"/>
        <googlepubsub.init>
            <apiUrl>{$ctx:apiUrl}</apiUrl>
            <apiVersion>{$ctx:apiVersion}</apiVersion>
            <accessToken>{$ctx:accessToken}</accessToken>
        </googlepubsub.init>
        <googlepubsub.createTopic>
            <topicName>{$ctx:topicName}</topicName>
            <projectId>{$ctx:projectId}</projectId>
        </googlepubsub.createTopic>
        <respond/>
 </inSequence>
 <outSequence>
    <log/>
    <send/>
 </outSequence>
 </target>
</proxy>
```
