# ExtendBinderExperiment

## INTO

Binder扩展机制：

对Android原本的Binder机制进行扩展，将 Android 四大组件扩展至桌面 Linux 应用程序，使得桌面 Linux 应用程序能够使用 Android 四大组件，从而实现与 Android 应用程序的交互。

> 本项目是我论文的实验DEMO，论文提出机制如下：一种 Android 与桌面 Linux 应用程序协同运行的机制。此机制利用 Change Root 技术和 VNC 技术实现桌面 Linux 应用程序在 Android 系统上的运行，并且通过 Binder 扩展机制将 Android 四大组件扩展至桌面 Linux 应用程序，使得桌面 Linux 应用程序能够使用 Android 四大组件，从而实现与 Android 应用程序的交互。

## HOW TO WORK

Binder扩展机制在原本的Binder机制上修改添加了三个部分。

1. 在原本的Android通信机制上修改了Android系统服务，在其中添加了Binder消息拦截处理模块。
2. 在一个单独的进程中运行Binder扩展服务管理器。
3. 在桌面Linux应用程序运行环境中的Binder扩展程序启动器。

![Binder扩展机制的数据流示意图](https://github.com/HsingPeng/ExtendBinderExperiment/raw/master/docu/extend_binder_data_flow.png)

上图的箭头表示进程通信的数据流。在Android系统服务进程中添加了Binder消息拦截处理模块，使得原本直接流向Android系统服务的通信请求得到了一次过滤。原生的Binder请求会释放仍然流向Android系统服务，Binder扩展机制的请求则会被拦截发往Binder扩展服务管理器。Binder扩展服务管理器同时接收来自桌面Linux应用程序的数据和Binder消息拦截处理模块的数据，并根据数据内容做出响应，发往目标程序。当请求的桌面Linux应用程序没有运行时，则会通知Binder扩展程序启动器启动目标程序。

**注意：** Binder消息拦截模块目前借助Xposed框架实现，方便修改系统函数。从原理上来说，完全可以脱离Xposed框架，直接在Android系统源码中修改。

## DIR STRUCTURE

* linuxClient --> 扩展Binder机制的linux客户端，包含基于python的API SDK和一个demo
    * demo1 --> linux客户端demo1
        * extend --> 扩展Binder机制的API SDK
        * launch.py --> demo1入口
    * start.py --> Binder扩展程序启动器
* protobuf --> protobuf通信格式约定
* server --> 扩展Binder机制的服务端
    * app --> binder扩展机制的服务端
    * testextendbinder --> 测试程序
    * xposedhook --> binder扩展机制的拦截器

# REQUIREMENT

## BUILD

- protobuf编译：libprotoc 3.5.1
- server编译：android studio 3.0.1

## OS

- android x86 6.0.1
- 桌面Linux环境：Debian 9，包含python 2.7

# INSTALL

1. 在Android系统上安装桌面Linux环境，包括桌面以及VNC服务端，可借助Linux Deploy等程序；
2. 在Android系统中安装VNC Viewer；
3. 在Android系统中安装Xposed框架；
4. 安装扩展Binder机制的服务端APP；
5. 安装xposedhook，并激活插件。

# USE

1. 启动桌面Linux环境中的VNC服务端；
2. 使用VNC Viewer连接上桌面Linux环境；
3. 启动扩展Binder机制的服务端APP；
4. 运行测试DEMO。

# DEMO IMAGE

为了方便需要的人验证本项目，特地上传了本人实验所用的Android虚拟机环境。
虚拟机格式为ovf，可导入 VMware 或 Virtualbox 系列虚拟机软件。

下载地址：
链接:https://pan.baidu.com/s/1LQDsOI_0dnAOUbRkEgN6FA  密码:59eu

# SCREENSHOT

按照要求启动桌面Linux环境，以及Binder扩展服务。

下图是Binder扩展机制的服务端APP：

![Binder扩展机制的服务端APP](https://github.com/HsingPeng/ExtendBinderExperiment/raw/master/docu/extend_binder_server_app.png)

下图是发送Intent给Android原生Activity的示意图，为了方便演示在Android虚拟机外又开了一个VNC窗口显示桌面Linux环境：

![发送Intent给Android原生Activity](https://github.com/HsingPeng/ExtendBinderExperiment/raw/master/docu/send_intent_to_android_activity.gif)

下图是发送Intent给桌面Linux环境中的demo1中的Activity的示意图，为了方便演示在Android虚拟机外又开了一个VNC窗口显示桌面Linux环境：

![发送Intent给demo1的Activity](https://github.com/HsingPeng/ExtendBinderExperiment/raw/master/docu/send_intent_to_linux_activity.gif)
