
select *
from student
where age > 10
  and age < 20;

select name
from student;

select * from student where lower(name) like lower('%s%');

select *
from student
where age < student.id;

select * from student ORDER BY age;