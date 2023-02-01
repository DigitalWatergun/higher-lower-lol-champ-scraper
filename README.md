# higher-lower-lol-champ-scraper
Cronjob that will scrape the web for LoL champions and their winrates.

## Architecture Diagram
![arch_diagram](https://github.com/DigitalWatergun/higher-lower-lol-champ-scraper/blob/main/diagram/HigherLowerLolChamp_Architecture_Diagram.png)

Champion Name and loading screen splash art images are retrieved from RiotGames' developer data assets.
The match data is going to be web scraped from U.GG. 
The data will then be parsed and inserted/updated in the database. 

### Credits
- Riot Developer Assets: https://developer.riotgames.com/docs/lol#data-dragon_data-assets
- U.GG: https://u.gg/