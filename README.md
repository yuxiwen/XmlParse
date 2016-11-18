# XmlParse
parse a xml file or inputstream and insert,update,delete database by the data parse from xml file or inputstream.
解析XML文件或者输入流，并根据解析的数据增删改数据库

## 步骤一
解析XML文件或者输入流，将数据保存到Map中，以节点名称为key,以节点值为value。

## 步骤二
根据XML的不同格式，自定义由占位符组成的sql语句，其中占位符类型为##,多个节点构成的组合键以“_”分隔。
<p>eg1:insert into A(a1,a2,a3) values(#key1#,#key2_key3#,#key4#);</p>
<p>eg2:update B set b1=#key10#,b2=#key11# where b3=#key2#;</p>
<p>eg3:delete from C where c1=#key1_key2#;</p>
<p>eg4:insert into D(d1,d2,d3,d4,d5) values(#key1#,#key20_key30#,#key21_key31#,#key40#,#key41#);</p>
<p>eg5:insert into E(e1,e2,e3) values(#key1#,#key2_key3#,#key4#);多行sql</p>

## 步骤三
用XML解析出的数据，替换占位符，得到可执行的sql语句

## 步骤四
依次执行sql语句完成对数据库的增删改操作

