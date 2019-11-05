#security-app接口访问api
- ##登录接口

demo:

```http
http://localhost:7070/authentication/login?username=15976043004&password=000000
```

>需要添加header：Authorization:Basic aXN1d2FuZzppc3V3YW5nLXNlY3VyaXR5
>  数据说明：Basic aXN1d2FuZzppc3V3YW5nLXNlY3VyaXR5为IsuwangAuthorizationServerConfig 类中配置的withClient、secret base64加密得到，如 "isuwang:isuwang-security" base64加密，既是访问的凭证。
>
>参数介绍
>
>1、username 用户名
>2、password 密码
>
>



- ##校验token

```http
http://localhost:7070/oauth/check_token?token=3cd0f53c-aa00-42bf-9a53-3d4836875f7a
```

>需要添加header：Authorization:Basic aXN1d2FuZzppc3V3YW5nLXNlY3VyaXR5
>
>参数介绍
>
>1、token




- ##刷新token

```http
http://localhost:7070/oauth/token?refresh_token=d6a0849d-dfd0-47db-83be-01055dcddb5d&grant_type=refresh_token
```

> 需要添加header：Authorization:Basic aXN1d2FuZzppc3V3YW5nLXNlY3VyaXR5
>
> 参数介绍
>
> 1、refresh_token
>
> 2、grant_type ：固定值为"refresh_token"

- ##注销
  

```http
http://localhost:7070/oauth/revoke-token?token=3c32b850-5b6a-44b8-952c-ec5426576a9b
```

> 需要添加header：Authorization:bearer 22164eea-bf58-4d84-9343-e159f5cdea6f
>
> 说明：22164eea-bf58-4d84-9343-e159f5cdea6f为登录后的access_token
>
> 参数介绍
>
> 1、token
>

- ##获取当前登录用户

  ```http
  http://localhost:7070/oauth/getCurrentUser?token=3c32b850-5b6a-44b8-952c-ec5426576a9b
  ```

  