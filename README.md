# Android Playground

A place to integrate, test, and demonstrate various things. See the [project's code documentation](https://jjerrell.github.io/AndroidPlaygroundPages/).

## Application - Capabilities

### Firebase

To use or preview any Firebase implementations, you'll need to add update `app/google-services.json` file with your Firebase application parameters.

Run the following command to prevent git from tracking your changes to the file:

```shell
git update-index --skip-worktree app/google-services.json
```

If you make changes which need to be committed, undo the above with this command:

```shell
git update-index --no-skip-worktree app/google-services.json
```

After committing the templated file, rerun the first command and update your local file with valid values.

### Koin

Multi-module dependency injection is integrated using various Koin "modules" exposed by the individual application modules. See [PlaygroundApplication](app/src/main/java/dev/jjerrell/android/playground/PlaygroundApplication.kt) for the main configuration.

### Timber

Debug logging is implemented via Timber which is also configured in [PlaygroundApplication](app/src/main/java/dev/jjerrell/android/playground/PlaygroundApplication.kt).

Timber is currently implemented for:

- General debugging
- The Logging Demo feature which uses a local `Timber.Tree` implementation to simply redirect calls to `Log.*` methods
- Koin logging

## Application - Demos

### Logging

Demonstrates various ways to log messages using the standard library in a Jetpack Compose MVVM architecture.

## Application - Features

## About - `feture-about`

Simple feature to describe the application and celebrate third-party contributions.

## Disclaimers

The Android robot is reproduced or modified from work created and shared by Google and used according to terms described in the Creative Commons 3.0 Attribution License.
