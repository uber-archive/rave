Changelog
=========

Version 2.1.0
----------------------------
2018-04-18

- Bump Android Support annotations to 27.1.1.
- New: Rave now supports `@LongDef`.

Version 2.0.0
----------------------------
2017-05-25

- New: Supports any `@NonNull` and `@Nullable` annotations in addition to Android support annotations.
- New: Adds `Rave.validateIgnoreUnsupported()` API to ignore `UnsupportedObjectException` when performing validation.
- Use Google's Maven for support library artifacts and require consumers do the same.
- RAVE is now annotated with JSR-305 nullability annotations.

Version 1.0.1
----------------------------
2017-05-04

- Fix: Do not generate validation for void return types.

Version 1.0.0
----------------------------
2017-05-03

- New: Add `@Excluded` API to facilitate ignoring methods in `@Validated` classes.
- Remove: `ExclusionStrategy` has been removed in favor of `@Excluded` which is more efficient.
- Improved validation for Maps: key and values are now validated.
- Improved validation for Collections: all elements of a collection are now validated, not just the collection object.
- Bump Android Support annotations down to avoid transitively bumping it in consuming projects.
- Unannotated methods are now validated as `@Nullable` by default.

Version 0.7.0
----------------------------
2017-04-22

- New: Add `@Validator` to enable nullness assumptions for unannotated methods.
- New: Add `@Generated` to generated validator classes.
- New: Add sample module to demonstrate how to use RAVE for disk and network validation with Retrofit 2.
- Fix: Improve multithreading performance when performing validation using `Rave.validate()`

Version 0.6.0
----------------------------
2016-04-22

Initial release
