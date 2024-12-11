-- 서브쿼리(SUBQUERY) SQL 문제입니다.

-- 단 조회결과는 급여의 내림차순으로 정렬되어 나타나야 합니다. 

-- 문제1.
-- 현재 전체 사원의 평균 급여보다 많은 급여를 받는 사원은 몇 명이나 있습니까?
select count(*)
	from employees e, salaries s
    where e.emp_no = s.emp_no
	and s.salary > (select avg(salary)
		from salaries
        where to_date = '9999-01-01');

-- 문제2. 
-- 현재, 각 부서별로 최고의 급여를 받는 사원의 사번, 이름, 부서 급여을 조회하세요. 단 조회결과는 급여의 내림차순으로 정렬합니다.
select a.emp_no, a.first_name, c.salary from employees a, dept_emp b, salaries c, departments d
	where a.emp_no = b.emp_no
    and b.dept_no = d.dept_no
    and a.emp_no = c.emp_no
    and b.to_date = '9999-01-01'
    and c.to_date = '9999-01-01'
    and (b.dept_no, c.salary) in (select d.dept_no, max(s.salary) from salaries s, dept_emp d
		where s.emp_no = d.emp_no
		and s.to_date = '9999-01-01'
		and d.to_date = '9999-01-01'
		group by d.dept_no)
	order by c.salary desc;

-- 문제3.
-- 현재, 사원 자신들의 부서의 평균급여보다 급여가 많은 사원들의 사번, 이름 그리고 급여를 조회하세요
select e.emp_no, e.first_name, s.salary
	from employees e, salaries s, dept_emp d, 
	(select d.dept_no as a, avg(s.salary) as b from salaries s, dept_emp d
		where s.emp_no = d.emp_no
		and s.to_date = '9999-01-01'
		and d.to_date = '9999-01-01'
		group by d.dept_no) t
	where e.emp_no = s.emp_no
    and e.emp_no = d.emp_no
    and s.to_date = '9999-01-01'
    and d.to_date = '9999-01-01'
    and d.dept_no = t.a
    and s.salary > t.b
    order by s.salary desc;
        
-- 문제4.
-- 현재, 사원들의 사번, 이름, 그리고 매니저 이름과 부서 이름을 출력해 보세요.
select e.emp_no, e.first_name, m.b, d.dept_name
	from employees e, departments d, dept_emp c, (select d.dept_no as a, e.first_name as b
		from employees e, dept_manager c, departments d
		where e.emp_no = c.emp_no
		and c.dept_no = d.dept_no
		and c.to_date = '9999-01-01') m
    where e.emp_no = c.emp_no
    and c.dept_no = d.dept_no
    and c.to_date = '9999-01-01'
    and c.dept_no = m.a;

-- select d.dept_name as a, e.first_name as b
-- 		from employees e, dept_manager c, departments d
-- 		where e.emp_no = c.emp_no
-- 		and c.dept_no = d.dept_no
-- 		and c.to_date = '9999-01-01';

-- 문제5.
-- 현재, 평균급여가 가장 높은 부서의 사원들의 사번, 이름, 직책 그리고 급여를 조회하고 급여 순으로 출력하세요.
select e.emp_no, e.first_name, t.title, s.salary
	from employees e, titles t, salaries s, dept_emp d
    where e.emp_no = t.emp_no
    and e.emp_no = s.emp_no
    and e.emp_no = d.emp_no
    and t.to_date = '9999-01-01'
    and s.to_date = '9999-01-01'
    and d.to_date = '9999-01-01'
    and d.dept_no = (select d.dept_no
		from salaries s, dept_emp d
        where s.emp_no = d.emp_no
        and s.to_date = '9999-01-01'
        and d.to_date = '9999-01-01'
        group by d.dept_no
        order by avg(s.salary) desc
        limit 1)
	order by s.salary desc;

-- 문제6.
-- 현재, 평균 급여가 가장 높은 부서의 이름 그리고 평균급여를 출력하세요.
select d.dept_name, avg(s.salary)
	from salaries s, departments d, dept_emp c
    where s.emp_no = c.emp_no
    and c.dept_no = d.dept_no
    and s.to_date = '9999-01-01'
    and c.to_date = '9999-01-01'
    group by c.dept_no
    order by avg(s.salary) desc limit 1;

-- 문제7.
-- 현재, 평균 급여가 가장 높은 직책의 타이틀 그리고 평균급여를 출력하세요.
select t.title, avg(s.salary)
	from salaries s, titles t
    where s.emp_no = t.emp_no
    and s.to_date = '9999-01-01'
    and t.to_date = '9999-01-01'
    group by t.title
    order by avg(s.salary) desc limit 1;