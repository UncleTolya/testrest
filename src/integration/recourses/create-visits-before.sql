delete from visit;

alter sequence HIBERNATE_SEQUENCE restart with 30;


insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (1, 'u1', 'p1', {ts '2019-04-04 00:00:00'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (2, 'u1', 'p2', {ts '2019-04-04 00:00:00'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (3, 'u1', 'p3', {ts '2019-04-04 20:00:00'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (4, 'u2', 'p4', {ts '2019-04-04 20:00:00'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (5, 'u2', 'p5', {ts '2019-04-04 20:00:00'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (6, 'u2', 'p6', {ts '2019-04-04 20:00:00'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (7, 'u3', 'p7', {ts '2019-04-04 20:00:00'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (8, 'u3', 'p8', {ts '2019-04-04 23:59:59'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (9, 'u3', 'p9', {ts '2019-04-04 23:59:59'});

insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (10, 'u1', 'p10', {ts '2019-04-05 00:00:00'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (11, 'u1', 'p11', {ts '2019-04-05 00:00:00'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (12, 'u1', 'p12', {ts '2019-04-05 20:00:00'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (13, 'u2', 'p13', {ts '2019-04-05 20:00:00'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (14, 'u2', 'p14', {ts '2019-04-05 20:00:00'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (15, 'u2', 'p15', {ts '2019-04-05 20:00:00'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (16, 'u3', 'p16', {ts '2019-04-05 20:00:00'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (17, 'u3', 'p17', {ts '2019-04-05 23:59:59'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (18, 'u3', 'p18', {ts '2019-04-05 23:59:59'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (19, 'u1', 'p19', {ts '2019-04-05 23:59:59'});

insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (20, 'u1', 'p20', {ts '2019-04-06 00:00:00'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (21, 'u1', 'p21', {ts '2019-04-06 00:00:00'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (22, 'u1', 'p22', {ts '2019-04-06 20:00:00'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (23, 'u2', 'p23', {ts '2019-04-06 20:00:00'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (24, 'u2', 'p24', {ts '2019-04-06 20:00:00'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (25, 'u2', 'p25', {ts '2019-04-06 20:00:00'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (26, 'u3', 'p26', {ts '2019-04-06 20:00:00'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (27, 'u3', 'p27', {ts '2019-04-06 23:59:59'});
insert into visit(ID, USER_ID, PAGE_ID, DATE_TIME) values (28, 'u3', 'p28', {ts '2019-04-06 23:59:59'});


