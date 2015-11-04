avro-codegen
============

[![Join the chat at https://gitter.im/Nitro/avro-codegen](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/Nitro/avro-codegen?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Scala code generation from Avro schemas. Generates code similar to ScalaPB.

Notably, the generated code for data encapsulation is in the form of case classes. The data representation is completely immutable.

All generated code adheres to the `GeneratedMessageCompanion` type class. This ensures that there exists decode and encode functionality.

working code generation for types:
* enums
* records
* scalar fields (for all types except for byte arrays)
* union fields via shapeless coproducts (for unions not containing NULL)
* optional union fields via shapeless coproducts (for unions containing more than 2 types, one of which is NULL)
* optional fields (for unions containing 2 types, one of which is NULL)
* arrays
* maps
* byte arrays

features:
* generated org.scalacheck.Gen[_] instances for generated enums, records

not implemented:
* fixed-length fields
* Generating code from inter-dependent schemas defined in multiple files (currently only handles this case when everything is in the same file).

Subprojects
==================
* runtime: runtime dependencies
* codegen: generates .scala files from avro schemas. Currently hardcoded to generate Out.scala in sandbox from the avro schemas in the example directory.
* proptest: generates random schemas, generates scala code for the schemas, tests the generated scala code by serializing and deserializing random message instances

To Use
============

There are two ways to use this project. The first is in creating `case class` instances from Avro schemas. The second is in interacting with the generated code. In practice, it's common for a project to do a little bit of both.

For the first use case:

add this to `project/plugins.sbt`
```
addSbtPlugin("com.gonitro" % "avro-codegen-compiler" % "X.Y.Z")
```

where `X.Y.Z` is the most recent version.

Add your avro schemas to `src/main/avro` with the `.avsc` extension. Generated scala classes will be created in `target/scala-2.11/src_managed/main/generated_avro_classes/`.

For the second use case:

When using code generated by this plugin, it is necessary to include the runtime dependency (which includes lots of goodies -- notably the serialization type class `GeneratedMessage`). Therefore, include the following in your `build.sbt`

```
libraryDependencies ++= Seq("com.gonitro" %% "avro-codegen-runtime" % "X.Y.Z")
```

