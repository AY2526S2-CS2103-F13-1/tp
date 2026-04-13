---
  layout: default.md
  title: "YingWen's Project Portfolio Page"
---

### Stuff that I worked on

#### Difficulty Level

I would consider this contribution to be of moderate-high difficulty.

The main challenge was implementing command-level behavior (`favourite`, `edit`, `list`) while keeping parser,
model, UI, and message behavior consistent with other advanced features.

#### Stuff that I worked on

- Implemented and refined the `favourite` and `unfavourite` commands, including success/failure flows, duplicate-state
  checks (already favourite / already unfavourite), and clearer user-facing messages.
- Implemented `FavouriteCommandParser` support with improved invalid index handling and command-format validation.
- Added command aliases (`fav` / `unfav`) and integrated both commands into `BlockBookParser`.
- Improved `edit` command execution and validation flow, including index validation, duplicate gamertag checks, and
  standardized success output formatting.
- Refined `EditCommandParser` to provide clearer error behavior for invalid indices and malformed preambles.
- Added reusable message-formatting helpers in `Messages` (e.g., compact contact summaries) to keep command outputs
  consistent across features.
- Updated list interaction by adding `clearSort()` support at model level and applying it in `list` so users can
  return to insertion order after sorting.
- Designed and implemented comprehensive tests for `edit`, `list`, and `favourite`/`unfavourite` across both parser
  and command layers.
- Updated relevant documentation related to `favourite`, `unfavourite`, `edit`, and `list`.

#### Challenges Faced

One key challenge was balancing strict validation with user-friendly error messages. For example, index-related errors
had to be handled consistently between parser-level failures and command-level failures, while still preserving clear
feedback for users.

Another challenge was cross-feature integration. Changes to list or output formatting could easily affect command
results, tests, and UI behavior, so I had to adjust implementations carefully to keep behavior consistent end-to-end.
