# Jotihunt Sync cronjob

- Runs every minute

## Main entry point
Start the class `JotihuntSync` (currently in the default package for simplicity)

# Configuration
Need system or JVM params:
- username
  - Type: `String`
- password
  - Type: `String`
- endpoint (URL)
  - Type: `String`
  - Example: https://jotihunt-roothaan.herokuapp.com/
- eventId
  - Type: `Integer`
  - example: 1
