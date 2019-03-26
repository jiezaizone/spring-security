#提供的访问接口
##登录接口
http://192.168.3.248:7070/authentication/login?username=15976043004&password=000000
####参数介绍
    usernam 用户名
    password 密码
   
  请求需要添加header，header参数
    headerKey: Authorization
    headerValue:  Basic aXN1d2FuZzppc3V3YW5nLXNlY3VyaXR5
  其中aXN1d2FuZzppc3V3YW5nLXNlY3VyaXR5 为IsuwangAuthorizationServerConfig 类中配置的withClient、secret base64加密得到，如 "isuwang:isuwang-security" base64加密
  
##校验token
http://localhost:7070/oauth/check_token?token=3cd0f53c-aa00-42bf-9a53-3d4836875f7a
####参数介绍
    token access_token
   
  请求需要添加header，header参数
    headerKey: Authorization
    headerValue:  Basic aXN1d2FuZzppc3V3YW5nLXNlY3VyaXR5
    
##刷新token
http://localhost:7070/oauth/token?refresh_token=d6a0849d-dfd0-47db-83be-01055dcddb5d&grant_type=refresh_token
####参数介绍
    refresh_token refresh_token
    grant_type 取值固定为"refresh_token"
   
  请求需要添加header，header参数
    headerKey: Authorization
    headerValue:  Basic aXN1d2FuZzppc3V3YW5nLXNlY3VyaXR5
    
##注销
http://localhost:5050/oauth/revoke-token?token=3c32b850-5b6a-44b8-952c-ec5426576a9b
####参数介绍
    token access_token
   
  请求需要添加header，header参数
    headerKey: Authorization
    headerValue:  Basic aXN1d2FuZzppc3V3YW5nLXNlY3VyaXR5
    