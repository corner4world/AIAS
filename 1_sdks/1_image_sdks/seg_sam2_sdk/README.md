### 官网：
[官网链接](https://www.aias.top/)

### 下载模型，放置于models目录
- 链接: https://pan.baidu.com/s/1m4NAiKTDdhcTWROdkwofFg?pwd=in39

### 模型使用方法：
- 1. 用模型的名字搜索代码，找到模型的加载位置
- 2. 然后更新模型路径（代码里默认加载路径是：项目/models 文件夹）
- 3. 具体模型加载方法
- http://aias.top/AIAS/guides/load_model.html


### SAM2抠图 SDK
SAM2（‌Segment Anything Model 2）是由‌Meta公司发布的先进图像和视频分割模型。‌它是Segment Anything Model（SAM）的升级版本，
SAM是Meta的‌FAIR实验室之前发布的一款用于图像分割的基础模型，能够在给定提示的情况下生成高质量的对象掩模。‌
SAM2模型的主要特点是其通用性和灵活性，它能够处理各种复杂的图像和视频分割任务，无论是简单的对象识别还是复杂的场景理解，SAM2都能提供准确的结果。
这使得它在许多实际应用场景中都非常有用，例如在‌医疗影像分析、‌自动驾驶、‌安防监控等领域都有着广泛的应用前景。

#### 1. 通用一键抠图
包括2个模型：满足不同精度，速度的要求。
- 大模型
- 小模型

- 测试图片（效果图）
  ![general](https://aias-home.oss-cn-beijing.aliyuncs.com/assets/sam2.png)


#### 开源项目地址
- https://github.com/facebookresearch/sam2


#### 模型导出
#### 1. 环境配置
```text
GPU 环境配置：
1. 创建虚拟环境
   conda create -n nv118 python=3.10.11 -y
   conda activate nv118

2. 安装pytorch
   conda install pytorch torchvision torchaudio pytorch-cuda=11.8 -c pytorch -c nvidia

3. 安装Sam2
   pip install opencv-python matplotlib
   pip install git+https://github.com/facebookresearch/sam2.git

4.安装huggingface_hub
pip install huggingface_hub

5. 安装 onnx
   pip install onnx
```


#### 2. 模型导出脚本位置
- 0_tutorials\python\sam2_export


#### 帮助文档：
- https://aias.top/guides.html
- 1.性能优化常见问题:
- https://aias.top/AIAS/guides/performance.html
- 2.引擎配置（包括CPU，GPU在线自动加载，及本地配置）:
- https://aias.top/AIAS/guides/engine_config.html
- 3.模型加载方式（在线自动加载，及本地配置）:
- https://aias.top/AIAS/guides/load_model.html
- 4.Windows环境常见问题:
- https://aias.top/AIAS/guides/windows.html