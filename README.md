# SHEIN Open Platform Java SDK

[![Java Version](https://img.shields.io/badge/Java-8+-blue.svg)](https://www.oracle.com/java/)
[![Maven Central](https://img.shields.io/badge/Maven-v0.0.2-green.svg)](#installation)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)

Official Java SDK for SHEIN Open Platform API integration. This SDK provides easy access to SHEIN's e-commerce APIs with built-in authentication, request signing, and data encryption/decryption capabilities.

## Features

- 🚀 **Easy Integration** - Simple setup with Spring Boot auto-configuration
- 🔐 **Security First** - Built-in request signing and data encryption
- 🛠️ **Flexible** - Support both Spring Boot and standalone Java applications
- 📝 **Well Documented** - Comprehensive JavaDoc and examples
- ⚡ **High Performance** - Connection pooling and timeout configuration

## Installation

### Maven

```xml
<dependency>
    <groupId>io.github.sheinsight</groupId>
    <artifactId>shein-open-sdk</artifactId>
    <version>0.0.2</version>
</dependency>
```

### Gradle

```gradle
implementation 'io.github.sheinsight:shein-open-sdk:0.0.2'
```

## Quick Start

### Spring Boot Integration

1. **Add configuration to `application.yml`:**

```yaml
shein:
  open:
    sdk:
      domain: https://openapi.shein.com
      connectTimeoutMillis: 10000
      requestTimeoutMillis: 10000
      responseTimeoutMillis: 10000
```

2. **Inject and use the SDK:**

```java
@Service
public class SheinApiService {
    
    @Autowired
    private SheinOpenSDKClient sheinOpenSDKClient;
    
    public String exchangeToken(String tempToken) throws OpenSdkException {
        AuthInfo authInfo = AuthInfo.buildAuthInfo(SignModeEnum.APPID)
                .withAppid("YOUR_APP_ID")
                .withAppSecret("YOUR_APP_SECRET");
                
        return sheinOpenSDKClient.getToken(tempToken, authInfo);
    }
    
    public String getProducts() throws OpenSdkException {
        RequestBuilder requestBuilder = new RequestBuilder();
        requestBuilder.setUrl("/open-api/products");
        requestBuilder.setMethod(HttpMethodEnum.GET);
        
        AuthInfo authInfo = AuthInfo.buildAuthInfo(SignModeEnum.OPEN_KEY_ID)
                .withOpenKeyId("YOUR_OPEN_KEY_ID")
                .withSecretKey("YOUR_SECRET_KEY");
        requestBuilder.setAuthInfo(authInfo);
        
        String encryptedResponse = sheinOpenSDKClient.get(requestBuilder);
        return sheinOpenSDKClient.decryptResponse(encryptedResponse, "YOUR_SECRET_KEY");
    }
}
```

### Standalone Java Application

```java
public class SheinApiExample {
    public static void main(String[] args) throws OpenSdkException {
        // Initialize configuration
        SheinAppConfig config = new SheinAppConfig();
        config.setDomain("https://openapi.shein.com");
        
        // Create SDK client
        SheinOpenSDKClient client = SheinOpenSDK.getInstance(config);
        
        // Exchange temporary token
        AuthInfo authInfo = AuthInfo.buildAuthInfo(SignModeEnum.APPID)
                .withAppid("YOUR_APP_ID")
                .withAppSecret("YOUR_APP_SECRET");
                
        String response = client.getToken("your-temp-token", authInfo);
        System.out.println("Token response: " + response);
    }
}
```

## API Reference

### Core Methods

| Method | Description |
|--------|-------------|
| `getToken(String tempToken, AuthInfo authInfo)` | Exchange temporary token for permanent access credentials |
| `get(RequestBuilder requestBuilder)` | Send GET requests to SHEIN Open Platform APIs |
| `post(RequestBuilder requestBuilder)` | Send POST requests to SHEIN Open Platform APIs |
| `decryptSecretKey(String encryptedData, String secretKey)` | Decrypt encrypted secret key from token exchange |
| `decryptEventData(String encryptedData, String secretKey)` | Decrypt webhook event callback data |
| `decryptResponse(String encryptedResponse, String secretKey)` | Decrypt encrypted API response data from gateway |

### RequestBuilder Configuration

```java
RequestBuilder requestBuilder = new RequestBuilder();
requestBuilder.setUrl("/your/api/endpoint");
requestBuilder.setMethod(HttpMethodEnum.POST);
requestBuilder.setBody("{\"key\":\"value\"}");

// Add custom headers
Map<String, String> headers = new HashMap<>();
headers.put("Custom-Header", "value");
requestBuilder.setHeaders(headers);

// Add query parameters (for GET requests)
Map<String, String> queryParams = new HashMap<>();
queryParams.put("page", "1");
queryParams.put("size", "20");
requestBuilder.setQueryParams(queryParams);

// Set authentication info
AuthInfo authInfo = AuthInfo.buildAuthInfo(SignModeEnum.OPEN_KEY_ID)
        .withOpenKeyId("YOUR_OPEN_KEY_ID")
        .withSecretKey("YOUR_SECRET_KEY");
requestBuilder.setAuthInfo(authInfo);
```

## Configuration Options

| Property | Description | Default |
|----------|-------------|---------|
| `shein.open.sdk.domain` | SHEIN Open Platform API domain | Required |
| `shein.open.sdk.connectTimeoutMillis` | Connection timeout in milliseconds | 10000 |
| `shein.open.sdk.requestTimeoutMillis` | Request timeout in milliseconds | 10000 |
| `shein.open.sdk.responseTimeoutMillis` | Response timeout in milliseconds | 10000 |
| `shein.open.sdk.followRedirects` | Whether to follow HTTP redirects | true |
| `shein.open.sdk.maxConnections` | Maximum HTTP connections | 32 |

## Error Handling

```java
try {
    String response = sheinOpenSDKClient.get(requestBuilder);
} catch (OpenSdkException e) {
    // Handle SDK-specific exceptions
    logger.error("SHEIN API call failed: {}", e.getMessage(), e);
}
```

## Requirements

- Java 8 or higher
- Spring Boot 2.7+ (for auto-configuration features)

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Support

For questions and support:
- 📧 Create an issue on GitHub
- 📖 Check the [documentation](docs/)
- 💬 Join our developer community

## Changelog

See [CHANGELOG.md](CHANGELOG.md) for details on recent changes.