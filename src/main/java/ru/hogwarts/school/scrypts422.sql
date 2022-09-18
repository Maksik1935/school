create table Cars
(
    id    integer primary key,
    make  text    not null,
    model text    not null,
    price numeric CHECK( price > 0)
);

create table Drivers
(
    name text primary key,
    age integer CHECK(age > 0),
    have_a_drive_pass boolean not null,
    car_id integer not null,
    FOREIGN KEY (car_id) REFERENCES Cars (id)

);


SELECT student.name, student.age, faculty.name FROM student INNER JOIN faculty on faculty.id = student.faculty_id
SELECT student.name, student.age FROM student LEFT JOIN avatar on student.id = avatar.student_id
