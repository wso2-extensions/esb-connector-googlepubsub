# Working with Projects Topics Operation

[[  Overview ]](#overview)  [[ Operation details ]](#operation-details)  [[  Sample configuration  ]](#sample-configuration)

### Overview 

The following operations allow you to carry out project topic operations. Click an operation name to see details on how to use it. For a sample proxy service that illustrates how to carry out the operations, see [Sample configuration](#sample-configuration).

| Operation        | Description |
| ------------- |-------------|
| [createTopic](#create-topic)    | Creates a new topic |
| [publishMessage](#publish-message)      | Adds message to a topic      |

### Operation details

This section provides details on each of the operations.

#### Create Topic
This method allows you to creates a new topic.

**createTopic**
```xml
<googlepubsub.createTopic>
    <topicName>{$ctx:topicName}</topicName>
    <projectId>{$ctx:projectId}</projectId>
</googlepubsub.createTopic>
```

**Properties**
* topicName :- Unique name of the topic
* projectId:- Unique value of the project (Id of the project that is created in the Google API).

**Sample request**

Following is a sample request that can be handled by the createTopic operation.

```json
{
  "apiUrl":"https://pubsub.googleapis.com",
  "apiVersion":"v1",
  "accessToken": "ya29.GlwG2NhgX_NQhxjtF_0G9bzf0FEj_shNWgF_GXmjeYQF0XQXrBjjcrJukforOeyTAHoFfSQW0x-OrrZ2lj47Z6k6DAYZuUv3ZhJMl-ll4mvouAbc",
  "projectId":"rising-parser-123456",
  "topicName":"topicA"
}
```

**Related Google pub/sub documentation**

[https://cloud.google.com/pubsub/docs/reference/rest/v1/projects.topics/create](https://cloud.google.com/pubsub/docs/reference/rest/v1/projects.topics/create)

#### Publish Message

This method allows you to Adds a message to the topic.

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
* topicName :- Unique name of the topic
* projectId:- Unique value of the project (Id of the project that is created in the Google API).
* data:- The message payload.
* attributes[optional]:- Optional attributes for this message.

**Sample request**

Following is a sample request that can be handled by the publishMessage operation.

```json
{
  "apiUrl":"https://pubsub.googleapis.com",
  "apiVersion":"v1",
  "accessToken": "ya29.GlwAJG2NhgX_NQhxjtF_0G9bzf0FEj_shNWgF_GXmYFjeYQF0XQXrBjjcrJukforOeyTAHoFfSQW0x-OrrZ2lj47Z6k6DAYZuUv3ZhJMl-ll4mvouAbc",
  "projectId":"rising-parser-123456",
  "topicName":"topicA",
  "data":"hi",
  "attributes":{"key": "value1","key2":"values2"}
}
```
**Related Google pub/sub documentation**

[https://cloud.google.com/pubsub/docs/reference/rest/v1/projects.topics/publish](https://cloud.google.com/pubsub/docs/reference/rest/v1/projects.topics/publish)

#### Sample configuration

Following is a sample proxy service that illustrates how to connect to Google pub/sub with the init operation and use the createTopic operation. The sample request for this proxy can be found in createTopic sample request. You can use this sample as a template for using other operations in this category.

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
