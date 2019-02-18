# create databases
CREATE DATABASE IF NOT EXISTS `brews`;
CREATE DATABASE IF NOT EXISTS `brews_auth`;

# create root user and grant rights
CREATE USER 'root'@'localhost' IDENTIFIED BY 'local';
GRANT ALL ON *.* TO 'root'@'%';