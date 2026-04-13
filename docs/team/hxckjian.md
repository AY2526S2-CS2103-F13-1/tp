---
  layout: default.md
  title: "Hock Jian's Project Portfolio Page"
---

### Stuff that I worked on

#### Difficulty Level

I would consider this contribution to be of moderate-high difficulty.

One major difficulty of implementing the `add` feature was that it was tightly coupled with many other components of the application.

#### Stuff that I worked on

- Implemented the `add` feature to support creating gamer contacts with a required gamertag and multiple optional gamer-specific fields.
- Developed and refined `AddCommandParser` so the add command could correctly parse user input and reject malformed commands.
- Extended parsing and validation support for attributes such as phone, email, server, country, region, and note.
- Added normalization logic for user input, such as standardizing names, countries, and region formats.
- Implemented custom phone number validation with more detailed checks and clearer error handling.
- Extended storage support so newly added gamer fields could be saved and loaded correctly.
- Updated the `Gamer` model and related classes to support the expanded set of contact details.
- Added and expanded tests for parser behavior, storage behavior, model logic, and field validation.
- Updated the User Guide and Developer Guide to document the add feature, expected inputs, and related behavior.

#### Challenges Faced

One key challenge was input validation. The application had to reject invalid values while still remaining user-friendly. This was especially relevant for phone numbers, where I worked on stricter validation rules and clearer error reporting rather than relying on only a basic format check. Handling edge cases properly was important so that invalid inputs would fail predictably and informatively.
