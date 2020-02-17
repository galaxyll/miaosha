miaosha project

使用jmeter压测时请勿将线程数设置大于1w,seconds=0




Ecs:一核+SSD
Local:八核+HHD

用户对象缓存+页面缓存
Ecs:200+QPS
Local:5000+QPS 

秒杀 /miaosha/do_miaosha
未缓存 5000并发*10
Ecs:1.2wQPS->1500QPS
Local:2000+QPS

队列+缓存 5000并发*10
Ecs:2000+QPS
Local:8000+QPS