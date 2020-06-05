【权限管理框架】
RBAC基于资源的访问控制（Resource Based Access Contaol ）是以资源为中心进行访问控制.

【主要技术栈】
网关spring cloud gateway：限流+降级+熔断+鉴权
安全模块 spring cloud security：jwt+权限管理动态加载配置+token结合缓存
Nacos：注册中心+配置中心
数据库：MYSQL5.7 + mybatis
缓存：Redis
统一异常处理
统一分页操作
mybatis逆向工程
swagger2接口文档