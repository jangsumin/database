select version(), current_date(), now() FROM dual;

-- 수학함수, 사칙연산도 된다.
select sin(pi()/4), 1 + 2 * 3 - 4 / 5 from dual;

-- 대소문자 구분이 없다.
select version(), current_DATE, NOW() From DUal;

-- table 생성
-- varchar는 가변이다.
-- char는 가변이 아니다.
create table pet (
	name varchar(100),
	owner varchar(20),
    species varchar(20),
    gender char(1),
	birth date,
    death date
);

-- schema 확인
describe pet;
desc pet;

-- table 삭제
drop table pet;

-- insert (C)
insert into pet values('성탄이', '수민장', 'dog', 'm', '2022-12-25', null);

-- select (R)
select * from pet;

-- update (U)
update pet set name='오타니' where name='성탄이';
update pet set death=null where death='0000-00-00';

-- delete (D)
delete from pet where name='오타니';

-- load data: mysql(CLI) Local 전용, 외부는 지원 안함
load data local infile '/root/pet.txt' into table pet;

-- select 연습
select name, species, birth
	from pet
where birth >= '1998-01-01';

select name, species, gender
	from pet
where species='dog' and gender='f';

select name, species from pet where species='snake' or species='bird';

select name, birth, death from pet where death is not null;

-- like 사용
select name from pet where name like 'b%';
select name from pet where name like '%fy';
select name from pet where name like 'B____';

select count(*), max(birth) from pet;