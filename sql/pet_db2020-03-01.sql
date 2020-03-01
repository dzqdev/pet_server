/*
Navicat MySQL Data Transfer

Source Server         : 47.101.221.184
Source Server Version : 50724
Source Host           : 47.101.221.184:3306
Source Database       : pet_db

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2020-03-01 23:10:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(32) DEFAULT NULL COMMENT '标题',
  `description` varchar(128) DEFAULT NULL COMMENT '描述',
  `author` varchar(64) DEFAULT NULL COMMENT '作者',
  `content` text COMMENT '内容',
  `view_count` int(11) DEFAULT '0' COMMENT '查看次数',
  `pet_id` int(11) NOT NULL COMMENT '所属宠物狗类别id',
  `type` varchar(16) DEFAULT NULL COMMENT '类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='文章表';

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('1', '新闻', '金毛寻回犬（Golden Retriever）是单猎犬，比较流行的狗的品种。在猎捕野禽的寻回犬中培养出来的，游泳的续航力极佳。金毛寻回犬是最常见的家犬之一，它很容易养，有耐心并且对主人要求不多，只要定期运动，食物和兽医体检就可以了。', '345', '<div><div><h1>问题提出</h1>\n<p>笔者现在是一个大三将要实习的学生，前段时间在掘金看到了一篇文章<a target=\"_blank\" href=\"https://juejin.im/post/5d153267e51d4510624f9809\" rel=\"\">《vue 248个知识点（面试题）为你保驾护航》</a>，作者在里面写到他在面试一个5年前端工作经验的小伙子中的一些问题，其中有一个问题引起了我的注意，这个问题就是：<code>不错，那我问下你 “vue为什么要求组件模板只能有一个根元素？”</code></p>\n<p>笔者入坑学习 Vue 也算是有段时间了，文档和一些大佬的项目、文章也多少看了一些，但是似乎这个问题思考的很少，每次在写项目或是 demo 的时候直接就是代码往上怼，深入的地方也仅限于了解了路由和模板编译的源码实现，也没有深究过为什么只能有一个根元素。这个问题我觉得对于刚入门 Vue 学习的朋友来说是一个很好的问题，在刚开始的时候就要知其所以然才能更好的掌握一门语言。</p>\n<p>在 google 和掘金简单找了一下，相关的问题资料似乎很少，也少有人写这个专题，那就不多吹了，今天就由我来带大家分析一下这个问题。以下都是我自己查阅了一些资料之后的个人思考，如果你觉得有问题，欢迎在评论区指正和一起探讨~</p>\n<h1>Vue 实例</h1>\n<p>首先，我觉得这个问题要先从 Vue 的实例开始讲起。Vue 的实例一般都是长成下面这个样子，不同的只是 id 名的不同。</p>\n<pre><code lang=\"html\">&lt;div id=\"app\"&gt;&lt;/div&gt;\n复制代码</code></pre><pre><code lang=\"js\">var vm = new Vue({\n    el: \'#app\',\n    data: {},\n    methods: {}\n    ...\n})\n复制代码</code></pre><p>这就是 Vue 实例的基本结构，并不陌生。从这里可以看到，el 的指定是一个 id 为 app 的 div 元素，Vue 实例接管了对它的控制，减少了我们的 DOM 操作，需要被 vm 控制的元素全部加在它的内部。如果是需要控制不同的部分，这就需要多个 Vue 的实例来实现。疑问就来了，为什么需要不同的 Vue 实例来接管?</p></div><br>作者：Horace_<br>链接：https://juejin.im/post/5e57d0f3e51d4526ec0d39ca<br>来源：掘金<br>著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。</div>', '6', '6', 'news', '2020-02-28 00:25:34');
INSERT INTO `article` VALUES ('2', '阿拉斯加介绍', '阿拉斯加雪橇犬也叫阿拉斯加犬，是最古老的极地雪橇犬之一，它的名字取自爱斯基摩人的伊努伊特族的一个叫做马拉缪特的部落。这个部落生活在阿拉斯加西部一个叫做科策布(Kotzebue)的岸边。成年阿拉斯加犬有着安静、高雅的气质，对主人非常忠心。', '123', '<p><span style=\"font-weight: bold;\">阿拉斯加介绍asas</span></p>', '1', '5', 'news', null);

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(128) DEFAULT NULL COMMENT '图片地址',
  `alt` varchar(64) DEFAULT NULL COMMENT '描述',
  `state` int(11) DEFAULT '0' COMMENT '显示状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='轮播图';

-- ----------------------------
-- Records of banner
-- ----------------------------
INSERT INTO `banner` VALUES ('2', 'http://localhost:8888/images/017c895a07ce51a80121985ce38531.jpg@2o.jpg', '松狮犬', '0', '2020-02-23 15:11:59');
INSERT INTO `banner` VALUES ('3', 'http://localhost:8888/images/0125f15a07ce4fa801204a0e504f29.jpg@2o.jpg', '秋田', '0', '2020-02-23 15:12:25');
INSERT INTO `banner` VALUES ('4', 'http://localhost:8888/images/bbed-fzrwiaz2685794.jpg', '金毛犬', '0', '2020-02-23 15:12:58');
INSERT INTO `banner` VALUES ('5', 'http://localhost:8888/images/2f16a8f369e1db74d41e565bad48a19c.jpg', '高加索犬', '1', '2020-02-23 15:13:16');

-- ----------------------------
-- Table structure for boarding_home
-- ----------------------------
DROP TABLE IF EXISTS `boarding_home`;
CREATE TABLE `boarding_home` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '名称',
  `main_pic` varchar(128) DEFAULT NULL COMMENT '主图',
  `sub_images` text COMMENT '副图',
  `promise` varchar(128) DEFAULT NULL COMMENT '基地承诺',
  `feature` varchar(128) DEFAULT NULL COMMENT '服务特色',
  `custom_comment` varchar(128) DEFAULT NULL COMMENT '客服点评',
  `concat` varchar(32) DEFAULT NULL COMMENT '联系方式',
  `location` varchar(64) DEFAULT NULL COMMENT '位置信息',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='寄养所信息';

-- ----------------------------
-- Records of boarding_home
-- ----------------------------
INSERT INTO `boarding_home` VALUES ('1', 'xx寄养所', 'http://localhost:8888/images/wallhaven-498mx1.jpg', 'http://localhost:8888/images/u=3087414443,1029214301&fm=26&gp=0.jpg;http://localhost:8888/images/timg (1).jpg;http://localhost:8888/images/u=1024641224,2906293824&fm=26&gp=0.jpg', '承诺', '服务特色', '点评', '13666666666', '阿萨飒飒', '2020-02-23 15:40:27');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL COMMENT '回复内容',
  `discussion_id` int(11) DEFAULT NULL COMMENT '讨论id',
  `author_id` int(20) DEFAULT NULL COMMENT '发表人id',
  `parent_id` int(11) DEFAULT NULL COMMENT '上一级评论',
  `to_uid` int(20) DEFAULT NULL COMMENT '评论目标',
  `level` varchar(1) DEFAULT NULL COMMENT '0 文章的评论 1 评论的评论 2 评论的回复 @',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论';

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for dict
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '字典名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据字典';

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO `dict` VALUES ('7', 'article_type', '文章类型', null);
INSERT INTO `dict` VALUES ('8', '其他', '其他', null);
INSERT INTO `dict` VALUES ('10', 'display_staus', '显示状态', null);

-- ----------------------------
-- Table structure for dict_detail
-- ----------------------------
DROP TABLE IF EXISTS `dict_detail`;
CREATE TABLE `dict_detail` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(255) DEFAULT NULL COMMENT '字典标签',
  `value` varchar(255) NOT NULL COMMENT '字典值',
  `sort` varchar(255) DEFAULT NULL COMMENT '排序',
  `dict_id` bigint(11) DEFAULT NULL COMMENT '字典id',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK5tpkputc6d9nboxojdbgnpmyb` (`dict_id`) USING BTREE,
  CONSTRAINT `FK5tpkputc6d9nboxojdbgnpmyb` FOREIGN KEY (`dict_id`) REFERENCES `dict` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据字典详情';

-- ----------------------------
-- Records of dict_detail
-- ----------------------------
INSERT INTO `dict_detail` VALUES ('14', '常见问题', 'problem', '999', '7', null);
INSERT INTO `dict_detail` VALUES ('15', '新闻', 'news', '999', '7', null);
INSERT INTO `dict_detail` VALUES ('16', '狗狗训练', 'train', '999', '7', null);
INSERT INTO `dict_detail` VALUES ('17', '养护知识', 'conserve', '999', '7', null);

-- ----------------------------
-- Table structure for discussion
-- ----------------------------
DROP TABLE IF EXISTS `discussion`;
CREATE TABLE `discussion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `view_count` int(11) DEFAULT '0' COMMENT '查看数量',
  `author_id` int(11) NOT NULL COMMENT '作者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='讨论';

-- ----------------------------
-- Records of discussion
-- ----------------------------

-- ----------------------------
-- Table structure for hospital
-- ----------------------------
DROP TABLE IF EXISTS `hospital`;
CREATE TABLE `hospital` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '名称',
  `main_pic` varchar(128) DEFAULT NULL COMMENT '主图',
  `sub_images` text COMMENT '副图',
  `content` text COMMENT '内容',
  `concat` varchar(32) DEFAULT NULL COMMENT '联系方式',
  `location` varchar(64) DEFAULT NULL COMMENT '位置信息',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='医院信息';

-- ----------------------------
-- Records of hospital
-- ----------------------------
INSERT INTO `hospital` VALUES ('1', '广东省第二人民医院', 'http://localhost:8888/images/timg.jpg', 'http://localhost:8888/images/timg (3).jpg;http://localhost:8888/images/timg (6).jpg;http://localhost:8888/images/u=417685397,1826062512&fm=26&gp=0.jpg', '<div><h2>前言</h2>\n<p>前面的文章里，我们了解到 canal 可以从 MySQL 中感知数据的变化。这是因为它模拟 MySQL slave 的交互协议，伪装自己为 MySQL slave ，从而实现了主从复制。</p>\n<p>正是了解到这一点，笔者有两个问题便一直萦绕于心：</p>\n<ul>\n<li>它是如何模拟 MySQL slave 交互协议的？</li>\n<li>它又是怎么解析 binlog 日志的呢？</li>\n</ul>\n<p>今天，笔者准备就着这两个问题，扒拉扒拉 canal 的代码，一探究竟。</p>\n<h2>一、MySQL 主从复制</h2>\n<p>在谈 canal 之前，我们有必要再重温下 MySQL 主从复制的原理。</p>\n<p></p><figure><br><figcaption></figcaption></figure><p></p>\n<p>总结上图的流程如下：</p>\n<ul>\n<li>MySQL master 将数据变更写入二进制日志 (binary log , 其中记录叫做二进制日志事件binary log events)；</li>\n<li>MySQL slave 将 master 的 binary log events 拷贝到它的中继日志 (relay log)；</li>\n<li>MySQL slave 重放 relay log 中的事件，将数据变更反映到自己的数据库。</li>\n</ul>\n<h2>二、canal 原理</h2><figure><figcaption></figcaption></figure><p></p>\n<p>上图就很形象的描述了 canal 的角色。它的原理也很简单：</p>\n<ul>\n<li>canal模拟mysql slave的交互协议，伪装自己为mysql slave，向mysql master发送dump协议；</li>\n<li>mysql master收到dump请求，开始推送binary log给slave(也就是canal)；</li>\n<li>canal解析binary log对象(原始为byte流)；</li>\n<li>canal将解析后的对象，根据业务场景，分发到比如 MySQL 、RocketMQ 或者 ES 中。</li>\n</ul>\n<h2>三、源码启动</h2>\n<p>看完了 MySQL 主从复制和 canal 原理之后，为了方便 debug ，笔者已经在 GitHub Fork 了源码，并导入本地。</p>\n<p>可以找到 <code>com.alibaba.otter.canal.deployer.CanalLauncher</code> 类，它就是 canal 独立版本启动的入口类。</p>\n<p>在这里，直接运行 main 方法即可运行 canal ，和在 <code>/canal/bin/startup.sh</code> 中效果一样。</p>\n<p>事实上，canal 的代码比较多，在架构上又分了很多模块设计，比如事件解析器、事件消费、内存存储、服务实例、元数据、高可用等。</p>\n<p>本文不打算面面俱到介绍每一个的实现，那就得正儿八经写一个 canal 系列才行。主要还是为了开头我们提出的那两个问题。</p>\n<h2>四、如何模拟slave ?</h2>\n<p>上面我们已经说到，<code>CanalLauncher</code>是canal 启动的入口类。</p>\n<p>运行 main 方法之后， canal 会先做很多准备工作。比如加载配置文件、初始化消息队列、启动 canal Admin、加载Spring配置、注册钩子程序等。</p>\n<p>canal 模拟 slave 协议，是在<code>EventParser</code>模块中开始进行的。</p></div><span style=\"font-weight: bold;\">作者：清幽之地<br>链接：https://juejin.im/post/5e57ac4cf265da57663fd721<br>来源：掘金<br>著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。</span>', '1388888888', '广东省广州市海珠区新港中路466号大院', '2020-02-23 15:28:29');

-- ----------------------------
-- Table structure for img_entity
-- ----------------------------
DROP TABLE IF EXISTS `img_entity`;
CREATE TABLE `img_entity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(128) DEFAULT NULL COMMENT '图片地址',
  `entity_type` varchar(16) NOT NULL COMMENT '图片对应实体类型',
  `entity_id` int(11) NOT NULL COMMENT '对应的实体id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片-实体中间表';

-- ----------------------------
-- Records of img_entity
-- ----------------------------

-- ----------------------------
-- Table structure for pet
-- ----------------------------
DROP TABLE IF EXISTS `pet`;
CREATE TABLE `pet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `description` text COMMENT '描述',
  `content` text COMMENT '内容',
  `main_pic` varchar(128) DEFAULT NULL COMMENT '主图片',
  `sub_images` text COMMENT '副图',
  `view_count` int(11) DEFAULT '0' COMMENT '查看次数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='宠物狗类别';

-- ----------------------------
-- Records of pet
-- ----------------------------
INSERT INTO `pet` VALUES ('5', '阿拉斯加', '阿拉斯加', '<p>&nbsp; 阿拉斯加光有外表好看体型庞大，但是不实用，它性格温顺，对人友善，跟谁都亲，要是有吃的，估计摇着尾巴叼着家里的东西给小偷，你要是想着这么庞大的大型犬给你看家护主？门都没有，这货跟哈士奇一个样，谁牵跟谁走！出门最好给它带牵引绳哦，不然成了别人家的狗了哦！</p><div style=\"text-align: center;\"><img src=\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=3829144956,2505407671&amp;fm=173&amp;app=25&amp;f=JPEG?w=640&amp;h=427&amp;s=F1A49B5560625F1F32A9E118030070F1\" style=\"max-width:50%;\"></div>', 'http://localhost:8888/images/7707795326a035b06e26d16a2bcd30cb.jpg', 'http://localhost:8888/images/wallhaven-48dox1.jpg;http://localhost:8888/images/wallhaven-498mx1.jpg;http://localhost:8888/images/622762d0f703918f3c528de35c3d269759eec41c.jpg', '5', '2020-02-23 14:26:44', null);
INSERT INTO `pet` VALUES ('6', '金毛犬', '金毛寻回犬是最常见的家犬之一，它很容易养，有耐心并且对主人要求不多，只要定期运动，食物和兽医体检就可以了。它属于匀称、有力、活泼的一个犬种，特征是稳固、身体各部位配合合理，腿既不太长也不笨拙，表情友善，个性热情、机警、自信而且不怕生，性格讨人喜欢。金毛犬最早是一种寻回猎犬，大多作为导盲犬与宠物狗。对小孩子或者婴儿十分友善。金毛犬是位列世界犬种智商排行的第四名。', '<p>&nbsp;源自苏格兰的金毛寻回犬能够风靡世人，除了它一身金黄色的被毛吸引众生外，同时也因它们天生温驯的个性，令人对它爱不释手。要追溯金毛寻回犬的历史，大概要穿梭到1865年的时候，那时苏格兰流行打猎，因此擅于捕猎野生动物的中型犬种就大受狩猎家欢迎。</p><div label-module=\"para\">&nbsp; 其后苏格兰有一位贵族就尝试以黄色的拉布拉多寻回犬(Labrador Retriever)同己绝种的拉布水猎犬混合繁殖，品种经过改良后，成为了今天的金毛寻回犬。</div><div label-module=\"para\">金毛寻回犬在英国风行一时，知名度不断提高，深受英国人的欢迎，1903年第一只金毛寻回犬在英国狗会正式登记，8年后英国金毛寻回犬会成立，可谓名噪英伦。其后有一些到英国旅行的游客，更把金毛寻回犬带回美国、加拿大等地，作为打猎之用。直到1932年，AKC成立了金毛寻回犬会(GRCA)，如今会员已多达数千名。50年后，金毛寻回犬在AKC犬只服从比赛中连续获得三届冠军。2001年，金毛寻回犬被AKC选为十大最受欢迎注册犬种之一，排名紧次于<a target=\"_blank\" href=\"https://baike.baidu.com/item/%E6%8B%89%E5%B8%83%E6%8B%89%E5%A4%9A%E7%8C%8E%E7%8A%AC/452983\">拉布拉多猎犬</a>，风头可谓一时无二<sup>&nbsp;[1]</sup><a name=\"ref_[1]_108363\">&nbsp;</a>&nbsp;。</div><p><br></p><p><br></p>', 'http://localhost:8888/images/17a4a27b3e5a76ba119b49073b7fac1c.jpeg', 'http://localhost:8888/images/d763e06c6add296565acbdba8d0e8c63.jpg;http://localhost:8888/images/575f86cf7910de92386b403a4c7f7f81.jpg;http://localhost:8888/images/dae4fc3dcf1b572c57abbe982f7637f5.jpg', '3', '2020-02-29 09:37:44', null);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) DEFAULT NULL COMMENT '用户名',
  `account` varchar(16) DEFAULT NULL COMMENT '登录账号',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `last_login` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of user
-- ----------------------------
