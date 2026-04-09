---
  layout: default.md
  title: "Logging guide"
---

# Logging guide

* We are using `java.util.logging` package for logging.
* The `LogsCenter` class is used to manage the logging levels and logging destinations.
*  The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to the specified logging level.
*  Log messages are output through the console and to a `.log` file.
*  The default logging level is `INFO`. The output logging level can be controlled using the `logLevel` setting in the configuration file (See the [Configuration guide](Configuration.md) section).
* The logging levels used in the codebase are:
    * `SEVERE` — critical errors (e.g., failure to save preferences)
    * `WARNING` — recoverable issues (e.g., data file could not be loaded, duplicate gamer tag detected)
    * `INFO` — general application flow (e.g., initialisation, user commands, sort/filter operations)
    * `FINE` — detailed tracing (e.g., reading/writing data files, model initialisation)
* To change the log level, edit the `logLevel` field in `config.json`:
  ```json
  {
    "logLevel" : "FINE",
    "userPrefsFilePath" : "preferences.json"
  }
  ```
  Set it to `FINE` or `FINER` to see more detailed log messages. The change takes effect on the next app launch.
* **When choosing a level for a log message**, follow the conventions given in [_[se-edu/guides] Java: Logging conventions_](https://se-education.org/guides/conventions/java/logging.html).
