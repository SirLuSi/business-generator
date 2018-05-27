# business-generator  
基于mybatis-generator-maven-plugin实现自动生成serveice和controller代码的插件

## 1.1

## 使用方式
```xml
 <build>
        <plugins>
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.6</version>
                <configuration>
                    <configurationFile>${basedir}/src/main/resources/generatorConfig.xml</configurationFile>
                    <overwrite>true</overwrite>
                    <verbose>true</verbose>
                </configuration>
                <dependencies>
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>5.1.46</version>
                    </dependency>
                    <dependency>
                        <groupId>org.mybatis.generator</groupId>
                        <artifactId>business-generator</artifactId>
                        <version>1.1</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>
```
    
## 注意  
* mybatis-generator-maven-plugin需1.3.6以上版本
## 配置明细
```
<generatorConfiguration>
    <!--<properties resource="config.properties"/>-->

    <context id="Mysql" targetRuntime="MyBatis3Simple" defaultModelType="flat">
        <property name="targetJavaProject" value="${targetJavaProject}"/>
        <property name="basePackage" value="com.a.b.openapi"/>
        <plugin type="com.business.code.generator.ServiceTemplatePlugin">
            <property name="baseService" value="com.a.b.BaseService" />
        </plugin>
        <plugin type="com.business.code.generator.ServiceImplTemplatePlugin">
            <property name="baseServiceImpl" value="com.a.b.BaseServiceImpl" />
            <property name="isOverride" value="true"/>
        </plugin>
        <plugin type="com.business.code.generator.ControllerTemplatePlugin">
        </plugin>
        ......
```
### 注意
* targetJavaProject:用于指定目标项目，一般是 
```
<property name="targetProject" value="../project/src/main/java"/>
```
* basePackage:会把service(接口)  serviceImpl（实现类）  controller  默认生成到此路径
或者你也可以分别定义路径比如以下配置
```
        <property name="targetServicePackage" value="com.a.b.service"/>
        <property name="targetServiceImplPackage" value="com.a.b.service.impl"/>
        <property name="targetControllerPackage" value="com.a.b.controller"/>
```
* baseService:生成的接口service会继承baseService
* baseServiceImpl:生成的实现类会继承此baseServiceImpl
* isOverride:是否覆盖现有文件 true：时  false:否
 ### 关于作者：NeverStop
