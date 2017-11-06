## 快速开始

克隆项目文件并安装:
```
git clone https://github.com/yezilong9/JdbcGenerator.git
cd JdbcGenerator
mvn clean install
```
进入项目目录执行插件:
```
cd youProject
mvn com.ubbcc.plugin:jdbc-generator:JPA -DdbUrl=jdbc:mysql://localhost:3306/TestDb?autoReconnect=true -DdbUser=username -DdbPwd=password -Dpath=com.ubbcc -DisAll=true
```


## JdbcGenerator是什么?
一个简单的代码生成器maven插件，好处是不需要依赖项目就能使用，用于自动生成数据库对应表的所有操作，后续会添加controller以及页面等。


## 生成的代码支持哪些风格？
##### 1.Spring data JPA
##### 2.Mybatis(暂无)

## 参数列表

| 参数        | 说明|
| ------------- |:-------------:|
| dbUrl     | 数据库url |
| dbUser     | 数据库username      |
| dbPwd | 数据库password|
| isAll | 是否生成所有表，若true，则tables不起作用|
| tables | 当isAll为false时才起作用，用于指定生成的表，逗号分隔。如：user,login      |
| path | 包路径，如传入：com.ubbcc 则service会生成在com.ubbcc.service|
