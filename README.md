# SHEIN Open Platform SDK

To facilitate external developers' access to the SHEIN Open Platform, the platform provides both SDK JAR packages and source code for developers to use. Developers can choose to directly import the JAR package or perform secondary development based on this source code.


1. Integration Methods
    1. Place the JAR file into the relevant directory of the project.
    2. Include the necessary dependencies in the pom.xml file.

        ```java
         <dependency>
                    <groupId>com.shein.open</groupId>
                    <artifactId>shein-open-sdk</artifactId>
                    <version>0.0.1-SNAPSHOT</version>
                    <scope>system</scope>
                    <systemPath>${pom.basedir}/src/main/resources/lib/shein-sdk-java-0.0.1-SNAPSHOT.jar</systemPath>
                </dependency>
        ```

       3. Instance Registration(**It is recommended to instantiate sheinOpenSDKClient using the official method.**)
           1. SpringBoot projects provide automatic registration for integration.

               ```java
               1、Configure automatic loading settings
               shein:
                 open:
                    sdk:
                      domain: http://xxxxx-xxxx.sheincorp.xx
                2、@Resource
                   private SheinOpenSDKClient sheinOpenSDKClient;
                
                    public String sdkTest() {
                       try {
                           AuthInfo authInfo = AuthInfo.buildAuthInfo(SignModeEnum.APPID).withAppid("1179xxxxxxxxxxxxxxxxxxADB").withAppSecret("AB195F1xxxxxxxxxxxxxxxxxxx8A3B948");
                           return sheinOpenSDKClient.getToken("xxxxxx",authInfo);
                       } catch (OpenSdkException e) {
                           throw new RuntimeException(e);
                       }
                   }
               ```

           2. Non-SpringBoot projects provide manual registration implementation.

               ```java
               1、 SheinAppConfig config = new SheinAppConfig();
                   config.setDomain("http://openapi-test01.sheincorp.cn");
                   SheinOpenSDKClient client = SheinOpenSDK.getInstance(config);
                   
               2、 void getByToken() throws OpenSdkException {
                       String tempToken = "xxxxx-210b-xxxxxxxx-5f1da6ea0a9b";
                       AuthInfo authInfo = AuthInfo.buildAuthInfo(SignModeEnum.APPID).withAppid("1179xxxxxxxxxxxxxxxxxxADB").withAppSecret("AB195F1xxxxxxxxxxxxxxxxxxx8A3B948");
                       String response = client.getToken(tempToken,authInfo);
                   }
               ```

2、Basic Capabilities Provided by the SDK

- getToken(String tempToken, AuthInfo authInfo)
    - This method is used to exchange a temporary token for a permanent access key.
- get(RequestBuilder requestBuilder)
    - This method is used to send GET requests to the interfaces provided by the Open Platform.
      - RequestBuilder：Request parameter object
        - method：Request type enum
            - GET
            - POST
        - url：Target URL
        - headers：Custom header information
        - queryParams：GET request parameters
        - body：POST request body parameters
        - signModeEnum：Signing method, default is openKeyId signing
- post(RequestBuilder requestBuilder)
    - This method is used to send POST requests to the interfaces provided by the Open Platform.
    - RequestBuilder: Request parameter object
        - Same as get
- decryptSecretKey(String encryptedData, String secretKey)
    - Used to decrypt the encrypted SecretKey obtained from getByToken.
- decryptEventData(String encryptedData, String secretKey)
    - Used to decrypt the encrypted information in the webhook event callback from the Open Platform.