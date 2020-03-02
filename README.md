miaosha project










### 测试
#### 账号
mobil:13000000000
passwd:000000

#### 接口
> 39.96.72.81:8080/miaosha/do_miaosha
> 现在加了验证码和随机path，只能通过登录测试
#### 登录页面
39.96.72.81:8080/login/to_login

#### jmeter
> 使用jmeter压测时请勿将线程数设置大于1w,seconds=0

Ecs:一核+SSD
Local:八核+HHD

用户对象缓存+页面缓存
Ecs:2000->200+QPS
Local:5000+QPS 

秒杀 /miaosha/do_miaosha
未缓存 5000并发*10s
Ecs:1.2wQPS->1500QPS
Local:2000+QPS

队列+缓存 5000并发*10s
Ecs:2000+QPS
Local:8000+QPS
> 可能因为云服务器是SSD,感觉优化作用不太大
