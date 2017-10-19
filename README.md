# auto
汽车城项目

# 项目运行
1:进入common中执行  mvn install <br>
2:步骤1执行成功后，进入website 执行 mvn jetty:run, 显示成功则项目启动成功 <br>
3:由于项目使用了redis缓存，需要安装redis-server <br>
4:步骤3完成，在本地host文件中加入   127.0.0.1  redis-server <br>
