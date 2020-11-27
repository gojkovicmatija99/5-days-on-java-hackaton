# 5 days on java hackaton

## Requrements

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
categories for which the player has achieved a double-digit performance. Explanation below the task)
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

