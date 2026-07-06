# Offline KJV Bible Reader — Android App

Browse the Bible like a real book: pick a book → pick a chapter → read
it, with Previous/Next buttons that flow naturally from one chapter
into the next (even across books). Fully offline, works on Android
5.0+.

This is a **separate app** from your Bible Search app (different
package name), so both can be installed on your phone at the same time.

## Step 1 — Build the reading database
You already have your `kjv.json` file from before. In that same
folder on your PC, save `build_reading_db.py` (included here) next to
it, then run:

```
python3 build_reading_db.py kjv.json
```

This creates `app/src/main/assets/kjv_reading.db` — check the output
says something like "Imported 31102 verses across 66 books."

## Step 2 — Create a new GitHub repo
Same as last time:
1. https://github.com/new
2. Name it `bible-reader-app`, make it **Public**
3. Create repository (leave it empty)

## Step 3 — Upload everything
Extract this zip, then drag **everything inside the extracted
folder** into the GitHub upload box (Add file → Upload files).

Based on last time, your browser will likely nest everything one
level deep (e.g. `bible-reader-app/BibleReaderApp/app/...` instead of
`bible-reader-app/app/...`). That's fine — the workflow file already
expects this and is pre-configured for it. If it does NOT nest this
time (i.e. `app` ends up directly in the repo root), tell Claude and
the workflow file will need one small tweak.

Also upload your `kjv_reading.db` from Step 1 into the
`app/src/main/assets/` folder (wherever it ends up based on the
nesting).

Commit.

## Step 4 — Build and install
1. Actions tab → wait for the green checkmark
2. Download `bible-reader-app-debug-apk` from Artifacts
3. Install:
```
adb install -r "app-debug.apk"
```
(This is a different app from Bible Search, so no uninstall needed —
they'll sit side by side on your phone.)

## What you get
- **Book list** — all 66 books in proper Bible order, Old Testament
  first then New Testament, each showing its chapter count
- **Chapter list** — tap a book, see all its chapters
- **Reading view** — full chapter text with verse numbers, plus
  Previous/Next buttons that automatically roll into the next or
  previous book at the start/end of a book

## If something crashes
Same debugging trick as before:
```
adb logcat -c
```
Reproduce the crash on your phone, then:
```
adb logcat -d *:E > "%USERPROFILE%\Desktop\crash.txt"
```
Open that file, search for `FATAL`, paste that section to Claude.
