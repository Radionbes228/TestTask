/*Use only on a clean base!*/
insert into users(email, password)
    VALUES
        ('test1@gmail.com', '$2a$12$NstFiiGSSHeVJ7uE/13F.uGnZZQoL2f1bpyGRsoldeckPvQ3bZAaW'), /* password = test1@gmail.com */
        ('test2@gmail.com', '$2a$12$9TYcGms8rEu0w9vNXuKgZOTfLf/OqQxTiIdLjqixuzqZ9Hdf4V6Ty'), /* password = test1@gmail.com */
        ('test3@gmail.com', '$2a$12$VUT81PhTmUZW25QArRW8femv8eryO/5z69WK6XoF.iELEdjeeucpK'), /* password = test1@gmail.com */
        ('test4@gmail.com', '$2a$12$hu0kZmxb0lVYLtkLFyAZn.4dzZjoHDmzvFd05ABerbUZcXKtHqT6O'), /* password = test1@gmail.com */
        ('test5@gmail.com', '$2a$12$M5tBVg8pnDRFfucEAIqGmupUwIJm9WADAmci.QPeJp1WDJJrRj.yi'); /* password = test1@gmail.com */


insert into task(description, priority, status, title, assignee_id, author_id)
    VALUES
        (null, 'LOW', 'PENDING', 'Handle validation error', null, 1),
        ('Finish before the weekend!', 'MEDIUM', 'IN_PROGRESS', 'Fix the data duplication bug', 2, 1),
        (null, 'HIGH', 'COMPLETED', 'Submit a test assignment', 5, 2),
        (null, 'LOW', 'PENDING', 'Talk to the Front end developer', 3, 4);

insert into comment(text, author_id, task_id)
    VALUES
        ('Faster!', 1, 2),
        ('Its interesting, but there are mistakes', 1, 2),
        ('Why are you procrastinating?', 4, 4);

/* Неоюходимо переписать данные для заполнения БД */