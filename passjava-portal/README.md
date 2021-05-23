PassJava-Portal 基于vue、element-ui构建开发，实现 PassJava 后台管理前端功能，提供一套更优的前端解决方案。

# 启动 Admin 后台

Admin 管理后台的技术选型还是用的 Vue，所以需要使用 npm 工具来安装依赖。

## 1 安装 npm、nvm

使用 homebrew 安装 npm

```sh
brew install npm
```

![](http://cdn.jayh.club/uPic/image-20210416204951214.png)

使用 homebrew 安装 nvm

```
brew install nvm
```

运行结果如下：

![](http://cdn.jayh.club/uPic/image-20210416211547660.png)


## 2 切换镜像源

默认的 npm 使用的是官方的镜像源，我们切换为国内的淘宝镜像源。

``` sh
npm install -g cnpm --registry=https://registry.npm.taobao.org --verbose
```

![](http://cdn.jayh.club/uPic/image-20210416205116707.png)

 ## 3 安装 node_module

仓库里面并没有将依赖包一起上传，因为依赖包太大了，所以可在本地通过如下命令安装依赖包，这个是一次性的，后面不需要再执行。

进入到 passjava-platform/passjava-portal 目录，执行如下命令来安装依赖：

```sh
cnpm install
```

![](http://cdn.jayh.club/uPic/image-20210416211729789.png)

启动前端portal

```sh
npm run dev
```

报错，提示 Node Sass 不兼容当前的系统：

``` sh
Node Sass does not yet support your current environment: OS X Unsupported architecture (arm64) with Unsupported runtime (88)
```

![](http://cdn.jayh.club/uPic/image-20210416212055167.png)

根据网上提供的解决方案，要先卸载 Node Saas

``` sh
cnpm uninstall node-sass
```

但是又提示 chromedriver 安装失败（当前操作系统不兼容），根据网上的解决方案，单独安装，但依旧提示 64 位系统不兼容，于是我把 package.json 文件中的 "chromedriver": "2.27.2" 删掉了，问题迎刃而解！最新的代码已删除该依赖项配置。

先删除之前安装 node_modules

```sh
rm -rf ./node_modules/
```

再次执行卸载 node-sass 的命令：

``` sh
cnpm uninstall node-sass
```

卸载成功后，安装 node-sass

``` sh
cnpm install node-sass  --unsafe-perm --save-dev
```

![image-20210416224957858](http://cdn.jayh.club/uPic/image-20210416224957858.png)

重新安装依赖

``` sh
cnpm install
```

![](http://cdn.jayh.club/uPic/image-20210416225210584.png)

## 4 启动后台

在根目录执行如下命令就可以启动后台了：

```sh
npm run dev
```

启动成功后，会自动打开浏览器，访问的地址是 http://localhost:8081

![](http://cdn.jayh.club/uPic/image-20210416225322860.png)

## 5 登陆后台

账号密码都是 admin，输入验证码即可登录。注意：如果验证码没有出现，说明 RenrenApplication 微服务有异常，请查看 IDEA 中打印出的 log。

登录后台界面如下图所示：

![PassJava后台](http://cdn.jayh.club/uPic/DQDm4seRS85s.png)

## 6 添加题目分类

首先需要给题目进行分类，在后台点击新增类型，如下图所示：

![](http://cdn.jayh.club/uPic/image-20210419215359713.png)

注意：上传图片前需要启动 thirdparty 微服务，且 OSS 配置正确。

## 7 添加面试题

![](http://cdn.jayh.club/uPic/image-20210419220316407.png)