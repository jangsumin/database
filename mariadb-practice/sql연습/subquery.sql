--
-- Subquery
--

--
-- 1) select 절
--
select (select 1 + 1 from dual) from dual;
-- insert into t1 values(null, (select max(no) + 1 from t1)

--
-- 2) from 절의 서브 쿼리
--
select now() as n, sysdate() as s, 3 + 1 as r from dual;
select a.n, a.r
	from (select now() as n, sysdate() as s, 3 + 1 as r from dual) a;
    
--
-- 3) where 절의 서브 쿼리
--
-- 예) 현재, Fai Bale이 근무하는 부서에서 근무하는 직원의 사번, 전체 이름을 출력해보세요.
select d.dept_no from employees e, dept_emp d
    where e.emp_no = d.emp_no
    and d.to_date = '9999-01-01'
    and concat(e.first_name, " ", e.last_name) = 'Fai Bale';
    
select a.emp_no, a.first_name
	from employees a, dept_emp b
    where a.emp_no = b.emp_no
    and b.to_date = '9999-01-01'
    and b.dept_no in 
		(select d.dept_no from employees e, dept_emp d
		where e.emp_no = d.emp_no
		and d.to_date = '9999-01-01'
		and concat(e.first_name, " ", e.last_name) = 'Fai Bale');
        
--
-- 3-1) 단일행 연산자: =, >, <, >=, <=, <>, !=
-- 실습문제1
-- 현재, 전체 사원의 평균 연봉보다 적은 급여를 받는 사원의 이름과 급여를 출력하세요
-- select avg(salary) from salaries;
select e.first_name, s.salary from employees e
	join salaries s on e.emp_no = s.emp_no
	where s.salary < (select avg(salary) from salaries where to_date = '9999-01-01');
    
-- 실습문제2
-- 현재, 직책별 평균급여 중에 가장 적은 평균급여의 직책 이름과 그 평균급여를 출력하세요
-- select 

select t.title as '직책', avg(s.salary) as '평균급여' from (select * from titles where to_date = '9999-01-01') t
	join (select * from salaries where to_date = '9999-01-01') s on (s.emp_no = t.emp_no)
	group by t.title order by avg(s.salary) asc limit 1;

-- 3-2) 복수행 연산자: in, not in, 비교연산자 any, 비교연산자 all
-- 1. =any: in
-- 2. >any, >=any: 최소값
-- 3. <=any, <any: 최대값
-- 4. <>any, !=any: not in

-- all 사용법
-- 1. =all: (x)
-- 2. >all, >=all: 최대값
-- 3. <all, <=all: 최소값
-- 4. <>all, !=all:

-- 실습문제3
-- 현재 급여가 50000 이상인 직원의 이름과 급여를 출력하세요.
select e.first_name, s.salary from employees e
	join (select emp_no, salary from salaries where to_date = '9999-01-01' and salary >= 50000) s
	on e.emp_no = s.emp_no;
    
-- 실습문제4
-- 현재, 각 부서별 최고급여를 받고 있는 직원의 이름, 부서이름, 급여를 출력하세요.

select a.first_name, d.dept_name, c.salary from employees a, dept_emp b, salaries c, departments d
	where a.emp_no = b.emp_no
    and b.dept_no = d.dept_no
    and a.emp_no = c.emp_no
    and b.to_date = '9999-01-01'
    and c.to_date = '9999-01-01'
    and (b.dept_no, c.salary) in (select d.dept_no, max(s.salary) from salaries s, dept_emp d
		where s.emp_no = d.emp_no
		and s.to_date = '9999-01-01'
		and d.to_date = '9999-01-01'
		group by d.dept_no);