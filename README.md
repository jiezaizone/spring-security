# isuwang-security

##1、运行security-demo，访问demo url http://localhost:8080/hello 提示输入用户名密码，配置application.properties， 添加以下配置关闭spring-security默认行为
```
#关闭spring-security 默认配置
security.basic.enabled=false
```
之后重新访问demo url http://localhost:8080/hello 便可以等到正常返回

#第三方登录
1、qq登录
默认地址：/auth/qq

其中auth 是SocialAuthenticationFilter接口配置的默认值，qq 是QQProperties配置的默认providerId

可通过配置更改filterProcessesUrl 更改默认值"auth"


#项目介绍
项目分为三个模块
1、isuwang-security-core
    核心包，isuwang-security-browser、isuwang-security-app 皆依赖这个核心包
    里面封装有其它两个模块的公共方法
    登录校验放方法、验证码发送与校验方法皆写在里面
    获取用户信息的类：MyUserDetailsService
    

2、isuwang-security-browser
    页面登录使用

3、isuwang-security-app
    app登录使用，里面封装了oauth2，校验成功返回token
