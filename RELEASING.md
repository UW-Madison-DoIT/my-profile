# Releasing

## Ensure CHANGELOG reflects changes and prospective release

Ensure changes since prior release are reflected in CHANGELOG.

Determine next version number depending on the nature of those changes.

(Semantic versioning:

+ Breaking changes yield MAJOR version number bump 2.7.3 --> 3.0.0
+ Backwards-compatible new or enhanced features yield MINOR bump 2.7.3 -> 2.8.0
+ Backwards-compatible bug fixes yield PATCH bump 2.7.3 -> 2.7.4

)

## Ensure desired version is pushed to remote `master`

The release process does not allow local difference from `master`; it's the tip
of `master` that will be released.

## mvn release:prepare

`mvn release:prepare`

## Push the changes

Push the 2 (two) commits generated by `release:prepare`.

Push the 1 (one) tag generated by `release:prepare`.

(`release:perform` requires checking these out cleanly from remote.)

## mvn release:perform

`mvn release:perform`
