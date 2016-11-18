// 常规情况下增删改sql
insert into A(a1,a2,a3) values(#key1#,#key2_key3#,#key4#);

update B set b1=#key10#,b2=#key11# where b3=#key2#;

delete from C where c1=#key1#;

// 相同节点转多列情况
insert into D(d1,d2,d3,d4,d5) values(#key1#,#key20_key30#,#key21_key31#,#key40#,#key41#);

// 相同节点转多行情况
insert into D(d1,d2,d3) values(#key1#,#key2_key3#,#key4#);