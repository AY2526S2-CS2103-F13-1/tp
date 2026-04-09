---
  layout: default.md
  title: "Jihun's Project Portfolio Page"
---

### Stuff that I worked on

**Difficulty Level**

BlockBook required more effort than AB3 because it manages a `Gamer` with many optional attributes and a separate `Group` entity, in contrast to AB3's single-entity model with mostly compulsory fields. Every feature had to handle missing optional values gracefully, which multiplied the work for parsing, validation, UI, and tests.

**Main Contributions**

* **Sort command (major feature)**: Designed and implemented multi-attribute sorting with alias support, defensive parsing, and special-case ordering for favourites and groups. The sort is session-based and does not affect storage.
* **UI overhaul**: Redesigned the entire UI into a Minecraft-themed look, including a new gamer card layout that surfaces all attributes at once and a custom favourite badge.
* **Add command hardening**: Added the `GamerTag` model, duplicate-gamertag prevention, and supporting tests.
* **Documentation**: Authored the UG and DG sections, manual test plan, and sequence diagram for the sort feature. Also edited the Model and Logging sections of the DG.

**Challenges Faced**

* The sort feature went through several refactor rounds as requirements evolved (group sorting, aliases, finer-grained error messages). Keeping the test suite green across each iteration was the most time-consuming part.
* Designing the Minecraft UI theme was time-consuming, as it involved many rounds of team discussion on how the UI should look. I took on the bulk of the UI implementation work, which made it a significant individual effort on top of the collaborative design process.
