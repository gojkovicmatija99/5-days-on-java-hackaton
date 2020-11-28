# 5 days on Java hackaton


![alt text](https://github.com/gojkovicmatija99/5-days-on-java-hackaton/blob/master/demo.PNG)

## Requirement

Basketball is one of the most popular sports in the world and in line with the needs of watching games
and statistics a large number of software solutions are being developed. It also belongs to national sports and
thus states have their own basketball leagues.
Currently, the world strives for each league to have its own system for monitoring results. As the strongest league in the world
considered the American NBA League. Based on their match tracking system and team / player statistics
we will build our prototype.
The plan is for our application to support statistical processing as well as event handling.

The task is to create a Java web application to display matches and details of individual matches.
The following queries need to be implemented, supported:
- QUERY_ID: 1 - Show all matches with current results. Both active and completed are considered
matches.
- QUERY_ID: 2 - Display player details for the selected match, ie player statistics for that match
match (points, assists, rebounds)
- QUERY_ID: 3 - Show statistics for the selected player for all matches (total and average points,
jumps, assists)
- QUERY_ID: 4 - Show the players who scored the most points, rebounds and assists at all
match (one player per category, if more players have achieved the same maximum number show them
all)
- QUERY_ID: 5 - Show the top 5 players with the most “double-doubles” (any 2 statistics
categories for which the player has achieved a double-digit performance)
- QUERY_ID: 6 - Show team rankings - calculated based on the percentage of wins in all
matches (if the teams have the same percentage, the advantage is given to the team that has a better overall
basket difference)

Frontend :
The initial frontend should contain the following visualizations (QUERY_ID: 1, QUERY_ID: 2):
- View all matches with results
- View details of one selected match with player statistics for that match (number
points, assists and rebounds per player)

Entries to the application are:
- Configuration JSON file with data on teams and players
- JSON file with a list of events that describe the course of matches

When loading a JSON data file, skip loading events that do not meet the rules and log them:
- The finished match must be covered between START and END events.
- It is also important to emphasize the possibility of a lack of END event, which therefore means that it is a match
still active, ie in progress.
- After a ASSIST event, only a POINT event can occur, with a value of 2 or 3
points.
- We have no major restrictions for POINT and JUMP events and they can happen in any
order and to repeat themselves.
- A player to whom any type of event is added must be a member of the team participating in that match.

## Implementation

### Description of the environment needed to do the build:
The environments we need to do the build are: Java Spring, Java Spring Boot

### How to build:
The application is built by running mvn clean install.

### Example of how to run an application:
The application is launched through the Application class, which is located in the backend folder. After we
launched the application, it starts working on localhost on port 8080. When loaded the main page displayes the result of query1, where the green matchs are still being played and when we click on a match (row in the table), it is displayed in the new table
statistics for all players in the selected match.
Also the file that logs invalid log.txt events is located at the root of the project. Files that are
used as input for the program are located in the resources folder named teams.json, players.json and
events.json.

### Example of calling REST endpoints for each implemented query:
1. query1: get the request sent in the path *localhost:8080/api/1*. As a return value we get a list of all matches where each match has the following data: home name
team, the name of the visiting team, the number of points scored by the host, the number of points scored
guest, whether the match is over and the match id.
2. query2: get the request sent to the path *localhost:8080/api/2/{id}*. As a return value
we get a list of all the players of one game, and that is the game whose id we forwarded in the path.
We get the performance of each player in that game, and that is the number of points scored, assists
and jumps.
3. query3: get the request sent in the path *localhost:8080/api/3/{id}*. As a return value
we get data about the player whose id we forwarded in the path. We get the number total
points scored by that player, average points per game, number of total assists, average
assistance per game, number of total rebounds, average rebounds per game.
4. query4: get the request sent in the path *localhost:8080/api/4*. As a return value
we get the player who has the best score in a certain category as well as what that score is.
5. query5: get the request sent to the path *localhost:8080/api/5*. As a return value
we get the top 5 players who scored the most “double-doubles” and how many they scored
"double-double".
6. query6: get the request sent in the path *localhost:8080/api/6*. As a return value we get sorted teams by the percentage of victories, if the percentage of victories is the same
then they are ranked by oblique difference. We have data on the percentage of victories and what the percentage is
is a piece of difference.

### List of used technologies with a short description:
The Java Spring Boot framework was used to create the backend part of the project, and Vue.js was used to create the frontend. In the backend in the Controller class we have paths to get
requests, and then in the frontend we take that data that we sent and display it in the browser.
We did not use any database, but we take the data from json files. Reading from files we work in the StorageReader class.

![alt text](https://github.com/gojkovicmatija99/5-days-on-java-hackaton/blob/master/classModel.PNG)
