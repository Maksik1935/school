create table "Drivers"."Drivers"
(
    name              text    not null
        constraint table_name_pkey1
            primary key,
    age               integer CHECK(age > 0),
    have_a_drive_pass boolean not null,
    car_id            integer not null
);

create table "Drivers"."Cars"
(
    id    integer not null
        constraint table_name_pkey
            primary key,
    make  text    not null,
    model text    not null,
    price numeric CHECK( price > 0)
);


SELECT student.name, student.age, faculty.name FROM student INNER JOIN faculty on faculty.id = student.faculty_id
SELECT student.name, student.age FROM student INNER JOIN avatar on student.id = avatar.student_id
