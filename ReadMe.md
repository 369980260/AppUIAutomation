# APP UI Automation Framework

一个基于Appium、TestNG，Page Object模式开发的UI自动化测试框架

![](https://github.com/lgxqf/AppUIAutomation/blob/master/doc/structure.png)


##设计理念
* 该框架适用于同一个APP, Android和iOS UI结构基本一致的情况
* 应用Page Object模式提高UI页面操作代码的复用度
* 用Driver类封装所有用到的Appium API, 框架中其它类只通过Driver调用Appium的方法
* 用Driver类封装用到Appium API有以下两点好处：
*      一、屏蔽对Appium API的依赖，如果Appium的某个API官方废弃了，只需修改Driver类封装的相应方法即可
*      二、如果将Appium换成Macaca或其它框架，除了改动Driver类 其它类无需改动
* 在Driver中用findElementById等封装对iOS和Android的元素查找，提高代码的复用，尽可能的避免iOS与Android因查找元素方式不同而写相似的代码


## 类
* Driver : 封装所有用到的Appium方法。作用屏幕对Appium的依赖、提供更方便的函数。
* BasePage : 所有Page类的基类
* BaseTest : 所有Test类的基类
* ConfigUtil : 读取工程配置文件
* ResourceUtil : 读取资源配置文件 
* Util : 工具类，提供一些能用方法
* PageUtil : 封装进入某个页面的方法，方便复杂test case的编写
* TestListener : 监听测试结果，用例执行失败时截图


## 配置文件 
* Config.yml 运行测试时的一些配置项 如包名，重试次数等等。 详见Config.ym内的l注释
* Android 查找找查元素时用到的字符串 如： LOGIN_PAGE_PHONE_TEXT_ID: com.xes.jazhanghui.activity:id/xes_login_username
* iOS 查找元素时用到的字符串 如： LOGIN_PAGE_PHONE_TEXT_ID: name == '用户名'


## Task 
* 框架通过读取 task目录下的yml 运行指定的测试任务


```
在任务的yml中配置四项值
1. port : Appium 端口   
2. udid : 设备ID
3. wdaPort : iOS设备运行的时的WDA port
4. class : 待运行的测试类

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">

<suite name="TestSuite">
    <listeners>
        <listener class-name="framework.TestListener" />
    </listeners>

    <test name="Performance">
        <parameter name = "port" value = "4723"/>     
        <parameter name = "udid" value = "SJE0217B29005225"/>
        <parameter name = "wdaport" value = "8001"/>
        <classes>
            <class name="testcase.weclass.WCPerformanceTestCPU"/>
            <class name="testcase.weclass.WCPerformanceTestBattery"/>
        </classes>
    </test>
</suite>
```

## 如何运行
* 启动Appium，然后运行测试用命
* 方式一 ： 将工程打成Jar包，然后运行命令 java -jar UIAutomation-1.0-fat-tests  ./task/demo.yml
* 方式2  ： IDEA中 右键单击demo.yml ,选择运行。见下图
![](https://github.com/lgxqf/AppUIAutomation/blob/master/doc/Run-By-IDEA.png)

## 参考文档
* Page Object
* How can I configure the maven shade plugin to include test code in my jar?
https://code.i-harness.com/en/q/4e91ca