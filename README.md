# Pacilkom-Bot

[![Telegram](http://trellobot.doomdns.org/telegrambadge.svg)](https://telegram.me/pacilkom_bot)

A Telegram bot specificially created for Pusat Ilmu Komputer UI. It can query some academic informations for students of Faculty of Computer Science.

### Features

  - Get 5 most recent announcements from SCeLE front page
  - Get latest date & time according to SCeLE server time
  - Get academic record summary on a certain semester (logged in users)
  - Get academic record summary on all semester (IPK) (logged in users)
  - Get academic record on a specific course (logged in users)
  - Retrieve chatbot user's class schedule for a week (logged in users)
  - Retrieve the number of class sessions skipped by user in this semester
    (logged in users, to be implemented)

### Usage

#### Default Features

- Retrieve 5 most recent announcements from Scele’s front page
   - Use `/news` command in chat to get the list of announcement hyperlinks
 
- Get latest date & time according to Scele server’s time
   - Use `/time` command. Scele's server time is using `ntp.ui.ac.id`
     time server so the bot refers from that

#### Logged In Features

You must be the student of Faculty of Computer Science, University of Indonesia to use these features.

- Logging In
  - Use `/login` command
  - You will be redirected to the login web page
  - Log in through this and you will be brought back to Telegram

- Getting academic record summary on a specific course
  - Use `/record` command that will help finding the specific course
  - Pick the year, followed by picking the term.
  - The bot then lists all the courses you have taken on that semester.
  - Pick one of them and the bot will display the record of the course.
  
- Get academic record summary on a certain semester
  - Use `/record` command that will help finding the specific course
  - Pick the year, followed by picking the term.
  - The bot then lists all the courses you have taken on that semester.
  - Choose the summarize course option and the bot will display the summary.

- Get academic record summary on all semester (IPK)
  - Use `/record` command that will help finding the specific course
  - Choose "Summarize My IPK"
  - The bot will summarize all the passed courses, and give the report

### Dependencies

* Java 8+
* Spring Boot framework `2.0.1`
* Telegram Bot API `org.telegrambots`
* JSoup for Scele features
* PostgreSQL JDBC Driver for login/logout feature

### Authors and their Workloads
1. Dennis Febri Dien - 1606838193 - A - dennis.febri.dien
   - Implements Scrapper to retreive Scele recent announcements feature
   - Implements Scele latest date and time feature
2. Ichlasul Affan - 1606895606 - B - ichlaffterlalu
   - Creates GitLab CI and Gradle configurations for `pacilkom-bot` project
   - Implements basic Spring project structure for bot's basic functionalities (pair programming role: driver)
   - Implements login and logout functionality (pair programming role: observer)
   - Implements utility for retrieving database from CSUI API
   - Implements schedule features
3. Muhammad Imbang Murtito - 1606889502 - A - Imbang_XXV
   - Implements login and logout functionality (pair programming role: driver)
   - Refactor login functionality and session database
   - Implements utility for retrieving database from CSUI API
4. Rachmat Ridwan - 1606886974 - A - 72ridwan
   - Implements basic Spring Boot project structure for bot's basic functionalities (pair programming role: observer)
5. Samuel Tupa Febrian - 1606878713 - B - spectrum71
   - Optimizes Spring project structure for overall bot functionality (refactors `CommandManager` and `UpdateHandler`)
   - Implements SCeLE recent announcements feature (pair programming role: driver)
   - Implements academic record summary features and academic record for specific courses