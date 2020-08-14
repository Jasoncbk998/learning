#  1 存储引擎

show engines;

![image-20200730095334805](assets\image-20200730095334805.png)



看你的mysql当前默认的存储引擎:

 show variables like '%storage_engine%';



![image-20200730095622529](assets\image-20200730095408304.png)

## 1.1 MyISAM 和InnoDB

![image-20200730095622529](assets\image-20200730095622529.png)

系统表使用MyISAM,不占用过多资源

## 1.2 其他存储引擎介绍

```
1、InnoDB存储引擎   实际应用
InnoDB是MySQL的默认事务型引擎，它被设计用来处理大量的短期(short-lived)事务。除非有非常特别的原因需要使用其他的存储引擎，否则应该优先考虑InnoDB引擎。
 
2、MyISAM存储引擎
MyISAM提供了大量的特性，包括全文索引、压缩、空间函数(GIS)等，但MyISAM不支持事务和行级锁，有一个毫无疑问的缺陷就是崩溃后无法安全恢复。
 
3、Archive引擎
Archive档案存储引擎只支持INSERT和SELECT操作，在MySQL5.1之前不支持索引。
Archive表适合日志和数据采集类应用。
根据英文的测试结论来看，Archive表比MyISAM表要小大约75%，比支持事务处理的InnoDB表小大约83%。
 
4、Blackhole引擎
Blackhole引擎没有实现任何存储机制，它会丢弃所有插入的数据，不做任何保存。但服务器会记录Blackhole表的日志，所以可以用于复制数据到备库，或者简单地记录到日志。但这种应用方式会碰到很多问题，因此并不推荐。 
 
5、CSV引擎 
CSV引擎可以将普通的CSV文件作为MySQL的表来处理，但不支持索引。
CSV引擎可以作为一种数据交换的机制，非常有用。
CSV存储的数据直接可以在操作系统里，用文本编辑器，或者excel读取。
 
6、Memory引擎
如果需要快速地访问数据，并且这些数据不会被修改，重启以后丢失也没有关系，那么使用Memory表是非常有用。Memory表至少比MyISAM表要快一个数量级。
 
7、Federated引擎
Federated引擎是访问其他MySQL服务器的一个代理，尽管该引擎看起来提供了一种很好的跨服务器的灵活性，但也经常带来问题，因此默认是禁用的。
```



# 2 性能分析

## 2.1 性能下降原因分析

数据过多  => 分库分表

关联表太多 ,太多join  =>sql优化

没有充分利用索引 =>索引建立

服务器调优各个参数配置=> 调整my.cnf



## 2.2 sql预热

![image-20200730100311969](assets\image-20200730100311969.png)

### 2.2.1 sql语句

```sql
CREATE TABLE `t_dept` (
 `id` INT(11) NOT NULL AUTO_INCREMENT,
 `deptName` VARCHAR(30) DEFAULT NULL,
 `address` VARCHAR(40) DEFAULT NULL,
 PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 
CREATE TABLE `t_emp` (
 `id` INT(11) NOT NULL AUTO_INCREMENT,
 `name` VARCHAR(20) DEFAULT NULL,
  `age` INT(3) DEFAULT NULL,
 `deptId` INT(11) DEFAULT NULL,
empno int  not null,
 PRIMARY KEY (`id`),
 KEY `idx_dept_id` (`deptId`)
 #CONSTRAINT `fk_dept_id` FOREIGN KEY (`deptId`) REFERENCES `t_dept` (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 
 
 
INSERT INTO t_dept(deptName,address) VALUES('华山','华山');
INSERT INTO t_dept(deptName,address) VALUES('丐帮','洛阳');
INSERT INTO t_dept(deptName,address) VALUES('峨眉','峨眉山');
INSERT INTO t_dept(deptName,address) VALUES('武当','武当山');
INSERT INTO t_dept(deptName,address) VALUES('明教','光明顶');
 INSERT INTO t_dept(deptName,address) VALUES('少林','少林寺');
 
INSERT INTO t_emp(NAME,age,deptId,empno) VALUES('风清扬',90,1,100001);
INSERT INTO t_emp(NAME,age,deptId,empno) VALUES('岳不群',50,1,100002);
INSERT INTO t_emp(NAME,age,deptId,empno) VALUES('令狐冲',24,1,100003);
 
 INSERT INTO t_emp(NAME,age,deptId,empno) VALUES('洪七公',70,2,100004);
INSERT INTO t_emp(NAME,age,deptId,empno) VALUES('乔峰',35,2,100005);
 
INSERT INTO t_emp(NAME,age,deptId,empno) VALUES('灭绝师太',70,3,100006);
INSERT INTO t_emp(NAME,age,deptId,empno) VALUES('周芷若',20,3,100007);
 
 
 
INSERT INTO t_emp(NAME,age,deptId,empno) VALUES('张三丰',100,4,100008);
 
INSERT INTO t_emp(NAME,age,deptId,empno) VALUES('张无忌',25,5,100009);
 
INSERT INTO t_emp(NAME,age,deptId,empno) VALUES('韦小宝',18,null,100010);
 

```

### 2.2.2 sql语句

```sql
 
1   所有有门派的人员信息 
（ A、B两表共有）
 
 select * from t_emp a inner join t_dept b on a.deptId = b.id; 
 
2   列出所有用户，并显示其机构信息 
 （A的全集）
 select * from t_emp a left join t_dept b on a.deptId = b.id; 
 
3   列出所有门派 
（B的全集）
 select * from  t_dept  b  
 
4   所有不入门派的人员 
（A的独有）
select * from t_emp a left join t_dept b on a.deptId = b.id where b.id is null; 
 
5   所有没人入的门派 
（B的独有）
 select * from t_dept b left join  t_emp a on a.deptId = b.id where a.deptId is null;  
 
6  列出所有人员和机构的对照关系
(AB全有)
#MySQL Full Join的实现 因为MySQL不支持FULL JOIN,下面是替代方法
 #left join + union(可去除重复数据)+ right join
 
SELECT * FROM t_emp A LEFT JOIN t_dept B ON A.deptId = B.id
UNION
SELECT * FROM t_emp A RIGHT JOIN t_dept B ON A.deptId = B.id
 
7 列出所有没入派的人员和没人入的门派
（A的独有+B的独有）
SELECT * FROM t_emp A LEFT JOIN t_dept B ON A.deptId = B.id WHERE B.`id` IS NULL
UNION
SELECT * FROM t_emp A RIGHT JOIN t_dept B ON A.deptId = B.id WHERE A.`deptId` IS NULL;

```

> 增加字段 掌门

```
ALTER TABLE `t_dept` 
add  CEO  INT(11)  ;
 
update t_dept set CEO=2 where id=1;
update t_dept set CEO=4 where id=2;
update t_dept set CEO=6 where id=3;
update t_dept set CEO=8 where id=4;
update t_dept set CEO=9 where id=5;
 
 
 
求各个门派对应的掌门人名称:
 
 
 
select   * from t_dept as  b left  join t_emp as a on  b.CEO=a.id;
 
求所有当上掌门人的平均年龄:
 
 
select  avg(a.age) from t_emp a inner join t_dept b on a.id=b.CEO  ;
 
求所有人物对应的掌门名称:
 
 
 
 
 
 
 

```

## 2.3 索引

### 2.3.1 简介

MySQL官方对索引的定义为：索引（Index）是帮助MySQL高效获取数据的数据结构。
可以得到索引的本质：索引是数据结构。

```
 
索引的目的在于提高查询效率，可以类比字典，
 
如果要查“mysql”这个单词，我们肯定需要定位到m字母，然后从下往下找到y字母，再找到剩下的sql。
 
如果没有索引，那么你可能需要a----z，如果我想找到Java开头的单词呢？或者Oracle开头的单词呢？
 
是不是觉得如果没有索引，这个事情根本无法完成？

```

可以简单理解为:排好序的快速查找数据结构

```
 在数据之外，数据库系统还维护着满足特定查找算法的数据结构，这些数据结构以某种方式引用（指向）数据，
这样就可以在这些数据结构上实现高级查找算法。这种数据结构，就是索引。下图就是一种可能的索引方式示例：

```

![image-20200730100646447](assets\image-20200730100646447.png)

左边是数据表，一共有两列七条记录，最左边的是数据记录的物理地址
 为了加快Col2的查找，可以维护一个右边所示的二叉查找树，每个节点分别包含索引键值和一个指向对应数据记录物理地址的指针，这样就可以运用二叉查找在一定的复杂度内获取到相应数据，从而快速的检索出符合条件的记录。

总结

>   数据本身之外，数据库还维护着一个满足特定查找算法的数据结构，这些数据结构以某种方式指向数据，这样就可以在这些数据结构的基础上实现高级查找算法，这种数据结构就是索引。

一般来说索引本身也很大，不可能全部存储在内存中，因此索引往往以索引文件的形式存储的磁盘上

### 2.3.2 索引的优点 & 劣势

优势

>类似大学图书馆建书目索引，提高数据检索的效率，降低数据库的IO成本
>
>通过索引列对数据进行排序，降低数据排序的成本，降低了CPU的消耗

劣势

>虽然索引大大提高了查询速度，同时却会降低更新表的速度，如对表进行INSERT、UPDATE和DELETE。
>因为更新表时，MySQL不仅要保存数据，还要保存一下索引文件每次更新添加了索引列的字段，
>都会调整因为更新所带来的键值变化后的索引信息
>
>实际上索引也是一张表，该表保存了主键与索引字段，并指向实体表的记录，所以索引列也是要占用空间的

### 2.3.3 mysql索引结构

#### BTree索引

平衡树

1 原理介绍

![image-20200730100857867](assets\image-20200730100857867.png)


【初始化介绍】 
一颗b树，浅蓝色的块我们称之为一个磁盘块，可以看到每个磁盘块包含几个数据项（深蓝色所示）和指针（黄色所示），
如磁盘块1包含数据项17和35，包含指针P1、P2、P3，
P1表示小于17的磁盘块，P2表示在17和35之间的磁盘块，P3表示大于35的磁盘块。
真实的数据存在于叶子节点即3、5、9、10、13、15、28、29、36、60、75、79、90、99。
非叶子节点只不存储真实的数据，只存储指引搜索方向的数据项，如17、35并不真实存在于数据表中。

【查找过程】
如果要查找数据项29，那么首先会把磁盘块1由磁盘加载到内存，此时发生一次IO，在内存中用二分查找确定29在17和35之间，锁定磁盘块1的P2指针，内存时间因为非常短（相比磁盘的IO）可以忽略不计，通过磁盘块1的P2指针的磁盘地址把磁盘块3由磁盘加载到内存，发生第二次IO，29在26和30之间，锁定磁盘块3的P2指针，通过指针加载磁盘块8到内存，发生第三次IO，同时内存中做二分查找找到29，结束查询，总计三次IO。

真实的情况是，3层的b+树可以表示上百万的数据，如果上百万的数据查找只需要三次IO，性能提高将是巨大的，如果没有索引，每个数据项都要发生一次IO，那么总共需要百万次的IO，显然成本非常非常高。

2 时间复杂度 

同一问题可用不同算法解决，而一个算法的质量优劣将影响到算法乃至程序的效率。算法分析的目的在于选择合适算法和改进算法。

![image-20200730100936471](assets\image-20200730100936471.png)

![image-20200730100940762](assets\image-20200730100940762.png)

#### B+Tree索引

##### 1 原理图

![image-20200730100959159](assets\image-20200730100959159.png)



##### 2 B树和B+树的区别

　1）B-树的关键字和记录是放在一起的，叶子节点可以看作外部节点，不包含任何信息；B+树的非叶子节点中只有关键字和指向下一个节点的索引，记录只放在叶子节点中。
　 2）在B-树中，越靠近根节点的记录查找时间越快，只要找到关键字即可确定记录的存在；而B+树中每个记录的查找时间基本是一样的，都需要从根节点走到叶子节点，而且在叶子节点中还要再比较关键字。从这个角度看B-树的性能好像要比B+树好，而在实际应用中却是B+树的性能要好些。因为B+树的非叶子节点不存放实际的数据，这样每个节点可容纳的元素个数比B-树多，树高比B-树小，这样带来的好处是减少磁盘访问次数。尽管B+树找到一个记录所需的比较次数要比B-树多，但是一次磁盘访问的时间相当于成百上千次内存比较的时间，因此实际中B+树的性能可能还会好些，而且B+树的叶子节点使用指针连接在一起，方便顺序遍历（例如查看一个目录下的所有文件，一个表中的所有记录等），这也是很多数据库和文件系统使用B+树的缘故。 
　
思考：为什么说B+树比B-树更适合实际应用中操作系统的文件索引和数据库索引？ 

```
1) B+树的磁盘读写代价更低 
　　B+树的内部结点并没有指向关键字具体信息的指针。因此其内部结点相对B 树更小。如果把所有同一内部结点的关键字存放在同一盘块中，那么盘块所能容纳的关键字数量也越多。一次性读入内存中的需要查找的关键字也就越多。相对来说IO读写次数也就降低了。 
2) B+树的查询效率更加稳定 
　　由于非终结点并不是最终指向文件内容的结点，而只是叶子结点中关键字的索引。所以任何关键字的查找必须走一条从根结点到叶子结点的路。所有关键字查询的路径长度相同，导致每一个数据的查询效率相当。
```



#### 聚簇索引与非聚簇索引

聚簇索引并不是一种单独的索引类型，而是一种数据存储方式。
术语‘聚簇’表示数据行和相邻的键值聚簇的存储在一起。
 如下图，左侧的索引就是聚簇索引，因为数据行在磁盘的排列和索引排序保持一致。

![image-20200730101054673](assets\image-20200730101054673.png)

1 聚簇索引的好处：

- 按照聚簇索引排列顺序，查询显示一定范围数据的时候，由于数据都是紧密相连，数据库不不用从多个数据块中提取数据，所以节省了大量的io操作。

2 聚簇索引的限制：

- 对于mysql数据库目前只有innodb数据引擎支持聚簇索引，而Myisam并不支持聚簇索引。

- 由于数据物理存储排序方式只能有一种，所以每个Mysql的表只能有一个聚簇索引。一般情况下就是该表的主键。

- 为了充分利用聚簇索引的聚簇的特性，所以innodb表的主键列尽量选用有序的顺序id，而不建议用无序的id，比如uuid这种。



### 2.3.4 索引分类

#### 单值索引

即一个索引只包含单个列，一个表可以有多个单列索引

```sql
随表一起建索引：
CREATE TABLE customer (id INT(10) UNSIGNED  AUTO_INCREMENT ,customer_no VARCHAR(200),customer_name VARCHAR(200),
  PRIMARY KEY(id),
  KEY (customer_name)
);
  
单独建单值索引：
CREATE  INDEX idx_customer_name ON customer(customer_name); 
 
删除索引：
DROP INDEX idx_customer_name  on customer;
```

#### 唯一索引

索引列的值必须唯一，但允许有空值

```sql
随表一起建索引：
CREATE TABLE customer (id INT(10) UNSIGNED  AUTO_INCREMENT ,customer_no VARCHAR(200),customer_name VARCHAR(200),
  PRIMARY KEY(id),
  KEY (customer_name),
  UNIQUE (customer_no)
);
  
单独建唯一索引：
CREATE UNIQUE INDEX idx_customer_no ON customer(customer_no); 
 
删除索引：
DROP INDEX idx_customer_no on customer ;

```

#### 主键索引

设定为主键后数据库会自动建立索引，innodb为聚簇索引

```sql
随表一起建索引：
CREATE TABLE customer (id INT(10) UNSIGNED  AUTO_INCREMENT ,customer_no VARCHAR(200),customer_name VARCHAR(200),
  PRIMARY KEY(id) 
);
   
CREATE TABLE customer2 (id INT(10) UNSIGNED   ,customer_no VARCHAR(200),customer_name VARCHAR(200),
  PRIMARY KEY(id) 
);
 
 单独建主键索引：
ALTER TABLE customer 
 add PRIMARY KEY customer(customer_no);  
 
删除建主键索引：
ALTER TABLE customer 
 drop PRIMARY KEY ;  
 
修改建主键索引：
必须先删除掉(drop)原索引，再新建(add)索引

```



#### 复合索引

即一个索引包含多个列

```sql
 随表一起建索引：
CREATE TABLE customer (id INT(10) UNSIGNED  AUTO_INCREMENT ,customer_no VARCHAR(200),customer_name VARCHAR(200),
  PRIMARY KEY(id),
  KEY (customer_name),
  UNIQUE (customer_name),
  KEY (customer_no,customer_name)
);
 
单独建索引：
CREATE  INDEX idx_no_name ON customer(customer_no,customer_name); //一个索引包括了两个字段
 
删除索引：
DROP INDEX idx_no_name  on customer ;

```

复合索引的结构

![image-20200730162137737](assets\image-20200730162137737.png)



age_depid_name

索引内部的结构会先确定age然后通过age找到age下的deptid,在找到deptid下的name

#### 基本语法

> 索引存在哪里了?

索引存在了元数据库中的STATISTICS表中,可以看到表下的索引名和列名

也可以在这个表里将索引删除

```sql
CREATE X
DROP INDEX idx_xxx ON emp
1 查出该表有哪些索引，索引名-->集合
SHOW INDEX FROM t_emp
元数据：meta DATA  描述数据的数据
查出索引名称
SELECT index_name  FROM information_schema.STATISTICS WHERE table_name='t_emp' AND table_schema='mydb'
 AND index_name <>'PRIMARY' AND seq_in_index = 1
2 如何循环集合
 CURSOR 游标
 FETCH xxx INTO xxx
3 如何让mysql执行一个字符串
PREPARE 预编译 XXX
EXECUTE
CALL proc_drop_index ('mydb','t_emp');
```



---

> 创建 

索引名称不能重复 

```sql
CREATE  [UNIQUE ]  INDEX [indexName] ON table_name(column)) 
```

> 删除

```sql
DROP INDEX [indexName] ON mytable; 
```

> 查看

```sql
SHOW INDEX FROM table_name
```

> 使用ALTER命令

```sql
 
有四种方式来添加数据表的索引：
ALTER TABLE tbl_name ADD PRIMARY KEY (column_list): 该语句添加一个主键，这意味着索引值必须是唯一的，且不能为NULL。
 
ALTER TABLE tbl_name ADD UNIQUE index_name (column_list): 这条语句创建索引的值必须是唯一的（除了NULL外，NULL可能会出现多次）。
 
ALTER TABLE tbl_name ADD INDEX index_name (column_list): 添加普通索引，索引值可出现多次。
 
ALTER TABLE tbl_name ADD FULLTEXT index_name (column_list):该语句指定了索引为 FULLTEXT ，用于全文索引。
 

```

### 2.3.4 什么情况需要创建索引& 哪些情况不需要创建索引

> 创建

```
主键自动建立唯一索引

频繁作为查询条件的字段应该创建索引

查询中与其它表关联的字段，外键关系建立索引

单键/组合索引的选择问题， 组合索引性价比更高

查询中排序的字段，排序字段若通过索引去访问将大大提高排序速度

查询中统计或者分组字段
```

> 不创建

```
表记录太少

经常增删改的表或者字段
Why:提高了查询速度，同时却会降低更新表的速度，如对表进行INSERT、UPDATE和DELETE。
因为更新表时，MySQL不仅要保存数据，还要保存一下索引文件

Where条件里用不到的字段不创建索引

过滤性不好的不适合建索引
```

# 3 执行计划

## 3.1 如何查看执行计划

```
使用EXPLAIN关键字可以模拟优化器执行SQL查询语句，从而知道MySQL是
如何处理你的SQL语句的。分析你的查询语句或是表结构的性能瓶颈
```

官网

>http://dev.mysql.com/doc/refman/5.5/en/explain-output.html

![image-20200730102946265](assets\image-20200730102946265.png)

---

> 执行计划可以做什么

```
表的读取顺序

那些索引可以使用

数据读取操作的操作类型

那些索引被实际使用

表之间的引用

每张表有多少行被物理查询
```

## 3.2 如何使用执行计划

> 1

Explain + sql语句

![image-20200730103137595](assets\image-20200730103137595.png)

> 建表脚本

```
 CREATE TABLE t1(id INT(10) AUTO_INCREMENT,content  VARCHAR(100) NULL ,  PRIMARY KEY (id));
 CREATE TABLE t2(id INT(10) AUTO_INCREMENT,content  VARCHAR(100) NULL ,  PRIMARY KEY (id));
 CREATE TABLE t3(id INT(10) AUTO_INCREMENT,content  VARCHAR(100) NULL ,  PRIMARY KEY (id));
 CREATE TABLE t4(id INT(10) AUTO_INCREMENT,content  VARCHAR(100) NULL ,  PRIMARY KEY (id));
 
 
 INSERT INTO t1(content) VALUES(CONCAT('t1_',FLOOR(1+RAND()*1000)));
 
  INSERT INTO t2(content) VALUES(CONCAT('t2_',FLOOR(1+RAND()*1000)));
  
  INSERT INTO t3(content) VALUES(CONCAT('t3_',FLOOR(1+RAND()*1000)));
    
  INSERT INTO t4(content) VALUES(CONCAT('t4_',FLOOR(1+RAND()*1000)));
```

## 3.3 字段解释

### 3.3.1 id

select查询的序列号,包含一组数字，表示查询中执行select子句或操作表的顺序

> 三种情况

1. id相同，执行顺序由上至下

![image-20200730103347338](assets\image-20200730103347338.png)

id相同，执行顺序由上至下

2. id不同，如果是子查询，id的序号会递增，id值越大优先级越高，越先被执行,先执行3在2在1

![image-20200730103404284](assets\image-20200730103404284.png)

id不同，如果是子查询，id的序号会递增，id值越大优先级越高，越先被执行



3. id相同不同，同时存在

![image-20200730103418814](assets\image-20200730103418814.png)

id如果相同，可以认为是一组，从上往下顺序执行；
在所有组中，id值越大，优先级越高，越先执行

衍生 = DERIVED

> 关注点

id号每个号码，表示一趟独立的查询。一个sql 的查询趟数越少越好。

### 3.3.2 select_type

![image-20200730103500472](assets\image-20200730103500472.png)

查询的类型，主要是用于区别
普通查询、联合查询、子查询等的复杂查询

1. SIMPLE

简单的 select 查询,查询中不包含子查询或者UNION

![image-20200730103628405](assets\image-20200730103628405.png)

2. PRIMARY

查询中若包含任何复杂的子部分，最外层查询则被标记为Primary

![image-20200730103639881](assets\image-20200730103639881.png)

3. DERIVED

先执行t1的在执行衍生表derived2,看下图

在FROM列表中包含的子查询被标记为DERIVED(衍生)
MySQL会递归执行这些子查询, 把结果放在临时表里。

![image-20200730103712394](assets\image-20200730103712394.png)



4. SUBQUERY

在SELECT或WHERE列表中包含了子查询

先查子查询

![image-20200730103733608](assets\image-20200730103733608.png)

5. DEPENDENT SUBQUERY

在SELECT或WHERE列表中包含了子查询,子查询基于外层

多条范围查询  依赖子查询

![image-20200730103746218](assets\image-20200730103746218.png)





6. UNCACHEABLE SUBQUREY

![image-20200730103836338](assets\image-20200730103836338.png)

不可用缓存查询,sql中出现系统变量

SHOW VARIABLES LIKE '%lower_case_table_names%';

SELECT @@lower_case_table_names FROM DUAL;



7. UNION

若第二个SELECT出现在UNION之后，则被标记为UNION；
若UNION包含在FROM子句的子查询中,外层SELECT将被标记为：DERIVED

![image-20200730103852166](assets\image-20200730103852166.png)



8. UNION RESULT

从UNION表获取结果的SELECT

![image-20200730103904413](assets\image-20200730103904413.png)



### 3.3.3 table

显示这一行的数据是关于那张表的

### 3.3.4 partitions

代表分区表中的命中情况，非分区表，该项为null

### 3.3.5 type

备注：一般来说，得保证查询至少达到range级别，最好能达到ref。

![image-20200730104052992](assets\image-20200730104052992.png)

> 访问类型排列

```sql
 
type显示的是访问类型，是较为重要的一个指标，结果值从最好到最坏依次是： 
 
system > const > eq_ref > ref > fulltext > ref_or_null > index_merge > unique_subquery > index_subquery > range > index > ALL 
 
system>const>eq_ref>ref>range>index>ALL
  
一般来说，得保证查询至少达到range级别，最好能达到ref。
```

>显示查询使用了何种类型，
>从最好到最差依次是：
>system>const>eq_ref>ref>range>index>ALL

range  index  all  这三个比较重要 其他知道意思即可

1. system  

表只有一行记录（等于系统表），这是const类型的特列，平时不会出现，这个也可以忽略不计

2. const

表示通过索引一次就找到了,const用于比较primary key或者unique索引。因为只匹配一行数据，所以很快
如将主键置于where列表中，MySQL就能将该查询转换为一个常量

![image-20200730104203252](assets\image-20200730104203252.png)

3. eq_ref

唯一性索引扫描，对于每个索引键，表中只有一条记录与之匹配。常见于主键或唯一索引扫描

![image-20200730104230824](assets\image-20200730104230824.png)

4. ref

非唯一性索引扫描，返回匹配某个单独值的所有行.
本质上也是一种索引访问，它返回所有匹配某个单独值的行，然而，
它可能会找到多个符合条件的行，所以他应该属于查找和扫描的混合体

![image-20200730104302629](assets\image-20200730104302629.png)



5. range

只检索给定范围的行,使用一个索引来选择行。key 列显示使用了哪个索引
一般就是在你的where语句中出现了between、<、>、in等的查询
这种范围扫描索引扫描比全表扫描要好，因为它只需要开始于索引的某一点，而结束语另一点，不用扫描全部索引。

![image-20200730104325804](assets\image-20200730104325804.png)



6. index

出现index是sql使用了索引但是没用通过索引进行过滤，一般是使用了覆盖索引或者是利用索引进行了排序分组

![image-20200730104348664](assets\image-20200730104348664.png)

7. all 尽量避免all

Full Table Scan，将遍历全表以找到匹配的行

尽量避免全表扫描

![image-20200730104423317](assets\image-20200730104423317.png)

8. index_merge

在查询过程中需要多个索引组合使用，通常出现在有 or 的关键字的sql中

![image-20200730104440093](assets\image-20200730104440093.png)

9. ref_or_null

对于某个字段既需要关联条件，也需要null值得情况下。查询优化器会选择用ref_or_null连接查询。

![image-20200730104502743](assets\image-20200730104502743.png)

10. index_subquery

利用索引来关联子查询，不再全表扫描。

可以看表,第一次查询 type是all 但是建完索引之后,就是index_subquery

![image-20200730104539155](assets\image-20200730104539155.png)



11. unique_subquery 

该联接类型类似于index_subquery。 子查询中的唯一索引

![image-20200730104559293](assets\image-20200730104559293.png)









### 3.3.6 possible_keys

显示可能应用在这张表中的索引，一个或多个。
查询涉及到的字段上若存在索引，则该索引将被列出，但不一定被查询实际使用



### 3.3.7 key

实际使用的索引。如果为NULL，则没有使用索引

查询中若使用了覆盖索引，则该索引和查询的select字段重叠

![image-20200730104715206](assets\image-20200730104715206.png)



### 3.3.8 key_len

key_len字段能够帮你检查是否充分的利用上了索引

表示索引中使用的字节数，可通过该列计算查询中使用的索引的长度。 

```sql
EXPLAIN SELECT SQL_NO_CACHE * FROM emp WHERE emp.age=30 AND emp.name LIKE 'ab%';
```

这个rows 代表了物理扫描的行数

![image-20200730104818518](assets\image-20200730104818518.png)



第一组

utf8字符集需要乘3

key_len=age的字节长度+name的字节长度=4+1  + ( 20*3+2)=5+62=67   67的更优
第二组
 key_len=age的字节长度=4+1=5

![image-20200730104831539](assets\image-20200730104831539.png)



### 3.3.9 ref

显示索引的哪一列被使用了，如果可能的话，是一个常数。哪些列或常量被用于查找索引列上的值

![image-20200730104923314](assets\image-20200730104923314.png)





### 3.3.10  rows

rows列显示MySQL认为它执行查询时必须检查的行数。物理扫描的行数

越少越好

![image-20200730104958967](assets\image-20200730104958967.png)



### 3.3.11 filtered

这个字段表示存储引擎返回的数据在server层过滤后，剩下多少满足查询的记录数量的比例，注意是百分比，不是具体记录数



### 3.3.12  extra

包含不适合在其他列中显示但十分重要的额外信息

Using filesoet

Using temporary

using join buffer



1. Using filesort 

说明mysql会对数据使用一个外部的索引排序，而不是按照表内的索引顺序进行读取。
MySQL中无法利用索引完成的排序操作称为“文件排序”

order by没用上索引,所以很慢  使用文件排序  

![image-20200730105151298](assets\image-20200730105151298.png)

![image-20200730105159227](assets\image-20200730105159227.png)



2. Using temporary 

使了用临时表保存中间结果,MySQL在对查询结果排序时使用临时表。常见于排序 order by 和分组查询 group by。

group by没有用到索引,效率很低,创建索引,后效率显著提升

![image-20200730105231706](assets\image-20200730105231706.png)

![image-20200730105239382](assets\image-20200730105239382.png)

3. USING index

**表示相应的select操作中使用了覆盖索引(Covering Index)，避免访问了表的数据行，效率不错！**
如果同时出现using where，表明索引被用来执行索引键值的查找;
如果没有同时出现using where，表明索引只是用来读取数据而非利用索引执行查找。

利用索引进行了排序或分组

4. Using where

表明使用了where过滤

5. using join buffer  

两个表的关联字段没有用上索引,用上了连接缓存

给关联字段创建索引

使用了连接缓存

![image-20200730105341844](assets\image-20200730105341844.png)

6. impossible where

sql有问题 逻辑有问题

where子句的值总是false，不能用来获取任何元组

![image-20200730105358030](assets\image-20200730105358030.png)

7. select tables optimized away

在没有GROUPBY子句的情况下，基于索引优化MIN/MAX操作或者
对于MyISAM存储引擎优化COUNT(*)操作，不必等到执行阶段再进行计算，
查询执行计划生成的阶段即完成优化。

![image-20200730141555356](assets\image-20200730141555356.png)

# 4 查询优化

## 4.1 生成数据脚本

> 建表

```sql
 CREATE TABLE `dept` (
 `id` INT(11) NOT NULL AUTO_INCREMENT,
 `deptName` VARCHAR(30) DEFAULT NULL,
 `address` VARCHAR(40) DEFAULT NULL,
 ceo INT NULL ,
 PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 
 
CREATE TABLE `emp` (
 `id` INT(11) NOT NULL AUTO_INCREMENT,
 `empno` INT NOT NULL ,
 `name` VARCHAR(20) DEFAULT NULL,
 `age` INT(3) DEFAULT NULL,
 `deptId` INT(11) DEFAULT NULL,
 PRIMARY KEY (`id`)
 #CONSTRAINT `fk_dept_id` FOREIGN KEY (`deptId`) REFERENCES `t_dept` (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```

> 设置参数log_bin_trust_function_creators

```sql
-- 创建函数，假如报错：This function has none of DETERMINISTIC......
-- 由于开启过慢查询日志，因为我们开启了 bin-log, 我们就必须为我们的function指定一个参数。
 
show variables like 'log_bin_trust_function_creators';
 
set global log_bin_trust_function_creators=1; -- global 整体的窗口都有效
 
-- 这样添加了参数以后，如果mysqld重启，上述参数又会消失，永久方法：
 
windows下
my.ini[mysqld]加上log_bin_trust_function_creators=1 
 
linux下    
/etc/my.cnf下my.cnf[mysqld]加上log_bin_trust_function_creators=1

```

> 创建函数,保证每条数据都不同

```
1 随机产生字符串
DELIMITER $$
CREATE FUNCTION rand_string(n INT) RETURNS VARCHAR(255)
BEGIN    
DECLARE chars_str VARCHAR(100) DEFAULT 'abcdefghijklmnopqrstuvwxyzABCDEFJHIJKLMNOPQRSTUVWXYZ';
 DECLARE return_str VARCHAR(255) DEFAULT '';
 DECLARE i INT DEFAULT 0;
 WHILE i < n DO  
 SET return_str =CONCAT(return_str,SUBSTRING(chars_str,FLOOR(1+RAND()*52),1));  
 SET i = i + 1;
 END WHILE;
 RETURN return_str;
END $$
 
 
#假如要删除
#drop function rand_string;

2 Subtopic

3 随机产生部门编号
 
#用于随机产生多少到多少的编号
DELIMITER $$
CREATE FUNCTION  rand_num (from_num INT ,to_num INT) RETURNS INT(11)
BEGIN   
 DECLARE i INT DEFAULT 0;  
 SET i = FLOOR(from_num +RAND()*(to_num -from_num+1))   ;
RETURN i;  
 END$$ 
 
#假如要删除
#drop function rand_num;



```

> 创建存储过程

```sql
1 创建往emp表中插入数据的存储过程
 
 
DELIMITER $$
CREATE PROCEDURE  insert_emp(  START INT ,  max_num INT )
BEGIN  
DECLARE i INT DEFAULT 0;   
#set autocommit =0 把autocommit设置成0  
 SET autocommit = 0;    
 REPEAT  
 SET i = i + 1;  
 INSERT INTO emp (empno, NAME ,age ,deptid ) VALUES ((START+i) ,rand_string(6)   , rand_num(30,50),rand_num(1,10000));  
 UNTIL i = max_num  
 END REPEAT;  
 COMMIT;  
 END$$ 
 
#删除
# DELIMITER ;
# drop PROCEDURE insert_emp;
 
2 创建往dept表中插入数据的存储过程

 
#执行存储过程，往dept表添加随机数据
DELIMITER $$
CREATE PROCEDURE `insert_dept`(  max_num INT )
BEGIN  
DECLARE i INT DEFAULT 0;   
 SET autocommit = 0;    
 REPEAT  
 SET i = i + 1;  
 INSERT INTO dept ( deptname,address,ceo ) VALUES (rand_string(8),rand_string(10),rand_num(1,500000));  
 UNTIL i = max_num  
 END REPEAT;  
 COMMIT;  
 END$$
 
#删除
# DELIMITER ;
# drop PROCEDURE insert_dept;
```



> 调用存储过程

```sql
1 dept
 #执行存储过程，往dept表添加1万条数据
 -- 先DELIMITER
DELIMITER ;
CALL insert_dept(10000); 

2 emp
#执行存储过程，往emp表添加50万条数据
DELIMITER ;
CALL insert_emp(100000,500000); 
```

> 批量删除某个表上的所有索引

- 存储过程

```sql
-- 涉及了游标 预编译等sql知识
DELIMITER $$
CREATE  PROCEDURE `proc_drop_index`(dbname VARCHAR(200),tablename VARCHAR(200))
BEGIN
       DECLARE done INT DEFAULT 0;
       DECLARE ct INT DEFAULT 0;
       DECLARE _index VARCHAR(200) DEFAULT '';
       DECLARE _cur CURSOR FOR  SELECT   index_name   FROM information_schema.STATISTICS   WHERE table_schema=dbname AND table_name=tablename AND seq_in_index=1 AND    index_name <>'PRIMARY'  ;
       DECLARE  CONTINUE HANDLER FOR NOT FOUND set done=2 ;      
        OPEN _cur;
        FETCH   _cur INTO _index;
        WHILE  _index<>'' DO 
               SET @str = CONCAT("drop index ",_index," on ",tablename ); 
               PREPARE sql_str FROM @str ;
               EXECUTE  sql_str;
               DEALLOCATE PREPARE sql_str;
               SET _index=''; 
               FETCH   _cur INTO _index; 
        END WHILE;
   CLOSE _cur;
   END$$

```

- 执行存储过程 

```sql
CALL proc_drop_index("数据库名称","表名");
```



## 4.2 单表使用索引及常见索引失效



### 4.2.1 案例

---

> 全值匹配 

建立索引

```sql
 系统中经常出现的sql语句如下：  
 EXPLAIN SELECT SQL_NO_CACHE * FROM emp WHERE emp.age=30  
 EXPLAIN SELECT SQL_NO_CACHE * FROM emp WHERE emp.age=30 and deptid=4   创建符合索引
 EXPLAIN SELECT SQL_NO_CACHE * FROM emp WHERE emp.age=30 and deptid=4 AND emp.name = 'abcd'  
 可以通过执行计划去查看优化效果
 可以使用函数将索引删除,进行比对测试
 索引应该如何建立 ？
```

给age deptid name 创建索引

![image-20200730143608484](assets\image-20200730143608484.png)



---

> 最佳左前缀原则

```sql
如果系统经常出现的sql如下：
	EXPLAIN SELECT SQL_NO_CACHE * FROM emp WHERE emp.age=30   AND emp.name = 'abcd'   
或者
	EXPLAIN SELECT SQL_NO_CACHE * FROM emp WHERE emp.deptid=1   AND emp.name = 'abcd'   
那原来的idx_age_deptid_name 还能否正常使用？
```

```
如果索引了多列，要遵守最左前缀法则。指的是查询从索引的最左前列开始并且不跳过索引中的列。
```

![image-20200730144905288](assets\image-20200730144905288.png)

---

> 不在索引列上做任何操作（计算、函数、(自动or手动)类型转换），会导致索引失效而转向全表扫描

这两条sql哪种写法更好
EXPLAIN  SELECT SQL_NO_CACHE * FROM emp WHERE   emp.name  LIKE 'abc%' 

EXPLAIN   SELECT SQL_NO_CACHE * FROM emp WHERE   LEFT(emp.name,3)  = 'abc'

这个left的意思是  截取emp.name字段从做开始截取 ,截取三个字符 跟abc进行匹配,但是left这样用不上索引

![image-20200730145011553](assets\image-20200730145011553.png)

![image-20200730145018860](assets\image-20200730145018860.png)

---

> 存储引擎不能使用索引中范围条件右边的列

如果sql中涉及到了范围查询,则将范围查询的索引放到最后去创建这样会避免该字段索引失效

![image-20200730145309082](assets\image-20200730145309082.png)

---

> mysql 在使用不等于(!= 或者<>)时 无法使用索引会导致全表扫描

SQL_NO_CACHE是查询时不使用缓存

![image-20200730145511560](assets\image-20200730145511560.png)



----

> is not null 无法使用索引
>
> 但是is null是可以使用索引的

```sql
UPDATE emp SET age =NULL WHERE id=123456;
下列哪个sql语句可以用到索引
EXPLAIN SELECT * FROM emp WHERE age IS NULL
EXPLAIN SELECT * FROM emp WHERE age IS NOT NULL
```

![image-20200730145659077](assets\image-20200730145659077.png)



---

> like以通配符开头('%abc...')mysql索引失效会变成全表扫描的操作

创建索引,like%开头的会导致索引失效

![image-20200730145717693](assets\image-20200730145717693.png)



---

> 字符串不加单引号索引失效

![image-20200730145738413](assets\image-20200730145738413.png)

---

> 小总结

![image-20200730145822397](assets\image-20200730145822397.png)



### 4.2.2 一般性建议

- 对于单键索引，尽量选择针对当前query过滤性更好的索引

- 在选择组合索引的时候，当前Query中过滤性最好的字段在索引字段顺序中，位置越靠前越好。

- 在选择组合索引的时候，尽量选择可以能够包含当前query中的where字句中更多字段的索引

- 在选择组合索引的时候，如果某个字段可能出现范围查询时，尽量把这个字段放在索引次序的最后面

- 书写sql语句时，尽量避免造成索引失效的情况

## 4.3 关联查询优化

### 4.3.1 案例演示

#### 语句

```sql
 
CREATE TABLE IF NOT EXISTS `class` (
`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
`card` INT(10) UNSIGNED NOT NULL,
PRIMARY KEY (`id`)
);
CREATE TABLE IF NOT EXISTS `book` (
`bookid` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
`card` INT(10) UNSIGNED NOT NULL,
PRIMARY KEY (`bookid`)
);
 
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
 
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
```

#### 优化

```sql
 
# 下面开始explain分析
EXPLAIN SELECT * FROM class LEFT JOIN book ON class.card = book.card;
#结论：type 有All
 
# 添加索引优化
ALTER TABLE `book` ADD INDEX Y ( `card`);
class表是驱动表,book是被驱动表,驱动表无法避免全表扫描,被驱动表可以创建索引加强优化
 
换成inner join, mysql会自己选择谁是驱动表,总之还是给被驱动表创建索引
delete from class where id<5;
 
# 第2次explain
EXPLAIN SELECT * FROM class LEFT JOIN book ON class.card = book.card;
#可以看到第二行的 type 变为了 ref,rows 也变成了优化比较明显。
#这是由左连接特性决定的。LEFT JOIN 条件用于确定如何从右表搜索行,左边一定都有,
#所以右边是我们的关键点,一定需要建立索引。
 
# 删除旧索引 + 新建 + 第3次explain
DROP INDEX Y ON book;
ALTER TABLE class ADD INDEX X (card);
EXPLAIN SELECT * FROM class LEFT JOIN book ON class.card = book.card;
```

延伸,关联查询时驱动表尽量小,因为尽可能减少物理扫描

#### 建议

保证被驱动表的join字段已经被索引

left join 时，选择小表作为驱动表，大表作为被驱动表。

inner join 时，mysql会自己帮你把小结果集的表选为驱动表。

子查询尽量不要放在被驱动表，有可能使用不到索引。

能够直接多表关联的尽量直接关联，不用子查询。

## 4.4 子查询优化

尽量不要使用not in  或者 not exists

```sql
-- 取所有不为掌门人的员工，按年龄分组 ，每个年龄段多少人
SELECT SQL_NO_CACHE age,count(*)  FROM emp a WHERE  id  NOT  IN(SELECT ceo FROM dept b2 WHERE ceo IS NOT NULL) group by age having count(*)<10000
```

![image-20200730201825507](assets\image-20200730201825507.png)



---

> 用left outer join  on  xxx is null 替代

```sql
  EXPLAIN SELECT SQL_NO_CACHE age,count(*) FROM  emp a LEFT OUTER JOIN dept b ON a.id =b.ceo
  WHERE    b.ceo IS   NULL
  group by age 
  having count(*)<10000
```

![image-20200730201927063](assets\image-20200730201927063.png)

## 4.5 排序分组优化

### 4.5.1 案例

```sql
create index idx_age_deptid_name on emp (age,deptid,name)

以下  是否能使用到索引，能否去掉using filesort

1、explain  select SQL_NO_CACHE * from emp order by age,deptid; 

2、 explain  select SQL_NO_CACHE * from emp order by age,deptid limit 10; 

 #无过滤 不索引
 
3、  explain  select * from emp where age=45 order by deptid;

4、explain  select * from emp where age=45 order by   deptid,name; 
 
5、explain  select * from emp where age=45 order by  deptid,empno;
 
6、explain  select * from emp where age=45 order by  name,deptid;
 
7、 explain select * from emp where deptid=45 order by age;

#顺序错，必排序  创建索引顺序错误 必定出现排序,using filesort
8、  explain select * from emp where age=45 order by  deptid desc, name desc ;

#方向反 必排序  using filesort
9、 explain select * from emp where age=45 order by  deptid asc, name desc ;


```

### 4.5.2 ORDER BY

子句，尽量使用Index方式排序,避免使用FileSort方式排序

### 4.5.3 索引的选择

执行案例前先清除emp上的索引，只留主键

```sql
#查询 年龄为30岁的，且员工编号小于101000的用户，按用户名称排序
SELECT SQL_NO_CACHE * FROM emp WHERE age =30 AND empno <101000 ORDER BY NAME ;
```

![image-20200730202128225](assets\image-20200730202128225.png)

```
#结论：很显然,type 是 ALL,即最坏的情况。Extra 里还出现了 Using filesort,也是最坏的情况。优化是必须的。
 
#开始优化：
思路：  尽量让where的过滤条件和排序使用上索引
但是一共两个字段(deptno,empno)上有过滤条件，一个字段(ename)有索引 
1、我们建一个三个字段的组合索引可否？
CREATE INDEX idx_age_empno_name ON emp(age,empno,NAME);
我们发现using filesort 依然存在，所以name 并没有用到索引。
原因是因为empno是一个范围过滤，所以索引后面的字段不会再使用索引了。
 
所以我们建一个3值索引是没有意义的 
那么我们先删掉这个索引，DROP INDEX idx_age_empno_name ON emp
 
为了去掉filesort我们可以把索引建成
CREATE INDEX idx_age_name ON emp(age,NAME);
```

![image-20200730205149717](assets\image-20200730205149717.png)

但是

如果我们选择那个范围过滤，而放弃排序上的索引呢

建立

```sql
DROP INDEX idx_age_name ON emp
create index idx_age_eno on emp(age,empno); 
```

![image-20200730205259079](assets\image-20200730205259079.png)



结果竟然有 filesort的 sql 运行速度，超过了已经优化掉 filesort的 sql ，而且快了好多倍。何故？

原因是所有的排序都是在条件过滤之后才执行的，所以如果条件过滤了大部分数据的话，几百几千条数据进行排序其实并不是很消耗性能，即使索引优化了排序但实际提升性能很有限。  相对的 empno<101000 这个条件如果没有用到索引的话，要对几万条的数据进行扫描，这是非常消耗性能的，所以索引放在这个字段上性价比最高，是最优选择。

结论： 当范围条件和group by 或者 order by  的字段出现二选一时 ，优先观察条件字段的过滤数量，如果过滤的数据足够多，而需要排序的数据并不多时，优先把索引放在范围字段上。反之，亦然。



---

### 4.5.4 如果不在索引列上，filesort有两种算法：

> mysql就要启动
>
> 双路排序
>
> 单路排序



> 双路排序

```
MySQL 4.1之前是使用双路排序,字面意思就是两次扫描磁盘，最终得到数据，
读取行指针和orderby列，对他们进行排序，然后扫描已经排序好的列表，按照列表中的值重新从列表中读取对应的数据输出
从磁盘取排序字段，在buffer进行排序，再从磁盘取其他字段。
```



但是 

取一批数据，要对磁盘进行了两次扫描，众所周知，I\O是很耗时的，所以在mysql4.1之后，出现了第二种改进的算法，就是单路排序。

> 单路排序  (比双路排序快)

从磁盘读取查询需要的所有列，按照order by列在buffer对它们进行排序，然后扫描排序后的列表进行输出，
它的效率更快一些，避免了第二次读取数据。并且把随机IO变成了顺序IO,但是它会使用更多的空间，
因为它把每一行都保存在内存中了。



结论及引申出的问题

由于单路是后出的，总体而言好过双路

但是用单路有问题

```
在sort_buffer中，方法B比方法A要多占用很多空间，因为方法B是把所有字段都取出, 所以有可能取出的数据的总大小超出了sort_buffer的容量，导致每次只能取sort_buffer容量大小的数据，进行排序（创建tmp文件，多路合并），排完再取取sort_buffer容量大小，再排……从而多次I/O。
 
本来想省一次I/O操作，反而导致了大量的I/O操作，反而得不偿失。
```



优化方案

- 增大sort_buffer_size参数的设置

- 增大max_length_for_sort_data参数的设置

- 减少select 后面的查询的字段。

```
 
提高Order By的速度
 
1. Order by时select * 是一个大忌只Query需要的字段， 这点非常重要。在这里的影响是：
  1.1 当Query的字段大小总和小于max_length_for_sort_data 而且排序字段不是 TEXT|BLOB 类型时，会用改进后的算法——单路排序， 否则用老算法——多路排序。
  1.2 两种算法的数据都有可能超出sort_buffer的容量，超出之后，会创建tmp文件进行合并排序，导致多次I/O，但是用单路排序算法的风险会更大一些,所以要提高sort_buffer_size。
 
2. 尝试提高 sort_buffer_size
不管用哪种算法，提高这个参数都会提高效率，当然，要根据系统的能力去提高，因为这个参数是针对每个进程的  1M-8M之间调整
 
3. 尝试提高 max_length_for_sort_data
提高这个参数， 会增加用改进算法的概率。但是如果设的太高，数据总容量超出sort_buffer_size的概率就增大，明显症状是高的磁盘I/O活动和低的处理器使用率.                  1024-8192之间调整
```

### 4.5.5 GROUP BY关键字优化

group by 使用索引的原则几乎跟order by一致 ，唯一区别是groupby 即使没有过滤条件用到索引，也可以直接使用索引。

## 4.6 最后使用索引的手段：覆盖索引

> 什么是覆盖索引？
> 简单说就是，select 到 from 之间查询的列 <=使用的索引列+主键

![image-20200730210103270](assets\image-20200730210103270.png)

![image-20200730210112019](assets\image-20200730210112019.png)



![image-20200730210117849](assets\image-20200730210117849.png)

# 5 查询截取分析

## 5.1 慢查询日志

- MySQL的慢查询日志是MySQL提供的一种日志记录，它用来记录在MySQL中响应时间超过阀值的语句，具体指运行时间超过long_query_time值的SQL，则会被记录到慢查询日志中。

- 具体指运行时间超过long_query_time值的SQL，则会被记录到慢查询日志中。long_query_time的默认值为10，意思是运行10秒以上的语句。

-  由他来查看哪些SQL超出了我们的最大忍耐时间值，比如一条sql执行超过5秒钟，我们就算慢SQL，希望能收集超过5秒的sql，结合之前explain进行全面分析。

### 5.1.1 如何使用

 默认情况下，MySQL数据库没有开启慢查询日志，需要我们手动来设置这个参数。

当然，如果不是调优需要的话，一般不建议启动该参数，因为开启慢查询日志会或多或少带来一定的性能影响。慢查询日志支持将日志记录写入文件

> 开启

```sql
SHOW VARIABLES LIKE '%slow_query_log%';
set global slow_query_log=1;
```

默认情况下slow_query_log的值为OFF，表示慢查询日志是禁用的，可以通过设置slow_query_log的值来开启

![image-20200730211527376](assets\image-20200730211527376.png)

---

使用set global slow_query_log=1;开启了慢查询日志只对当前数据库生效，
如果MySQL重启后则会失效。

![image-20200730211608088](assets\image-20200730211608088.png)

![image-20200730211629302](assets\image-20200730211629302.png)




如果要永久生效，就必须修改配置文件my.cnf（其它系统变量也是如此）

修改my.cnf文件，[mysqld]下增加或修改参数

```sql
slow_query_log 和slow_query_log_file后，然后重启MySQL服务器。也即将如下两行配置进my.cnf文件

slow_query_log =1
slow_query_log_file=/var/lib/mysql/atguigu-slow.log

关于慢查询的参数slow_query_log_file ，它指定慢查询日志文件的存放路径，系统默认会给一个缺省的文件host_name-slow.log（如果没有指定参数slow_query_log_file的话）
```

---

> 那么开启了慢查询日志后，什么样的SQL才会记录到慢查询日志里面呢？

这个是由参数long_query_time控制，默认情况下long_query_time的值为10秒，
命令：SHOW VARIABLES LIKE 'long_query_time%';

![image-20200730211725484](assets\image-20200730211725484.png)

可以使用命令修改，也可以在my.cnf参数里面修改。

假如运行时间正好等于long_query_time的情况，并不会被记录下来。也就是说，
在mysql源码里是判断大于long_query_time，而非大于等于。

### 5.1.2  总结

- 查看当前多少秒算慢

SHOW VARIABLES LIKE 'long_query_time%';

- 设置慢的阙值时间

set  long_query_time=1

- 记录慢sql并后续分析

![image-20200730211936778](assets\image-20200730211936778.png)

- 查询当前系统中有多少条慢查询记录

show global status like '%Slow_queries%';

![image-20200730211955881](assets\image-20200730211955881.png)



### 5.1.3 配置

修改my.cnf

```sql
slow_query_log=1
slow_query_log_file=/var/lib/mysql/xxx-slow.log
long_query_time=3
log_output=FILE
```

### 5.1.4 日志分析工具 mysqldumpslow





## 5.2 SHOW PROCESSLIST

