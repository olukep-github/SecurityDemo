该项目是一个demo项目，用于学习Security
没有连接数据库，用户名和密码都是写死的\
学习经验：\
在后端的url是一个接口\
security的自定义能力很强，可以自定义登录方法和处理器\
自定义access方法可以控制接口的访问权限，需要在用户中加上/main.html以后才能有访问该html的权限\
自定义access方法是用于实现自己需要的登录和访问控制逻辑\
通过增加@EnableGlobalMethodSecurity可以增加注解来实现访问控制（另一种方式是通过注释），注解可以用在Service层或Controller层，一般用在Controller\
Controller直接是对应的接口，而Service一般有很多个接口会调用，更加复杂，所以注解一般写在Controller上\
@Secured判断是否含有角色，里面的参数是"ROLE_"，@PreAuthorize是在执行被注释的类或方法之前进行判读，里面的参数是access语句\
记住我功能中的PersistentTokenRepository有循环依赖的报错，虽然通过修改配置文件允许循环依赖，但后续可以优化一下\
跨域请求：网路协议、IP地址、端口有任何一个不同就是跨站请求。\
因为http协议本身是无状态的，需要通过session和cookie来记录客户端身份，在会话过程中，有可能被劫持，导致身份伪造。