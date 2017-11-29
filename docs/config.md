# Configuring Google Pub/Sub Operations

[[Configuring the connector]](#Configuring-the-connector)  [[Obtaining user credentials]](#Obtaining-user-credentials)

> NOTE: To work with the Google Pub/Sub connector, you need to have a Google Cloud Platform account. If you do not have a Google Cloud Platform account, go to [https://console.cloud.google.com/freetrial](https://console.cloud.google.com/freetrial) and create a Google Cloud Platform trial account.

To use the Google Pub/Sub connector, add the <googlepubsub.init> element in your configuration before any other Google Pub/Sub operation. This configuration authenticates with Google Pub/Sub via user credentials.

Google Pub/Sub uses the OAuth 2.0 protocol for authentication and authorization. All requests to the Google Cloud Pub/Sub API must be authorized by an authenticated user.

## Obtaining user credentials

* **Follow the steps below to obtain a client ID and client secret:**

    1. Go to https://console.developers.google.com/projectselector/apis/credentials, and sign in to your Google account. 
    2. If you do not already have a project, create a new project, click **Create credentials** and then select **OAuth client ID**.
    3. Next, select **Web Application**, and create a client. 
    4. Provide  https://developers.google.com/oauthplayground as the redirect URL under **Authorized redirect URIs**, and then click **Create**. This displays the client ID and client secret.
    5. Make a note of the client ID and client secret that is displayed, and then click **OK**.
    6. Click **Library** on the left navigation pane, and select **Google Cloud Pub/Sub API** under the **Big data** or **Networking** category. 
    7. Click **Enable**. This enables the Google Cloud Pub/Sub API.

* **Follow the steps below to obtain an access token and refresh token:**

    1. Go to https://developers.google.com/oauthplayground, click the gear icon on the top right corner of the screen, and select **Use your own OAuth credentials**. 
    2. Specify the client ID and client secret that you obtained in step 3 above, and click **Close**.
    3. Under Step 1 on the screen, select **Google Cloud Pub/Sub API** from the list of APIs, and select all the scopes that are listed down under **Google Cloud Pub/Sub API**.
    4. Click **Authorize APIs**. This requests for permission to access your profile details.
    5. Click **ALLOW**.
    6. In Step 2 on the screen, click **Exchange authorization code for tokens** to generate and view the access token and refresh token. 

## Configuring the connector
Specify the init method as follows:

**init**
```xml
<googlepubsub.init>
    <apiUrl>{$ctx:apiUrl}</apiUrl>
    <apiVersion>{$ctx:apiVersion}</apiVersion>
    <accessToken>{$ctx:accessToken}</accessToken>
    <clientId>{$ctx:clientId}</clientId>
    <clientSecret>{$ctx:clientSecret}</clientSecret>
    <refreshToken>{$ctx:refreshToken}</refreshToken>
    <blocking>{$ctx:blocking}</blocking>
</googlepubsub.init>
```
**Properties** 
* apiUrl: The application URL of Google Pub/Sub. 
* apiVersion: The version of the Google Pub/Sub API.
* accessToken: The access token that grants access to the Google Pub/Sub API on behalf of a user.
* clientId: The client id provided by the Google developer console.
* clientSecret: The client secret provided by the Google developer console.
* refreshToken: The refresh token provided by the Google developer console, which can be used to obtain new access tokens.
* blocking: Set this to true if you want the connector to perform blocking invocations to Google Pub/Sub.

Now that you have connected to Google Pub/Sub, use the information in the following topics to perform various operations with the connector:

[Working with Projects Topics](topics.md)

[Working with Projects Subscriptions](subscriptions.md)
