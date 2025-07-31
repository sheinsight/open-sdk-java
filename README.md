# SHEIN Open Platform Java SDK

[English](#english) | [中文](#中文)

---

## English

[![Java Version](https://img.shields.io/badge/Java-8+-blue.svg)](https://www.oracle.com/java/)
[![Maven Central](https://img.shields.io/badge/Maven-v0.0.2-green.svg)](#installation)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)

Official Java SDK for SHEIN Open Platform API integration. This SDK provides easy access to SHEIN's e-commerce APIs with built-in authentication, request signing, and data encryption/decryption capabilities.

### Features

- 🚀 **Easy Integration** - Simple setup with Spring Boot auto-configuration
- 🔐 **Security First** - Built-in request signing and data encryption
- 🛠️ **Flexible** - Support both Spring Boot and standalone Java applications
- 📝 **Well Documented** - Comprehensive JavaDoc and examples
- ⚡ **High Performance** - Connection pooling and timeout configuration

### Installation

#### Maven

```xml
<dependency>
    <groupId>io.github.sheinsight</groupId>
    <artifactId>shein-open-sdk</artifactId>
    <version>0.0.2</version>
</dependency>
```

#### Gradle

```gradle
implementation 'io.github.sheinsight:shein-open-sdk:0.0.2'
```

### Quick Start

#### Spring Boot Integration

1. **Add configuration to `application.yml`:**

```yaml
shein:
  open:
    sdk:
      # Choose the appropriate domain based on your business needs
      # Available options: https://openapi.shein.com or https://openapi.shein.cn
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
        
        // API response is returned directly, no decryption needed
        return sheinOpenSDKClient.get(requestBuilder);
    }
}
```

#### Standalone Java Application

```java
public class SheinApiExample {
    public static void main(String[] args) throws OpenSdkException {
        // Initialize configuration
        SheinAppConfig config = new SheinAppConfig();
        // Choose the appropriate domain based on your business needs
        // Available options: https://openapi.shein.com or https://openapi.shein.cn
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

### API Reference

#### Core Methods

| Method | Description |
|--------|-------------|
| `getToken(String tempToken, AuthInfo authInfo)` | Exchange temporary token for permanent access credentials |
| `get(RequestBuilder requestBuilder)` | Send GET requests to SHEIN Open Platform APIs |
| `post(RequestBuilder requestBuilder)` | Send POST requests to SHEIN Open Platform APIs |
| `decryptSecretKey(String encryptedData, String secretKey)` | Decrypt encrypted secret key from token exchange response |
| `decryptEventData(String encryptedData, String secretKey)` | Decrypt webhook event callback data |
| `decryptResponse(String encryptedResponse, String secretKey)` | Decrypt encrypted data when SHEIN calls your endpoints |

#### Usage Scenarios

**When calling SHEIN APIs (you → SHEIN):**
- Use `get()` and `post()` methods directly
- API responses are returned as plain text, no decryption needed

**When SHEIN calls your endpoints (SHEIN → you):**
- Use `decryptEventData()` for webhook event notifications
- Use `decryptResponse()` for other encrypted callback data

**When exchanging tokens:**
- Use `decryptSecretKey()` to decrypt the secret key from token exchange response

#### RequestBuilder Configuration

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

### Domain Configuration

The SDK supports two domains. Choose the appropriate one based on your business needs:

| Domain | URL |
|--------|-----|
| Domain 1 | `https://openapi.shein.com` |
| Domain 2 | `https://openapi.shein.cn` |

### Configuration Options

| Property | Description | Default |
|----------|-------------|---------|
| `shein.open.sdk.domain` | SHEIN Open Platform API domain<br/>Options: `https://openapi.shein.com` or `https://openapi.shein.cn` | Required |
| `shein.open.sdk.connectTimeoutMillis` | Connection timeout in milliseconds | 10000 |
| `shein.open.sdk.requestTimeoutMillis` | Request timeout in milliseconds | 10000 |
| `shein.open.sdk.responseTimeoutMillis` | Response timeout in milliseconds | 10000 |
| `shein.open.sdk.followRedirects` | Whether to follow HTTP redirects | true |
| `shein.open.sdk.maxConnections` | Maximum HTTP connections | 32 |

### Error Handling

```java
try {
    String response = sheinOpenSDKClient.get(requestBuilder);
} catch (OpenSdkException e) {
    // Handle SDK-specific exceptions
    logger.error("SHEIN API call failed: {}", e.getMessage(), e);
}
```

### Requirements

- Java 8 or higher
- Spring Boot 2.7+ (for auto-configuration features)

---

## 中文

SHEIN开放平台官方Java SDK，提供便捷的API集成，内置身份验证、请求签名和数据加密/解密功能。

### 特性

- 🚀 **简单集成** - Spring Boot自动配置，快速上手
- 🔐 **安全优先** - 内置请求签名和数据加密
- 🛠️ **灵活使用** - 支持Spring Boot和独立Java应用
- 📝 **文档完善** - 详细的JavaDoc和使用示例
- ⚡ **高性能** - 连接池和超时配置优化

### 安装

#### Maven

```xml
<dependency>
    <groupId>io.github.sheinsight</groupId>
    <artifactId>shein-open-sdk</artifactId>
    <version>0.0.2</version>
</dependency>
```

#### Gradle

```gradle
implementation 'io.github.sheinsight:shein-open-sdk:0.0.2'
```

### 快速开始

#### Spring Boot集成

1. **在`application.yml`中添加配置：**

```yaml
shein:
  open:
    sdk:
      # Choose the appropriate domain based on your business needs
      # Available options: https://openapi.shein.com or https://openapi.shein.cn
      domain: https://openapi.shein.com
      connectTimeoutMillis: 10000
      requestTimeoutMillis: 10000
      responseTimeoutMillis: 10000
```

2. **注入并使用SDK：**

```java
@Service
public class SheinApiService {
    
    @Autowired
    private SheinOpenSDKClient sheinOpenSDKClient;
    
    public String exchangeToken(String tempToken) throws OpenSdkException {
        AuthInfo authInfo = AuthInfo.buildAuthInfo(SignModeEnum.APPID)
                .withAppid("您的应用ID")
                .withAppSecret("您的应用密钥");
                
        return sheinOpenSDKClient.getToken(tempToken, authInfo);
    }
    
    public String getProducts() throws OpenSdkException {
        RequestBuilder requestBuilder = new RequestBuilder();
        requestBuilder.setUrl("/open-api/products");
        requestBuilder.setMethod(HttpMethodEnum.GET);
        
        AuthInfo authInfo = AuthInfo.buildAuthInfo(SignModeEnum.OPEN_KEY_ID)
                .withOpenKeyId("您的开放密钥ID")
                .withSecretKey("您的密钥");
        requestBuilder.setAuthInfo(authInfo);
        
        // API响应直接返回，无需解密
        return sheinOpenSDKClient.get(requestBuilder);
    }
}
```

#### 独立Java应用

```java
public class SheinApiExample {
    public static void main(String[] args) throws OpenSdkException {
        // 初始化配置
        SheinAppConfig config = new SheinAppConfig();
        config.setDomain("https://openapi.shein.com");
        
        // 创建SDK客户端
        SheinOpenSDKClient client = SheinOpenSDK.getInstance(config);
        
        // 交换临时令牌
        AuthInfo authInfo = AuthInfo.buildAuthInfo(SignModeEnum.APPID)
                .withAppid("您的应用ID")
                .withAppSecret("您的应用密钥");
                
        String response = client.getToken("您的临时令牌", authInfo);
        System.out.println("令牌响应: " + response);
    }
}
```

### API参考

#### 核心方法

| 方法 | 描述 |
|------|------|
| `getToken(String tempToken, AuthInfo authInfo)` | 交换临时令牌获取永久访问凭证 |
| `get(RequestBuilder requestBuilder)` | 向SHEIN开放平台API发送GET请求 |
| `post(RequestBuilder requestBuilder)` | 向SHEIN开放平台API发送POST请求 |
| `decryptSecretKey(String encryptedData, String secretKey)` | 解密令牌交换响应中的加密密钥 |
| `decryptEventData(String encryptedData, String secretKey)` | 解密webhook事件回调数据 |
| `decryptResponse(String encryptedResponse, String secretKey)` | 解密SHEIN主动调用您的接口时的加密数据 |

#### 使用场景

**当您调用SHEIN API时（您 → SHEIN）：**
- 直接使用 `get()` 和 `post()` 方法
- API响应以明文返回，无需解密

**当SHEIN调用您的接口时（SHEIN → 您）：**
- 使用 `decryptEventData()` 解密webhook事件通知数据
- 使用 `decryptResponse()` 解密其他加密回调数据

**当交换令牌时：**
- 使用 `decryptSecretKey()` 解密令牌交换响应中的密钥

### 域名配置

SDK支持两个域名，请根据业务需求选择合适的域名：

| 域名 | URL |
|------|-----|
| 域名1 | `https://openapi.shein.com` |
| 域名2 | `https://openapi.shein.cn` |

### 配置选项

| 配置项 | 描述 | 默认值 |
|--------|------|--------|
| `shein.open.sdk.domain` | SHEIN开放平台API域名<br/>可选项: `https://openapi.shein.com` 或 `https://openapi.shein.cn` | 必填 |
| `shein.open.sdk.connectTimeoutMillis` | 连接超时时间（毫秒） | 10000 |
| `shein.open.sdk.requestTimeoutMillis` | 请求超时时间（毫秒） | 10000 |
| `shein.open.sdk.responseTimeoutMillis` | 响应超时时间（毫秒） | 10000 |
| `shein.open.sdk.followRedirects` | 是否自动跟随HTTP重定向 | true |
| `shein.open.sdk.maxConnections` | 最大HTTP连接数 | 32 |

### 异常处理

```java
try {
    String response = sheinOpenSDKClient.get(requestBuilder);
} catch (OpenSdkException e) {
    // 处理SDK特定异常
    logger.error("SHEIN API调用失败: {}", e.getMessage(), e);
}
```

### 系统要求

- Java 8或更高版本
- Spring Boot 2.7+（用于自动配置功能）

### 贡献

1. Fork本仓库
2. 创建特性分支 (`git checkout -b feature/amazing-feature`)
3. 提交更改 (`git commit -m 'Add some amazing feature'`)
4. 推送到分支 (`git push origin feature/amazing-feature`)
5. 创建Pull Request

### 许可证

本项目采用Apache License 2.0许可证 - 查看[LICENSE](LICENSE)文件了解详情。

### 支持

如有问题和技术支持：
- 📧 在GitHub上创建issue
- 📖 查看[文档](docs/)
- 💬 加入我们的开发者社区

### 更新日志

查看[CHANGELOG.md](CHANGELOG.md)了解最新更改详情。
