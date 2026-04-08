---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---


# BlockBook Developer Guide
The developer guide will be updated iteratively as the implementation progresses.
The current content is based on the design decisions we have made so far, and may be updated as we make more design decisions in the future.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------
## **Acknowledgements**
The UI mockup was generated with ChatGPT using the following [input](https://chatgpt.com/share/69a2747d-cb94-800c-bb01-49b78ced58b4).

## **Setting up and getting started**

Refer to the guide: [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2526S2-CS2103-F13-1/tp/tree/master/src/main/java/seedu/blockbook/Main.java) and [`MainApp`](https://github.com/AY2526S2-CS2103-F13-1/tp/tree/master/src/main/java/seedu/blockbook/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2526S2-CS2103-F13-1/tp/tree/master/src/main/java/seedu/blockbook/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `GamerListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2526S2-CS2103-F13-1/tp/tree/master/src/main/java/seedu/blockbook/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2526S2-CS2103-F13-1/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Gamer` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2526S2-CS2103-F13-1/tp/tree/master/src/main/java/seedu/blockbook/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `BlockBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to delete a gamer).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `BlockBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `BlockBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2526S2-CS2103-F13-1/tp/tree/master/src/main/java/seedu/blockbook/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores contact data i.e., all `Gamer` objects (which are contained in a `UniqueGamerList` object) and all `Group` objects (which are contained in a `UniqueGroupList` object).
  * stores the currently 'selected' `Gamer` objects (e.g., results of a search query) as a separate _sorted_ and _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Gamer>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPrefs` object that represents the user's preferences. This is exposed to the outside as a `ReadOnlyUserPrefs` object.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Group` list in the `BlockBook`, which `Gamer` references. This allows `BlockBook` to only require one `Group` object per unique group, instead of each `Gamer` needing their own `Group` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2526S2-CS2103-F13-1/tp/tree/master/src/main/java/seedu/blockbook/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both contact data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `BlockBookStorage` and `UserPrefsStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.blockbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section is WIP

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* Minecraft player
* Discord user
* Has a Minecraft gamertag
* CLI users, fast typer
* Comfortable typing Minecraft commands
* Needs to keep track of a significant number of contacts for gaming together

**Value proposition**: BlockBook makes it easy for Minecraft gamers to connect with other players by saving contacts of players they meet on servers. With a familiar command line interface, adding, organising and finding is a breeze.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a ...                     | I want to ...                                            | So that I can...                                                       |
|----------|-----------------------------|---------------------------------------------------------|-----------------------------------------------------------------------|
| `* * *`  | general user                | add a new contact                                       | link multiple contact methods to a gamer                              |
| `* * *`  | general user                | delete a gamer                                         | remove contact entries that I no longer need                          |
| `* * *`  | general user                | list out my contacts                                    | see my contacts that I saved previously                               |
| `* * *`  | general user                | view a contact’s profile with their full details        | access comprehensive details when needed                              |
| `* *`    | general user                | find a gamer by name                                   | locate details of gamers without having to go through the entire list |
| `* *`    | new user                    | see usage instructions                                  | figure out how to use the app easily                                  |
| `* *`    | general user                | update contact details                                  | keep track of my contacts' latest information                         |
| `* *`    | general user                | avoid adding duplicate contacts                         | not store the same contact twice by accident                          |
| `* *`    | general user                | sort the contacts alphabetically                        | access my contacts easier                                             |
| `* *`    | general user                | sort the contacts by added date                         | find my contacts I recently added                                     |
| `* *`    | minecraft gamer / pro typer | delete contacts in bulk                                 | delete more contacts at one go                                        |
| `* *`    | general user                | see clear error messages when I enter invalid commands  | correct my mistakes quickly                                           |
| `* *`    | general user                | add contacts to a favourites list                       | access my favourite contacts easier                                   |
| `* *`    | general user                | list out my favourite contacts                          | find my favourite contacts                                            |
| `* *`    | general user                | add a personal note to a contact's profile              | preserve context information about a contact                          |
| `* *`    | general user                | create a social group                                   | create groups with contacts with a context                            |
| `* *`    | general user                | add contact to social group                             | find the contacts I want to play with based on context                |
| `*`      | general user                | use autocomplete when typing in CLI                     | type faster and easier when I forget the command                      |
| `*`      | general user                | add profile picture to contact                          | recognise contacts more easily via visual                             |
| `*`      | minecraft gamer             | see quality sprite styles that align with minecraft     | have a good interface experience                                      |

### Use cases

(For all use cases below, the **System** is the `BlockBook (BB)` and the **Actor** is the `user`, unless specified otherwise)
As these represent the expected behaviour of the final iteration, some use cases might not reflect the current functionality of the app.

**UC01 - Add a Gamer Contact**

**MSS**

1.  User chooses to add a new contact.
2.  BB requests the contact's details (gamertag, server name, optional label).
3.  User enters the requested details.
4.  BB requests confirmation.
5.   User confirms.
6.    BB saves the new contact and displays the updated contact list.

Use case ends.

**Extensions**

3a. BB detects that the gamertag field is empty or contains invalid characters.

- 3a1. BB displays an error and requests correct data.

- 3a2. User enters new data.

- Steps 3a1-3a2 are repeated until the data entered is correct.

- Use case resumes from step 4.

3b. BB detects that a contact with the same gamertag already exists.

- 3b1. BB warns the user of the duplicate entry and asks whether to proceed.
- 3b2. User chooses to proceed or cancel.
- If User cancels, use case ends. Otherwise, use case resumes from step 4.

*a. At any time, User chooses to cancel adding the contact.

- *a1. BB discards all entered data.
- Use case ends.

**UC02 - List All Gamer Contacts**

**MSS**

1. User chooses to view all saved contacts.
2. BB retrieves all entries.
3. BB displays a list of all contacts with their basic details.

Use case ends.

**Extensions**

2a. The contact list is empty.

- 2a1. BB informs the user that no contacts are currently stored.
- Use case ends.

**UC03 - Favourite a Contact**

**MSS**

1. User chooses to favourite a contact.
2. BB requests the gamertag of the contact to favourite.
3. User enters the gamertag.
4. BB marks the contact as a favourite and confirms the update.

Use case ends.

**Extensions**

3a. BB cannot find a contact matching the entered gamertag.

- 3a1. BB displays an error and requests a valid gamertag.
- 3a2. User enters a new gamertag.
- Steps 3a1-3a2 are repeated until a match is found.
- Use case resumes from step 4.

4a. The contact is already marked as a favourite.

- 4a1. BB notifies the user that the contact is already a favourite.
- Use case ends.

(An extension can be added for unfavourite here)

**UC04 - Add Profile Picture to Contact** (TBA)

**MSS**

1. User chooses to add a profile picture to a contact.
2. BB requests the contact's gamertag.
3. User enters the gamertag.
4. BB requests the image to use as the profile picture.
5. User provides the image.
6. BB requests confirmation.
7. User confirms.
8. BB saves the profile picture and displays the updated contact profile.

Use case ends.

**Extensions**

3a. BB detects that the gamertag is empty or contains invalid characters.

- 3a1. BB displays an error and requests correct data.
- 3a2. User enters new data.
- Steps 3a1-3a2 are repeated until the data entered is correct.
- Use case resumes from step 4.

5a. BB detects that the provided image is invalid or cannot be accessed.

- 5a1. BB displays an error and requests a valid image.
- 5a2. User provides new image data.
- Steps 5a1-5a2 are repeated until the data entered is correct.
- Use case resumes from step 6.

*a. At any time, User chooses to cancel.

- *a1. BB discards all entered data.
- Use case ends.

**UC05 - Add Note to Contact** (Can be deleted)

**MSS**

1. User chooses to add a note to an existing contact.
2. BB requests the gamertag of the target contact.
3. User enters the gamertag.
4. BB displays the contact's current details.
5. User enters the note to be added.
6. BB saves the note and displays the updated contact profile.

Use case ends.

**Extensions**

3a. BB cannot find a contact matching the entered gamertag.

- 3a1. BB displays an error and requests a valid gamertag.
- 3a2. User enters a new gamertag.
- Steps 3a1-3a2 are repeated until a match is found.
- Use case resumes from step 4.

5a. User enters a note that exceeds the maximum character limit.

- 5a1. BB displays an error indicating the limit and requests a shorter note.
- 5a2. User enters a new note.
- Use case resumes from step 6.

*a. At any time, User chooses to cancel.

- *a1. BB discards all unsaved changes.
- Use case ends.

**UC06 - Sort Gamer Contacts** (Extensions can be added for the optional attribute criteria)
(Base case as MSS, other sorting criteria can be added as extensions)

**MSS**

1. User chooses to sort contacts by added date.
2. BB displays all contacts sorted in chronological order by added date, from most recent.

Use case ends.

**Extensions**

2a. BB finds no contacts.
- 2a1. BB informs the user that there are no contacts.
- Use case ends.

**UC07 - Edit a Gamer Contact** (Change of name from Update)

**MSS**

1. User chooses to update a contact's details.
2. BB requests the current gamertag of the contact.
3. User enters the current gamertag.
4. BB displays the contact's current details and requests which attribute to change.
5. User enters the attribute to change.
6. BB requests the new value for the attribute.
7. User enters the new value.
8. BB requests confirmation.
9. User confirms.
10. BB updates the contact and displays the updated contact profile.

Use case ends.

**Extensions**

3a. BB cannot find a contact matching the entered gamertag.

- 3a1. BB displays an error and requests a valid gamertag.
- 3a2. User enters a new gamertag.
- Steps 3a1-3a2 are repeated until a match is found.
- Use case resumes from step 4.

5a. BB cannot identify the attribute to edit.

- 5a1. BB displays an error and requests a valid attribute name.
- 5a2. User enters a new attribute name.
- Steps 5a1-5a2 are repeated until a valid attribute is entered.
- Use case resumes from step 6.

7a. BB detects that the new gamertag is already in use by another contact.

- 7a1. BB warns the user of the conflict and requests a different gamertag.
- 7a2. User enters a new gamertag.
- Use case resumes from step 8.

7b. BB detects that the entered value contains invalid characters.

- 7b1. BB displays an error and requests a valid value.
- 7b2. User enters a new value.
- Use case resumes from step 8.

*a. At any time, User chooses to cancel.

- *a1. BB discards all changes.
- Use case ends.

**UC08 - Delete a Gamer Contact**

**UC09 - View a Gamer Contact**

**UC10- Find Gamer Contacts**

**UC11 - Clear all Gamer Contacts**

**UC12 - Show Help**

**UC13 - Create a Group (TBA)**

**UC14 - Add a Gamer to a Group (TBA)**

**UC15 - Remove a Gamer from a Group (TBA)**

**UC16 - Delete a Group (TBA)**

**UC17 - List Groups (TBA)**

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 gamers without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Any successful command (e.g., `add`, `delete`) should cause the **GUI to update without noticeable delay** (less than **1 second**).
5. The application **should not crash or terminate** under normal usage scenarios (e.g., listing, adding, or deleting contacts).
6. The application should **not lose user data during normal operation**.
7. The application should **continue operating normally when invalid input is provided**.
8. When performing operations such as **bulk delete** or **filter**, the system should process the request **without freezing the GUI**.
9. **All user data should be stored locally.**
10. The data should be stored locally in a **human-editable text file** so that advanced users can manually manipulate the data if necessary.
11. The GUI should **work well** (i.e., should not cause resolution-related inconveniences) for:
   - screen resolutions **1920 x 1080 and higher**
   - screen scales **100% and 125%**
12. The GUI should remain **usable** (i.e., all functions can still be used even if the user experience is not optimal) for:
   - screen resolutions **1280 x 720 and higher**
   - screen scales **150%**

*{More to be added}*

### Glossary
- **Minecraft**: A sandbox game developed and published by Mojang Studios. See more [here](https://www.minecraft.net/en-us).
    - **Gamertag**: A Minecraft player's in-game username.
    - **Modpack**: A collection of Minecraft modifications bundled together for gameplay.
    - **Server**: A multiplayer Minecraft world hosted online where players interact.
- **Discord**: An instant messaging and VoIP social platform popular among gamers that allows communication through voice calls, video calls, text messaging, and media.
  Communication can be private or in virtual communities called "servers". See more [here](https://discord.com/).
- **Contact**: A gamer that a user has saved in BlockBook, representing a Minecraft player they have met on servers. A contact typically includes details such as the player's gamertag, server name, and other attributes.
- **CLI**: Command Line Interface, a way to interact with a computer program by typing commands into a console or terminal.
- **GUI**: Graphical User Interface, a way to interact with a computer program through graphical elements like windows, buttons, and icons.
- **Mainstream OS**: The common personal computer operating systems that BlockBook should be able to run on - Windows, Linux and MacOS.
- **Alias**: A shortened version of a command that performs the same function.
    - For example: `l` can be an alias for `list`, and `d` can be an alias for `delete`.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases ... }_

### Using the help command

### Adding a gamer contact

### Editing a gamer contact

### Deleting a gamer contact

1. Deleting a gamer while all gamers are being shown

   1. Prerequisites: List all gamers using the `list` command. Multiple gamers in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Gamertag of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No gamer is deleted. Error details shown in the status message. Status bar remains the same.

   1. Test case: `delete 1 2`<br>
      Expected: The first two contacts are deleted from the list. Gamertags of the deleted contacts shown in the status message. Timestamp in the status bar is updated.
4
   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases ... }_

### Setting a gamer contact as a favourite contact

### Listing gamer contacts

### Viewing a gamer contact

### Finding a gamer contact

1. Finding gamers with global keyword(s)

   1. Prerequisites: There are three gamers in the list (Alex with gamertag `CraftyAlex`, email `alex@craft.net`,
      group `Explorers`, server `srv1.gamehub.net`, country `USA`, region `NA`, note `builder`,
      Steve with phone `987654`, email `steve@craft.net`, group `Explorers`, server `srv2.gamehub.net`,
      country `USA`, note `builder pro`, and Herobrine with favourite status set).

   1. Test case: `find alex`<br>
      Expected: Only Alex is displayed in the list. A message indicates 1 gamer(s) found.

   1. Test case: `find al`<br>
      Expected: Only Alex is displayed in the list. A message indicates 1 gamer(s) found.

   1. Test case: `find craft`<br>
      Expected: Alex and Steve are displayed in the list. A message indicates 2 gamer(s) found.

   1. Test case: `find hub`<br>
      Expected: Alex and Steve are displayed in the list. A message indicates 2 gamer(s) found.

   1. Test case: `find gamehub`<br>
      Expected: Alex and Steve are displayed in the list. A message indicates 2 gamer(s) found.

   1. Test case: `find explorers`<br>
      Expected: Alex and Steve are displayed in the list. A message indicates 2 gamer(s) found.

   1. Test case: `find craft.net`<br>
      Expected: Alex and Steve are displayed in the list. A message indicates 2 gamer(s) found.

   1. Test case: `find usa`<br>
      Expected: Alex and Steve are displayed in the list. A message indicates 2 gamer(s) found.

   1. Test case: `find builder`<br>
      Expected: Alex and Steve are displayed in the list. A message indicates 2 gamer(s) found.

   1. Test case: `find na`<br>
      Expected: Only Alex is displayed in the list. A message indicates 1 gamer(s) found.

   1. Test case: `find 987`<br>
      Expected: Only Steve is displayed in the list. A message indicates 1 gamer(s) found.

   1. Test case: `find alex steve`<br>
      Expected: No gamer is displayed in the list. A message indicates no gamers were found.

   1. Test case: `find Sean`<br>
      Expected: No gamer is displayed in the list. A message indicates no gamers were found.

1. Finding gamers with specific prefixes

   1. Prerequisites: There are three gamers in the list (Alex with gamertag `CraftyAlex`, email `alex@craft.net`,
      group `Explorers`, server `srv1.gamehub.net`, country `USA`, region `NA`, note `builder`,
      Steve with phone `987654`, email `steve@craft.net`, group `Explorers`, server `srv2.gamehub.net`,
      country `USA`, note `builder pro`, and Herobrine with favourite status set).

   1. Test case: `find name/Alex`<br>
      Expected: Only Alex is displayed in the list. A message indicates 1 gamer(s) found.

   1. Test case: `find name/aLeX`<br>
      Expected: Only Alex is displayed in the list. A message indicates 1 gamer(s) found.

   1. Test case: `find gamertag/Craft`<br>
      Expected: Only Alex is displayed in the list. A message indicates 1 gamer(s) found.

   1. Test case: `find phone/987`<br>
      Expected: Only Steve is displayed in the list. A message indicates 1 gamer(s) found.

   1. Test case: `find email/alex@craft.net`<br>
      Expected: Only Alex is displayed in the list. A message indicates 1 gamer(s) found.

   1. Test case: `find group/Explorers`<br>
      Expected: Alex and Steve are displayed in the list. A message indicates 2 gamer(s) found.

   1. Test case: `find server/srv1.gamehub.net`<br>
      Expected: Only Alex is displayed in the list. A message indicates 1 gamer(s) found.

   1. Test case: `find server/gamehub`<br>
      Expected: Alex and Steve are displayed in the list. A message indicates 2 gamer(s) found.

   1. Test case: `find country/USA`<br>
      Expected: Alex and Steve are displayed in the list. A message indicates 2 gamer(s) found.

   1. Test case: `find region/NA`<br>
      Expected: Only Alex is displayed in the list. A message indicates 1 gamer(s) found.

   1. Test case: `find note/build`<br>
      Expected: Alex and Steve are displayed in the list. A message indicates 2 gamer(s) found.

   1. Test case: `find favourite/`<br>
      Expected: Only favourite gamers are displayed in the list. A message indicates the number of gamers found.

   1. Test case: `find name/Alex country/USA`<br>
      Expected: Only Alex is displayed in the list. A message indicates 1 gamer(s) found.

   1. Test case: `find group/Explorers country/USA`<br>
      Expected: Alex and Steve are displayed in the list. A message indicates 2 gamer(s) found.

1. Invalid find inputs

   1. Prerequisites: None.

   1. Test case: `find name/`<br>
      Expected: Error indicating the search keyword for `name/` cannot be empty.

   1. Test case: `find phone/9123abcd`<br>
      Expected: Error indicating the phone search keyword contains invalid characters.

   1. Test case: `find email/not#email`<br>
      Expected: Error indicating the email search keyword contains invalid characters.

   1. Test case: `find gamertag/Bad Tag`<br>
      Expected: Error indicating the gamertag search keyword contains invalid characters.

   1. Test case: `find group/Explorers2`<br>
      Expected: Error indicating the group search keyword contains invalid characters.

   1. Test case: `find server/srv#1`<br>
      Expected: Error indicating the server search keyword contains invalid characters.

   1. Test case: `find country/U$A`<br>
      Expected: Error indicating the country search keyword contains invalid characters.

   1. Test case: `find note/build!`<br>
      Expected: Error indicating the note search keyword contains invalid characters.

   1. Test case: `find region/XX`<br>
      Expected: Error indicating the region search keyword is invalid.

   1. Test case: `find alex name/Steve`<br>
      Expected: Error indicating global and specific searches cannot be combined.

   1. Test case: `find name/Alex name/Steve`<br>
      Expected: Error indicating duplicate prefixes are not allowed.

   1. Test case: `find favourite/yes`<br>
      Expected: Error indicating the `favourite/` prefix does not take a value.

   1. Test case: `find aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa`<br>
      Expected: Error indicating global search keyword input cannot exceed 50 characters.

   1. Test case: `find`<br>
      Expected: Error indicating invalid command format for `find`.

### Sorting gamer contacts

### Clearing all gamer contacts

### Creating a group (TBA)

### Editing a group (TBA)

### Deleting a group (TBA)

### Adding a gamer to a group (TBA)

### Listing groups (TBA)

### Dealing with data
1. Saving data to `contacts.json`

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases ... }_
