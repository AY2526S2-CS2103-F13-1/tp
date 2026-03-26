---
layout: default.md
title: "User Guide"
pageNav: 3
---

# BlockBook User Guide

BlockBook is a **desktop app for managing contacts, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, BlockBook can get your contact management tasks done faster than traditional GUI apps.

BlockBook makes it easy to manage the contacts of other gamers you meet on servers, allowing you to manage contacts through not just names, but other gaming attributes too.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S2-CS2103-F13-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for BlockBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar BlockBook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open this User Guide in a browser window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add name/John Doe gamertag/JD910` : Adds a contact named `John Doe` to BlockBook with the gamertag `JD910`.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  * e.g. in `add name/NAME`, `NAME` is a parameter which can be used as `add name/John Doe`.

* Items in square brackets are optional.<br>
  * e.g `name/NAME [t/TAG]` can be used as `name/John Doe t/friend` or as `name/John Doe`.

* Items with `…` after them can be used multiple times including zero times.
  * e.g. `[t/TAG]…` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `name/NAME gamertag/GAMERTAG`, `gamertag/GAMERTAG name/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Opens this User Guide in a browser window.

Format: `help`


### Adding a gamer: `add`

Adds a gamer to BlockBook.

Format: `add name/NAME gamertag/GAMERTAG`

<box type="tip" seamless>

**Tip:** Both `name/` and `gamertag/` parameters are required.
</box>

Examples:
* `add name/John Doe gamertag/JohnThePro`
* `add name/Betsy Crowe gamertag/ShadowCrowe`

### Listing all gamers : `list`

Shows a list of all gamers stored in BlockBook.

Format: `list`

* Only show important fields (gamertag, name, server, favourite and region)

### Editing a gamer : `edit`

Edits an existing gamer stored in BlockBook.

Format: `edit INDEX [gamertag/GAMERTAG] [name/NAME] [phone/PHONE] [email/EMAIL] [group/GROUP] [server/SERVER] [favourite/FAVOURITE] [country/COUNTRY] [region/REGION] [note/NOTE]`

* Edits the gamer at the specified `INDEX`. The index refers to the index number shown in the displayed gamer list. The index **must be a positive integer** 1, 2, 3, ...
* At least one of the optional fields must be provided.
* Does not allow duplicate gamertags.
* Existing values will be updated to the input values.
* Edit for region and favourite must follow the provided values.

Examples:
*  `edit 1 name/Herobrine gamertag/ilovesteve phone/99999 email/brine@gmail.com group/DestroySteve favourite/fav country/Singapore region/SEA note/I hate steve` Edits all the fields of the first gamer.     
*  `edit 2 name/Betsy ` Edits the name of the 2nd gamer to be `Betsy`.

### Editing a gamer’s favourite status : `favourite/unfavourite`

Updates a gamer’s favourite status via index

Format: `favourite INDEX` or `unfavourite INDEX`

* Updates the favourite status of the gamer at the specified `INDEX`. The index refers to the index number shown in the displayed gamer list.

Examples:
*  `favourite 1` Updates the favourite status of the first gamer to favourite.
*  `unfavourite 1` Remove the first gamer from favourites.

### Creating a group
* To create a group, it can be done via the `add` command or `edit` command.
* Group names are also a max 50 characters, letters, spaces, hyphens, and apostrophes allowed.
* A gamer contact can have zero or multiple groups.

Examples:
* `add gamertag/GAMERTAG group/GROUP` (Derived from [1] Add a Contact, any valid command with group/GROUP)
* `edit INDEX group/GROUP` (Derived from [2] Edit a Contact, any valid command with group/GROUP)

### Locating gamers by name: `find`

Finds gamers whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* gamers matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Viewing a gamer's full contact details: `view`
Returns in the command prompt and GUI, the full contact details of the gamer associated given a valid Gamertag.

Format: `view gamertag/GAMERTAG`

<box type="tip" seamless>

**Tip:** `gamertag/` parameter is required.
</box>


Examples:
* `view gamertag/SteveMaster99`

### Deleting a Gamer : `delete`

Deletes the specified gamers from BlockBook.

Format: `delete INDEX [INDEX]...`

* Deletes the gamers at each specified `INDEX`.
* The indexes refer to the index numbers shown in the displayed gamer list.
* Each index **must be a positive integer** 1, 2, 3, ...

Examples:
* `list` followed by `delete 2` deletes the 2nd gamer shown in the list.
* `find Betsy` followed by `delete 1 2` deletes the 1st and 2nd gamer in the results of the `find` command.

### Sorting contacts : `sort`

Sorts the contact list by the specified attributes. Favourite contacts are always shown first.

Format: `sort [ATTRIBUTE/]…`

* If no attributes are provided, contacts are sorted by gamertag (default).
* Multiple attributes can be specified to sort by priority order (first attribute is the primary sort key).
* Sorting is **session-based** and does not persist after closing the app.
* Valid attributes: `name`, `phone`, `email`, `groups`, `server`, `favourite`, `country`, `region`, `note`, `gamertag`.

<box type="tip" seamless>

**Tip:** Favourite contacts are always pinned to the top of the list, regardless of the sort attributes.
</box>

Examples:
* `sort` sorts contacts by gamertag (default).
* `sort name/` sorts contacts by name.
* `sort name/ phone/` sorts contacts by name first, then by phone for contacts with the same name.

### Clearing all entries : `clear`

Clears all entries from BlockBook.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

BlockBook data is saved to primary storage automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

BlockBook data is saved automatically as a JSON file `[JAR file location]/data/contacts.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, BlockBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause BlockBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.

</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous BlockBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action     | Format, Examples                                                                 |
|------------|----------------------------------------------------------------------------------|
| **Add**    | `add name/NAME gamertag/GAMERTAG` <br> e.g., `add name/James Ho gamertag/JamieH` |
| **Clear**  | `clear`                                                                          |
| **Delete** | `delete INDEX [INDEX]...`<br> e.g., `delete 3`, `delete 2 5`                     |
| **Edit**   | `edit INDEX [name/NAME] [gamertag/GAMERTAG]`<br> e.g.,`edit 2 name/James Lee`    |
| **Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                       |
| **View**   | `view gamertag/GAMERTAG` <br> e.g., `view gamertag/SteveMaster99`                |
| **List**   | `list`                                                                           |
| **Sort**   | `sort [ATTRIBUTE/]…`<br> e.g., `sort name/`, `sort name/ phone/`                 |
| **Help**   | `help`                                                                           |

