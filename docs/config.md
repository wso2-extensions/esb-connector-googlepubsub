# Configuring Google Pub Sub Operations

[[Configuration]](#configuration)  [[Get User Credentials]](#to-get-user-credentials)

> NOTE: To work with Google Pub/Sub connector you need to have a  Google Cloud Platform account. If you don't have an account use this URL [https://console.cloud.google.com/freetrial](https://console.cloud.google.com/freetrial) to create Google Cloud Platform trail account.

To use the Google Pub/Sub connector, add the <googlepubsub.init> element in your configuration before any other Google Pub/Sub operations. This configuration authenticates with Google Pub/Sub by configuring the user credentials using OAuth2 authentication for accessing the Google account.

## To Get User Credentials

* **Creating a Client ID and Client Secret**

    1. navigate to the URL  https://console.developers.google.com/projectselector/apis/credentials  and log in to your google account. 
    2. If you do not already have a project, create a new project and navigate to Create Credential -> OAuth client ID.
    3. Select the Web Application option and create a client. Provide  https://developers.google.com/oauthplayground  as the redirect URL under Authorized redirect URIs and click on Create. The client ID and client secret will then be displayed. 
    4. Click on the Library on the side menu, and select **Google cloud Pub/Sub API** under the **Big data** or **Networking** category. Click Enable.

* **Obtaining the Access Token and Refresh Token**

    1. Navigate to the https://developers.google.com/oauthplayground URL and click on the gear wheel at the top right corner of the screen and select the option Use your own OAuth credentials. Provide the client ID and client secret you previously created and click on Close.
    2. Now under Step 1, select Google cloud Pub/Sub API from the list of APIs and check all the scopes listed down and click on Authorize APIs. You will then be prompted to allow permission, click on Allow.
    3. In Step 2, click on Exchange authorization code for tokens to generate an display the access token and refresh token. 

## Configuration
Use the init method as below.

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
* apiUrl: The application URL of Google pub/sub. 
* accessToken:Access token through the OAuth2 playground.
* apiVersion: Version of the google pub/sub API.
* clientId: Value of your client id, which can be obtained at Google developer console.
* clientSecret: Value of your client secret, which can be obtained at Google developer console.
* refreshToken: Refresh token to exchange with an access token.
* blocking: Set to true to perform the blocking invocations to google pub/sub.

Now that you have connected to Google cloud Pub/Sub, use the information in the following topics to perform various operations with the connector.

[Working with Projects Topics](topics.md)

[Working with Projects Subscriptions](subscriptions.md)