# 概念

**逻辑删除**又名软删除，与物理删除、硬删除相对应，含义是并没有实际的删除数据，**只是对外部屏蔽了这些数据，对外部造成了数据已经被删除的假象**.

逻辑删除是一个非常常见的需求和概念,例如windows系统的删除文件和文件夹就是逻辑删除,很明显的是被删除的文件和文件夹在回收站还能被看见,很明显这是一种逻辑删除.

# 作用

总体来讲,逻辑删除的作用主要是保留被删除数据,在某些地方对被删除数据进行查看、分析、操作.

或者换一句话说,之所以有要逻辑删除,是因为在某些地方,这些数据不是被删除了,而是一种特殊状态的数据.

# 实现方案

## 表结构增加boolean类型的状态字段

这是最常见的做法,**在需要进行逻辑删除的表中增加一个boolean类型的状态字段.**

当然这个boolean类型指的是编程语言的类型,包括mysql的某些数据库是没有这种字段类型的,所以一般会以tinyint类型来实现. **所以一般这个字段类型是boolean,int,char之一,以is_deleted之类的名称命名,但对应的是boolean类型,里面只有两种值表示被逻辑删除和没被逻辑删除.**

### 优点

**被删除的数据只是修改了一个状态值,当需要查看和操作被删除的数据时,不需要特殊处理和对待.**

### 缺点

**对唯一性约束无法很好的处理.**

举个例子,假设有一个账户表,里面有很多唯一性约束,比如账号名\邮箱\手机号等. 此时要使用这个方案进行逻辑删除,那么情况有2种,以账号名唯一约束为例.

(1)unique key为account_name:此时,假如一个account_name为abc的数据被逻辑删除了,然后我们再添加一条account_name为abc的新账户,那么就会发现产生了唯一性约束冲突,导致无法添加新账户.

(2)unique key为account_name+is_deleted:此时,假如一个account_name为abc的数据被逻辑删除了,然后我们再添加一条account_name为abc的新账户是没有问题的. 但是当再次删除这条新的account_name为abc的新数据时,还是会发生唯一性约束冲突,导致无法成功删除.

这两种情况显然都是有问题的,因为逻辑删除应该使用起来和物理删除一模一样,让使用者做到无感知.

### 解决方案

上面说的这种**唯一性约束本质问题是,我们需要的是account_name+is_deleted=0的unique key,但是包括mysql在内的很多数据库不支持这种唯一性约束设置.** 但是某些数据库是支持的,比如SQL Server就是支持的,所以改用特定数据库就能解决这个问题. 不过这显然不是一个推荐方案,因为这直接限制了项目数据库的选型,设计的可用范围极小. 跟把业务逻辑写在存储过程并大量使用存储过程是一样的问题,会造成项目的数据库可迁移性极差.

## 删除数据表

这也是比较常见的做法,**对每张需要逻辑删除的表都创建一张结构相同的表,用来单独存放被删除数据.** 一般另一张表的名称为history_xxxxx表或者deleted_xxxxxx等. 实现方案可以是在代码中把删除逻辑改为删除前先查询需要删除的数据,然后在删除后把这些数据插入到另一张表中,也可以是用数据库监听工具监听删除数据的日志,然后把这部分数据插入到另一张表中.

### 优点

1. **解决了状态字段的唯一性约束问题**,因为已经不放在一张表了
2. **在性能上比状态字段要好一些**,因为在查询未删除数据时,少了一个字段的条件筛选

### 缺点

1. **需要对这些逻辑删除的表进行数据库和编程两方面的特殊处理**. 有人把这种方案理解为以是否被逻辑删除为维度的分库分表,但实际上还是存在不同的.

   分库分表是一种海量数据的关系型数据库存储解决方案,虽然说并不能做到被封装后,让调用者无感知.但是通过框架的封装,除了查询和操作时的数据维度受限,必须是分库分表逻辑字段以外,基本是无感知的. 而如果采用多份数据备份,每份数据采用不同字段维度进行分库分表,以此满足所有需要的查询维度的话,甚至能做到完全的无感知.

   但是这种删除数据表的方案不同,表结构虽然一般相同,但是索引上不一定相同. 比如以上面说的账户表为例,删除账户表中是没法加账户名的唯一性约束的,需要改成普通索引. 所以在表结构修改上,必须要一些精力进行特殊对待,维护两张表. 并且采用了这种删除数据表的方案的两张表也还是会面对海量数据,可能需要对这两张表进行分库分表.

   同时编程层面更加需要特殊对待,两张表在代码中是不同对象,需要互相转换,需要写两份相关业务层代码.

2. **对需要将未删除数据和已删除数据一起查询和操作时不太适用**. 比如在某些后台类管理系统中,需要同时展示未删除数据和已删除数据且查询和排序条件对未删除数据和已删除数据都有效,可以进行已删除数据的恢复操作. 在这种情况下,如果使用union sql来满足需求,那么性能只会比采用状态字段方案的性能更差,并且如果需要分库分表,那么union sql也无法接受了.

   另一个方案就是使用Elasticsearch和solar这样的搜索服务来解决,但是引入其他服务肯定会增加复杂度,肯定是最后的选项.而且如果使用Elasticsearch和solar这样的搜索服务,那关系型数据库层面的性能也就没什么意义了,优点也消失了一条.

## 表结构增加long、datetime类型的删除时间字段

这是对状态字段方案的一种改进,**依旧是通过一个字段来表明是否删除的状态,未删除数据的该字段值虽然相同,但是被删除数据的该字段值却是不同的.**

被删除数据的这个字段值既然需要不同,那么干脆用来记录一些有意义的数据更好,而不是使用随机数之类的填充方法来浪费字段.

**所以一般用来表示时间,可以是时间戳或者时间类型,存储被删除时的时间,而未删除时值为一个默认值,默认值可以使用最小值或者最大值,以gmt_deleted、deleted_time等名称命名字段.**

**这里需要强调的是不能使用null作为逻辑删除字段的默认值,否则因为null值无法放入索引,会导致唯一索引加上逻辑删除字段后失效,可以插入多条该字段为null的数据,存在多条未被删除的有效数据.**

### 优点

1. **解决了状态字段的唯一性约束问题.** 只要在所有的unique key后面加上删除时间字段即可.
2. **不需要对已删除数据和未删除数据进行分开的特殊处理,使用一套代码,可以通过框架封装得无感知.**

### 缺点

其实并没有什么硬伤的缺点,硬要说的话跟删除数据表方案比起来,数据量会膨胀的更快一些,更早需要分库分表. 但是大数据量下,分库分表是必须的,本来就要做好分库分表方案,迟还是早区别不大. 如果小数据量,那也不太在乎这点性能损耗. 很少有情况会是使用这种方案会需要分库分表而使用删除数据表方案正好不需要分库分表这么恰好.

当然还有一个不算缺点的注意点:很多数据库的索引大小是有限制的,所以索引和唯一索引需要加上删除时间字段就意味着减少了一点业务可使用的大小.

# 总结

**删除数据表方案是一个可接受的方案,但是相对来说我更推荐删除时间字段方案,因为很容易可以封装的无感知.**

目前本人采用的整体方案是:

1. 表结构增加long、datetime类型的删除时间字段

2. 逻辑删除功能支持开关配置,在业务应用时开启,后台管理类应用时关闭.

3. 逻辑删除功能开启时,使用orm框架拦截器(本人使用的是mybatis)进行底层拦截,完成以下逻辑: (1)将删除操作改为更新删除时间字段值的操作

   (2)将查询和更新操作的where条件额外增加一个and删除时间字段等于未删除的条件

使用以上方案,就可以做到逻辑删除的完全无感知,在业务应用感知完全等同于删除,在后台管理类应用时删除时间字段只是一个普通字段.