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

1. When `Logic` is called upon to execute a command, it is passed to a `BlockBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
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

### Add gamer feature

The Add feature allows users to create a new gamer contact in BlockBook by entering an `add` command with a required gamertag and optional fields such as name, phone number, email address, server, country, region, and note. This feature allows users to record gamer contacts efficiently while ensuring that the updated data is saved after a successful add operation.

The sequence diagram below illustrates the main interactions that take place when an `add` command is executed.

<puml src="diagrams/AddCommandSequenceDiagram.puml" width="300" />

**User enters command:**  
The process begins when the user types an `add` command into the UI.

**UI passes command to Logic:**  
After receiving the command, the UI forwards it to the Logic component for processing.

**Logic parses the command:**  
Within the Logic component, the command is recognized as an `add` command. The input is then parsed internally, where the command arguments are validated and converted into the corresponding gamer attribute objects before an `AddCommand` is created.

**Logic updates the Model:**  
The `AddCommand` is executed using the current Model. During this step, BlockBook checks whether a gamer with the same gamertag already exists. If no duplicate is found, the new gamer contact is added to the Model.

**Logic saves the updated data:**  
Once the gamer has been added successfully, the Logic component calls the Storage component to persist the updated BlockBook data.

**Storage writes data to file:**  
The Storage component saves the updated data to file so that the newly added gamer contact is retained after the application closes.

**Logic returns the result:**  
After the save operation is completed, Logic produces a `CommandResult` describing the outcome of the operation.

**UI shows the outcome:**  
Finally, the UI displays the result to the user, such as a success message when the gamer is added or an error message if the operation fails.


## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

## **Future Developments**
In the future, we plan to implement the following features and enhancements to further improve the functionality and user experience of BlockBook.

### Planned Enhancements
These are some enhancements that we plan to implement in the future.

#### Command History Log
**Purpose**: Allows the user to view a history of previously sent commands
**Outputs**: Commands are added to a log file

#### Profile Picture Support
**Purpose**: Allows the user to upload an image for each gamer contact card in the contacts via a button in GUI/(Or via CLI add?).
**Acceptable values**: IMAGESRC → Compulsory, path to the image file source.
**Error messages**:
- Invalid file format: “BlockBook only supports a valid .png/.jpg file. Please choose another file. ”
- Missing/Corrupted file: “Profile picture file cannot be found or is corrupted. Reverting to default image. ”
  Outputs:

**Success**: "Profile picture updated to the image located at {IMAGESRC}"
**Possible errors**:
- Invalid file format 
- Missing file 
- Corrupted file

#### Theme Customization
Allow the user to customize the theme of the app (e.g., light mode, dark mode, etc.) via a `theme` command in CLI or a button in GUI.
The user can choose from predefined themes or create their own custom theme by specifying colors for different UI elements.
**Purpose**: Allows the user to customize the theme of the app (e.g., light mode, dark mode, etc.)
**Acceptable values**: THEME → Compulsory, the theme to set the app to. Possible values include "light", "dark", and "custom".

#### Better `contact.json` Handling
The current implementation that handles `contact.json` will render the entire file invalid once a single entry has an error.
Improve the handling of the `contact.json` file to allow valid entries to be shown in BlockBook while ignoring invalid entries.

### Known Bugs
These are some known bugs that we have identified but have not yet fixed.

**Validation of Invalid Prefixes**: For example, entering `edit 1 region/na er/asd` returns `invalid region` instead of `invalid command format`. Updating the parser implementation to handle this will solve the issue.

--------------------------------------------------------------------------------------------------------------------

## **Requirements**

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

#### UC01 - Add a Gamer Contact

**MSS**

1.  User chooses to add a new gamer contact.
2.  BB prompts for the contact details required to create the gamer contact.
3.  User provides the required gamertag and any optional fields.
4.  BB validates the provided details.
5.  BB saves the new gamer contact and displays a success message with the updated contact list.

Use case ends.

**Extensions**

4a. The required gamertag field is missing.

- 4a1. BB displays an error message showing the correct command format.
- 4a2. User re-enters the add command with corrected input.
- Use case resumes at step 1.

4b. One or more specified fields contain invalid values.

- 4b1. BB displays an error message indicating that the input is invalid.
- 4b2. User re-enters the add command with corrected input.
- Use case resumes at step 1.

4c. A contact with the same gamertag already exists.

- 4c1. BB displays an error message indicating that the gamertag is already in use.
- 4c2. User re-enters the add command with a different gamertag.
- Use case resumes at step 1.

#### UC02 - List All Gamer Contacts

**MSS**

1. User chooses to view all saved contacts.
2. BB retrieves all entries.
3. BB displays a list of all contacts with their basic details.

Use case ends.

**Extensions**

2a. The contact list is empty.

- 2a1. BB informs the user that no contacts are currently stored.
- Use case ends.

#### UC03 - Favourite a Contact

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

#### UC04 - Add Profile Picture to Contact

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

#### UC05 - Add Note to Contact (Can be deleted)

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

#### UC06 - Sort Gamer Contacts

**MSS**

1. User requests to sort contacts by one or more attributes.
2. BB sorts and displays the currently displayed contacts by the specified attributes in priority order.

Use case ends.

**Extensions**

1a. User does not specify any attributes.

- 1a1. BB uses gamertag as the default sort attribute.
- Use case resumes from step 2.

1b. User specifies one or more invalid attributes.

- 1b1. BB displays an error message indicating that one or more sort attributes are invalid.
- Use case ends.

1c. User specifies duplicate attributes.

- 1c1. BB displays an error message indicating that duplicate sort attributes are not allowed.
- Use case ends.

2a. There are no currently displayed contacts to sort.

- 2a1. BB informs the user that there are no contacts to sort.
- Use case ends.

#### UC07 - Edit a Gamer Contact (Change of name from Update)

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

#### UC08 - Delete a Gamer Contact
**Preconditions**
- User knows the index of the contact they wish to delete (e.g. having previously executed UC02)
- User has at least one contact saved in BlockBook.

**MSS**
1. User requests to delete one or more contacts.
2. BB deletes the contacts specified and displays a confirmation message.
Use case ends.

**Extensions**
1a. User enters an invalid index.
- 1a1. BB displays an error message.
- Use case ends.

#### UC09 - View a Gamer Contact

**Preconditions**
- User has a list of gamer contacts displayed and knows the index of the contact to view (e.g. after UC02).

**MSS**

1. User requests to view a gamer contact by its index in the current list.
2. BB displays the contact's full profile details.

Use case ends.

**Extensions**

1a. User enters a non-numeric index.

- 1a1. BB displays an error message.
- Use case ends.

1b. Index is out of range.

- 1b1. BB displays an error message.
- Use case ends.

#### UC10 - Find Gamer Contacts

#### UC11 - Clear all Gamer Contacts
**Preconditions**
- User has at least one contact saved in BlockBook.

**MSS**
1. User requests to clear all contacts.
2. BB prompts the user for confirmation.
3. User confirms.
4. BB deletes all contacts and displays a success message.

**Extensions**
2a. User does not follow through with confirmation. 
- Use case ends
3a. User used the wrong confirmation input.
- 3a1. BB displays an error message and prompts the user for confirmation again.
- Use case resumes from step 3.

#### UC12 - Show Help
**MSS**
1. User requests to view the help message.
2. BB displays a help message that includes a summary of all available commands and their usage.

Use case ends.

#### UC13 - Create a Group

**MSS**
1. User requests to create a group with a group name.
2. BB creates the group and displays a success message.

Use case ends.

**Extensions**
1a. The group name is invalid.
- 1a1. BB displays an error message.
- Use case ends.

1b. A group with the same name already exists.
- 1b1. BB displays an error message.
- Use case ends.

#### UC14 - Edit a Group

**MSS**
1. User requests to edit a group by its index and provides a new group name.
2. BB updates the group name and displays a success message.

Use case ends.

**Extensions**
1a. User enters an invalid index.
- 1a1. BB displays an error message.
- Use case ends.

1b. The group name is invalid.
- 1b1. BB displays an error message.
- Use case ends.

1c. A group with the same name already exists.
- 1c1. BB displays an error message.
- Use case ends.

#### UC15 - Delete a Group

**MSS**
1. User requests to delete a group by its index.
2. BB prompts the user with a confirmation code and repeats the required delete format.
3. User confirms by entering the group index and confirmation code.
4. BB deletes the group and removes it from all associated gamers.
5. BB displays a success message.

Use case ends.

**Extensions**
1a. User enters an invalid index.
- 1a1. BB displays an error message.
- Use case ends.

2a. User does not follow through with confirmation.
- Use case ends.

3a. User used the wrong confirmation input.
- 3a1. BB displays an error message and prompts the user for confirmation again with a new code.
- Use case resumes from step 3.

#### UC16 - Add a Gamer to a Group

**MSS**
1. User requests to add a gamer to a group by providing the gamer index and group index.
2. BB adds the gamer to the group and displays a success message.

Use case ends.

**Extensions**
1a. User enters an invalid index.
- 1a1. BB displays an error message.
- Use case ends.

1b. The gamer is already in the group.
- 1b1. BB displays an error message.
- Use case ends.

#### UC17 - Remove a Gamer from a Group

**MSS**
1. User requests to remove a gamer from a group by providing the gamer index and the gamer's group index.
2. BB removes the gamer from the group and displays a success message.

Use case ends.

**Extensions**
1a. User enters an invalid index.
- 1a1. BB displays an error message.
- Use case ends.

#### UC18 - List all Groups

**MSS**
1. User requests to list all groups.
2. BB displays the list of groups.

Use case ends.

**Extensions**
1a. The group list is empty.
- 1a1. BB informs the user that no groups are currently stored.
- Use case ends.

#### UC19 - View a Group

**MSS**
1. User requests to view a group by its index.
2. BB displays the gamers associated with that group.

Use case ends.

**Extensions**
1a. User enters an invalid index.
- 1a1. BB displays an error message.
- Use case ends.

2a. There are no gamers in the group.
- 2a1. BB displays a message indicating there are no associated gamers and leaves the current list unchanged.
- Use case ends.

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

## **Instructions for manual testing**

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
1. Typing `help` in the command box and pressing Enter should display a help message that includes a summary of all available commands and their usage.
2. A new window should pop up showing the same help message.

### Adding a gamer contact

1. Valid inputs

   i. Prerequisites: Launch the application. The contact list is visible.

   ii. Test case: `add g/Steve123`  
   Expected: A new gamer with gamertag `Steve123` is added successfully.

   iii. Test case: `add g/Alex99 n/Alex`  
   Expected: A new gamer with gamertag `Alex99` and name `Alex` is added successfully.

   iv. Test case: `add g/BuilderPro p/91234567 e/builder@example.com`  
   Expected: A new gamer with gamertag `BuilderPro`, phone number `91234567`, and email `builder@example.com` is added successfully.

   v. Test case: `add g/NetherKing s/mc.example.net c/Singapore r/ASIA note/Friendly player`  
   Expected: A new gamer is added successfully with the server, country, region, and note fields stored correctly.

   vi. Test case: `add g/Herobrine n/Herobrine p/99999 e/brine@gmail.com s/127.0.0.1:8080 c/Singapore r/ASIA note/I hate steve`  
   Expected: A new gamer with all provided fields is added successfully.

2. Invalid inputs

   i. Prerequisites: Launch the application. The contact list is visible.

   ii. Test case: `add n/Steve`  
   Expected: No gamer is added. An error message indicates invalid command format because the required `gamertag/` prefix is missing.

   iii. Test case: `add g/Bad Tag`  
   Expected: No gamer is added. An error message indicates that the gamertag is invalid.

   iv. Test case: `add g/FreshUser1 e/not-an-email`  
   Expected: No gamer is added. An error message indicates that the email is invalid.

   v. Test case: `add g/FreshUser2 p/abcde`  
   Expected: No gamer is added. An error message indicates that the phone number is invalid.

   vi. Test case: `add g/FreshUser3 c/Sing@pore`  
   Expected: No gamer is added. An error message indicates that the country is invalid.

   vii. Test case: `add g/FreshUser4 r/XYZ`  
   Expected: No gamer is added. An error message indicates that the region is invalid.

   viii. Test case: `add g/FreshUser5 s/server#1`  
   Expected: No gamer is added. An error message indicates that the server is invalid.

   ix. Test case: `add g/FreshUser6 n/Steve n/Stephen`  
   Expected: No gamer is added. An error message indicates that duplicate prefixes are not allowed.

   x. Test case: `add g/UniqueSteve123` followed by `add g/UniqueSteve123`  
   Expected: The first command adds the gamer successfully. The second command does not add a gamer. An error message indicates that the gamertag is already used by someone in BlockBook.

   xi. Test case: `add hello g/FreshUser7`  
   Expected: No gamer is added. An error message indicates invalid command format because extra preamble text is not allowed.

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

1. Viewing a gamer by index

   1. Prerequisites: List all gamers using the `list` command. There is at least 1 gamer in the list.

   1. Test case: `view 1`<br>
      Expected: The command result displays the full details of the first gamer in the currently displayed list. A pop-up window containing the gamer's information is shown. The list remains unchanged.

   1. Test case: `v 1`<br>
      Expected: Same result as `view 1`.

1. Viewing from a filtered list

   1. Prerequisites: Filter the list to a single gamer using `find name/Alex`.

   1. Test case: `view 1`<br>
      Expected: The command result displays the full details of the filtered gamer given by the list index. A pop-up window containing the gamer's full information is shown. The list remains filtered.

1. Invalid index

   1. Prerequisites: The list contains 1 gamer.

   1. Test case: `view 2`<br>
      Expected: Error indicating index is out of range.

   1. Test case: `view 0`<br>
      Expected: Error indicating index is out of range.

   1. Test case: `view -1`<br>
      Expected: Error indicating index is out of range.

1. Invalid command format

   1. Prerequisites: None.

   1. Test case: `view`<br>
      Expected: Error indicating invalid command format for `view`.

   1. Test case: `view one`<br>
      Expected: Error indicating invalid command format for `view`.

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

1. Sorting gamers by default (gamertag)

   1. Prerequisites: List all gamers using the `list` command. Multiple gamers in the list.

   1. Test case: `sort`<br>
      Expected: Displayed contacts are sorted alphabetically by gamertag (case-insensitive). Status message shows "Sorted all contacts by gamertag (default)."

1. Sorting gamers by a single attribute

   1. Prerequisites: List all gamers using the `list` command. Multiple gamers in the list, some with missing optional fields.

   1. Test case: `sort name/`<br>
      Expected: Displayed contacts are sorted alphabetically by name (case-insensitive). Contacts with no name are placed at the end. Status message shows "Sorted all contacts by name."

   1. Test case: `sort favourite/`<br>
      Expected: Displayed contacts with favourite status appear before non-favourite contacts. Status message shows "Sorted all contacts by favourite."

1. Sorting gamers by multiple attributes

   1. Prerequisites: List all gamers using the `list` command. Multiple gamers in the list.

   1. Test case: `sort country/ name/`<br>
      Expected: Displayed contacts are sorted by country first, then by name for contacts with the same country. Status message shows "Sorted all contacts by country, name."

1. Sorting gamers using attribute aliases

   1. Prerequisites: List all gamers using the `list` command. Multiple gamers in the list.

   1. Test case: `sort n/`<br>
      Expected: Same result as `sort name/`. Displayed contacts are sorted alphabetically by name.

1. Sorting gamers with invalid input

   1. Prerequisites: List all gamers using the `list` command. Multiple gamers in the list.

   1. Test case: `sort xyz/`<br>
      Expected: No sorting occurs. Error message indicates invalid attribute detected.

   1. Test case: `sort name/ name/`<br>
      Expected: No sorting occurs. Error message indicates duplicate attribute detected.

   1. Test case: `sort abc`<br>
      Expected: No sorting occurs. Error message indicates invalid command format.

1. Sorting with no contacts displayed

   1. Prerequisites: There are no contacts in BlockBook (e.g., start with empty data or run `clear` with confirmation). Do not use a `find` command with no matches for this precondition, because `find` retains the current displayed list when no results are found.

   1. Test case: `sort`<br>
      Expected: No sorting occurs. Error message indicates there are no contacts to sort.

### Clearing all gamer contacts
1. Clearing all contacts

   1. Prerequisites: There is at least 1 contact in BlockBook.

   1. Test case: `clear` followed by confirming the action<br>
      Expected: All contacts are deleted. A success message is shown.
   2. Test case: `clear` followed by not confirming the action (e.g. entering any other command)<br>
      Expected: No contacts are deleted. The contact list remains unchanged. The next command entered executes as expected.
   3. Test case: `clear` followed by invalid confirmation input (e.g., `clear 123` when the confirmation message is `clear 246`)<br>
      Expected: No contacts are deleted. The confirmation code changes. The contact list remains unchanged.
   4. Test case: `clear` followed by `clear`<br>
      Expected: The confirmation code for the first `clear` command is different from the second `clear` command. No contacts are deleted.

### Creating a group (TBA)

### Editing a group (TBA)

### Deleting a group (TBA)

### Adding a gamer to a group (TBA)

### Listing groups (TBA)

### Dealing with data
1. Saving data to `contacts.json`

   1. Test case: Add a gamer (e.g., `add gamertag/steve1`).<br>
      Expected: `contacts.json` is updated with the new gamer entry.

1. Dealing with missing/corrupted data files

   1. Missing data file

      1. Prerequisites: Delete or rename `contacts.json`.

      1. Expected: BlockBook starts with an empty list. The result will display that no file was found and that BlockBook will be starting with an empty Gamer Contact list instead.
       A new `contacts.json` file is created at the specified path after a command that invokes saving is executed or on app exit.

   1. Corrupted data file

      1. Prerequisites: Edit `contacts.json` to an invalid JSON (e.g., remove a closing brace).

      1. Expected: BlockBook starts with an empty list. The result will display that data could not be loaded from the file and that BlockBook will be starting with an empty Gamer Contact list instead. 
         A new `contacts.json` file will be created to replace the corrupted `contacts.json` file after a command that invokes saving is executed or on app exit.
