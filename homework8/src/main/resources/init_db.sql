CREATE TABLE IF NOT EXISTS `books` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR (200) NOT NULL
)

INSERT INTO `books`(`id`, `name`) VALUES(1, 'Tell my name')