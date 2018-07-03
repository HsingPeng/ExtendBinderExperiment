# ExtendBinderExperiment

## INTO

Binder扩展机制：

对Android原本的Binder机制进行扩展，将 Android 四大组件扩展至桌面 Linux 应用程序，使得桌面 Linux 应用程序能够使用 Android 四大组件，从而实现与 Android 应用程序的交互。

> 本项目是我论文的实验DEMO，论文提出机制如下：一种 Android 与桌面 Linux 应用程序协同运行的机制。此机制利用 Change Root 技术和 VNC 技术实现桌面 Linux 应用程序在 Android 系统上的运行，并且通过 Binder 扩展机制将 Android 四大组件扩展至桌面 Linux 应用程序，使得桌面 Linux 应用程序能够使用 Android 四大组件，从而实现与 Android 应用程序的交互。

## DIR STRUCTURE

* linuxClient --> 扩展Binder机制的linux客户端，包含基于python的API SDK和一个demo
    * demo1 --> linux客户端demo
        * extend --> 扩展Binder机制的API SDK
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
